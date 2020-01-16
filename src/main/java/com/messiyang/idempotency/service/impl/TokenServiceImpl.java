package com.messiyang.idempotency.service.impl;


import com.messiyang.idempotency.common.Constant;
import com.messiyang.idempotency.common.ResponseCode;
import com.messiyang.idempotency.common.ServerResponse;
import com.messiyang.idempotency.exception.ServiceException;
import com.messiyang.idempotency.service.TokenService;
import com.messiyang.idempotency.util.JedisUtil;
import com.messiyang.idempotency.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return ServerResponse.success(token.toString());
    }

    /**
     * 幂等性判断
     * @param request
     */
    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

    @Override
    public void checkAccessLimit(String key,int maxCount,int seconds) {
        //判断是否存在这个key，如果不存在表示第一次进入，访问次数加1，否则进入下一步的业务流程
        Boolean exists = jedisUtil.exists(key);
        if (!exists) {
            jedisUtil.set(key, String.valueOf(1), seconds);
        } else {
            int count = Integer.valueOf(jedisUtil.get(key));
            if (count < maxCount) {
                Long ttl = jedisUtil.ttl(key);
                if (ttl <= 0) {
                    jedisUtil.set(key, String.valueOf(1), seconds);
                } else {
                    jedisUtil.set(key, String.valueOf(++count), ttl.intValue());
                }
            } else {
                throw new ServiceException(ResponseCode.ACCESS_LIMIT.getMsg());
            }
        }
    }

}

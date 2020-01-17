package com.messiyang.idempotency.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 9035584359424322830L;

    /** ID **/
    private Long id;
    /**
     * userId
     */
    private Long userId;
    /**
     * 日志类型:1登录 2登出
     */
    private Integer type;
    /**
     * 日志描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 消息id
     */
    private String msgId;

}

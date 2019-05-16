package com.xia.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements EnumCode {

    WAIT((byte)0, "等待支付"),
    SUCCESS((byte)1, "支付成功"),
    ;

    private Byte code;

    private String message;

    PayStatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

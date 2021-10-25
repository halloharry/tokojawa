package com.test.tokoko.common.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -6233145663410668178L;

    private Integer code = 999;
    private String message = CommonMessage.ERROR;
    private T data;

    //overloading
    public BaseResponse(String message, T data){
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(String message){
        this.code = 400;
        this.message = message;
        this.data = null;
    }
}

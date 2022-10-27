package com.zsy.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zsy.common.enums.ResultMessageStatus;
import lombok.Data;

/**
 * @author 郑书宇
 * @create 2022/10/27 1:01
 * @desc
 */
@Data
@JsonInclude
public class ResultResponse {

    private static final ResultMessageStatus DEFAULT_SUCCESS=ResultMessageStatus.DEFAULT_SUCCESS;

    private static final ResultMessageStatus DEFAULT_ERROR=ResultMessageStatus.DEFAULT_ERROR;

    private static final ResultMessageStatus DEFAULT_FAIL=ResultMessageStatus.DEFAULT_FAIL;

    private int code;

    private String message;

    private Object data;

    private ResultResponse(){

    }

    private ResultResponse(Builder builder){
        this.code=builder.code;
        this.message=builder.message;
        this.data=builder.data;
    }

    public static ResultResponse success(){
        return new Builder()
                .code(DEFAULT_SUCCESS.getCode())
                .message(DEFAULT_SUCCESS.getMessage())
                .build();
    }

    public static ResultResponse success(Object data){
        return new Builder()
                .code(DEFAULT_SUCCESS.getCode())
                .message(DEFAULT_SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static ResultResponse success(ResultMessageStatus messageStatus,Object data){
        return new Builder()
                .code(messageStatus.getCode())
                .message(messageStatus.getMessage())
                .data(data)
                .build();
    }

    public static ResultResponse error(){
        return new Builder()
                .code(DEFAULT_ERROR.getCode())
                .message(DEFAULT_ERROR.getMessage())
                .build();
    }

    public static ResultResponse error(ResultMessageStatus messageStatus){
        return new Builder()
                .code(messageStatus.getCode())
                .message(messageStatus.getMessage())
                .build();
    }

    public static ResultResponse fail(){
        return new Builder()
                .code(DEFAULT_FAIL.getCode())
                .message(DEFAULT_FAIL.getMessage())
                .build();
    }

    public static ResultResponse fail(ResultMessageStatus messageStatus){
        return new Builder()
                .code(messageStatus.getCode())
                .message(messageStatus.getMessage())
                .build();
    }


    static class Builder{
        private int code;
        private String message;
        private Object data;

        public Builder code(int code){
            this.code=code;
            return this;
        }

        public Builder message(String message){
            this.message=message;
            return this;
        }

        public Builder data(Object data){
            this.data=data;
            return this;
        }

        public ResultResponse build(){
            return new ResultResponse(this);
        }
    }
}

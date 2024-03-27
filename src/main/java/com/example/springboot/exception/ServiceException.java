package com.example.springboot.exception;


import lombok.Getter;

/**
 * @author : siwei.fan
 * @date : 2024/3/2 11:57
 * @modyified By :
 */

@Getter
public class ServiceException extends RuntimeException {
    
    private final String code;
 
    public ServiceException(String msg){
  
     super(msg);
     this.code = "500";
    }
 
    public ServiceException(String code, String msg){
     super(msg);
     this.code = code;
    }
 
}

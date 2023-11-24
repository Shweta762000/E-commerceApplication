package com.lcwd.electronic.store.electronicStore.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Resource not Found!!");
    }
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}

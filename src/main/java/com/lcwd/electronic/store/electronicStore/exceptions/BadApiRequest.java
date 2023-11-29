package com.lcwd.electronic.store.electronicStore.exceptions;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String s) {
        super(s);
    }
    public BadApiRequest(){
        super("BadRequest!");
    }
}

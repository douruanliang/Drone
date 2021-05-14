package com.dourl.compose.asm;

import net.sf.cglib.proxy.Enhancer;

public class TestCGlib {

    public static void main(String[] args) {
        //子类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Library.class);
        enhancer.setCallback(new BuyBookInterceptor());
        Library targetLibrary = (Library)enhancer.create();
        targetLibrary.BuyBook();
        targetLibrary.toString();
    }
}

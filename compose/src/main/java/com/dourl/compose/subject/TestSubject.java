package com.dourl.compose.subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class TestSubject {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        //代理类要执行动作的调佣处理器
        InvocationHandler dynSubject = new DynamicSubject(realSubject);

        Class<?> c= realSubject.getClass();
        //代理类
        Subject subject = (Subject) Proxy.newProxyInstance(c.getClassLoader(),c.getInterfaces(),dynSubject);
        subject.request();
    }
}

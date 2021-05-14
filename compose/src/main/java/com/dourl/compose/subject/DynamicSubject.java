package com.dourl.compose.subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicSubject  implements InvocationHandler {

    private Object subject;

    public DynamicSubject(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备调用方法："+ method);
        method.invoke(subject,args);
        System.out.println("调用结束：");

        return null;
    }


}

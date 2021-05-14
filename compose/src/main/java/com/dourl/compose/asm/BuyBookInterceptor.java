package com.dourl.compose.asm;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * 代理对象
 */
public class BuyBookInterceptor implements MethodInterceptor {
    /**
     *
     * @param obj 目标对象
     * @param method 目标方法
     * @param args  方法参数
     * @param proxy CGlib方法代理对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("调佣前");
        Object result= proxy.invokeSuper(obj,args);
        System.out.println("调佣后");
        return result;
    }














}

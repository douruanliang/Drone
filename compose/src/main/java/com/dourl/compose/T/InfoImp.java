package com.dourl.compose.T;


/**
 * 定义 泛型接口的子类
 * @param <T> 泛型变量 T
 */
public class InfoImp<T,K,U> implements Info<T> {
    private T var;
    public InfoImp(T var) {
        this.var = var;
    }

    @Override
    public T getVar() {
        return this.var;
    }

    @Override
    public void setVar() {

    }
}

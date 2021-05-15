package com.dourl.compose.T;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型 只是一个 参数而已
 */
public class StaticFan {

    public static <T> void staticMethod(T t) {

    }

    /**
     * 与 普通方法不一样的 就是 返回值 加上<T> 来表示泛型变量
     * </>
     *
     * @param a
     * @param <T>
     */
    public <T> void Other(T a) {

    }

    public <T> String Other1(T s) {
        return "S";
    }

    public static <E, T> List<E> parseArray(String res, Class<T> object) {

        List<E> list = new ArrayList<E>();
        List<T> list1 = new ArrayList<>();
        return list;
    }

    public static <M> M[] fun1(M args[]) {

        return args;
    }

    /**
     * 其中 第一个<T> 是与传入的参数Class<T> 相对应，第二个 T 是返回值类型，代表
     * 方法返回类型T类型，有传入参数Class<T>决定</></>
     *
     * @param res
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T request(String res, Class<T> tClass) {
        return (T) ""; //举个例子
    }
}

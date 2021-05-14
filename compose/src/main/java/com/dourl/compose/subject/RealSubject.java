package com.dourl.compose.subject;


/**
 * 真实角色
 */
public class RealSubject  implements Subject{
    @Override
    public void request() {
        System.out.println("调用真实角色的方法");
    }
}

package com.dourl.compose.observer;

public class WhetherDisplay2 implements Observer {
    private float temperature;

    /**
     * 实例化自己的时候 直接绑定自己
     * @param subject
     */
    public WhetherDisplay2(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(float temp) {
      this.temperature = temp;
        display();
    }

    /**
     * 自己的业务逻辑
     */
    private void display(){
        System.out.println("display data  TYPE 2");
    }
}

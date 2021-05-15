package com.dourl.compose.observer;

public class Test {
    public static void main(String[] args) {
        //主题 - 天气
        WhetherSubject whetherSubject = new WhetherSubject();
        //订阅主题的 - 设备1
        WhetherDisplay whetherDisplay = new WhetherDisplay(whetherSubject);
        //订阅主题的 - 设备2
        WhetherDisplay2 whetherDisplay2 = new WhetherDisplay2(whetherSubject);

        whetherSubject.setTemperature(10f);
    }
}

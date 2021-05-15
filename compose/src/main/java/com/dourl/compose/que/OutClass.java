package com.dourl.compose.que;

import java.util.ArrayList;
import java.util.List;

public class OutClass {
    private int d1 = 100;
    public static String s = "";


    public <T extends Number> void test(T param){

        List<Number> list = new ArrayList<Number>();
        list.add(100);
        list.add(param);
    }

}

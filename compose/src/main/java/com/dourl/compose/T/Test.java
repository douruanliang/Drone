package com.dourl.compose.T;

public class Test {
    public static void main(String[] args) {
        InfoImp<String,String,Integer> infoImp = new InfoImp<String,String,Integer>("泛型");
        System.out.println("{}"+infoImp.getVar());  ;
    }
}

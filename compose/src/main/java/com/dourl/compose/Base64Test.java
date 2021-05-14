package com.dourl.compose;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Test {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        String name = "豆软亮";

        String encodeName = Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
        System.out.println("encodeName{}" + encodeName);
        String decodeName = new String(Base64.getDecoder().decode(encodeName));
        System.out.println("decodeName{}" + decodeName);

        Thread.currentThread();
    }
}

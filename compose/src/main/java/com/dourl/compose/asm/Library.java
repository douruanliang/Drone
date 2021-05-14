package com.dourl.compose.asm;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Library {

    public boolean BuyBook() {
        System.out.println("买书成功");
        return true;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "被代理的对象{}" +getClass();
    }
}

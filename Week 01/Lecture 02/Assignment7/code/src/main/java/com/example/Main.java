package com.example;

import com.example.control.AppManager;

public class Main {
    public static void main(String[] args) {
        AppManager appManager = AppManager.getInstance();
        appManager.start();
    }
}
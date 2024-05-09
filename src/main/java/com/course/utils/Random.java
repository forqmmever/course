package com.course.utils;

import java.util.UUID;

public class Random {

    public static String generateRandomUUIDString() {
        return UUID.randomUUID().toString();
    }

//    public static void main(String[] args) {
//        String randomUUID = generateRandomUUIDString();
//        System.out.println("Random UUID: " + randomUUID);
//    }
}

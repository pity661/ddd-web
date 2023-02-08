package com.wenky.ddd.jna;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-29 11:48
 */
public class JNA {
    public static void main(String[] args) {
        //
        JNALibrary jnaLibrary = JNALibrary.INSTANCE;
        System.out.println(JNALibrary.libPath);
        jnaLibrary.hello();
        System.out.println(jnaLibrary.add(1, 2));
    }
}

package com.wenky.ddd.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Objects;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-29 11:50
 */
public interface JNALibrary extends Library {
    String libPath =
            Objects.requireNonNull(JNALibrary.class.getClassLoader().getResource("libJNA1.dylib"))
                    .getFile();

    JNALibrary INSTANCE = Native.load(libPath, JNALibrary.class);

    void hello();

    int add(int a, int b);
}

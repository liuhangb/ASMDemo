package com.library.transform.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Constructor;

/**
 * Created by lh, 2020/12/1
 */
public class ClassUtils {
    /**
     * 修改类
     * @param srcClass
     * @param classVisitorCanonicalName
     * @return
     */
    public static byte[] modifyClass(byte[] srcClass, String classVisitorCanonicalName) {
        //对class文件进行读取与解析
        ClassReader classReader = new ClassReader(srcClass);
        //对class文件的写入
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法

        ClassVisitor classVisitor = null;
        try {
            Class<?> aClass = Class.forName(classVisitorCanonicalName);
            Constructor constructor=  aClass.getDeclaredConstructor(ClassVisitor.class);
            //设置允许访问，防止private修饰的构造方法
            constructor.setAccessible(true);
            classVisitor = (ClassVisitor) constructor.newInstance(classWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //依次调用 ClassVisitor接口的各个方法
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        //toByteArray方法会将最终修改的字节码以 byte 数组形式返回。
        byte[] bytes = classWriter.toByteArray();
        return bytes;
    }
}

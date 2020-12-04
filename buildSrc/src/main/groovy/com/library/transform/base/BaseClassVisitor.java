package com.library.transform.base;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lh, 2020/11/30
 */
public abstract class BaseClassVisitor extends ClassVisitor {

    protected String className;
    /**
     * key为方法名
     */
    protected Map<String, BaseMethodVisitor> mMethodVisitorMap = new HashMap<>(10);
    public BaseClassVisitor(ClassVisitor cv) {
        /**
         * 参数1：ASM API版本，源码规定只能为4，5，6
         * 参数2：ClassVisitor不能为 null
         */
        super(Opcodes.ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println("------ClassVisitor visit start-------");
        System.out.println(" visit className-------" + name);
        System.out.println(" visit superName-------" + superName);
        this.className = name;
    }



    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name-------" + name);
//        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if (mMethodVisitorMap.containsKey(name)) {
            BaseMethodVisitor baseMethodVisitor = mMethodVisitorMap.get(name);
            baseMethodVisitor.setMethodVisitor(mv);
            return baseMethodVisitor;
        }

        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("------ClassVisitor visit end-------");
    }

    public void registerMethodVisitor(BaseMethodVisitor methodVisitor) {
        if (methodVisitor != null && !mMethodVisitorMap.containsKey(methodVisitor.getMethodName())) {
            mMethodVisitorMap.put(methodVisitor.getMethodName(), methodVisitor);
        }
    }

    public abstract String getClassName();
}

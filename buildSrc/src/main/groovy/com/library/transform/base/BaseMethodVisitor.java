package com.library.transform.base;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by lh, 2020/11/30
 */
public abstract class BaseMethodVisitor extends MethodVisitor {

    public BaseMethodVisitor() {
        super(Opcodes.ASM6);
    }

    public void setMethodVisitor(MethodVisitor methodVisitor) {
        mv = methodVisitor;
    }

    /**
     * 方法执行前插入
     */
    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("MethodVisitor visitCode------");
    }

    /**
     * 方法执行后插入
     */
    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("MethodVisitor visitInsn------");
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("MethodVisitor visitEnd------");
    }

    /**
     * 重写的方法名
     * @return
     */
    public abstract String getMethodName();
}

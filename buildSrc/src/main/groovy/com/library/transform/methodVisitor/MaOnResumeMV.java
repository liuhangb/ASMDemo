package com.library.transform.methodVisitor;

import com.library.transform.base.BaseMethodVisitor;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * Created by lh, 2020/12/4
 */
public class MaOnResumeMV extends BaseMethodVisitor {
    @Override
    public void visitCode() {
        super.visitCode();
        MethodVisitor methodVisitor = mv;
        methodVisitor.visitLdcInsn("TEST");
        methodVisitor.visitLdcInsn("MainActivity onResume=====");
        methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
    }

    @Override
    public String getMethodName() {
        return "onResume";
    }
}

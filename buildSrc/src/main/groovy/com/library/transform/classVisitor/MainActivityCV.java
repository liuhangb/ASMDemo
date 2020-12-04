package com.library.transform.classVisitor;

import com.library.transform.base.BaseClassVisitor;
import com.library.transform.methodVisitor.MaOnResumeMV;

import org.objectweb.asm.ClassVisitor;

/**
 * Created by lh, 2020/12/4
 */
public class MainActivityCV extends BaseClassVisitor {
    {
        registerMethodVisitor(new MaOnResumeMV());
    }
    public MainActivityCV(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public String getClassName() {
        return "com/example/order/asmdemo/MainActivity.class";
    }
}

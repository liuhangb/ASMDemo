package com.library.transform

import com.android.build.gradle.AppExtension
import com.library.transform.classVisitor.MainActivityCV
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPluginA implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("hello gradle plugin!");
        System.out.println("========================");
        // 获取Android扩展
        AppExtension android = project.extensions.getByType(AppExtension)
        // 注册Transform，其实就是添加了Task
//        android.registerTransform(new MyTestTransform(GidTestClassVisitor.canonicalName))
        def transform = new com.library.transform.transform.TestTransform()
        transform.registerClassVisitor(new MainActivityCV())
        android.registerTransform(transform)

    }
}
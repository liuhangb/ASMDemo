# ASMDemo
https://cloud.tencent.com/developer/article/1160090

**ASM中文手册：**

https://www.yuque.com/mikaelzero/asm/qzigi6

**实现原理**

自定义transform扫描所有类，为添加指定注解的方法或类添加字节码，然后替换class

**自定义transform**
```
Gradle 学习之插件：https://juejin.cn/post/6844903886927446023
学习创建插件项目：https://juejin.cn/post/6844903438342438925
```

**自定义transform注意事项**

```
- 自定义 Transform 无法处理 Dex ；
- 自定义 Transform 无法使用自定义 Transform ；
- 可以使用 isIncremental 来支持增量编译以及并发处理来加快 Transform 编译速度；
- Transform 只能在全局注册，并将其应用于所有变体（variant）。
```
**ASM示例**

https://www.jianshu.com/p/d5333660e312

**注意事项**
```
如果ASM代码编写错误，编译的class文件是看不到内容
如果ASM代码引用错误可能会导致编译失败
ASM SDK：org.ow2.asm:asm：6.0
这个依赖是由 implementation 'com.android.tools.build:gradle:3.3.1' 引入的
ClassVisitor、MethodVisitor必须用这个sdk内的，不能用rt.java中的，否则会提示找不到类
```

**ASM插件下载**：
```
推荐 ASM ByteCode Viewer Support Kotlin
ASM ByteCode Outline 在AS 4.0不行
```
**编译打包dex时报错**
```
问题：
Caused by: org.gradle.tooling.BuildException: Failed to process
ArrayIndexOutOfBoundsException 0

原因： cv.visitMethod(access, name, desc, signature, exceptions)调用了两次
解决方案：
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        super.visitMethod(access, name, desc, signature, exceptions);   注释掉super，防止调用两次

        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (className.contains("MainActivity")) {
            return new GidTestMethodVisitor(mv, className, name);
        }

        return mv;
    }

```
`ASM修改后的class文件在build/intermediates/transforms/MyTestTransform(自定义的transform文件夹)
`

**ASM ByteCode Viewer Support Kotlin插件生成的代码**
```
         源代码会带有行数，需要去掉
               Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(63, label0);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/meitu/library/analytics/sdk/test/TestConfig", "getInstance", "()Lcom/meitu/library/analytics/sdk/test/TestConfig;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/meitu/library/analytics/sdk/test/TestConfig", "isForeGidRespondDataNull", "()Z", false);
            Label label1 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label1);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(64, label2);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLabel(label1);
            
            修改后
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/meitu/library/analytics/sdk/test/TestConfig", "getInstance", "()Lcom/meitu/library/analytics/sdk/test/TestConfig;", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/meitu/library/analytics/sdk/test/TestConfig", "isForeGidRespondDataNull", "()Z", false);
Label label1 = new Label();
methodVisitor.visitJumpInsn(IFEQ, label1);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitInsn(RETURN);
methodVisitor.visitLabel(label1);
```

```或者修改设置去掉行号```

![avatar](https://raw.githubusercontent.com/liuhangb/ASMDemo/master/ASMPlugin.png)

```
ASM常量NEW引用错误导致transformClassesWithDexBuilderForDebug ArrayIndexOutOfBoundsException
错误❌
import static org.objectweb.asm.TypeReference.NEW；
正确✔️
import static org.objectweb.asm.Opcodes.NEW;
```

```
也可能出现如下异常
Caused by: com.android.builder.dexing.DexArchiveBuilderException: 
Failed to process /Users/liuhang/sourcecode/Android/MtAnalytics/app/build/intermediates/transforms/TestTransform/debug/35.jar
```


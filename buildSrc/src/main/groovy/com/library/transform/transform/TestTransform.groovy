package com.library.transform.transform

class TestTransform extends com.library.transform.base.BaseTransform {

    private String classVisitorName

    @Override
    boolean isShouldModify(String className) {
        def isShouldModify = mClassVisitorMap.containsKey(className)
        if (isShouldModify) {
            System.out.println("isShouldModify: "+ className)
            def visitor = mClassVisitorMap[className]
            classVisitorName = visitor.getClass().canonicalName
        }
        return isShouldModify
    }

    @Override
    byte[] modifyClass(byte[] srcClass) throws IOException {
        def bytes = com.library.transform.utils.ClassUtils.modifyClass(srcClass, classVisitorName)
        return bytes
    }

    @Override
    String getName() {
        return "TestTransform"
    }
}
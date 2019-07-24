package com.gaoqi.classLoad;

/**
 * 线程上下文类加载器的测试
 */
public class ThreadContext {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = ThreadContext.class.getClassLoader();
        System.out.println(loader);


        ClassLoader loader2 = Thread.currentThread().getContextClassLoader();
        System.out.println(loader2);

        Thread.currentThread().setContextClassLoader(new FileSystemClassLoader("d:/myjava/"));
        System.out.println(Thread.currentThread().getContextClassLoader());

        Class<DoubleParentProxy> c = (Class<DoubleParentProxy>) Thread.currentThread().getContextClassLoader().loadClass("com.pattern.test.DoubleParentProxy");
        System.out.println(c);
        System.out.println(c.getClassLoader());

    }
}

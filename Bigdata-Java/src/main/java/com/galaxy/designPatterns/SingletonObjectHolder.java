package com.galaxy.designPatterns;

public class SingletonObjectHolder {

    /**
     * 懒加载,线程安全,效率高,不加锁
     *
     * 类加载
     *  1.装载, 读.class文件
     *  2.链接, 变量赋初始值,static区域(属性或class)的初始化
     *  3.初始化
     *
     * SingletonObjectHolder.clas装载
     * 初始化static class InstanceHolder, 给其instance赋null
     * 在第三阶段给初始化为引用实例
     *
     * */
    private SingletonObjectHolder() {
    }

    private static class InstanceHolder {
        //static在jvm内只会被加载一次, 严格保证线程执行顺序, 因此不会创建两个实例
        //static为主动加载, 只有调用该类时, 该类才会加载static属性
        private final static SingletonObjectHolder instance = new SingletonObjectHolder();
    }

    public static SingletonObjectHolder getInstance() {
        //只有调用到此方法才会加载
        return InstanceHolder.instance;
    }

}

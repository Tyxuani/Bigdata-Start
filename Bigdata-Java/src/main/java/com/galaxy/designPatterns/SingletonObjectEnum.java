package com.galaxy.designPatterns;

public class SingletonObjectEnum {
    private SingletonObjectEnum() {
    }
    private enum Singleton{
        INSTANCE;
        private final SingletonObjectEnum instance;

        Singleton(){
            instance = new SingletonObjectEnum();
        }

        public SingletonObjectEnum getInstance(){
            return instance;
        }
    }
    public static SingletonObjectEnum getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

}

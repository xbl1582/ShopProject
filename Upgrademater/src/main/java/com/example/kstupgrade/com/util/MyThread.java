package com.example.kstupgrade.com.util;

import java.util.concurrent.Callable;

public abstract   class MyThread implements Callable {

    @Override
    public abstract Object call() throws Exception ;
}

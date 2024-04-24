package com.example.kstupgrade.com.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFureTask extends FutureTask {
    public MyFureTask(Callable callable) {
        super(callable);
    }

    public MyFureTask(Runnable runnable, Object result) {
        super(runnable, result);
    }
}

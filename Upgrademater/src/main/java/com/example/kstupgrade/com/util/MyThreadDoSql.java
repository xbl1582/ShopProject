package com.example.kstupgrade.com.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Component
@Slf4j
public class MyThreadDoSql<T,R> {
    public   Object dosql(T t, Function<T, R> func) throws ExecutionException, InterruptedException {

        MyThread selectipaduserlist=new MyThread() {
            @Override
            public Object call() {

                return  func.apply(t);
            }
        };
        MyFureTask myFureTask=new MyFureTask(selectipaduserlist);
        new Thread(myFureTask).start();
        return myFureTask.get();
    }
}

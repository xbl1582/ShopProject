package com.example.kstupgrade.com.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Aspect
@Component
@Slf4j
public class SqlLiteSyncAspect {

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Pointcut("@annotation(com.example.kstupgrade.com.annotation.Sqllitessave))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().getName();

        log.debug("方法环绕proceed，class - {}, 方法是 - {}", pjp.getTarget().getClass().getName(), name);
        if (name.contains("save") || name.contains("del") || name.contains("update")) {

            readWriteLock.writeLock().lock();
            try {
                Object proceed = pjp.proceed();
                return proceed;
            } catch (Exception e) {
                log.error("写锁保护代码发生异常", e);
                throw e;
            } finally {
                readWriteLock.writeLock().unlock();
            }

        } else {
            try {
                readWriteLock.readLock().lock();
                Object proceed = pjp.proceed();
                return proceed;
            } catch (Exception e) {
                log.error("读锁保护代码发生异常", e);
                throw e;
            } finally {
                readWriteLock.readLock().unlock();
            }

        }

    }
}
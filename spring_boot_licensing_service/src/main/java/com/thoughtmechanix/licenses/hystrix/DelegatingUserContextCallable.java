package com.thoughtmechanix.licenses.hystrix;

import com.thoughtmechanix.licenses.utils.UserContext;
import com.thoughtmechanix.licenses.utils.UserContextHolder;

import java.util.concurrent.Callable;


public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private UserContext originalUserContext;

    /*
     * 原始callable被传递到自定义的callable类中，自定义的callable将使用hystrix保护的代码和来自父线程的UserContext。
     */
    public DelegatingUserContextCallable(Callable<V> delegate,
                                             UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    // call()方法在被@HystrixCommand注解保护的方法之前调用。
    public V call() throws Exception {
        // 已设置UserContext，存储UserContext的UserContextHolder将与运行hystrix保护的方法的线程相关联。
        UserContextHolder.setContext( originalUserContext );

        // UserContext设置后，在hystrix保护的方法上调用call()方法。
        try {
            return delegate.call();
        }
        finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
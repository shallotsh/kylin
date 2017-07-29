package org.kylin.algorithm.strategy;


/**
 * @author huangyawu
 * @date 2017/7/29 下午2:14.
 */
@FunctionalInterface
public interface Strategy<T,P> {
    T execute(P param);
}

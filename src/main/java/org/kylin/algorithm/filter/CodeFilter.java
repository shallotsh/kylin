package org.kylin.algorithm.filter;

import org.kylin.bean.FilterParam;
import org.kylin.bean.WelfareCode;

import java.util.Objects;

/**
 * @author huangyawu
 * @date 2017/7/23 下午12:34.
 */
@FunctionalInterface
public interface CodeFilter<T> {
    default boolean shouldBeFilter(FilterParam param){
        return true;
    }
    void filter(T code, FilterParam filterParam);
}

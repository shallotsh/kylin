package org.kylin.algorithm.filter;

import org.kylin.bean.FilterParam;
import org.kylin.bean.WelfareCode;

/**
 * @author huangyawu
 * @date 2017/7/23 下午12:34.
 */
@FunctionalInterface
public interface CodeFilter<T> {
    void filter(T code, FilterParam filterParam);
}

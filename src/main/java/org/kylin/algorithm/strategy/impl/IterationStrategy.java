package org.kylin.algorithm.strategy.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kylin.algorithm.filter.impl.*;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.FilterParam;
import org.kylin.bean.WelfareCode;
import org.kylin.bean.WyfParam;
import org.kylin.constant.ConstantsEnum;
import org.kylin.util.Encoders;
import org.kylin.util.TransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/7/29 下午2:15.
 */
public class IterationStrategy implements Strategy<WelfareCode, WyfParam>{
    private static final Logger LOGGER = LoggerFactory.getLogger(IterationStrategy.class);

    @Override
    public WelfareCode execute(WyfParam wyfParam) {
        if(!check(wyfParam)){
            throw new IllegalArgumentException("参数错误");
        }

        FilterParam filterParam = getFilterParam(wyfParam.getFilterParam());
        List<Set<Integer>> riddles = TransferUtil.toIntegerSets(wyfParam.getRiddles());
        WelfareCode base = Encoders.quibinaryEncode3DCodes(riddles);

        // 执行策略
        WelfareCode welfareCode = executeStrategy(base, filterParam, wyfParam.getRiddles());

        return welfareCode;
    }

    private boolean check(WyfParam wyfParam){
        if(wyfParam == null || CollectionUtils.size(wyfParam.getRiddles()) < 2){
            return false;
        }else {
            return true;
        }
    }

    private FilterParam getFilterParam(FilterParam param){
        FilterParam filterParam = new FilterParam();
        filterParam.setBoldCode(ConstantsEnum.DEFAULT_FILTER_CODES.getData());
        filterParam.setRange(ConstantsEnum.DEFAULT_RANGE_CODE.getData());
        filterParam.setHuBits(ConstantsEnum.DEFAULT_FILTER_CODES.getData());

        if(param != null){
            // 用户参数覆盖默认参数
            if(!StringUtils.isBlank(param.getBoldCode())){
                filterParam.setBoldCode(param.getBoldCode());
            }

            if(!StringUtils.isBlank(param.getHuBits())){
                filterParam.setHuBits(param.getHuBits());
            }

            if(!StringUtils.isBlank(param.getRange())){
                filterParam.setRange(param.getRange());
            }

            filterParam.setSumValue(param.getSumValue());
            filterParam.setKillAllOddEven(param.getKillAllOddEven());
            filterParam.setKillOneEnd(param.getKillOneEnd());
            filterParam.setKillDipolar(param.getKillDipolar());
            filterParam.setKillBigSum(param.getKillBigSum());
        }

        return filterParam;
    }

    private WelfareCode executeStrategy(WelfareCode base, FilterParam filterParam, List<String> riddles){
        int count = CollectionUtils.size(riddles);

        List<WelfareCode> welfareCodes = new ArrayList<>();

        for(int i = 0; i < count; i++){
            WelfareCode code = doStrategy(base, filterParam, riddles.get(i));
            if(code != null){
                welfareCodes.add(code);
            }
        }

        return Encoders.mergeWelfareCodes(welfareCodes);
    }

    private WelfareCode doStrategy(WelfareCode base, FilterParam filterParam, String filterString){

        WelfareCode welfareCode = new WelfareCode(base);
        FilterParam param = new FilterParam();
        param.setBoldCode(filterString);
        param.setHuBits(filterString);
        new BoldFilter().filter(welfareCode, param);

        // 暂存
        WelfareCode cacheCode = new WelfareCode(welfareCode);

        // 进行 和值尾， 跨度, 大和，全奇全偶， 两头，全大全小 等杀码操作
        new SubTailFilter().filter(welfareCode, filterParam);
        new RangeFilter().filter(welfareCode, filterParam);
        param.setKillDipolar(true);
        param.setKillOneEnd(true);
        param.setKillAllOddEven(true);
        param.setKillBigSum(true);

        new BigSumFilter().filter(welfareCode, param);
        new AllOddEvenFilter().filter(welfareCode, param);
        new DipolarFilter().filter(welfareCode, param);
        new OneEndFilter().filter(welfareCode, param);

        // 取余
        cacheCode.minus(welfareCode);

        // 转直选
        cacheCode.toDirect();

        // 百个位杀码
        new HUBitFilter().filter(cacheCode, param);

        return cacheCode.distinct().sort(WelfareCode::bitSort).generate();
    }

}

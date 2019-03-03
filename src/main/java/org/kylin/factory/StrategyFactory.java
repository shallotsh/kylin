package org.kylin.factory;

import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.algorithm.strategy.impl.*;
import org.kylin.constant.FilterStrategyEnum;

public class StrategyFactory {
    public static SequenceProcessor createProcessor(FilterStrategyEnum filterStrategyEnum){
        switch (filterStrategyEnum){
            case LITTLE_SUM_FILTER:
                return new LitttleSumProcessor();
            case BIG_SUM_FILTER:
                return new BigSumProcessor();
            case ODD_EVEN_FILTER:
                return new OddEvenProcessor();
            case CONTAIN_THREE_FILTER:
                return new ContainThreeProcessor();
            case CONTAIN_FOUR_FILTER:
                return new ContainFourProcessor();
            case CONTAIN_FIVE_FILTER:
                return new ContainFiveProcessor();
            case EXTREMA_FILTER:
                return new ExtremumProcessor();
            case UNORDERED_FISH_MAN_FILTER:
                return new UnorderedFishManProcessor();
            case ORDERED_FISH_MAN_FILTER:
                return new OrderedFishManProcessor();
            case NON_REPEAT_FILTER:
                return new NonRepeatCodeProcessor();
            case SINK_FILTER:
                return new SinkCodeProcessor();
            case TAIL_THREE_FILTER:
                return new TailThreeCompareProcessor();
            case SUM_TAIL_FILTER:
                return new SumTailProcessor();
            case RANDOM_FILTER:
                return new RandomProcessor();
            case SUM_FILTER:
                return new SumProcessor();
            case BOLD_INCREASE_FREQ:
                return new BoldIncreaseFreqProcessor();
            case SUM_INCREASE_FREQ:
                return new SumTailIncreaseFreqProcessor();
            case HIGH_FREQ_FILTER:
                return new HighFreqProcessor();
            case FREQ_KILL_FILTER:
                return new FreqKillProcessor();
                default:
                    return null;
        }
    }

}

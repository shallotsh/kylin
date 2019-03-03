package org.kylin.algorithm.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.kylin.constant.BitConstant;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public enum BitSeqEnum {
    ABC(1, "a<b<c"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.UNIT] > target[BitConstant.DECADE]
                    ? (target[BitConstant.DECADE] > target[BitConstant.HUNDRED]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(0)<target.get(1) ? (target.get(1) < target.get(2)) : false;
        }
    },

    ACB(2, "a<c<b"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.DECADE] > target[BitConstant.UNIT]
                    ? (target[BitConstant.UNIT] > target[BitConstant.HUNDRED]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(0)<target.get(2) ? (target.get(2) < target.get(1)) : false;
        }
    },
    BAC(3, "b<a<c"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.UNIT] > target[BitConstant.HUNDRED]
                    ? (target[BitConstant.HUNDRED] > target[BitConstant.DECADE]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(1)<target.get(0) ? (target.get(0) < target.get(2)) : false;
        }
    },
    BCA(4, "b<c<a"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.HUNDRED] > target[BitConstant.UNIT] ? (target[BitConstant.UNIT] > target[BitConstant.DECADE]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(1)<target.get(2) ? (target.get(2) < target.get(0)) : false;
        }
    },
    CAB(5, "c<a<b"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.DECADE] > target[BitConstant.HUNDRED]
                    ? (target[BitConstant.HUNDRED] > target[BitConstant.UNIT]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(2)<target.get(0) ? (target.get(0) < target.get(1)) : false;
        }
    },
    CBA(6, "c<b<a"){
        @Override
        public boolean seqEqual(Integer[] target) {
            if(ArrayUtils.getLength(target) < 3){
                return false;
            }
            return target[BitConstant.HUNDRED] > target[BitConstant.DECADE] ?
                    (target[BitConstant.DECADE] > target[BitConstant.UNIT]) : false;
        }

        @Override
        public boolean seqEqual(List<Integer> target) {
            if(CollectionUtils.size(target) < 3){
                return false;
            }
            return target.get(2)<target.get(1) ? (target.get(1) < target.get(0)) : false;
        }
    },
    ;


    @Getter
    private int id;
    @Getter
    private String desc;
    public abstract boolean seqEqual(Integer[] target);
    public abstract boolean seqEqual(List<Integer> target);


    public static Optional<BitSeqEnum> getById(int id){
        for(BitSeqEnum seqEnum : BitSeqEnum.values()){
            if(seqEnum.getId() == id){
                return Optional.of(seqEnum);
            }
        }
        return Optional.empty();
    }

}

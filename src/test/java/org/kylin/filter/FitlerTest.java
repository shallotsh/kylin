//package org.kylin.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.sun.tools.javac.util.Assert;
//import org.junit.Test;
//import org.kylin.algorithm.filter.impl.*;
//import org.kylin.bean.FilterParam;
//import org.kylin.bean.W3DCode;
//import org.kylin.bean.WelfareCode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author huangyawu
// * @date 2017/7/23 下午9:56.
// */
//public class FitlerTest {
//    private static final Logger LOGGER = LoggerFactory.getLogger(FitlerTest.class);
//
//    @Test
//    public void testAllOddEvenFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(2,6,0));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setKillAllOddEven(true);
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new AllOddEvenFilter(), filterParam);
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//
//        LOGGER.info(JSON.toJSONString(welfareCode));
//
//    }
//
//    @Test
//    public void testBigSumFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setKillBigSum(true);
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new BigSumFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testBoldFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setBoldCode("59");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new BoldFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//
//    }
//
//    @Test
//    public void testDBitFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setdBits("59");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new DBitFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testhBitFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.sethBits("34");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new HBitFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testuBitFilter(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setuBits("7");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new UBitFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testDipolarFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setKillDipolar(true);
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new DipolarFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testFishManFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setFishMan("234 8756");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new FishManFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testGossipFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setGossip("23#96");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new GossipFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testOneEndFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setKillOneEnd(true);
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new OneEndFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testRangeFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));
//        w3DCodes.add(new W3DCode(3,5,7));
//        w3DCodes.add(new W3DCode(4,5,7));
//        w3DCodes.add(new W3DCode(3,8,7));
//        w3DCodes.add(new W3DCode(9,9,6));
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setRange("234");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new RangeFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testSubTailFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));   // 6
//        w3DCodes.add(new W3DCode(3,5,7));   // 5
//        w3DCodes.add(new W3DCode(4,5,7));   // 6
//        w3DCodes.add(new W3DCode(3,8,7));   // 8
//        w3DCodes.add(new W3DCode(9,9,6));   // 4
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setSumValue("546");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new SubTailFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testTernaryLocationFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));   // 6
//        w3DCodes.add(new W3DCode(3,5,7));   // 5
//        w3DCodes.add(new W3DCode(4,5,7));   // 6
//        w3DCodes.add(new W3DCode(3,8,7));   // 8
//        w3DCodes.add(new W3DCode(9,9,6));   // 4
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setTernaryLocation("12357#3478");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new TernaryLocationFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//    @Test
//    public void testHUBitFitler(){
//        List<W3DCode> w3DCodes = new ArrayList<>();
//        w3DCodes.add(new W3DCode(3,1,2));   // 6
//        w3DCodes.add(new W3DCode(3,5,7));   // 5
//        w3DCodes.add(new W3DCode(4,5,7));   // 6
//        w3DCodes.add(new W3DCode(3,8,7));   // 8
//        w3DCodes.add(new W3DCode(9,9,6));   // 4
//
//        FilterParam filterParam = new FilterParam();
//        filterParam.setHuBits("37");
//
//        WelfareCode welfareCode = new WelfareCode();
//        welfareCode.setW3DCodes(w3DCodes);
//        welfareCode.filter(new HUBitFilter(), filterParam);
//
//        LOGGER.info(JSON.toJSONString(welfareCode.getW3DCodes()));
//
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(4,5,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,5,7)));
//        Assert.check(welfareCode.getW3DCodes().contains(new W3DCode(3,1,2)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(3,8,7)));
//        Assert.check(!welfareCode.getW3DCodes().contains(new W3DCode(9,9,6)));
//    }
//
//}

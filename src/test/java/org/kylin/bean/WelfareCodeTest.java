package org.kylin.bean;

import org.junit.Test;
import org.kylin.constant.CodeTypeEnum;
import org.kylin.util.TransferUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyawu
 * @date 2017/7/24 下午8:37.
 */
public class WelfareCodeTest {

    @Test
    public void testMerge(){
        List<W3DCode> w3DCodes = new ArrayList<>();
        w3DCodes.add(new W3DCode(3,5,7));
        w3DCodes.add(new W3DCode(4,5,7));
        w3DCodes.add(new W3DCode(3,8,7));
        w3DCodes.add(new W3DCode(9,9,6));

        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.GROUP);
        welfareCode.setW3DCodes(w3DCodes);


        System.out.println(TransferUtil.findInGroupW3DCodes(w3DCodes, new W3DCode(8,3,7) ));

        List<W3DCode> w3DCodes2 = new ArrayList<>(w3DCodes);
        w3DCodes.add(new W3DCode(6,8,6));
        w3DCodes.add(new W3DCode(8,3,7));

        WelfareCode welfareCode2 = new WelfareCode();
        welfareCode2.setCodeTypeEnum(CodeTypeEnum.GROUP);
        welfareCode2.setW3DCodes(w3DCodes2);

        welfareCode2.merge(welfareCode);

        System.out.println(welfareCode2.getW3DCodes());

    }


    @Test
    public void testMinus(){
        List<W3DCode> w3DCodes = new ArrayList<>();
        w3DCodes.add(new W3DCode(3,5,7));
        w3DCodes.add(new W3DCode(4,5,7));
        w3DCodes.add(new W3DCode(3,8,7));
        w3DCodes.add(new W3DCode(9,9,6));

        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.GROUP);
        welfareCode.setW3DCodes(w3DCodes);


//        System.out.println(TransferUtil.findInGroupW3DCodes(w3DCodes, new W3DCode(8,3,7) ));

        List<W3DCode> w3DCodes2 = new ArrayList<>(w3DCodes);
        w3DCodes2.add(new W3DCode(6,8,6));
        w3DCodes2.add(new W3DCode(8,3,7));


        WelfareCode welfareCode2 = new WelfareCode();
        welfareCode2.setCodeTypeEnum(CodeTypeEnum.GROUP);
        welfareCode2.setW3DCodes(w3DCodes2);

        System.out.println("wel1:" + welfareCode.getW3DCodes());
        System.out.println("wel2:" + welfareCode2.getW3DCodes());


        welfareCode2.minus(welfareCode);

        System.out.println(welfareCode2.getW3DCodes());
    }

    @Test
    public void testGroup(){
        List<W3DCode> w3DCodes = new ArrayList<>();
        W3DCode code = new W3DCode(3,5,7);
        code.setFreq(2);
        w3DCodes.add(code);

        code = new W3DCode(7,5,3);
        code.setFreq(3);
        w3DCodes.add(code);

        code = new W3DCode(5,3,7);
        code.setFreq(2);
        w3DCodes.add(code);

        code = new W3DCode(1,3,8);
        code.setFreq(3);
        w3DCodes.add(code);

        WelfareCode welfareCode = new WelfareCode();
        welfareCode.setCodeTypeEnum(CodeTypeEnum.DIRECT);
        welfareCode.setW3DCodes(w3DCodes);
        welfareCode.generate();
        System.out.println(welfareCode.getCodes());

        welfareCode.toGroup().generate();
        System.out.println(welfareCode.getCodes());
    }
}

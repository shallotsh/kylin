package org.kylin.bean;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyawu
 * @date 2017/7/21 上午2:45.
 */
public class W3DCodeTest {

    @Test
    public void testW3DCode(){
        W3DCode w3DCode = new W3DCode(3,2,1);
        W3DCode w3DCode1 = new W3DCode(1, 2, 3);

        System.out.println(w3DCode.asc());

        List<W3DCode> list = new ArrayList<>();
        list.add(w3DCode);
        list.add(w3DCode1);

        List<W3DCode> list2 = new ArrayList<>(list);
        list.remove(list.get(list.size() - 1));

        System.out.println(JSON.toJSONString(list));

        System.out.println("--------");

        System.out.println(JSON.toJSONString(list2));

    }
}

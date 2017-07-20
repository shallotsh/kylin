package org.kylin.bean;

import org.junit.Assert;
import org.junit.Test;

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
    }
}

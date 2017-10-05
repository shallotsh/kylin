package org.kylin.util;

import org.junit.Assert;
import org.junit.Test;
import org.kylin.bean.W3DCode;
import org.kylin.constant.WelfareConfig;

import java.util.List;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/10/5 下午4:19.
 */
public class TransferUtilTest {

    @Test
    public void testParse(){
        List<W3DCode> w3DCodes = TransferUtil.parseFromStringArrays(WelfareConfig.HFC);
        Assert.assertNotNull(w3DCodes);
    }
}

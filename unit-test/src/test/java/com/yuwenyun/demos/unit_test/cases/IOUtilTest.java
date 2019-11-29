package com.yuwenyun.demos.unit_test.cases;

import com.yuwenyun.demos.unit_test.util.JsonUtil;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-10 21:14
 */
public class IOUtilTest {

    private String json;
    @Before
    public void setUp(){
        json = JsonUtil.readResourceJsonFileAsString("/sample.json");
    }

    /**
     * 测试IO类的工具方法，可给定可控的样例文本并验证读取是否正常
     */
    @Test
    public void testReadResourceJsonFileAsString(){
        Assert.assertNotNull(json);
        Assert.assertTrue(json.startsWith("{"));
        Assert.assertTrue(json.endsWith("}"));
    }

    @Test
    public void testModifyNodeWithNewValue_firstLevel(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "A", "X");
        String expected = "{\"A\":\"X\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":true,\"E\":[\"e\",\"E\"],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"g3\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_embeddedLevel(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "C.D", false);
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":false,\"E\":[\"e\",\"E\"],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"g3\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_array(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "B.1", "X");
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"X\"],\"C\":{\"D\":true,\"E\":[\"e\",\"E\"],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"g3\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_embeddedArray(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "C.E.0", 100);
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":true,\"E\":[\"E\",100],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"g3\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_embeddedObjectAttributeInArray(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "C.F.G.1.G3", "X");
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":true,\"E\":[\"e\",\"E\"],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"X\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_wholeList(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "C.E", Arrays.asList(1, 2, 3));
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":true,\"E\":[1,2,3],\"F\":{\"G\":[{\"G1\":\"g1\",\"G2\":\"g2\"},{\"G3\":\"g3\",\"G4\":\"g4\"}],\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testModifyNodeWithNewValue_wholeObject(){
        String result = JsonUtil.modifyNodeWithNewValue(json, "C.F.G", "X");
        String expected = "{\"A\":\"a\",\"B\":[\"b\",\"B\"],\"C\":{\"D\":true,\"E\":[\"e\",\"E\"],\"F\":{\"G\":\"X\",\"H\":1}}}";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetValueByFlattenedKey_firstLevel(){
        String result = (String)JsonUtil.getValueByFlattenedKey(json, "A");
        String expected = "a";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetValueByFlattenedKey_embeddedLevel(){
        boolean result = (boolean)JsonUtil.getValueByFlattenedKey(json, "C.D");
        Assert.assertTrue(result);
    }

    @Test
    public void testGetValueByFlattenedKey_array(){
        String result = (String)JsonUtil.getValueByFlattenedKey(json, "B.0");
        String expected = "b";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetValueByFlattenedKey_embeddedArray(){
        String result = (String)JsonUtil.getValueByFlattenedKey(json, "C.E.1");
        String expected = "E";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetValueByFlattenedKey_embeddedObjectAttributeInArray(){
        String result = (String)JsonUtil.getValueByFlattenedKey(json, "C.F.G.0.G1");
        String expected = "g1";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetValueByFlattenedKey_wholeList(){
        List<String> result = (List)JsonUtil.getValueByFlattenedKey(json, "C.E");
        Assert.assertTrue(result.size() == 2);
        Assert.assertTrue(result.contains("e"));
        Assert.assertTrue(result.contains("E"));
    }
}

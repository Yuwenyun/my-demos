package com.yuwenyun.demos.unit_test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-01 16:32
 */
public class JsonUtil {

    /**
     * 读取json文件，返回整个字符串
     * @param filePath 文件在classpath中的路径
     * @return 返回文件内容
     */
    public static String readResourceJsonFileAsString(String filePath){
        try (InputStream is = JsonUtil.class.getResourceAsStream(filePath)) {
            if (is != null) {
                return IOUtils.toString(is);
            }else{
                throw new RuntimeException("unable to read " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("unable to read " + filePath);
        }
    }

    public static Object getValueByFlattenedKey(String json, String flattenedKey){
        if(StringUtils.isBlank(flattenedKey)){
            throw new IllegalArgumentException("target key shall not be blank...");
        }
        Map<String, Object> resultMap = getMapFromJsonString(json);
        String[] keys = flattenedKey.split("\\.");
        return getValueByFlattenedKey(resultMap, keys, 0);
    }

    private static Object getValueByFlattenedKey(Object currObj, String[] keys, int index){
        if(currObj instanceof Map){
            Map<String, Object> currMap = (Map)currObj;
            if(index + 1 == keys.length){
                return currMap.get(keys[index]);
            }
            return getValueByFlattenedKey(currMap.get(keys[index]), keys, index + 1);
        }
        if(currObj instanceof List){
            List<Object> currList = (List)currObj;
            if(index + 1 == keys.length){
                return currList.get(Integer.parseInt(keys[index]));
            }
            return getValueByFlattenedKey(currList.get(Integer.parseInt(keys[index])), keys, index + 1);
        }
        return null;
    }

    /**
     * 根据给定扁平化键值置换指定节点
     * @param json json字符串
     * @param flattenedKey 扁平化的键值，eg: A.B
     * @param newValue 替换的新值
     * @return 返回替换之后的json字符串
     */
    public static String modifyNodeWithNewValue(String json, String flattenedKey, Object newValue){
        if(StringUtils.isBlank(flattenedKey)){
            throw new IllegalArgumentException("target key shall not be blank...");
        }
        Map<String, Object> resultMap = getMapFromJsonString(json);

        String[] keys = flattenedKey.split("\\.");
        modifyNodeWithNewValue(resultMap, keys, 0, newValue);

        return getJsonStringFromObject(resultMap);
    }

    /**
     * 递归调用找到键值指定的节点并替换成新值
     * @param currentObj 当前参与递归的节点的值
     * @param keys 扁平化键值按"."分割成的所有的键
     * @param index 当前节点的键值在keys中的位置
     * @param newValue 要替换的值
     */
    private static void modifyNodeWithNewValue(Object currentObj, String[] keys, int index, Object newValue){
        if(index >= keys.length){
            return;
        }
        if(currentObj instanceof Map){
            Map<String, Object> currentMap = (Map)currentObj;
            if(index + 1 == keys.length){
                currentMap.put(keys[index], newValue);
                return;
            }
            currentObj = currentMap.get(keys[index]);
            modifyNodeWithNewValue(currentObj, keys, index + 1, newValue);
            return;
        }

        if(currentObj instanceof List){
            List<Object> currentList = (List)currentObj;
            if(index + 1 == keys.length){
                currentList.remove(Integer.parseInt(keys[index]));
                currentList.add(newValue);
                return;
            }
            currentObj = currentList.get(Integer.parseInt(keys[index]));
            modifyNodeWithNewValue(currentObj, keys, index + 1, newValue);
        }
    }

    public static Map<String, Object> getMapFromJsonString(String json){
        if(StringUtils.isBlank(json)){
            throw new IllegalArgumentException("provided json is null/empty...");
        }
        ObjectMapper objMapper = new ObjectMapper();
        try {
            JavaType collectionType = objMapper.getTypeFactory()
                .constructParametricType(Map.class, String.class, Object.class);
            return objMapper.readValue(json, collectionType);
        } catch (IOException e) {
            throw new IllegalArgumentException("provided json is corrupted...");
        }
    }

    private static String getJsonStringFromObject(Object object){
        if(object == null){
            throw new IllegalArgumentException("provided object is null...");
        }
        ObjectMapper objMapper = new ObjectMapper();
        try {
            return objMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unable to write the provided object to json string...");
        }
    }
}

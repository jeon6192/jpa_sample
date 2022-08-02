package com.example.jpa_test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonTest {

    private final String sampleString = "{\"name\":\"John\", \"age\":30, \"car\":null}";
    private final Map<String, Object> sampleMap = new HashMap<>();

    @BeforeEach
    void init() {
        sampleMap.put("name", "John");
        sampleMap.put("age", 30);
        sampleMap.put("car", null);
    }

    @Test
    @Order(1)
    void Jackson() throws JsonProcessingException {
        // parse to Map & String
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> stringToMap = objectMapper.readValue(sampleString, Map.class);
        String mapToString = objectMapper.writeValueAsString(sampleMap);

        System.out.printf("mapToString By Jackson : %s%n", mapToString);
        assertEquals(sampleMap, stringToMap);

    }

    @Test
    @Order(2)
    void FastJson() {
        // parse to Map & String By JSON
        Map<String, Object> stringToMap = JSON.parseObject(sampleString);
        String mapToString = JSON.toJSONString(sampleMap);

        System.out.printf("mapToString By FastJson : %s%n", mapToString);
        assertEquals(sampleMap, stringToMap);

        // parse to Map & String By JSONObject
        Map<String, Object> stringToMap2 = com.alibaba.fastjson.JSONObject.parseObject(sampleString);
        Map<String, Object> stringToMap3 = com.alibaba.fastjson2.JSONObject.parseObject(sampleString);
        String mapToString2 = com.alibaba.fastjson.JSONObject.toJSONString(sampleMap);
        String mapToString3 = com.alibaba.fastjson2.JSONObject.toJSONString(sampleMap, JSONWriter.Feature.WriteNulls);

        System.out.printf("mapToString By FastJSONObject1 : %s%n", mapToString2);
        System.out.printf("mapToString By FastJSONObject2 : %s%n", mapToString3);
        assertEquals(sampleMap, stringToMap2);
        assertEquals(sampleMap, stringToMap3);

        Map<String, Object> stringToMap4 = com.alibaba.fastjson2.JSONObject.parseObject(mapToString3, Map.class, JSONReader.Feature.IgnoreSetNullValue);
        System.out.println(stringToMap4);

        // make Json
        com.alibaba.fastjson2.JSONObject jsonObject = new com.alibaba.fastjson2.JSONObject();
        jsonObject.put("name", "John");
        jsonObject.put("age", 30);
        jsonObject.put("car", null);

        com.alibaba.fastjson2.JSONObject jsonObject2 = new com.alibaba.fastjson2.JSONObject()
                .fluentPut("name", "John")
                .fluentPut("age", 30)
                .fluentPut("car", null);

        assertEquals(sampleMap, jsonObject);
        assertEquals(sampleMap, jsonObject2);

        String stringByCustom = FastJsonUtil.toJsonString(jsonObject);
        System.out.printf("stringByCustom : %s%n", stringByCustom);
        Map<String, Object> mapByCustom = FastJsonUtil.toObject(stringByCustom);
        System.out.println(mapByCustom);

    }

    // minidev
    @Test
    @Order(3)
    void JsonSmart() throws ParseException {
        // parse to Map & String
        String mapToString = net.minidev.json.JSONObject.toJSONString(sampleMap);
        net.minidev.json.parser.JSONParser jsonParser = new JSONParser(-1);
        Map<String, Object> stringToMap = (Map<String, Object>) jsonParser.parse(sampleString);
        Map<String, Object> test = jsonParser.parse(sampleString, Map.class);

        System.out.printf("mapToString By JsonSmart : %s%n", mapToString);
//        assertEquals(sampleString, mapToString);
        assertEquals(sampleMap, stringToMap);

        // make Json
        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject()
                .appendField("name", "John")
                .appendField("age", 30)
                .appendField("car", null);

        assertEquals(sampleMap, jsonObject);
    }

    @Test
    @Order(4)
    void JsonSimple() throws org.json.simple.parser.ParseException {
        // parse to Map & String
        String mapToString = org.json.simple.JSONObject.toJSONString(sampleMap);
        String mapToString2 = new org.json.simple.JSONObject(sampleMap).toJSONString();
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        Map<String, Object> stringToMap = (Map<String, Object>) jsonParser.parse(sampleString);

        System.out.printf("mapToString By JsonSimple JSONObject static method : %s%n", mapToString);
        System.out.printf("mapToString By JsonSimple JSONObject method : %s%n", mapToString2);
//        assertEquals(sampleString, mapToString);
//        assertEquals(sampleString, mapToString2);
        assertEquals(sampleMap, stringToMap);

        // make Json
        org.json.simple.JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John");
        jsonObject.put("age", 30);
        jsonObject.put("car", null);

        assertEquals(sampleMap, jsonObject);
    }

    @Test
    void jsonTest() {

        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        json
                .fluentPut("list", integerList)
                .fluentPut("json", new com.alibaba.fastjson.JSONObject()
                        .fluentPut("name", "json")
                        .fluentPut("date", LocalDateTime.now()))
                .fluentPut("obj", new com.alibaba.fastjson.JSONObject()
                        .fluentPut("isAvailable", true)
                        .fluentPut("date", LocalDateTime.now().plusDays(2))
                        .fluentPut("test", "test")
                        .fluentPut("test2", 15))
                .fluentPut("sampleMap", new com.alibaba.fastjson.JSONObject(sampleMap))
                .fluentPut("sampleString", sampleString);

        System.out.println(json);
        System.out.println("toString : " + json.toJSONString());

        com.alibaba.fastjson.JSONArray jsonArray = json.getJSONArray("list");
        jsonArray.forEach(System.out::println);

    }
}

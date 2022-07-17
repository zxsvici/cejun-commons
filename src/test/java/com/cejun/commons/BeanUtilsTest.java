package com.cejun.commons;

import com.alibaba.fastjson.JSON;
import com.cejun.commons.utils.BeanUtils;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BeanUtilsTest {

    @Test
    public void testA() {
        ClassA classA = new ClassA();
        classA.setId(1);
        classA.setName("name1");
        classA.setA_aka("aka");
        classA.setI_kun("iKun");
        classA.setJdbc_url("jdbc");
        classA.setZ_note("note");
        classA.setCoco("coco");
        ClassB classB = new ClassB();
//        BeanUtils.copyProperties(classA, classB);
        Map<String, String> map = new HashMap<>();
        map.put("niko", "coco");
        BeanUtils.copyPropertiesFromCamel(classA, classB, map);
        System.out.println(JSON.toJSONString(classB));

        ClassA a = new ClassA();
        BeanUtils.copyPropertiesToCamel(classB, a);
        System.out.println(JSON.toJSONString(a));
    }
}

@Data
class ClassA {
    private Integer id;
    private String name;
    private String jdbc_url;
    private String i_kun;
    private String z_note;
    private String A_aka;
    private String coco;
}

@Data
class ClassB {
    private Integer id;
    private String name;
    private String jdbcUrl;
    private String iKun;
    private String zNote;
    private String AAka;
    private String niko;
}

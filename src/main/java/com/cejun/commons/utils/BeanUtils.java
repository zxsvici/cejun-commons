package com.cejun.commons.utils;

import com.cejun.commons.exceptions.BaseException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {

    private static final String GET = "get";
    private static final String SET = "set";

    public static void copyProperties(Object source, Object target) throws BaseException {
        copyProperties(source, target, null);
    }

    public static void copyProperties(Object source, Class<?> targetClass) throws BaseException {
        Object target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BaseException("target can not newInstance");
        }
        copyProperties(source, target, null);
    }

    public static void copyPropertiesFromCamel(Object source, Object target) throws BaseException {
        copyProperties(source, target, true, null);
    }

    public static void copyPropertiesToCamel(Object source, Object target) throws BaseException {
        copyProperties(source, target, false, null);
    }

    public static void copyPropertiesFromCamel(Object source, Object target, Map<String, String> map) throws BaseException {
        copyProperties(source, target, true, map);
    }

    public static void copyPropertiesToCamel(Object source, Object target, Map<String, String> map) throws BaseException {
        copyProperties(source, target, false, map);
    }

    private static void copyProperties(Object source, Object target, boolean flag, Map<String, String> mapper) {
        mapper = Optional.ofNullable(mapper).orElseGet(HashMap::new);
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        Arrays.stream(fields).forEach(it -> {
            String name = it.getName();
            map.put(name, flag ? StrUtils.strFromCamel(name) : StrUtils.strToCamel(name));
        });
        mapper.forEach(map::put);
        copyProperties(source, target, map);
    }

    public static void copyProperties(Object source, Object target, Map<String, String> mapper) throws BaseException {
        Optional.ofNullable(source).orElseThrow(() -> new BaseException("source bean is null"));
        Optional.ofNullable(target).orElseThrow(() -> new BaseException("target bean is null"));
        mapper = Optional.ofNullable(mapper).orElseGet(HashMap::new);
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String sourceName = mapper.get(name);
            String suffix = getStartChar(sourceName) + sourceName.substring(1);
            Object value = readValue(source, sourceClass, suffix);
            suffix = getStartChar(name) + name.substring(1);
            writeValue(target, targetClass, suffix, value);
        }
    }

    private static char getStartChar(String name) {
        Optional.ofNullable(name).orElseThrow(BaseException::new);
        char start = name.charAt(0);
        if(start >= 'a' && start <= 'z') {
            start -= 32;
        }else if (start > 'Z' || start < 'A') {
            throw new BaseException("field names cannot start with special characters");
        }
        return start;
    }

    private static void writeValue(Object target, Class<?> clazz, String suffix, Object value) {
        if(Objects.isNull(value)) {
            return;
        }
        try {
            Method method = clazz.getMethod(SET + suffix, value.getClass());
            method.setAccessible(true);
            method.invoke(target, value);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            if(e instanceof NoSuchMethodException) {
                return;
            }
            e.printStackTrace();
            throw new BaseException();
        }
    }

    private static Object readValue(Object source,Class<?> clazz, String suffix) {
        try {
            Method method = clazz.getMethod(GET + suffix);
            method.setAccessible(true);
            return method.invoke(source);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            if(e instanceof NoSuchMethodException) {
//                  throw new BaseException("bean method must be get/set");
                return null;
            }
            throw new BaseException();
        }
    }
}

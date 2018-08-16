package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JsonUtil(序列化工具类)
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/8/16
 */
@Slf4j
public class JsonUtil {

    /**
     * 声明ObjectMapper
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 使用静态块初始化ObjectMapper
     */
    static {
        // 序列化时对象的字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        // 序列化时取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 序列化时忽略空Bean转Json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        // 序列化时所有的日期格式都统一为yyyy-MM-dd HH:mm:ss的样式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        // 反序列化时,忽略在json字符串中存在,但是在java对象中不存在对应属性的情况,防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 把对象转换为json
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * 把对象转换成json,返回格式化的json
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * 将字符串转换成对象
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        // 如果传入的字符串及对象都为空,则直接返回空
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 将字符串转换成对象(处理多泛型等复杂对象)
     *
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 将字符串转换成对象(处理集合 多参数 多泛型等复杂对象)
     *
     * @param str
     * @param collectionClass
     * @param elementClasses
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * Json序列化测试
     *
     * @param args
     */
    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setEmail("test@test.com");

        User u2 = new User();
        u2.setId(2);
        u2.setEmail("test2@test.com");

        String userJson1 = JsonUtil.obj2String(u1);
        String userJson2 = JsonUtil.obj2StringPretty(u1);
        log.info("userJson1:{}", userJson1);
        log.info("userJson2:{}", userJson2);

        User user = JsonUtil.string2Obj(userJson1, User.class);

        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String users = JsonUtil.obj2StringPretty(userList);
        log.info("=====================");
        log.info(users);

        List<User> userList1 = JsonUtil.string2Obj(users, new TypeReference<List<User>>() {
        });

        List<User> userList2 = JsonUtil.string2Obj(users, List.class, User.class);

        System.out.println("end");
    }
}

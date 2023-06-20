package com.cheese.db.rpc.serializer.json;

import com.cheese.db.rpc.serializer.Serializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * json序列化的实现
 *
 * @author sobann
 */
public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        this.objectMapper = new ObjectMapper();
        // 序列化时间的格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 序列化时省略无意义的null和空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 格式化缩进和换行，正式使用后屏蔽此处对信息进行压缩
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 手动关闭底层的输出目标
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        // 手动关闭json结构
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
        // 禁止每次写入输出流后刷新输出流
        objectMapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        // 禁止自动关闭资源对象
        objectMapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        // 禁止序列化空对象抛出异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁止反序列化未知属性抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 反序列化对象时候忽略json中的未知属性
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    }



    @Override
    public <T> byte[] serialize(T obj) {
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        }catch (JsonProcessingException e){
            throw new IllegalStateException(e.getMessage(), e);
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] buf, Class<T> clazz) {
        T obj;
        try {
            obj = objectMapper.readValue(buf, clazz);
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return obj;
    }
}

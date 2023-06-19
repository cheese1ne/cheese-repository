package com.cheese.db.rpc.common.serializer;

/**
 * 序列化、反序列化
 *
 * @author sobann
 */
public interface Serializer {

    /**
     * 序列化为字节数组
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化为对象
     *
     * @param buf
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] buf, Class<T> clazz);
}

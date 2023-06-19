package com.cheese.db.rpc.common.serializer.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.cheese.db.rpc.common.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * hessian序列化
 *
 * @author sobann
 */
public class HessianSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        byte[] bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bos);
        try {
            output.writeObject(obj);
            output.flush();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            try {
                output.close();
                bos.close();
            } catch (Exception ignored) {
            }
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] buf, Class<T> clazz) {
        T obj;
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        Hessian2Input input = new Hessian2Input(bis);
        try {
            obj = (T) input.readObject();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            try {
                input.close();
                bis.close();
            } catch (Exception ignored) {
            }
        }
        return obj;
    }
}

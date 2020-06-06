package rs.ac.uns.ftn.findaroommate.utils;

import com.activeandroid.serializer.TypeSerializer;

import java.util.Date;

public final class UtilDateSerializer extends TypeSerializer {
    public Class<?> getDeserializedType() {
        return Date.class;
    }

    public Class<?> getSerializedType() {
        return long.class;
    }

    public Long serialize(Object data) {
        if (data == null) {
            return null;
        }

        return ((Date) data).getTime();
    }

    public Date deserialize(Object data) {
        if (data == null) {
            return null;
        }

        return new Date((Long) data);
    }
}

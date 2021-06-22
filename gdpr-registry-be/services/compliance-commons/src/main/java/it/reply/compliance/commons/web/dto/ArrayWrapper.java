package it.reply.compliance.commons.web.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class ArrayWrapper<T> {

    private Map<String, Collection<T>> array;

    @JsonAnyGetter
    public Map<?, ?> getArray() {
        return this.array;
    }

    public static <T> ArrayWrapper<T> of(String name, Collection<T> array) {
        ArrayWrapper<T> arrayWrapper = new ArrayWrapper<>();
        arrayWrapper.array = new HashMap<>();
        arrayWrapper.array.put(name, array);
        return arrayWrapper;
    }
}

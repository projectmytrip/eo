package it.reply.compliance.commons.map;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MappingUtils {

    public static <T> void setIfHasValue(Consumer<? super T> consumer, T value) {
        setIfHasValue(consumer, value, Function.identity());
    }

    public static <T, R> void setIfHasValue(Consumer<? super R> consumer, T value, Function<T, R> mapper) {
        if (value != null && consumer != null) {
            if (value instanceof String) {
                if (((String) value).isBlank()) {
                    consumer.accept(mapper.apply(value));
                }
            } else {
                consumer.accept(mapper.apply(value));
            }
        }
    }

    public static <T, R> R retrieveIfHasValue(Function<? super T, Optional<R>> function, T value) {
        if (value != null && function != null) {
            return function.apply(value).orElse(null);
        }
        return null;
    }
}

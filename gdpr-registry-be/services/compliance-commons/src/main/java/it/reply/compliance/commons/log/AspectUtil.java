package it.reply.compliance.commons.log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
final class AspectUtil {

    private AspectUtil() {
    }

    static List<Map.Entry<String, Object>> getArguments(ProceedingJoinPoint jp) {
        String[] argNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        Object[] values = jp.getArgs();
        return IntStream.range(0, argNames.length).mapToObj(index -> {
            Object value = values[index];
            return Map.entry(argNames[index], value != null ? value : "null");
        }).collect(Collectors.toList());
    }

    static Optional<Logger> getTargetClassLogger(ProceedingJoinPoint joinPoint) {
        return Optional.ofNullable(joinPoint).map(JoinPoint::getTarget).map(Object::getClass).map(clazz -> {
            try {
                Field logField = clazz.getDeclaredField("log");
                logField.setAccessible(true);
                return (Logger) logField.get(joinPoint.getTarget());
            } catch (NoSuchFieldException e) {
                log.warn("Logger not found in target class, fallback to the Aspect logger. Try to add @Slf4j " +
                        "annotation to class {}", joinPoint.getTarget().getClass().getName());
                return null;
            } catch (IllegalAccessException e) {
                log.warn("Cannot access 'log' field in your target class {}, fallback to aspect class logger",
                        joinPoint.getTarget().getClass().getSimpleName());
                return null;
            }
        });
    }

    static <E> String getJsonString(E object) {
        if (object == null) {
            return "null";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            return "Cannot transform object to json string";
        }
    }
}

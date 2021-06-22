package it.reply.compliance.commons.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public final class ControllerAspect {

    @Pointcut("execution(* it.reply.compliance..*.*(..))")
    private void anyComplianceMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void getMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    private void postMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    private void putMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    private void patchMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    private void deleteMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void requestMappingMethod() {
    }

    @Pointcut("anyComplianceMethod() && (getMappingMethod() || postMappingMethod() || putMappingMethod() || " +
            "patchMappingMethod() || deleteMappingMethod() || requestMappingMethod())")
    private void requestHandlerMethod() {
    }

    @Around("requestHandlerMethod()")
    private Object aroundRequestHandlerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = AspectUtil.getTargetClassLogger(joinPoint).orElse(log);
        logger.info("Received request for {} with parameters {}", joinPoint.getSignature().getName(),
                AspectUtil.getArguments(joinPoint));
        Object result = joinPoint.proceed();
        logger.debug("Response: {}", AspectUtil.getJsonString(result));
        return result;
    }
}

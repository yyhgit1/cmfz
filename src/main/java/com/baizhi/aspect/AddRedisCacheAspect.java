package com.baizhi.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Aspect //定义切点
@Configuration
public class AddRedisCacheAspect {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //环绕通知
    @Around("@annotation(com.baizhi.annotation.AddRedisCacheAnnotation)")
    public Object addRedisCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // System.out.println("进入了缓存=======================");
        //获取类名称
        String typeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        //获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        //创建
        /*StringBuilder builder = new StringBuilder();
        builder.append(name);*/
        //初始值就自带方法名
        StringBuilder builder = new StringBuilder(name);
        for (int i = 0; i < args.length; i++) {
            //累计添加参数
            builder.append(args[i]);
        }
        //创建新的方法名
        String newMethod = builder.toString();
        //对key进行序列化
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        //对hashkey进行序列化
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //序列化值        创建对象
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //对值序列化
        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //查看是否存在Redis中
        Boolean aBoolean = stringRedisTemplate.opsForHash().hasKey(typeName, newMethod);
        //进行判断有无
        if (aBoolean) {
            //如果有就返回该key的结果
            return stringRedisTemplate.opsForHash().get(typeName, newMethod);
        }
        //value的返回值
        Object proceed = proceedingJoinPoint.proceed();
        //添加Redis缓存
        stringRedisTemplate.opsForHash().put(typeName, newMethod, proceed);
        // System.out.println("缓存结束");
        return proceed;
    }

    @Around("@annotation(com.baizhi.annotation.DeleteRedisCacheAnnotation)")
    public Object deleteRedisCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        //获取类名称
        String typeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        stringRedisTemplate.opsForHash().delete(typeName);
        return proceed;
    }
}

package com.homework.rpc.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.homework.rpc.annotation.Service;
import com.homework.rpc.api.RpcfxRequest;
import com.homework.rpc.api.RpcfxResolver;
import com.homework.rpc.api.RpcfxResponse;
import com.homework.rpc.exception.RpcfxException;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Redick
 * @date 2020/12/14 11:17 下午
 */
public class RpcfxInvoker<T> {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        try {
            Reflections reflections = new Reflections((new ConfigurationBuilder()
                    .forPackages("com.redick.rpcfx.demo.provider")
                    .addScanners(new FieldAnnotationsScanner())
                    .addScanners(new MethodAnnotationsScanner())
                    .addScanners(new MethodParameterScanner())));
            Set<Class<?>> set = reflections.getTypesAnnotatedWith(Service.class);
            Object service = null;
            for (Class<?> clazz : set) {
                service = Class.forName(clazz.getName());
                String interfaceName = ((T)service).getClass().getSuperclass().getName();
                if (serviceClass.equals(interfaceName)) {
                    break;
                }
            }
            // 通过反射创建实现类的对象
            //Object service = resolver.resolve(serviceClass);
            // Object service = Class.forName("").newInstance();
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setException(new RpcfxException(e));
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}

package com.homework.rpc;

import com.homework.rpc.api.RegisterCenter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Redick
 * @date 2020/12/15 7:15 下午
 */
public class LocalRegisterCenter implements RegisterCenter {

    private static final Map<String, String> REGISTER = new ConcurrentHashMap<>(16);

    @Override
    public void register(String serviceName, String implName) {
        REGISTER.put(serviceName, implName);
    }

    @Override
    public String getImplName(String serviceName) {
        return REGISTER.get(serviceName);
    }

    private LocalRegisterCenter() {}

    private static LocalRegisterCenter instance;

    public static LocalRegisterCenter getInstance() {
        if (null == instance) {
            synchronized (LocalRegisterCenter.class) {
                if (null == instance) {
                    instance = new LocalRegisterCenter();
                }
            }
        }
        return instance;
    }

    @Override
    public List<String> getChildren(String nameSpace) throws Exception {
        return null;
    }
}

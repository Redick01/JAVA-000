package com.homework.rpc.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author Redick
 * @date 2020/12/21 11:23 上午
 */
@Data
@Builder
public class ServiceProviderDesc {
    private String host;
    private Integer port;
    private String serviceClass;

    // group
    // version
}
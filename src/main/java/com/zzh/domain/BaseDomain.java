package com.zzh.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhao on 2017/6/24.
 * 实现了Serializable接口，以便JVM可以序列化PO实例
 */
public class BaseDomain implements Serializable {
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

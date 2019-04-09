package com.sucl.sbjms.core.orm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author sucl
 * @date 2019/4/1
 */
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer","fieldHandler"})
public interface Domain extends Serializable {
}

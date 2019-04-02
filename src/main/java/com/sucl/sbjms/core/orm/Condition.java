package com.sucl.sbjms.core.orm;

/**
 * @author sucl
 * @date 2019/4/1
 */
public interface Condition {
    String getProperty();

    Opt getOperate();

    Object getValue();

    void setProperty(String prop);

    public enum Opt{
        EQ,
        NQ,
        IS_NULL,
        NOT_NULL,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        LEFT_LIKE,
        RIGHT_LIKE,
        BWT,
        IN,
        NOT_IN;
    }
}

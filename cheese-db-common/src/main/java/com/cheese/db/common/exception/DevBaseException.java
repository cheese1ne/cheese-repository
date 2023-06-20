package com.cheese.db.common.exception;

/**
 * devbase异常基类
 *
 * @author sobann
 */
public abstract class DevBaseException extends RuntimeException {

    public DevBaseException(String message) {
        super(message);
    }
}
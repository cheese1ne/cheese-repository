package com.cheese.db.core.exception;

/**
 * action参数未找到异常
 *
 * @author sobann
 */
public class ParamNotFountException extends DevBaseException {
    public ParamNotFountException() {
        super("Devbase action param not found, pls check the parameters of the method definition");
    }
}
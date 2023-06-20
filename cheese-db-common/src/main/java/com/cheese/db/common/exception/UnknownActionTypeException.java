package com.cheese.db.common.exception;

/**
 * 未知的ActionType异常
 *
 * @author sobann
 */
public class UnknownActionTypeException extends DevBaseException {
    public UnknownActionTypeException() {
        super("Non-existent actionType, pls check the action type of Action");
    }
}
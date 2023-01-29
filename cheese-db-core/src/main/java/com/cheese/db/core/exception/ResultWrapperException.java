package com.cheese.db.core.exception;

/**
 * 结果包装异常
 *
 * @author sobann
 */
public class ResultWrapperException extends DevBaseException {
    public ResultWrapperException(String message) {
        super("wrapper result error, the reason is " + message);
    }
}

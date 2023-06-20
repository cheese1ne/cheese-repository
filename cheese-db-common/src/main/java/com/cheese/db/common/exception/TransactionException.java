package com.cheese.db.common.exception;

/**
 * devbase事务异常
 *
 * @author sobann
 */
public class TransactionException extends DevBaseException {

    public TransactionException(Exception e) {
        super(e.getMessage());
    }
}

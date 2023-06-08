package com.cheese.db.spring.exception;

import com.cheese.db.core.exception.DevBaseException;

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

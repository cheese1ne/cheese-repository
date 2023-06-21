package com.cheese.db.common.wrapper;

import java.io.Serializable;

/**
 * action执行结果
 *
 * @author sobann
 */
public class ActionResult implements Serializable {
    private static final long serialVersionUID = -8751406300498875518L;

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

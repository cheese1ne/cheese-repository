package com.cheese.db.client.spring.props;

import com.cheese.db.rpc.common.enums.RpcEnum;

/**
 * 客户端配置项
 *
 * @author sobann
 */
public class DevBaseDBProps {

    private boolean enabled = true;

    private RpcEnum rpcType;

    private String rpcServer;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RpcEnum getRpcType() {
        return rpcType;
    }

    public void setRpcType(RpcEnum rpcType) {
        this.rpcType = rpcType;
    }

    public String getRpcServer() {
        return rpcServer;
    }

    public void setRpcServer(String rpcServer) {
        this.rpcServer = rpcServer;
    }
}

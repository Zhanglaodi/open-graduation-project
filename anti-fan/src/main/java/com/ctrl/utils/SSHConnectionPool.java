package com.ctrl.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * The type Ssh connection pool.
 */
public class SSHConnectionPool {

    private final ObjectPool<Session> pool;

    /**
     * Instantiates a new Ssh connection pool.
     */
    public SSHConnectionPool() {
        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts("~/.ssh/known_hosts");
            SSHSessionFactory factory = new SSHSessionFactory(jsch, "192.168.0.119", 22, "root", "root");
            GenericObjectPoolConfig<Session> poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setMaxTotal(10);
            pool = new GenericObjectPool<>(factory, poolConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws Exception the exception
     */
    public Session getConnection() throws Exception {
        return pool.borrowObject();
    }

    /**
     * Release connection.
     *
     * @param session the session
     * @throws Exception the exception
     */
    public void releaseConnection(Session session) throws Exception {
        pool.returnObject(session);
    }

    /**
     * Close.
     */
    public void close() {
        pool.close();
    }
}

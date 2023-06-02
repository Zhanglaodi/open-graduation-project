package com.ctrl.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * The type Ssh session factory.
 */
public class SSHSessionFactory extends BasePooledObjectFactory<Session> {
    private final JSch jsch;
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    /**
     * Instantiates a new Ssh session factory.
     *
     * @param jsch     the jsch
     * @param host     the host
     * @param port     the port
     * @param username the username
     * @param password the password
     */
    public SSHSessionFactory(JSch jsch, String host, int port, String username, String password) {
        this.jsch = jsch;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public Session create() throws Exception {
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    @Override
    public PooledObject<Session> wrap(Session session) {
        return new DefaultPooledObject<>(session);
    }

    @Override
    public void destroyObject(PooledObject<Session> p) {
        Session session = p.getObject();
        if (session.isConnected()) {
            session.disconnect();
        }
    }
}

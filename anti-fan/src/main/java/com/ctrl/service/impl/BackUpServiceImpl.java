package com.ctrl.service.impl;

import com.ctrl.entity.CommandResult;
import com.ctrl.entity.CommonResult;
import com.ctrl.service.BackUpService;
import com.ctrl.utils.SSHConnectionPool;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * The type Back up service.
 */
@Service
@Slf4j
public class BackUpServiceImpl implements BackUpService {


    /**
     * Back up my sql common result.
     *
     * @param userName the username
     * @param password the password
     * @param dbName   the db name
     * @return the common result
     */
    @Override
    public CommonResult<String> backUpMySql(String userName, String password, String dbName) throws Exception {
        String commandBuilder = "bash " +
                "/opt/shell/backup_database.sh " +
                " " + userName + " " + password + " " + dbName;
        CommandResult commandResult = backUpMethods(commandBuilder);
        log.info("执行信息:{}", commandResult);
        if (commandResult != null && commandResult.getExitCode() == 0) {
            return CommonResult.ok("执行成功", null);
        }
        return CommonResult.error(-1, "执行失败");
    }

    /**
     * 备份方法
     *
     * @param command command
     * @return CommandResult
     * @throws Exception exception
     */
    private CommandResult backUpMethods(String command) throws Exception {
        SSHConnectionPool connectionPool = new SSHConnectionPool();
        Session session = null;
        try {
            session = connectionPool.getConnection();
            return executeCommand(session, command);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                connectionPool.releaseConnection(session);
            }
            connectionPool.close();
        }
        return null;
    }

    /**
     * @param session session
     * @param command command
     * @return CommandResult
     * @throws Exception 异常
     */
    private static CommandResult executeCommand(Session session, String command) throws Exception {
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            StringBuilder output = new StringBuilder();

            try (InputStream inputStream = channel.getInputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.append(new String(buffer, 0, bytesRead));
                }
            }

            int exitCode = channel.getExitStatus();

            return new CommandResult(output.toString(), exitCode);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }

    }
}

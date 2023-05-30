package com.ctrl.service.impl;

import com.ctrl.entity.CommonResult;
import com.ctrl.service.BackUpService;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Back up service.
 */
@Service
public class BackUpServiceImpl implements BackUpService {
    @Value("${ssh.host}")
    private String host;

    @Value("${ssh.port}")
    private int port;

    @Value("${ssh.username}")
    private String username;

    @Value("${ssh.password}")
    private String sshPassword;

    /**
     * Back up my sql common result.
     *
     * @param userName the username
     * @param password the password
     * @param dbName   the db name
     * @return the common result
     */
    @Override
    public CommonResult<String> backUpMySql(String userName, String password, String dbName) throws IOException {
        try (SSHClient sshClient = new SSHClient()) {
            // 设置SSH连接的配置
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(host, port);
            sshClient.authPassword(username, sshPassword);
            // 构建远程命令
            String scriptPath = "/opt/shell/backup_database.sh ";
            String commandBuilder = "bash " +
                    scriptPath +
                    " " + userName + " " + password + " " + dbName;
            String command = commandBuilder.trim();
            Session session = sshClient.startSession();
            Session.Command commandObj = session.exec(command);
            commandObj.join();
            int exitCode = commandObj.getExitStatus();
            commandObj.close();
            session.close();
            if (exitCode == 0) {
                return CommonResult.ok("执行成功", null);
            }
            return CommonResult.error(-1, "执行失败");
        }
    }
}

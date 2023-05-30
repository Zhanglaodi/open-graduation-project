package com.ctrl.service;

import com.ctrl.entity.CommonResult;

import java.io.IOException;

/**
 * The interface Back up service.
 */
public interface BackUpService {
    /**
     * Back up my sql common result.
     *
     * @param userName the user name
     * @param password the password
     * @param dbName   the db name
     * @return the common result
     */
    CommonResult<String> backUpMySql(String userName, String password, String dbName) throws IOException;
}

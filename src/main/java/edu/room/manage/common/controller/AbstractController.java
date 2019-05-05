package edu.room.manage.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基础 controller
 *
 * @author 执笔
 * @date 2019/3/22 9:46
 */
@Component
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(AbstractController.class);
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;
}

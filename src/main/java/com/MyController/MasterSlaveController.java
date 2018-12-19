package com.MyController;

import com.MyDao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Copyright (C), 2018-2018
 * FileName: MasterSlaveController
 * Date:     2018/12/19 14:05
 * Description:主备库测试
 */
@Controller
@RequestMapping("/master")
public class MasterSlaveController {

    @Autowired
    private DaoService daoService;

    @RequestMapping(value = "/test.do",method = RequestMethod.GET)
    public String index() {
        //http://localhost/master/test.do
        daoService.getList();
        return "index";
    }
}

package com.MyDao.impl;

import com.MyDao.DaoService;
import com.MyMapper.read.ReadMapper;
import com.MyMapper.write.WriteMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2018
 * FileName: DaoServiceImpl
 * Date:     2018/12/19 14:33
 * Description:
 */

@Service("daoService")
public class DaoServiceImpl implements DaoService {

    @Autowired
    private ReadMapper readMapper;
    @Autowired
    private WriteMapper writeMapper;

    @Override
    public List<String> getList() {

        List<String> stringList = new ArrayList<>();
        System.out.println("readMapper 为空；");
        System.out.println(JSON.toJSONString(stringList));
        stringList = readMapper.getList();
        System.out.println("readMapper");
        System.out.println(JSON.toJSONString(stringList));
        stringList = null;
        System.out.println("writeMapper 为空；");
        stringList = writeMapper.getList();
        System.out.println("writeMapper");
        System.out.println(JSON.toJSONString(stringList));
        return null;
    }

    public ReadMapper getReadMapper() {
        return readMapper;
    }

    public void setReadMapper(ReadMapper readMapper) {
        this.readMapper = readMapper;
    }

    public WriteMapper getWriteMapper() {
        return writeMapper;
    }

    public void setWriteMapper(WriteMapper writeMapper) {
        this.writeMapper = writeMapper;
    }
}
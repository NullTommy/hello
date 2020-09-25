package com.MyJunit;

import com.MyDao.DaoService;
import com.MyModel.MyBean;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @return:
 * @since: 1.0.0
 * @Date: 2020/9/25 0025 14:25
 */
@RunWith(SpringJUnit4ClassRunner.class) //指定测试用例的运行器 这里是指定了Junit4
@ContextConfiguration("classpath:mvc-dispatcher.xml")
@WebAppConfiguration
public class TestSpring {

    @Autowired
    private DaoService daoService;
    @Resource
    private MyBean myBean;

    @Test
    public void testDaoService() {
        System.out.println("测试开始");
        daoService.getList();
        System.out.println("测试结束");
        System.out.println(myBean.getName());
    }

}

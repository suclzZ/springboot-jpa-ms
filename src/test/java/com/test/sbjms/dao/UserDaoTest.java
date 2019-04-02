package com.test.sbjms.dao;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.core.orm.jpa.JpaOrCondition;
import com.sucl.sbjms.core.service.impl.CustomSpecification;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.UserService;
import com.test.sbjms.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/1
 */
public class UserDaoTest extends Test {
    @Resource
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @org.junit.Test
    public void test(){
        t3();
    }

    public void t1(){
//        User user = userDao.getOne("1");
        List<Condition> conditions = new ArrayList<>();
        Condition condition = new Condition() {
            @Override
            public String getProperty() {
                return "userId";
            }

            @Override
            public Opt getOperate() {
                return Opt.EQ;
            }

            @Override
            public Object getValue() {
                return "1";
            }

            @Override
            public void setProperty(String prop) {

            }
        };
        conditions.add(condition);
        Specification<User> spec = new CustomSpecification<User>(conditions);
        User user = userDao.findOne(spec);
        System.out.println(user);
    }

    public void t2(){
        User user = new User();
        user.setSex("1");
        List<User> user2 = userService.getAll(user);
        System.out.println(user2);
    }

    public void t3(){
        List<Condition> conds = new ArrayList<>();
        conds.add(new JpaCondition("userId","1"));
        conds.add(new JpaOrCondition("username","tom"));
        conds.add(new JpaCondition("age","26"));
        conds.add(new JpaCondition("agency.agencyId","1"));
        List<User> users = userService.getAll(conds);
        System.out.println(users);
    }

}

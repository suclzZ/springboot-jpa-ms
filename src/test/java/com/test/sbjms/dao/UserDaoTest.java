package com.test.sbjms.dao;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.jpa.JpaCondition;
import com.sucl.sbjms.core.orm.jpa.JpaOrCondition;
import com.sucl.sbjms.core.orm.jpa.NestedCondition;
import com.sucl.sbjms.core.service.impl.CustomSpecification;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.UserService;
import com.test.sbjms.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        t4();
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
        conds.add(new JpaCondition("age",Condition.Opt.LE,"26"));

        NestedCondition nestedCondition = new NestedCondition(new JpaCondition("userId","2"),new JpaCondition("username","abc"));
        conds.add(nestedCondition);

//        conds.add(new JpaCondition("agency.agencyId","3"));
//        conds.add(new JpaCondition("agency.agencyCode","003"));
//        conds.add(new JpaCondition("agency.company.companyId","c2"));// 二级关联查询，还没解决
        List<User> users = userService.getAll2(conds);
        System.out.println(users);
    }

    public void t4() {
        List<Condition> conds = new ArrayList<>();
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-02");
            conds.add(new JpaCondition("createDate", Condition.Opt.GT,"2019-04-02"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<User> users = userService.getAll2(conds);
        System.out.println(users);
    }

}

package com.sucl.sbjms.core.web;

import com.sucl.sbjms.core.method.annotation.QueryCondition;
import com.sucl.sbjms.core.method.annotation.QueryOrder;
import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Domain;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.Pager;
import com.sucl.sbjms.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
public abstract class BaseController<S extends BaseService<?,T>,T> {

    @Autowired
    protected S service;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public T get(@PathVariable String id){
        return service.getById(id);
    }

    /**
     * 根据条件查询
     * @param conditions
     * @return
     */
    @GetMapping
    public List<T> getAll(@QueryCondition Collection<Condition> conditions){
        return  service.getAll2(conditions);
    }

    /**
     * 按道理查询条件需要加上类型约束，只对指定类型的属性进行封装，但是T是泛型，没办法获取class
     * @param pager
     * @param conditions
     * @param orders
     * @return
     */
    @GetMapping(params = {"pager:pageIndex","pager:pageSize"})
    public Pager<T> getPager(Pager pager, @QueryCondition Collection<Condition> conditions,@QueryOrder Collection<Order> orders){
        return service.getPager(pager,conditions,orders);
    }

    /**
     * 保存或更新
     * 原则上是：有主键则根据主键查找，没有数据异常；没主键则新增
     * @param t
     * @return
     */
    @PostMapping
    public T saveOrUpdate(T t){
        return service.saveOrUpdate(t);
    }

    /**
     * 根据主键删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.deleteById(id);
    }
}

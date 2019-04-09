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

    @GetMapping("/{id}")
    public T get(@PathVariable String id){
        return service.getById(id);
    }

    @GetMapping
    public List<T> getAll(Collection<Condition> conditions){
        return  service.getAll2(conditions);
    }

    @GetMapping(params = {"pager:pageIndex","pager:pageSize"})
    public Pager<T> getPager(Pager pager, @QueryCondition Collection<Condition> conditions,@QueryOrder Collection<Order> orders){
        return service.getPager(pager,conditions,orders);
    }

    @PostMapping
    public T saveOrUpdate(T t){
        return service.saveOrUpdate(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.deleteById(id);
    }
}

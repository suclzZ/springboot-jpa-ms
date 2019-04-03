package com.sucl.sbjms.core.web;

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
public class BaseController<S extends BaseService<?,T>,T> {

    @Autowired
    protected S s;

    @GetMapping("/{id}")
    public T get(@PathVariable String id){
        return s.getById(id);
    }

    @GetMapping
    public List<T> getAll(Collection<Condition> conditions){
        return  s.getAll(conditions);
    }

    @GetMapping(params = {"pageIndex","pageSize"})
    public Pager<T> getPager(Pager pager, Collection<Condition> conditions, Collection<Order> orders){
        return s.getPager(pager,conditions,orders);
    }

    @PostMapping
    public T saveOrUpdate(T t){
        return s.saveOrUpdate(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        s.deleteById(id);
    }
}

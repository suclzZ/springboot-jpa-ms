package com.sucl.sbjms.core.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.Order;
import com.sucl.sbjms.core.orm.Pager;
import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.core.util.ConditionHelper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 方便之后对泛型R的使用，放弃Repository类型注入，改为JpaRepository,否则需要做各种类型校验与转换
 *
 * @author sucl
 * @date 2019/4/1
 */
public abstract class BaseServiceImpl<R extends Repository<T,Serializable>,T> implements BaseService<R,T>{

    /**
     * 通过examle查询
     */
    protected JpaRepository<T,Serializable> repository;
    /**
     * 通过Specification查询
     */
    protected JpaSpecificationExecutor<T> specificationExecutor;

    protected abstract Class<T> getDomainClazz();

    @Resource
    public void setRepository(R r) {
        if(r instanceof JpaRepository){
            this.repository = (JpaRepository<T, Serializable>) r;
        }
        if(r instanceof JpaSpecificationExecutor){
            this.specificationExecutor = (JpaSpecificationExecutor<T>) r;
        }
    }

    @Override
    public T getById(Serializable id) {
        return repository.getOne(id);
    }

    @Override
    public T getOne(String property, Object value) {
        return repository.findOne(ConditionHelper.buildExample(getDomainClazz(),property,value));
    }

    @Override
    public List<T> getAll(Collection<Condition> conditions) {
        return specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions));
    }

    @Override
    public List<T> getAll(T t) {
        return repository.findAll(Example.of(t));
    }

    @Override
    public Pager<T> getPager(Pager pager, Collection<Condition> conditions, Collection<Order> orders) {
        Pageable pageable = new PageRequest(pager.getPageIndex(),pager.getPageSize(),ConditionHelper.buildSort(orders));
        specificationExecutor.findAll(ConditionHelper.buildSpecification(conditions),pageable);
        return null;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void saveBatch(Collection<T> ts) {
        repository.save(ts);
    }

    @Override
    public T updateById(T t) {
        return repository.save(t);
    }

    @Override
    public T saveOrUpdate(T t) {
        return repository.save(t);
    }

    @Override
    public void deleteById(Serializable id) {
        repository.delete(id);
    }

    @Override
    public void delete(String property, Object value) {

    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteAll(Collection<T> ts) {
        repository.deleteInBatch(ts);
    }

    @Override
    public boolean exist(Serializable id) {
        return repository.exists(id);
    }

    @Override
    public boolean exist(T t) {
        return repository.exists(Example.of(t));
    }
}

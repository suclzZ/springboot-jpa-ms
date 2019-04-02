package com.sucl.sbjms.core.service.impl;

import com.sucl.sbjms.core.orm.Condition;
import com.sucl.sbjms.core.orm.OrCondition;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.jpa.internal.metamodel.EntityTypeImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 配合JpaSpecificationExecutor接口实现多条件查询
 * 关联查询
 * 嵌套查询
 * @author sucl
 * @date 2019/4/1
 */
public class CustomSpecification<T> implements Specification {
    private Collection<Condition> conditions;
    private Map<String,Set<String>> entityField = new HashMap<>();//ConcurrentHashMap<>();
    private Map<String,Attribute> entityAttribute = new HashMap<>();

    public CustomSpecification(){}

    public CustomSpecification(Collection<Condition> conditions){
        this.conditions = conditions;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(conditions!=null){
            conditions.stream().forEach(c->{
                boolean or = OrCondition.class.isAssignableFrom(c.getClass());//是否or
                boolean use = false;
                if(isProperty(root,c)){
                    use = true;
                }
                if(isRelateProperty(c)){
                    Class clazz = getRelationClazz(root,c);
                    if(clazz !=null){
                        CriteriaQuery relQuery = criteriaBuilder.createQuery(clazz);
                        Subquery subquery = relQuery.subquery(clazz);
                        Root subRoot = subquery.from(clazz);
                        subquery.select(subRoot);
                        Predicate subPredicate = criteriaBuilder.and(predicate(criteriaBuilder, subRoot, c));;
                        subquery.where(subPredicate);
                    }
                }
                if(use){
                    if(!or){
                        predicates.add(criteriaBuilder.and(predicate(criteriaBuilder,root,c)) );
                    }else{

                    }
                }
            });
        }
//        query.subquery();
//        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//        return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Class getRelationClazz(Root root, Condition c) {
        String[] props = c.getProperty().split("\\.");
        String entityClassName = ((EntityTypeImpl) root.getModel()).getTypeName();
        Attribute attr = entityAttribute.get(entityClassName + "." + props[0]);
        c.setProperty(props[1]);
        return attr.getJavaType();
    }

    /**
     * 是否是对象属性
     * @param root
     * @param c
     * @return
     */
    private boolean isProperty(Root root, Condition c) {
        EntityType model = root.getModel();
        String entityClassName = ((EntityTypeImpl) model).getTypeName();
        Set<String> fields = null;
        if(entityField.get(entityClassName)!=null){
            fields = entityField.get(entityClassName);
        }else{
            Set<Attribute> attrs = model.getDeclaredAttributes();
            fields = attrs.stream().map(s -> {
                entityAttribute.put(entityClassName+"."+s.getName(),s);
                return s.getName();
            }).collect(Collectors.toSet());
            entityField.put(entityClassName,fields);
        }
        return fields.contains(c.getProperty());
    }

    /**
     * 关联字段
     * @param c
     * @return
     */
    private boolean isRelateProperty(Condition c) {
        if(c!=null && c.getProperty().indexOf(".")!=-1){
            return true;
        }
        return false;
    }


    private Predicate predicate(CriteriaBuilder criteriaBuilder,Root<T> root, Condition condition){
        String property = condition.getProperty();
        Condition.Opt operate = condition.getOperate();
        Object value = condition.getValue();
        Predicate predicate = null;
        //校验property是否合法
        if(value!=null){
            switch (operate){
                case EQ:
                    predicate = criteriaBuilder.equal(root.get(property).as(String.class),value);
                    break;
                case NQ:
                    predicate = criteriaBuilder.notEqual(root.get(property).as(String.class),value);
                    break;
                case IS_NULL:
                    predicate = criteriaBuilder.isNull(root.get(property).as(String.class));
                    break;
                case NOT_NULL:
                    predicate = criteriaBuilder.isNotNull(root.get(property).as(String.class));
                    break;
                case GT:
                    predicate = criteriaBuilder.greaterThan(root.get(property),value.toString());
                    break;
                case GE:
                    predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(property),value.toString());
                    break;
                case LT:
                    predicate = criteriaBuilder.lessThan(root.get(property),property.toString());
                    break;
                case LE:
                    predicate = criteriaBuilder.lessThanOrEqualTo(root.get(property),value.toString());
                    break;
                case LIKE:
                    predicate = criteriaBuilder.like(root.get(property).as(String.class),"%"+value.toString()+"%");
                    break;
                case LEFT_LIKE:
                    predicate = criteriaBuilder.like(root.get(property).as(String.class),value.toString()+"%");
                    break;
                case RIGHT_LIKE:
                    predicate = criteriaBuilder.like(root.get(property).as(String.class),"%"+value.toString());
                    break;
                case BWT:
                    String[] values = value.toString().trim().split(",|，");
                    assert values.length!=2:"the format of value:"+value+" do not be supported,must contain ','";
                    predicate = criteriaBuilder.between(root.get(property),values[0],values[1]);
                    break;
                case IN:
                    String[] value2s = value.toString().trim().split(",");
                    if(value2s!=null && value2s.length>0){
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(property));
                        for(String inVal : value2s){
                            in.value(inVal);
                        }
                        predicate = in;
                    }
                    break;
                case NOT_IN:

                    break;
            }
        }else{
            if(operate == Condition.Opt.EQ || operate == Condition.Opt.IS_NULL){
                predicate = criteriaBuilder.isNull(root.get(property).as(String.class));
            }
            if(operate == Condition.Opt.NQ || operate == Condition.Opt.NOT_NULL){
                predicate = criteriaBuilder.isNotNull(root.get(property).as(String.class));
            }
        }
        return predicate;
    }
}

package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.Condition;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 嵌套查询条件
 * @author sucl
 * @date 2019/4/2
 */
public class NestedCondition implements Condition {

    private JpaCondition condition1;
    private JpaCondition condition2;

    public NestedCondition(JpaCondition condition1, JpaCondition condition2) {
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    public Criterion generateExpression() {
        return Restrictions.or(convertToCriterion(this.condition1), convertToCriterion(this.condition1));
    }

    private Criterion convertToCriterion(Condition condition) {
        if( condition instanceof JpaCondition ){
            return ((JpaCondition) condition).generateExpression(null);
        }else if(condition instanceof NestedCondition){
            return ((NestedCondition) condition).generateExpression();
        }
        return null;
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Opt getOperate() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setProperty(String prop) {

    }
}

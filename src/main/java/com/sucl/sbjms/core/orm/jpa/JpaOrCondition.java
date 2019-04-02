package com.sucl.sbjms.core.orm.jpa;

import com.sucl.sbjms.core.orm.OrCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * or 查询条件
 * @author sucl
 * @date 2019/4/1
 */
@Data
@NoArgsConstructor
public class JpaOrCondition extends JpaCondition implements OrCondition {

    public JpaOrCondition(String property,Object value){
        this(property,Opt.EQ,value);
    }

    public JpaOrCondition(String property,Opt opt,Object value){
        super(property,opt,value);
    }
}

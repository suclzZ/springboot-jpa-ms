package com.sucl.sbjms.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/4/3
 */
@Data
@Entity
@Table(name = "COMPANY")
public class Company {
    @Id
    @Column(name = "company_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String companyId;

    @Column(name = "description",length = 256)
    private String description;

}

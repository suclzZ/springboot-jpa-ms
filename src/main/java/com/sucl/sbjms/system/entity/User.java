package com.sucl.sbjms.system.entity;

import com.sucl.sbjms.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Temporal
 * @Transient
 *
 * @author sucl
 * @date 2019/4/1
 */
@Data
@Entity
@Table(name = "USER")
public class User implements Domain {

    @Id
    @Column(name = "USER_ID",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @Column(name = "username",length = 24)
    private String username;

    @Column(name = "password",length = 56)
    private String password;

    @Column(name = "user_caption",length = 56)
    private String userCaption;

    @Column(name = "sex",length = 2)
    private String sex;

    @Column(name = "age",length = 3)
    private String age;

    @Column(name = "telephone",length = 16)
    private String telephone;

    @Column(name = "email",length = 56)
    private String email;

    @Column(name = "address",length = 128)
    private String address;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "description",length = 256)
    private String description;
}

package com.sucl.sbjms.system.service;

import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.system.dao.UserDao;
import com.sucl.sbjms.system.entity.User;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 * @author sucl
 * @date 2019/4/2
 */
@RepositoryRestController
public interface UserService extends BaseService<UserDao,User>{

}

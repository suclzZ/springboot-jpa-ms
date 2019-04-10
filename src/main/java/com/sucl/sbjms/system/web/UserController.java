package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.entity.User;
import com.sucl.sbjms.system.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService,User> {

    @GetMapping(value = "/{id}",params = {"initialize"})
    public User getUser(@PathVariable String id,@RequestParam String[] initialize){
        return service.getInitializeObject(id,initialize);
    }

    @PostMapping("/authc")
    public void authc(User user){
        List<Role> roles = user.getRoles();
        user = service.getById(user.getUserId());
        user.setRoles(roles);
        service.save(user);
    }
}

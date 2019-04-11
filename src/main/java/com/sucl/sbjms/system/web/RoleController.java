package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Menu;
import com.sucl.sbjms.system.entity.Role;
import com.sucl.sbjms.system.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleService,Role> {

    @PostMapping("/authc")
    public void menuAuthc(Role role){
        List<Menu> menus = role.getMenus();
        role = service.getById(role.getRoleCode());
        role.setMenus(menus);
        service.save(role);
    }
}

package com.sucl.sbjms.system.service.impl;

import com.sucl.sbjms.core.service.impl.BaseServiceImpl;
import com.sucl.sbjms.system.dao.MenuDao;
import com.sucl.sbjms.system.entity.Menu;
import com.sucl.sbjms.system.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<MenuDao,Menu> implements MenuService {
    @Override
    protected Class<Menu> getDomainClazz() {
        return Menu.class;
    }
}

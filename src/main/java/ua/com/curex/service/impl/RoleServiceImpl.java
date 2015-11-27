/* 
 * Copyright (c) 2015
 */

package ua.com.curex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.inject.Inject;

import ua.com.curex.domain.Role;
import ua.com.curex.service.RoleService;
import ua.com.curex.dao.RoleDao;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService  {
@Inject private RoleDao roleDao;
	
	public List<Role> getRoleList() {
        return roleDao.getRoleList();
    }
}

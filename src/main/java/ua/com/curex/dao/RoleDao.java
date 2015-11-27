/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.util.List;

import ua.com.curex.domain.Role;


/**
 * @author Irochka
 */
public interface RoleDao {
	
	Role findByCode(String code);
	
	public List<Role> getRoleList();
}

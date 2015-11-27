/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.RoleDao;
import ua.com.curex.domain.Role;

/**
 * @author Irochka
 */
@Repository
public class HbnRoleDao extends AbstractHbnDao<Role> implements RoleDao {

	public Role findByCode(String code) {
		Query q = getSession().getNamedQuery("role.byCode");
		q.setParameter("code", code);
		return (Role) q.uniqueResult();
	}
	
	public List<Role> getRoleList(){
		return this.getAll();
	}

	@Override
	public Page<Role> findAll(int page, int size) {
		return findAll(page,size);
	}
}

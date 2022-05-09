/**
 * 
 */
package com.jeeplus.modules.base.classgroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroup;
import com.jeeplus.modules.base.classgroup.mapper.BaseClassGroupMapper;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroupUser;
import com.jeeplus.modules.base.classgroup.mapper.BaseClassGroupUserMapper;

/**
 * 班组管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseClassGroupService extends CrudService<BaseClassGroupMapper, BaseClassGroup> {

	@Autowired
	private BaseClassGroupUserMapper baseClassGroupUserMapper;
	
	public BaseClassGroup get(String id) {
		BaseClassGroup baseClassGroup = super.get(id);
		baseClassGroup.setBaseClassGroupUserList(baseClassGroupUserMapper.findList(new BaseClassGroupUser(baseClassGroup)));
		return baseClassGroup;
	}
	
	public List<BaseClassGroup> findList(BaseClassGroup baseClassGroup) {
		return super.findList(baseClassGroup);
	}
	
	public Page<BaseClassGroup> findPage(Page<BaseClassGroup> page, BaseClassGroup baseClassGroup) {
		return super.findPage(page, baseClassGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseClassGroup baseClassGroup) {
		super.save(baseClassGroup);
		for (BaseClassGroupUser baseClassGroupUser : baseClassGroup.getBaseClassGroupUserList()){
			if (baseClassGroupUser.getId() == null){
				continue;
			}
			if (BaseClassGroupUser.DEL_FLAG_NORMAL.equals(baseClassGroupUser.getDelFlag())){
				if (StringUtils.isBlank(baseClassGroupUser.getId())){
					baseClassGroupUser.setP(baseClassGroup);
					baseClassGroupUser.preInsert();
					baseClassGroupUserMapper.insert(baseClassGroupUser);
				}else{
					baseClassGroupUser.preUpdate();
					baseClassGroupUserMapper.update(baseClassGroupUser);
				}
			}else{
				baseClassGroupUserMapper.delete(baseClassGroupUser);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseClassGroup baseClassGroup) {
		super.delete(baseClassGroup);
		baseClassGroupUserMapper.delete(new BaseClassGroupUser(baseClassGroup));
	}
	
}
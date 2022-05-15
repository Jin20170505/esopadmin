/**
 * 
 */
package com.jeeplus.modules.base.cangku.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.api.bean.ckandhw.CkBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;

/**
 * 仓库档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseCangKuService extends CrudService<BaseCangKuMapper, BaseCangKu> {

	public BaseCangKu get(String id) {
		return super.get(id);
	}
	
	public List<BaseCangKu> findList(BaseCangKu baseCangKu) {
		return super.findList(baseCangKu);
	}
	
	public Page<BaseCangKu> findPage(Page<BaseCangKu> page, BaseCangKu baseCangKu) {
		return super.findPage(page, baseCangKu);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseCangKu baseCangKu) {
		super.save(baseCangKu);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseCangKu baseCangKu) {
		super.delete(baseCangKu);
	}


	public List<CkBean> findAllCk(){
		List<BaseCangKu> cangKus = mapper.findAllCk();
		if(cangKus==null||cangKus.isEmpty()){
			return new ArrayList<>(0);
		}
		List<CkBean> ckBeans = new ArrayList<>(cangKus.size());
		cangKus.forEach(d->{
			CkBean ckBean = new CkBean();
			ckBean.setValue(d.getId()).setText(d.getName()+"("+d.getFactory().getName()+")");
			ckBeans.add(ckBean);
		});
		return ckBeans;
	}
}
/**
 * 
 */
package com.jeeplus.modules.base.cangku.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.api.bean.ckandhw.CkBean;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.u8data.warehouse.entity.U8WareHouse;
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

	@Transactional(readOnly = false)
	public void sychU8(List<U8WareHouse> list){
		List<BaseCangKu> data = new ArrayList<>(list.size());
		list.forEach(w->{
			BaseCangKu cangKu = new BaseCangKu();
			cangKu.preInsert();
			cangKu.setId(w.getcWhCode());
			cangKu.setName(w.getcWhName());
			cangKu.setCode(w.getcWhCode());
			cangKu.setAddress(w.getcWhAddress());
			cangKu.setUsehw(w.getbWhPos());
			cangKu.setMaster(w.getcWhPerson());
			cangKu.setTel(w.getcWhPhone());
			if(w.getdWhEndDate()==null){
				cangKu.setIndate(DateUtils.parseDate("2050-12-31 23:59:59"));
			}else {
				cangKu.setIndate(w.getdWhEndDate());
			}
			data.add(cangKu);
		});
		saveU8Data(data);
	}

	@Transactional(readOnly = false)
	public void saveU8Data(List<BaseCangKu> data){
		if(!data.isEmpty()){
			int i = 0;
			int j = 0;
			int mlen = data.size();
			while (i<mlen){
				j = i;
				i = i+300;
				if(i>=mlen){
					mapper.batchInsert(data.subList(j,mlen));
				}else {
					mapper.batchInsert(data.subList(j,i));
				}
			}
		}
	}
}
/**
 * 
 */
package com.jeeplus.modules.base.huowei.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.u8data.warehouse.entity.U8Position;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;

/**
 * 货位档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseHuoWeiService extends CrudService<BaseHuoWeiMapper, BaseHuoWei> {

	public BaseHuoWei get(String id) {
		return super.get(id);
	}
	
	public List<BaseHuoWei> findList(BaseHuoWei baseHuoWei) {
		return super.findList(baseHuoWei);
	}
	
	public Page<BaseHuoWei> findPage(Page<BaseHuoWei> page, BaseHuoWei baseHuoWei) {
		return super.findPage(page, baseHuoWei);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseHuoWei baseHuoWei) {
		super.save(baseHuoWei);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseHuoWei baseHuoWei) {
		super.delete(baseHuoWei);
	}

	public List<BaseHuoWei> findPrintInfo(String rids){
		List<BaseHuoWei> list = Lists.newArrayList();
		Arrays.asList(rids.split(",")).forEach(id->{
			list.add(mapper.getInfoById(id));
		});
		return list;
	}

	@Transactional(readOnly = false)
	public void sychU8(List<U8Position> list){
		BaseHuoWei huoWei = null;
		List<BaseHuoWei> data = new ArrayList<>(list.size());
		for (U8Position position : list) {
			huoWei = new BaseHuoWei();
			huoWei.preInsert();
			huoWei.setId(position.getcPosCode());
			huoWei.setCode(position.getcPosCode());
			huoWei.setName(position.getcPosName());
			huoWei.setCk(new BaseCangKu(position.getcWhCode()));
			data.add(huoWei);
		}
		saveU8Data(data);
	}

	@Transactional(readOnly = false)
	public void saveU8Data(List<BaseHuoWei> data){
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
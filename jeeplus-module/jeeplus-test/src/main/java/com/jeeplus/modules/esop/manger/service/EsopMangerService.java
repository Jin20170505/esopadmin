/**
 * 
 */
package com.jeeplus.modules.esop.manger.service;

import java.util.Arrays;
import java.util.List;

import com.jeeplus.modules.api.bean.ApiFileViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.esop.manger.entity.EsopManger;
import com.jeeplus.modules.esop.manger.mapper.EsopMangerMapper;
import com.jeeplus.modules.esop.manger.entity.EsopMangerSub;
import com.jeeplus.modules.esop.manger.mapper.EsopMangerSubMapper;

/**
 * ESOP工单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class EsopMangerService extends CrudService<EsopMangerMapper, EsopManger> {

	@Autowired
	private EsopMangerSubMapper esopMangerSubMapper;
	
	public EsopManger get(String id) {
		EsopManger esopManger = super.get(id);
		esopManger.setEsopMangerSubList(esopMangerSubMapper.findList(new EsopMangerSub(esopManger)));
		return esopManger;
	}
	
	public List<EsopManger> findList(EsopManger esopManger) {
		return super.findList(esopManger);
	}
	
	public Page<EsopManger> findPage(Page<EsopManger> page, EsopManger esopManger) {
		return super.findPage(page, esopManger);
	}
	
	@Transactional(readOnly = false)
	public void save(EsopManger esopManger) {
		if(StringUtils.isNotEmpty(esopManger.getId())){
			esopMangerSubMapper.delete(new EsopMangerSub(esopManger));
		}
		super.save(esopManger);
		for (EsopMangerSub esopMangerSub : esopManger.getEsopMangerSubList()){
			if (esopMangerSub.getId() == null){
				continue;
			}
			if (EsopMangerSub.DEL_FLAG_NORMAL.equals(esopMangerSub.getDelFlag())){
				esopMangerSub.setM(esopManger);
				esopMangerSub.preInsert();
				esopMangerSubMapper.insert(esopMangerSub);
			}else{
				esopMangerSubMapper.delete(esopMangerSub);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(EsopManger esopManger) {
		super.delete(esopManger);
		esopMangerSubMapper.delete(new EsopMangerSub(esopManger));
	}


	@Transactional(readOnly = false)
	public void updateStatus(String ids,String status){
		Arrays.asList(ids.split(",")).forEach(id->mapper.updateStatus(id,status));
	}

	public List<ApiFileViewBean> findFile(String productid,String site,String name,int page,int size){
		int from = (page-1) * size;
		return esopMangerSubMapper.findFile(productid, site, name, from, size);
	}

	public int countPageSize(String productid,String site,String name,int size){
		int len = esopMangerSubMapper.countFile(productid, site, name);
		if(len%size==0){
			return len/size;
		}else {
			return  1 + len/size;
		}
	}

	public String getFileUrl(String id){
		return esopMangerSubMapper.getFileUrl(id);
	}
}
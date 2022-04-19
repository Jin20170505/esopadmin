/**
 * 
 */
package com.jeeplus.modules.esop.filemanger.service;

import java.util.Arrays;
import java.util.List;

import com.jeeplus.modules.esop.filemanger.entity.FileBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.esop.filemanger.entity.EsopFileManger;
import com.jeeplus.modules.esop.filemanger.mapper.EsopFileMangerMapper;

/**
 * ESOP文件管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class EsopFileMangerService extends CrudService<EsopFileMangerMapper, EsopFileManger> {

	public EsopFileManger get(String id) {
		return super.get(id);
	}
	
	public List<EsopFileManger> findList(EsopFileManger esopFileManger) {
		return super.findList(esopFileManger);
	}
	
	public Page<EsopFileManger> findPage(Page<EsopFileManger> page, EsopFileManger esopFileManger) {
		return super.findPage(page, esopFileManger);
	}
	
	@Transactional(readOnly = false)
	public void save(EsopFileManger esopFileManger) {
		super.save(esopFileManger);
	}
	
	@Transactional(readOnly = false)
	public void delete(EsopFileManger esopFileManger) {
		super.delete(esopFileManger);
	}

	@Transactional(readOnly = false)
	public void xiafaOrhuishou(String ids,String status){
		Arrays.asList(ids.split(",")).forEach(id->mapper.updateStatus(id,status));
	}

	public List<FileBean> findFiles(String name,int page,int size) {
		int from = (page-1)*size;
		return mapper.findFiles(name,size,from);
	}

	public int getPageSize(String name,int size){
		int rows = mapper.countFiles(name);
		if(rows%size==0){
			return rows/size;
		}else {
			return rows/size +1;
		}
	}

	public String getUrl(String id){
		return mapper.getUrl(id);
	}
	
}
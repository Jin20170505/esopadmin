/**
 * 
 */
package com.jeeplus.modules.business.filemanger.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.filemanger.entity.BussinessFileManger;
import com.jeeplus.modules.business.filemanger.mapper.BussinessFileMangerMapper;

/**
 * 文件档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BussinessFileMangerService extends CrudService<BussinessFileMangerMapper, BussinessFileManger> {

	public BussinessFileManger get(String id) {
		return super.get(id);
	}
	
	public List<BussinessFileManger> findList(BussinessFileManger bussinessFileManger) {
		return super.findList(bussinessFileManger);
	}
	
	public Page<BussinessFileManger> findPage(Page<BussinessFileManger> page, BussinessFileManger bussinessFileManger) {
		return super.findPage(page, bussinessFileManger);
	}
	
	@Transactional(readOnly = false)
	public void save(BussinessFileManger bussinessFileManger) {
		super.save(bussinessFileManger);
	}
	
	@Transactional(readOnly = false)
	public void delete(BussinessFileManger bussinessFileManger) {
		super.delete(bussinessFileManger);
	}


	@Transactional(readOnly = false)
	public void updateStatus(String ids,String status){
		Arrays.asList(ids.split(",")).forEach(id->{
			mapper.updatestatus(id,status);
		});
	}

	/**
	 * 获取文件物理路径
	 * @param id
	 * @return 文件物理路径
	 */
	public String getFilePath(String id){
		return mapper.getFilePath(id);
	}
	
}
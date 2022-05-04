/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.filemanger.service;

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
 * @version 2022-05-04
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
	
}
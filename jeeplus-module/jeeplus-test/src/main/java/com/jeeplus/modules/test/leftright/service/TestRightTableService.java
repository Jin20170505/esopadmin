/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.leftright.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.leftright.entity.TestRightTable;
import com.jeeplus.modules.test.leftright.mapper.TestRightTableMapper;

/**
 * 右表Service
 * @author Jin
 * @version 2022-05-04
 */
@Service
@Transactional(readOnly = true)
public class TestRightTableService extends CrudService<TestRightTableMapper, TestRightTable> {

	public TestRightTable get(String id) {
		return super.get(id);
	}
	
	public List<TestRightTable> findList(TestRightTable testRightTable) {
		return super.findList(testRightTable);
	}
	
	public Page<TestRightTable> findPage(Page<TestRightTable> page, TestRightTable testRightTable) {
		return super.findPage(page, testRightTable);
	}
	
	@Transactional(readOnly = false)
	public void save(TestRightTable testRightTable) {
		super.save(testRightTable);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestRightTable testRightTable) {
		super.delete(testRightTable);
	}
	
}
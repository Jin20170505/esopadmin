/**
 * 
 */
package com.jeeplus.modules.test.leftright.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.leftright.entity.TestLeftTree;
import com.jeeplus.modules.test.leftright.mapper.TestLeftTreeMapper;

/**
 * 左树右表Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class TestLeftTreeService extends TreeService<TestLeftTreeMapper, TestLeftTree> {

	public TestLeftTree get(String id) {
		return super.get(id);
	}
	
	public List<TestLeftTree> findList(TestLeftTree testLeftTree) {
		if (StringUtils.isNotBlank(testLeftTree.getParentIds())){
			testLeftTree.setParentIds(","+testLeftTree.getParentIds()+",");
		}
		return super.findList(testLeftTree);
	}
	
	@Transactional(readOnly = false)
	public void save(TestLeftTree testLeftTree) {
		super.save(testLeftTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestLeftTree testLeftTree) {
		super.delete(testLeftTree);
	}
	
}
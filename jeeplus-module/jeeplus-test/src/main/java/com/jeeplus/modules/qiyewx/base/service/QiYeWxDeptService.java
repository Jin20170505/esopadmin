/**
 *
 */
package com.jeeplus.modules.qiyewx.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxDept;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxDeptMapper;

/**
 * 部门员工Service
 * @author Jin
 * @version 2021-08-25
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxDeptService extends TreeService<QiYeWxDeptMapper, QiYeWxDept> {

	public QiYeWxDept get(String id) {
		return super.get(id);
	}
	
	public List<QiYeWxDept> findList(QiYeWxDept qiYeWxDept) {
		if (StringUtils.isNotBlank(qiYeWxDept.getParentIds())){
			qiYeWxDept.setParentIds(","+qiYeWxDept.getParentIds()+",");
		}
		return super.findList(qiYeWxDept);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxDept qiYeWxDept) {
		super.save(qiYeWxDept);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxDept qiYeWxDept) {
		super.delete(qiYeWxDept);
	}
	
}
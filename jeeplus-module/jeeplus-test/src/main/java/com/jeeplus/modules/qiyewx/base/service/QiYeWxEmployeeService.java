/**
 *
 */
package com.jeeplus.modules.qiyewx.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeeplus.modules.qiyewx.base.DeptUtil;
import com.jeeplus.modules.qiyewx.base.EmployeeUtil;
import com.jeeplus.modules.qiyewx.base.entity.Dept;
import com.jeeplus.modules.qiyewx.base.entity.Employee;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxDept;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxEmployeeMapper;

/**
 * 企业微信_员工Service
 * @author Jin
 * @version 2021-08-25
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxEmployeeService extends CrudService<QiYeWxEmployeeMapper, QiYeWxEmployee> {
	@Autowired
	private QiYeWxDeptMapper deptMapper;

	public String getIdByNameLast(String name,String dept){
		return mapper.getIdByNameLast(name, dept);
	}
	public QiYeWxEmployee get(String id) {
		return super.get(id);
	}
	
	public List<QiYeWxEmployee> findList(QiYeWxEmployee qiYeWxEmployee) {
		return super.findList(qiYeWxEmployee);
	}
	
	public Page<QiYeWxEmployee> findPage(Page<QiYeWxEmployee> page, QiYeWxEmployee qiYeWxEmployee) {
		return super.findPage(page, qiYeWxEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxEmployee qiYeWxEmployee) {
		super.save(qiYeWxEmployee);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxEmployee qiYeWxEmployee) {
		super.delete(qiYeWxEmployee);
	}

	@Transactional(readOnly = false)
	public void syncData(){
		List<Dept> deptList = DeptUtil.findDepts("");
		if(deptList.isEmpty()){
			throw new RuntimeException("部门获取失败");
		}
		deptMapper.deleteAll();
		List<QiYeWxDept> qiYeWxDepts = new ArrayList<>(deptList.size());
		deptList.forEach(d->{
			QiYeWxDept dept = new QiYeWxDept();
			dept.preInsert();
			dept.setRemarks(d.getParentid().toString());
			dept.setId(d.getId().toString());
			dept.setName(d.getName());
			dept.setSort(d.getOrder());
			qiYeWxDepts.add(dept);
		});
		batchInsertDept(qiYeWxDepts);
		List<Employee> employees = EmployeeUtil.findEmployees("1",true);
		if(employees.isEmpty()){
			throw new RuntimeException("人员获取失败");
		}
		List<QiYeWxEmployee> qiYeWxEmployees = new ArrayList<>(employees.size());
		employees.forEach(e->{
			QiYeWxEmployee q = new QiYeWxEmployee();
			q.setAlias(e.getAlias());
			q.setAvatar(e.getAvatar());
			q.setDeptmentids(e.getDeptment());
			q.setEmail(e.getEmail());
			q.setGender(e.getGender());
			q.setMobile(e.getMobile());
			q.setTelephone(e.getTelephone());
			q.setThumbvatar(e.getThumbAvatar());
			q.setRemarks(e.getMainDepartment().toString());
			q.setName(e.getName());
			q.setPosition(e.getPosition());
			q.setStatus(e.getStatus());
			q.setUserid(e.getUserid());
			q.preInsert();
			qiYeWxEmployees.add(q);
		});
		batchInsertEmployee(qiYeWxEmployees);
	}
	@Transactional(readOnly = false)
	public void batchInsertDept(List<QiYeWxDept> list){
		if (list == null || list.isEmpty()) {
			return;
		}
		int i = 0;
		int j = 0;
		int size = list.size();
		if(size<=300){
			deptMapper.batchInsert(list);
			return;
		}
		while(size>i){
			j++;
			if(j*300>size){
				deptMapper.batchInsert(list.subList(i,size));
			}else{
				deptMapper.batchInsert(list.subList(i,j*300));
			}
			i = j*300;
		}
	}

	@Transactional(readOnly = false)
	public void batchInsertEmployee(List<QiYeWxEmployee> list){
		if (list == null || list.isEmpty()) {
			return;
		}
		int i = 0;
		int j = 0;
		int size = list.size();
		if(size<=300){
			mapper.batchInsert(list);
			return;
		}
		while(size>i){
			j++;
			if(j*300>size){
				mapper.batchInsert(list.subList(i,size));
			}else{
				mapper.batchInsert(list.subList(i,j*300));
			}
			i = j*300;
		}
	}

	@Transactional(readOnly = false)
	public void updateStatus(String ids,Integer status){
		Arrays.asList(ids).forEach(id->{
			mapper.updateStatus(id,status);
		});
	}
}
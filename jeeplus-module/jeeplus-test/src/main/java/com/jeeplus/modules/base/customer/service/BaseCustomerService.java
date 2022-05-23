/**
 * 
 */
package com.jeeplus.modules.base.customer.service;

import java.util.List;

import com.jeeplus.modules.u8data.customer.entity.U8Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import com.jeeplus.modules.base.customer.mapper.BaseCustomerMapper;

/**
 * 客户档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseCustomerService extends CrudService<BaseCustomerMapper, BaseCustomer> {

	public BaseCustomer get(String id) {
		return super.get(id);
	}
	
	public List<BaseCustomer> findList(BaseCustomer baseCustomer) {
		return super.findList(baseCustomer);
	}
	
	public Page<BaseCustomer> findPage(Page<BaseCustomer> page, BaseCustomer baseCustomer) {
		return super.findPage(page, baseCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseCustomer baseCustomer) {
		super.save(baseCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseCustomer baseCustomer) {
		super.delete(baseCustomer);
	}
	@Transactional(readOnly = false)
    public void sychU8(List<U8Customer> data) {
		data.stream().filter(d->null==mapper.hasById(d.getcCusCode())).forEach(d->{
			BaseCustomer customer = new BaseCustomer();
			customer.preInsert();
			customer.setId(d.getcCusCode());
			customer.setCode(d.getcCusCode());
			customer.setName(d.getcCusName());
			customer.setAddress(d.getcCusAddress());
			customer.setTelephone(d.getcCusPhone());
			customer.setPerson(d.getcCusPerson());
			customer.setEnddate(d.getdEndDate());
			customer.setRemarks(d.getcMemo());
			mapper.insert(customer);
		});
    }
}
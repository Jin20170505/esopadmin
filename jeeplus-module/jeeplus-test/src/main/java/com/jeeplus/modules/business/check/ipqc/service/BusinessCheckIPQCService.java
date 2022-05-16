/**
 * 
 */
package com.jeeplus.modules.business.check.ipqc.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQC;
import com.jeeplus.modules.business.check.ipqc.mapper.BusinessCheckIPQCMapper;

/**
 * IPQC检验Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessCheckIPQCService extends CrudService<BusinessCheckIPQCMapper, BusinessCheckIPQC> {

	public BusinessCheckIPQC get(String id) {
		return super.get(id);
	}
	
	public List<BusinessCheckIPQC> findList(BusinessCheckIPQC businessCheckIPQC) {
		return super.findList(businessCheckIPQC);
	}
	
	public Page<BusinessCheckIPQC> findPage(Page<BusinessCheckIPQC> page, BusinessCheckIPQC businessCheckIPQC) {
		return super.findPage(page, businessCheckIPQC);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessCheckIPQC businessCheckIPQC) {
		super.save(businessCheckIPQC);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessCheckIPQC businessCheckIPQC) {
		super.delete(businessCheckIPQC);
	}

	@Transactional(readOnly = false)
	public void zhijian(String sccode,String scline,String userid,String userno,String username,String remarks,Double jynum,Double hgnum,Double bhgnum,Double blnum){
		BusinessCheckIPQC ipqc = new BusinessCheckIPQC();
		ipqc.setBadnum(blnum);
		ipqc.setChecknum(jynum);
		ipqc.setHegenum(hgnum);
		ipqc.setNohegenum(bhgnum);
		ipqc.setSccode(sccode);
		ipqc.setLinecode(scline);
		ipqc.setCheckdate(new Date());
		ipqc.setCode("IPQC"+ DateUtils.getDate("yyyyMMddHHmmss"));
		ipqc.setRemarks(remarks);
		ipqc.setUsername(userno);
		ipqc.setCheckname(username);
		ipqc.preInsert();
		ipqc.setCreateBy(new User(userid));
		mapper.insert(ipqc);
	}
}
/**
 * 
 */
package com.jeeplus.modules.business.check.ipqc.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQCFile;
import com.jeeplus.modules.business.check.ipqc.mapper.BusinessCheckIPQCFileMapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanBom;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanMingXi;
import com.jeeplus.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private BusinessCheckIPQCFileMapper fileMapper;
	public BusinessCheckIPQC get(String id) {
		BusinessCheckIPQC ipqc = super.get(id);
		ipqc.setBusinessCheckIPQCFileList(fileMapper.findList(new BusinessCheckIPQCFile(new BusinessCheckIPQC(id))));
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
		for (BusinessCheckIPQCFile businessCheckIPQCFile : businessCheckIPQC.getBusinessCheckIPQCFileList()){
			if (businessCheckIPQCFile.getId() == null){
				continue;
			}
			if (BusinessCheckIPQCFile.DEL_FLAG_NORMAL.equals(businessCheckIPQCFile.getDelFlag())){
				if (StringUtils.isBlank(businessCheckIPQCFile.getId())){
					businessCheckIPQCFile.setP(businessCheckIPQC);
					businessCheckIPQCFile.preInsert();
					fileMapper.insert(businessCheckIPQCFile);
				}else{
					businessCheckIPQCFile.setP(businessCheckIPQC);
					businessCheckIPQCFile.preUpdate();
					fileMapper.update(businessCheckIPQCFile);
				}
			}else{
				fileMapper.delete(businessCheckIPQCFile);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessCheckIPQC businessCheckIPQC) {
		super.delete(businessCheckIPQC);
		fileMapper.delete(new BusinessCheckIPQCFile(businessCheckIPQC));
	}

	@Transactional(readOnly = false)
	public void zhijian(String sccode,String scline,String userid,String userno,String username,
						String bgid,String bgcode,String bghid,String siteid,String sitename,Double hglv,
						String remarks,String cinvcode,String cinvname,Double jynum,Double hgnum,Double bhgnum,Double blnum){
		BusinessCheckIPQC ipqc = new BusinessCheckIPQC();
		ipqc.setBgid(bgid).setBzhglv(hglv).setBgcode(bgcode).setBghid(bghid).setSiteid(siteid).setSitename(sitename);
		ipqc.setBadnum(blnum);
		ipqc.setChecknum(jynum);
		ipqc.setHegenum(hgnum);
		ipqc.setNohegenum(bhgnum);
		ipqc.setCinvcode(cinvcode).setCinvname(cinvname);
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
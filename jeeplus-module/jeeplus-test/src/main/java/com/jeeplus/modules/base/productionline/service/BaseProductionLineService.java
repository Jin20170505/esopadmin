/**
 * 
 */
package com.jeeplus.modules.base.productionline.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.productionline.entity.BaseProductionLine;
import com.jeeplus.modules.base.productionline.mapper.BaseProductionLineMapper;

/**
 * 产线管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseProductionLineService extends CrudService<BaseProductionLineMapper, BaseProductionLine> {

	public BaseProductionLine get(String id) {
		return super.get(id);
	}
	
	public List<BaseProductionLine> findList(BaseProductionLine baseProductionLine) {
		return super.findList(baseProductionLine);
	}
	
	public Page<BaseProductionLine> findPage(Page<BaseProductionLine> page, BaseProductionLine baseProductionLine) {
		return super.findPage(page, baseProductionLine);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseProductionLine baseProductionLine) {
		super.save(baseProductionLine);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseProductionLine baseProductionLine) {
		super.delete(baseProductionLine);
	}
	
}
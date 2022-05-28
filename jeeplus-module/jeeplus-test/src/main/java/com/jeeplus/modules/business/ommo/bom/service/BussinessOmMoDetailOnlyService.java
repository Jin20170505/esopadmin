/**
 * 
 */
package com.jeeplus.modules.business.ommo.bom.service;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.u8data.ommo.entity.U8MOMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import com.jeeplus.modules.business.ommo.bom.mapper.BussinessOmMoDetailOnlyMapper;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoYongItem;
import com.jeeplus.modules.business.ommo.bom.mapper.BussinessOmMoYongItemMapper;

/**
 * 委外用料Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BussinessOmMoDetailOnlyService extends CrudService<BussinessOmMoDetailOnlyMapper, BussinessOmMoDetailOnly> {

	@Autowired
	private BussinessOmMoYongItemMapper bussinessOmMoYongItemMapper;
	
	public BussinessOmMoDetailOnly get(String id) {
		BussinessOmMoDetailOnly bussinessOmMoDetailOnly = super.get(id);
		bussinessOmMoDetailOnly.setBussinessOmMoYongItemList(bussinessOmMoYongItemMapper.findList(new BussinessOmMoYongItem(bussinessOmMoDetailOnly)));
		return bussinessOmMoDetailOnly;
	}

	
	@Transactional(readOnly = false)
	public void save(BussinessOmMoDetailOnly bussinessOmMoDetailOnly) {
		// super.save(bussinessOmMoDetailOnly);
		for (BussinessOmMoYongItem bussinessOmMoYongItem : bussinessOmMoDetailOnly.getBussinessOmMoYongItemList()){
			if (bussinessOmMoYongItem.getId() == null){
				continue;
			}
			if (BussinessOmMoYongItem.DEL_FLAG_NORMAL.equals(bussinessOmMoYongItem.getDelFlag())){
				if (StringUtils.isBlank(bussinessOmMoYongItem.getId())){
					bussinessOmMoYongItem.setOmmo(bussinessOmMoDetailOnly);
					bussinessOmMoYongItem.preInsert();
					bussinessOmMoYongItemMapper.insert(bussinessOmMoYongItem);
				}else{
					bussinessOmMoYongItem.preUpdate();
					bussinessOmMoYongItemMapper.update(bussinessOmMoYongItem);
				}
			}else{
				bussinessOmMoYongItemMapper.delete(bussinessOmMoYongItem);
			}
		}
	}
	@Transactional(readOnly = false)
	public void sychU8(List<U8MOMaterials> list){
		List<BussinessOmMoYongItem> data = new ArrayList<>(list.size());
		int i = 1;
		for(U8MOMaterials d:list) {
			BussinessOmMoYongItem item = new BussinessOmMoYongItem();
			item.preInsert();
			item.setId(d.getMomaterialsID());
			item.setOmmo(new BussinessOmMoDetailOnly(d.getMoDetailsID()));
			item.setCinvcode(d.getcInvCode());
			item.setCinvname(d.getcInvName());
			item.setCinvstd(d.getcInvStd());
			item.setBatchno(d.getcBatch());
			item.setCk(new BaseCangKu(d.getcWhCode()));
			item.setHw(new BaseHuoWei(d.getHw()));
			item.setNum(d.getiQuantity());
			item.setNo(i++);
			item.setUnit(d.getcComUnitName());
			item.setRequireddate(d.getdRequiredDate());
			data.add(item);
		}
		i = 0;
		int len =  data.size();
		int j = 0;
		while (i<len){
			j = i;
			i = i+300;
			if(i>=len){
				bussinessOmMoYongItemMapper.batchInsert(data.subList(j,len));
			}else {
				bussinessOmMoYongItemMapper.batchInsert(data.subList(j,i));
			}
		}


	}
}
/**
 *
 */
package com.jeeplus.modules.qiyewx.date_type.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.daka.DakaDateUtils;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaDate;
import com.jeeplus.modules.qiyewx.daka.mapper.QiYeWxDaKaRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.qiyewx.date_type.entity.QiYeWxDateType;
import com.jeeplus.modules.qiyewx.date_type.mapper.QiYeWxDateTypeMapper;

/**
 * 日期所属Service
 * @author Jin
 * @version 2021-09-11
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxDateTypeService extends CrudService<QiYeWxDateTypeMapper, QiYeWxDateType> {
	@Autowired
	private QiYeWxDaKaRecordMapper qiYeWxDaKaRecordMapper;
	public QiYeWxDateType get(String id) {
		return super.get(id);
	}
	
	public List<QiYeWxDateType> findList(QiYeWxDateType qiYeWxDateType) {
		return super.findList(qiYeWxDateType);
	}
	
	public Page<QiYeWxDateType> findPage(Page<QiYeWxDateType> page, QiYeWxDateType qiYeWxDateType) {
		return super.findPage(page, qiYeWxDateType);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxDateType qiYeWxDateType) {
		super.save(qiYeWxDateType);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxDateType qiYeWxDateType) {
		super.delete(qiYeWxDateType);
	}

	public int getMaxWorkDay(String ym){
		return mapper.getMaxWorkDay(ym);
	}

	@Transactional(readOnly = false)
    public void pledit(String rid, String type) {
		Arrays.asList(rid.split(",")).forEach(id->mapper.updateType(id,type));
    }
	@Transactional(readOnly = false)
	public void js(String ym, Date start, Date end) {
		mapper.deleteByYm(ym);
		String userid = qiYeWxDaKaRecordMapper.getUseridByYm(ym);
		List<DaKaDate> list = DakaDateUtils.findDaKaDates(start,end,Arrays.asList(userid));
		List<QiYeWxDateType> dateTypes = new ArrayList<>(list.size());
		list.forEach(e->{
			QiYeWxDateType type = new QiYeWxDateType(IdGen.uuid());
			type.setYmd(e.getDate());
			type.setType(e.getDateType());
			dateTypes.add(type);
		});
		batchInsert(dateTypes);
	}

	@Transactional(readOnly = false)
	void batchInsert(List<QiYeWxDateType> list){
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

	@Transactional(readOnly = true)
	public String getDayType(Date date){
		String ymd = DateUtils.formatDate(date,"yyyy-MM-dd");
		String type =  mapper.getTypeByYmd(ymd);
		return StringUtils.isEmpty(type)?"1":type;
	}
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.qiyewx.daka_day.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxEmployeeMapper;
import com.jeeplus.modules.qiyewx.daka.DaKaDayRecordsUtils;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaDayData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.daka_day.entity.QiYewxDaKaDay;
import com.jeeplus.modules.qiyewx.daka_day.mapper.QiYewxDaKaDayMapper;
import com.jeeplus.modules.qiyewx.daka_day.entity.QiyewxDakaDaySpitems;
import com.jeeplus.modules.qiyewx.daka_day.mapper.QiyewxDakaDaySpitemsMapper;

/**
 * 打卡日报Service
 * @author Jin
 * @version 2021-11-25
 */
@Service
@Transactional(readOnly = true)
public class QiYewxDaKaDayService extends CrudService<QiYewxDaKaDayMapper, QiYewxDaKaDay> {
	@Autowired
	private QiYeWxEmployeeMapper employeeMapper;

	@Autowired
	private QiyewxDakaDaySpitemsMapper qiyewxDakaDaySpitemsMapper;
	
	public QiYewxDaKaDay get(String id) {
		QiYewxDaKaDay qiYewxDaKaDay = super.get(id);
		qiYewxDaKaDay.setQiyewxDakaDaySpitemsList(qiyewxDakaDaySpitemsMapper.findList(new QiyewxDakaDaySpitems(qiYewxDaKaDay)));
		return qiYewxDaKaDay;
	}
	
	public List<QiYewxDaKaDay> findList(QiYewxDaKaDay qiYewxDaKaDay) {
		return super.findList(qiYewxDaKaDay);
	}
	
	public Page<QiYewxDaKaDay> findPage(Page<QiYewxDaKaDay> page, QiYewxDaKaDay qiYewxDaKaDay) {
		return super.findPage(page, qiYewxDaKaDay);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYewxDaKaDay qiYewxDaKaDay) {
		super.save(qiYewxDaKaDay);
		for (QiyewxDakaDaySpitems qiyewxDakaDaySpitems : qiYewxDaKaDay.getQiyewxDakaDaySpitemsList()){
			if (qiyewxDakaDaySpitems.getId() == null){
				continue;
			}
			if (QiyewxDakaDaySpitems.DEL_FLAG_NORMAL.equals(qiyewxDakaDaySpitems.getDelFlag())){
				if (StringUtils.isBlank(qiyewxDakaDaySpitems.getId())){
					qiyewxDakaDaySpitems.setDay(qiYewxDaKaDay);
					qiyewxDakaDaySpitems.preInsert();
					qiyewxDakaDaySpitemsMapper.insert(qiyewxDakaDaySpitems);
				}else{
					qiyewxDakaDaySpitems.preUpdate();
					qiyewxDakaDaySpitemsMapper.update(qiyewxDakaDaySpitems);
				}
			}else{
				qiyewxDakaDaySpitemsMapper.delete(qiyewxDakaDaySpitems);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYewxDaKaDay qiYewxDaKaDay) {
		super.delete(qiYewxDaKaDay);
		qiyewxDakaDaySpitemsMapper.delete(new QiyewxDakaDaySpitems(qiYewxDaKaDay));
	}
	@Transactional(readOnly = false)
	public void syncDaKaRecord(Date start, Date end){
		// 同步数据
		List<String> useridList = employeeMapper.findIdOfAll();
		List<DaKaDayData> list = DaKaDayRecordsUtils.findDaKaDayData(start,end,useridList);
		if(null==list || list.isEmpty()){
			return;
		}
		mapper.delByDate(start,end);
		list.forEach(e->{
			QiYewxDaKaDay day = new QiYewxDaKaDay();
			day.setDate(new Date(e.getBase_info().getDate()));
			day.setName(e.getBase_info().getName());
			day.setUserid(e.getBase_info().getAcctid());
			day.setDepartsName(e.getBase_info().getDeparts_name());
			day.setDayType(e.getBase_info().getDay_type());
			day.setRecordType(e.getBase_info().getRecord_type()+"");
			day.setOtStatus(e.getOtInfo().getOt_status()+"");
			day.setOtDuration(e.getOtInfo().getOt_duration());
			day.setCheckinCount(e.getSummaryInfo().getCheckin_count());
			day.setRegularWorkSec(e.getSummaryInfo().getRegular_work_sec());
			day.setStandardWorkSec(e.getSummaryInfo().getStandard_work_sec());
			e.getSpItems().forEach(s->{
				QiyewxDakaDaySpitems item = new QiyewxDakaDaySpitems();
				item.setId(""); item.setDelFlag("0");
				item.setName(s.getName());
				item.setType(s.getType()+"");
				item.setVacationId(s.getVacation_id()+"");
				item.setCount(s.getCount());
				item.setDuration(Integer.valueOf(s.getDuration().toString()));
				item.setTimeType(s.getTime_type());
				day.getQiyewxDakaDaySpitemsList().add(item);
			});
			save(day);
		});

	}
}
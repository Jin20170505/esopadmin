/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxEmployeeMapper;
import com.jeeplus.modules.qiyewx.daka.DaKaRecordsUtils;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.qiyewx.daka.entity.QiYeWxDaKaRecord;
import com.jeeplus.modules.qiyewx.daka.mapper.QiYeWxDaKaRecordMapper;

/**
 * 打卡记录Service
 * @author Jin
 * @version 2021-08-31
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxDaKaRecordService extends CrudService<QiYeWxDaKaRecordMapper, QiYeWxDaKaRecord> {
	@Autowired
	private QiYeWxEmployeeMapper employeeMapper;

	public QiYeWxDaKaRecord get(String id) {
		return super.get(id);
	}
	
	public List<QiYeWxDaKaRecord> findList(QiYeWxDaKaRecord qiYeWxDaKaRecord) {
		return super.findList(qiYeWxDaKaRecord);
	}
	
	public Page<QiYeWxDaKaRecord> findPage(Page<QiYeWxDaKaRecord> page, QiYeWxDaKaRecord qiYeWxDaKaRecord) {
		return super.findPage(page, qiYeWxDaKaRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxDaKaRecord qiYeWxDaKaRecord) {
		super.save(qiYeWxDaKaRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxDaKaRecord qiYeWxDaKaRecord) {
		super.delete(qiYeWxDaKaRecord);
	}
	/** 同步打卡记录数据 */
	@Transactional(readOnly = false)
	public void syncDaKaRecord(Date start,Date end){
		List<String> useridList = employeeMapper.findIdOfAll();
		List<DaKaRecord> records = DaKaRecordsUtils.findDaKaRecordsNoUseridListLimit(3,start,end,useridList);
		if(records==null && records.isEmpty()){
			return;
		}
		List<QiYeWxDaKaRecord> list = new ArrayList<>(records.size());
		records.forEach(e->{
			QiYeWxDaKaRecord record = new QiYeWxDaKaRecord();
			record.setEmplyee(new QiYeWxEmployee(e.getUserid())).setCheckinType(e.getCheckin_type()).setCheckinTime(new Date(e.getCheckin_time()*1000))
					.setDeviceid(e.getDeviceid()).setExceptionType(e.getException_type()).setGroupid(e.getGroupid()+"").setGroupname(e.getGroupname())
					.setLat(e.getLat()+"").setLng(e.getLng()+"").setLocationDetail(e.getLocation_detail()).setLocationTitle(e.getLocation_title())
					.setMediaids(e.getMediaids()).setNotes(e.getNotes()).setScheduleid(e.getSchedule_id()==null?"":e.getSchedule_id()+"")
					.setTimelineid(e.getTimeline_id()).setWifimac(e.getWifimac()).setWifiname(e.getWifiname());
			if(e.getSch_checkin_time()!=null){
				record.setSchCheckinTime(new Date(e.getSch_checkin_time()*1000));
			}
			record.preInsert();
			list.add(record);
		});
		// 删除原记录
		mapper.deleteFromTo(start,end);
		// 批量插入
		batchInsertDaKaData(list);
	}

	@Transactional(readOnly = false)
	public void batchInsertDaKaData(List<QiYeWxDaKaRecord> list){
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

}
/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.date_type.service.QiYeWxDateTypeService;
import com.jeeplus.modules.qiyewx.shenpi.ShenPiDataDetailUtil;
import com.jeeplus.modules.qiyewx.shenpi.ShenPiDataUtil;
import com.jeeplus.modules.qiyewx.shenpi.entity.*;
import com.jeeplus.modules.qiyewx.sp.entity.*;
import com.jeeplus.modules.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.sp.mapper.QiYeWxSpMapper;
import com.jeeplus.modules.qiyewx.sp.mapper.QiYeWxSpApplyMapper;
import com.jeeplus.modules.qiyewx.sp.mapper.QiYeWxSpRecordMapper;

/**
 * 申请审批Service
 * @author Jin
 * @version 2021-08-31
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxSpService extends CrudService<QiYeWxSpMapper, QiYeWxSp> {

	@Autowired
	private QiYeWxSpApplyMapper qiYeWxSpApplyMapper;
	@Autowired
	private QiYeWxSpRecordMapper qiYeWxSpRecordMapper;
	@Autowired
	private QiYeWxDateTypeService typeService;
	public QiYeWxSp get(String id) {
		QiYeWxSp qiYeWxSp = super.get(id);
		qiYeWxSp.setQiYeWxSpApplyList(qiYeWxSpApplyMapper.findList(new QiYeWxSpApply(qiYeWxSp)));
		qiYeWxSp.setQiYeWxSpRecordList(qiYeWxSpRecordMapper.findList(new QiYeWxSpRecord(qiYeWxSp)));
		return qiYeWxSp;
	}
	public List<QiYeWxSp> findAllByYmAndType(String ym,String type){
		List<QiYeWxSp> list = mapper.findAllByYmAndType(ym,type);
		if(list==null){
			return Lists.newArrayList();
		}
		list.forEach(q->{
			q.setQiYeWxSpApplyList(qiYeWxSpApplyMapper.findList(new QiYeWxSpApply(q)));
		});
		return list;
	}
	public List<QiYeWxSp> findList(QiYeWxSp qiYeWxSp) {
		return super.findList(qiYeWxSp);
	}
	
	public Page<QiYeWxSp> findPage(Page<QiYeWxSp> page, QiYeWxSp qiYeWxSp) {
		return super.findPage(page, qiYeWxSp);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxSp qiYeWxSp) {
		super.save(qiYeWxSp);
		for (QiYeWxSpApply qiYeWxSpApply : qiYeWxSp.getQiYeWxSpApplyList()){
			if (qiYeWxSpApply.getId() == null){
				continue;
			}
			if (QiYeWxSpApply.DEL_FLAG_NORMAL.equals(qiYeWxSpApply.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxSpApply.getId())){
					qiYeWxSpApply.setSp(qiYeWxSp);
					qiYeWxSpApply.preInsert();
					qiYeWxSpApplyMapper.insert(qiYeWxSpApply);
				}else{
					qiYeWxSpApply.preUpdate();
					qiYeWxSpApplyMapper.update(qiYeWxSpApply);
				}
			}else{
				qiYeWxSpApplyMapper.delete(qiYeWxSpApply);
			}
		}
		for (QiYeWxSpRecord qiYeWxSpRecord : qiYeWxSp.getQiYeWxSpRecordList()){
			if (qiYeWxSpRecord.getId() == null){
				continue;
			}
			if (QiYeWxSpRecord.DEL_FLAG_NORMAL.equals(qiYeWxSpRecord.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxSpRecord.getId())){
					qiYeWxSpRecord.setSp(qiYeWxSp);
					qiYeWxSpRecord.preInsert();
					qiYeWxSpRecordMapper.insert(qiYeWxSpRecord);
				}else{
					qiYeWxSpRecord.preUpdate();
					qiYeWxSpRecordMapper.update(qiYeWxSpRecord);
				}
			}else{
				qiYeWxSpRecordMapper.delete(qiYeWxSpRecord);
			}
		}
	}
	@Transactional(readOnly = false)
	public void updateStatus(){
		List<String> list = mapper.findSpNoOfSping();
		if(list!=null){
			list.forEach(spNo->{
				try {
					ShenPiDataDetail dataDetail = ShenPiDataDetailUtil.getShenPiDetail(spNo);
					if(dataDetail!=null){
						mapper.updateStatus(spNo,dataDetail.getSp_status().toString());
					}
				}catch (Exception e){

				}
			});
		}
	}

	@Transactional(readOnly = false)
	public void delete(QiYeWxSp qiYeWxSp) {
		super.delete(qiYeWxSp);
		qiYeWxSpApplyMapper.delete(new QiYeWxSpApply(qiYeWxSp));
		qiYeWxSpRecordMapper.delete(new QiYeWxSpRecord(qiYeWxSp));
	}
	@Transactional(readOnly = true)
	public List<JiaBanBean> findJiaBan(String ym,String userid){
		List<String> spids = mapper.findPassJiaBanIdsByYmAndUserid(ym,userid);
		if(spids==null ||spids.isEmpty()){
			return null;
		}
		List<JiaBanBean> list = new ArrayList<>(spids.size());
		spids.forEach(mid->{
			List<QiYeWxSpApply> applies = qiYeWxSpApplyMapper.findJiaBanBean(mid);
			Date start = applies.stream().filter(e->"加班开始时间".equals(e.getTitle())).findFirst().get().getVacationAttendanceDateRangeBegin();
			Date end = applies.stream().filter(e->"加班结束时间".equals(e.getTitle())).findFirst().get().getVacationAttendanceDateRangeEnd();
			String hourTxt = applies.stream().filter(e->"加班时长".equals(e.getTitle())).findFirst().get().getValueText();
			JiaBanBean jiaBanBean = new JiaBanBean();
			hourTxt = switchString(hourTxt);
			String txt = NumberUtil.getNumber(hourTxt);
			if("".equals(txt)){
				long time = (end.getTime()-start.getTime())/1000;
				jiaBanBean.setHour(time/3600.0);
			}else {
				if(hourTxt.endsWith("天")){
					jiaBanBean.setHour(Double.parseDouble(txt)*8);
				}else{
					jiaBanBean.setHour(Double.parseDouble(txt));
				}
			}
			jiaBanBean.setDayType(typeService.getDayType(start));
			list.add(jiaBanBean);
		});
		return list;
	}

	private static String switchString(String txt){
		if(txt.startsWith("半")){
			txt = txt.replace("半","0.5");
		}
		if(txt.contains("个半")){
			txt = txt.replace("个半",".5");
		}
		txt = txt.replace("一","1").replace("二","2").replace("三","3").replace("四","4").replace("五","5")
				.replace("六","6").replace("七","7").replace("八","8").replace("九","9");
		if(txt.startsWith("十")){
			txt = txt.replace("十","1");
		}else{
			txt = txt.replace("十","0");
		}
		return txt;
	}

	public List<JiaBanItem> findJiaBanItems(Date start,Date end){
		return mapper.findJiaBanTime(start, end);
	}
	/**
	 * @param record_type 1-请假；2-打卡补卡；3-出差；4-外出；5-加班
	 * */
	@Transactional(readOnly = false)
	public void syncData(Date start,Date end,String record_type,String ym){
		List<String> deleteids = mapper.findIdsByYm(start,end,record_type);
		if(deleteids!=null){
			deleteids.forEach(id->{
				QiYeWxSp qiYeWxSp = new QiYeWxSp(id);
				this.delete(qiYeWxSp);
			});
		}
		String tempId = "";
		String type = record_type;
		if(record_type.equals("5")){
			type = "";
			tempId = "1970324963002892_1688853781320138_429775872_1495181804"; // 加班模板id
		}
		ShenPiDanHao shenPiDanHao = new ShenPiDanHao();
		ShenPiDataUtil.findShenPiDanHao(start,end,0,tempId,"","","",
				type,shenPiDanHao);
		if(shenPiDanHao.getSp_no_list()==null || shenPiDanHao.getSp_no_list().isEmpty()){
			return;
		}
		shenPiDanHao.getSp_no_list().forEach(dh->{
			ShenPiDataDetail dataDetail = ShenPiDataDetailUtil.getShenPiDetail(dh);
			if(dataDetail!=null){
				QiYeWxSp sp = new QiYeWxSp();
				sp.setApplyTime(new Date(dataDetail.getApply_time()*1000));
				sp.setYm(DateUtils.formatDate(sp.getApplyTime(),"yyyy-MM"));
				sp.setRecordType(record_type);
				sp.setSpNo(dataDetail.getSp_no());
				sp.setTemplateId(dataDetail.getTemplate_id());
				sp.setSpName(dataDetail.getSp_name());
				sp.setSpStatus(dataDetail.getSp_status().toString());
				sp.setApply(new QiYeWxEmployee(dataDetail.getApplyer()));
				// 审批记录
				if(dataDetail.getSp_record()!=null){
					dataDetail.getSp_record().forEach(e->{
						if(e.getDetails()!=null){
							e.getDetails().forEach(ed->{
								QiYeWxSpRecord r = new QiYeWxSpRecord();
								r.setApprover(new QiYeWxEmployee(ed.getApprover()));
								r.setMediaid(ed.getMedia_id());
								r.setSpeech(ed.getSpeech());
								r.setSpStatus(ed.getSp_status()+"");
								r.setSptime(ed.getSptime()!=null&&ed.getSptime()>0?new Date(ed.getSptime()*1000):null);
								r.setId("");
								r.setDelFlag("0");
								sp.getQiYeWxSpRecordList().add(r);
							});
						}
					});
				}
				// 申请记录
				ApplyData applyData = dataDetail.getApply_data();
				if(applyData!=null && applyData.getContents()!=null && !applyData.getContents().isEmpty()){
					applyData.getContents().stream().filter(v->!"File".equals(v.getControl())).forEach(e->{
						QiYeWxSpApply apply = new QiYeWxSpApply();
						apply.setControl(e.getControl());
						apply.setControlid(e.getId());
						apply.setTitle(e.getTitle().stream().map(FormTitle::getText).collect(Collectors.joining("/")));
						// apply.setValueJson(e.getValueJson());
						if("Text".equals(e.getControl())|| "Textarea".equals(e.getControl())||"Number".equals(e.getControl())){
							apply.setValueText(e.getValue().getText());
						}
						if("Vacation".equals(e.getControl())){
							// 假勤
							apply.setVacationAttendanceType(e.getValue().getVacation().getAttendance().getType().toString());
							apply.setVacationAttendanceDateRangeBegin(new Date(e.getValue().getVacation().getAttendance().getDate_range().getNew_begin()*1000));
							apply.setVacationAttendanceDateRangeEnd(new Date(e.getValue().getVacation().getAttendance().getDate_range().getNew_end()*1000));
							apply.setVacationAttendanceDateRangeDuration(e.getValue().getVacation().getAttendance().getDate_range().getNew_duration());
							apply.setVacationAttendanceDateRangeType(e.getValue().getVacation().getAttendance().getDate_range().getType());
							if("single".equals(e.getValue().getVacation().getSelector().getType())){
								apply.setVacationSelector(e.getValue().getVacation().getSelector().getOptions().get(0).getValue().stream().map(FormTitle::getText).collect(Collectors.joining("/")));
							}else{
								List<String> selectors = new ArrayList<>(4);
								e.getValue().getVacation().getSelector().getOptions().forEach(o->{
									o.getValue().forEach(v->{
										selectors.add(v.getText());
									});
								});
								apply.setVacationSelector(selectors.stream().collect(Collectors.joining("/")));
							}
							sp.setType(apply.getVacationSelector());
							sp.setStarttime(apply.getVacationAttendanceDateRangeBegin());
							sp.setEndtime(apply.getVacationAttendanceDateRangeEnd());
							sp.setTimeLen(apply.getVacationAttendanceDateRangeDuration());
						}
						if("Attendance".equals(e.getControl())){
							apply.setVacationAttendanceType(e.getValue().getAttendance().getType().toString());
							apply.setVacationAttendanceDateRangeBegin(new Date(e.getValue().getAttendance().getDate_range().getNew_begin()*1000));
							apply.setVacationAttendanceDateRangeEnd(new Date(e.getValue().getAttendance().getDate_range().getNew_end()*1000));
							apply.setVacationAttendanceDateRangeDuration(e.getValue().getAttendance().getDate_range().getNew_duration());
							apply.setVacationAttendanceDateRangeType(e.getValue().getAttendance().getDate_range().getType());
							sp.setType(sp.getSpName());
							sp.setStarttime(apply.getVacationAttendanceDateRangeBegin());
							sp.setEndtime(apply.getVacationAttendanceDateRangeEnd());
							sp.setTimeLen(apply.getVacationAttendanceDateRangeDuration());
						}
						if("PunchCorrection".equals(e.getControl())){
							apply.setPunchCorrectionState(e.getValue().getPunch_correction().getState());
							apply.setPunchCorrectionTime(new Date(e.getValue().getPunch_correction().getTime()*1000));
						}
						// 特殊处理(加班)
						if(dataDetail.getSp_name().equals("加班")){
							if(apply.getTitle().contains("加班开始时间")){
								apply.setVacationAttendanceDateRangeBegin(new Date(Long.parseLong(e.getValue().getDate().getS_timestamp())*1000));
								sp.setStarttime(apply.getVacationAttendanceDateRangeBegin());
							}
							if(apply.getTitle().contains("加班结束时间")){
								apply.setVacationAttendanceDateRangeEnd(new Date(Long.parseLong(e.getValue().getDate().getS_timestamp())*1000));
								sp.setEndtime(apply.getVacationAttendanceDateRangeEnd());
							}
							if(apply.getTitle().contains("加班时长")){
								sp.setJiabanLen(e.getValue().getText());
							}
							sp.setType("加班");
						}
						apply.setDelFlag("0");apply.setId("");
						sp.getQiYeWxSpApplyList().add(apply);
					});
				}
				save(sp);
			}
		});
	}

	private String switchType(String spName){
		if(spName.contains("请假")){
			return "1";
		}
		if(spName.contains("补卡")){
			return "2";
		}
		if(spName.contains("出差")){
			return "3";
		}
		if(spName.contains("外出")){
			return "4";
		}
		if(spName.contains("加班")){
			return "5";
		}
		if(spName.contains("调班")){
			return "6";
		}
		return "";
	}
}
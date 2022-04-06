/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.service;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxEmployeeMapper;
import com.jeeplus.modules.qiyewx.daka.DaKaRecordsUtils;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaMonthData;
import com.jeeplus.modules.qiyewx.daka.month.entity.*;
import com.jeeplus.modules.salary.kaoqintongji.entity.SalaryKaoQinTongJi;
import com.jeeplus.modules.salary.kaoqintongji.service.SalaryKaoQinTongJiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.daka.month.mapper.QiYeWxDaKaMonthMapper;
import com.jeeplus.modules.qiyewx.daka.month.mapper.QiYeWxDakaMonthExceptionMapper;
import com.jeeplus.modules.qiyewx.daka.month.mapper.QiYeWxDaKaMonthOverworkMapper;
import com.jeeplus.modules.qiyewx.daka.month.mapper.QiYeWxDaKaMonthSpItemMapper;
import com.jeeplus.modules.qiyewx.daka.month.mapper.QiYeWxDaKaMonthSummaryMapper;

/**
 * 打卡月报Service
 * @author Jin
 * @version 2021-08-31
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxDaKaMonthService extends CrudService<QiYeWxDaKaMonthMapper, QiYeWxDaKaMonth> {
	@Autowired
	private QiYeWxEmployeeMapper employeeMapper;
	@Autowired
	private QiYeWxDakaMonthExceptionMapper qiYeWxDakaMonthExceptionMapper;
	@Autowired
	private QiYeWxDaKaMonthOverworkMapper qiYeWxDaKaMonthOverworkMapper;
	@Autowired
	private QiYeWxDaKaMonthSpItemMapper qiYeWxDaKaMonthSpItemMapper;
	@Autowired
	private QiYeWxDaKaMonthSummaryMapper qiYeWxDaKaMonthSummaryMapper;

	public KaoQin getKaoQinByYm(String ym,String userid){
		KaoQin kaoQin = new KaoQin();
		String mid = mapper.getIdByYmAndUserid(ym,userid);
		if(StringUtils.isEmpty(mid)){
			kaoQin.setBingjiadays(0.00).setChuchaidays(0.00).setHolidayOverDays(0.00).setQueqindays(0.00).setRestOverDays(0.00)
					.setShijichuqindays(0.00).setWorkOverDays(0.00).setYingchuqindays(0.00).setTiaoxiuDays(0.00).setNianxiuDays(0.00).setHunjiaDay(0.00).setShijiaDay(0.00)
					.setSangjiaDay(0.00);
			return kaoQin;
		}
		QiYeWxDaKaMonthOverwork overwork = qiYeWxDaKaMonthOverworkMapper.getDaysOfOverWork(mid);
		if(overwork==null){
			overwork = new QiYeWxDaKaMonthOverwork();
		}
		kaoQin.setWorkOverDays(overwork.getWorkdayOverSec()/(3600*8.00)).setRestOverDays(overwork.getRestdaysOverSec()/(3600*8.00))
				.setHolidayOverDays(overwork.getHolidaysOverSec()/(3600*8.00));
		List<QiYeWxDaKaMonthSpItem> spItems = qiYeWxDaKaMonthSpItemMapper.findJiaQinDays(mid);
		if(spItems!=null){
			QiYeWxDaKaMonthSpItem hunjiaItem = spItems.stream().filter(e->e.getName().equals("婚假")).findFirst().get();
			QiYeWxDaKaMonthSpItem sangjiaItem = spItems.stream().filter(e->e.getName().equals("丧假")).findFirst().get();
			QiYeWxDaKaMonthSpItem bingjiaItem = spItems.stream().filter(e->e.getName().equals("病假")).findFirst().get();
			QiYeWxDaKaMonthSpItem nianjiaItem = spItems.stream().filter(e->e.getName().equals("年假")).findFirst().get();
			QiYeWxDaKaMonthSpItem tiaoxiuItem = spItems.stream().filter(e->e.getName().equals("调休")).findFirst().get();
			QiYeWxDaKaMonthSpItem shijiaItem = spItems.stream().filter(e->e.getName().equals("事假")).findFirst().get();
			kaoQin.setBingjiadays(bingjiaItem.getDuration()/86400.00).setHunjiaDay(hunjiaItem.getDuration()/86400.00).setSangjiaDay(sangjiaItem.getDuration()/86400.00)
					.setNianxiuDays(nianjiaItem.getDuration()/86400.00).setTiaoxiuDays(tiaoxiuItem.getDuration()/86400.00).setShijiaDay(shijiaItem.getDuration()/86400.00);
		}
		return kaoQin;
	}

	public List<QingJiaItem> findQingJiaItems(String ym){
		return qiYeWxDaKaMonthSpItemMapper.findQingJiaItems(ym);
	}

	public List<String> findQingJiaNames(String ym){
		return qiYeWxDaKaMonthSpItemMapper.findQingJiaName(ym);
	}

	@Transactional(readOnly = true)
	public KaoQin getKaoQin(String ym,String userid){
		KaoQin kaoQin = new KaoQin();
		String mid = mapper.getIdByYmAndUserid(ym,userid);
		if(StringUtils.isEmpty(mid)){
			kaoQin.setBingjiadays(0.00).setChuchaidays(0.00).setHolidayOverDays(0.00).setQueqindays(0.00).setRestOverDays(0.00)
			.setShijichuqindays(0.00).setWorkOverDays(0.00).setYingchuqindays(0.00);
		}
		Double queqinday = qiYeWxDakaMonthExceptionMapper.getDaysOfKuangGong(mid);
		kaoQin.setQueqindays(queqinday);
		QiYeWxDaKaMonthOverwork overwork = qiYeWxDaKaMonthOverworkMapper.getDaysOfOverWork(mid);
		if(overwork==null){
			overwork = new QiYeWxDaKaMonthOverwork();
		}
		kaoQin.setWorkOverDays(overwork.getWorkdayOverSec()/(3600*8.00)).setRestOverDays(overwork.getRestdaysOverSec()/(3600*8.00))
				.setHolidayOverDays(overwork.getHolidaysOverSec()/(3600*8.00));
		QiYeWxDaKaMonthSummary summary =qiYeWxDaKaMonthSummaryMapper.getDaysOfChuQin(mid);
		if(summary == null){
			summary = new QiYeWxDaKaMonthSummary();
		}
		kaoQin.setChuchaidays(summary.getRegularDays()*1.00).setShijichuqindays((summary.getRegularDays()-queqinday));
		List<QiYeWxDaKaMonthSpItem> spItems = qiYeWxDaKaMonthSpItemMapper.findJiaQinDays(mid);
		QiYeWxDaKaMonthSpItem chuchaiItem = spItems.stream().filter(e->e.getName().equals("出差")).findFirst().get();
		QiYeWxDaKaMonthSpItem bingjiaItem = spItems.stream().filter(e->e.getName().equals("病假")).findFirst().get();
		kaoQin.setChuchaidays(chuchaiItem.getDuration()/86400.0).setBingjiadays(bingjiaItem.getDuration()/86400.00);
		return kaoQin;
	}
	public QiYeWxDaKaMonth get(String id) {
		QiYeWxDaKaMonth qiYeWxDaKaMonth = super.get(id);
		qiYeWxDaKaMonth.setQiYeWxDakaMonthExceptionList(qiYeWxDakaMonthExceptionMapper.findList(new QiYeWxDakaMonthException(qiYeWxDaKaMonth)));
		qiYeWxDaKaMonth.setQiYeWxDaKaMonthOverworkList(qiYeWxDaKaMonthOverworkMapper.findList(new QiYeWxDaKaMonthOverwork(qiYeWxDaKaMonth)));
		qiYeWxDaKaMonth.setQiYeWxDaKaMonthSpItemList(qiYeWxDaKaMonthSpItemMapper.findList(new QiYeWxDaKaMonthSpItem(qiYeWxDaKaMonth)));
		qiYeWxDaKaMonth.setQiYeWxDaKaMonthSummaryList(qiYeWxDaKaMonthSummaryMapper.findList(new QiYeWxDaKaMonthSummary(qiYeWxDaKaMonth)));
		return qiYeWxDaKaMonth;
	}
	
	public List<QiYeWxDaKaMonth> findList(QiYeWxDaKaMonth qiYeWxDaKaMonth) {
		return super.findList(qiYeWxDaKaMonth);
	}
	
	public Page<QiYeWxDaKaMonth> findPage(Page<QiYeWxDaKaMonth> page, QiYeWxDaKaMonth qiYeWxDaKaMonth) {
		return super.findPage(page, qiYeWxDaKaMonth);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxDaKaMonth qiYeWxDaKaMonth) {
		super.save(qiYeWxDaKaMonth);
		for (QiYeWxDakaMonthException qiYeWxDakaMonthException : qiYeWxDaKaMonth.getQiYeWxDakaMonthExceptionList()){
			if (qiYeWxDakaMonthException.getId() == null){
				continue;
			}
			if (QiYeWxDakaMonthException.DEL_FLAG_NORMAL.equals(qiYeWxDakaMonthException.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxDakaMonthException.getId())){
					qiYeWxDakaMonthException.setMonth(qiYeWxDaKaMonth);
					qiYeWxDakaMonthException.preInsert();
					qiYeWxDakaMonthExceptionMapper.insert(qiYeWxDakaMonthException);
				}else{
					qiYeWxDakaMonthException.preUpdate();
					qiYeWxDakaMonthExceptionMapper.update(qiYeWxDakaMonthException);
				}
			}else{
				qiYeWxDakaMonthExceptionMapper.delete(qiYeWxDakaMonthException);
			}
		}
		for (QiYeWxDaKaMonthOverwork qiYeWxDaKaMonthOverwork : qiYeWxDaKaMonth.getQiYeWxDaKaMonthOverworkList()){
			if (qiYeWxDaKaMonthOverwork.getId() == null){
				continue;
			}
			if (QiYeWxDaKaMonthOverwork.DEL_FLAG_NORMAL.equals(qiYeWxDaKaMonthOverwork.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxDaKaMonthOverwork.getId())){
					qiYeWxDaKaMonthOverwork.setMonth(qiYeWxDaKaMonth);
					qiYeWxDaKaMonthOverwork.preInsert();
					qiYeWxDaKaMonthOverworkMapper.insert(qiYeWxDaKaMonthOverwork);
				}else{
					qiYeWxDaKaMonthOverwork.preUpdate();
					qiYeWxDaKaMonthOverworkMapper.update(qiYeWxDaKaMonthOverwork);
				}
			}else{
				qiYeWxDaKaMonthOverworkMapper.delete(qiYeWxDaKaMonthOverwork);
			}
		}
		for (QiYeWxDaKaMonthSpItem qiYeWxDaKaMonthSpItem : qiYeWxDaKaMonth.getQiYeWxDaKaMonthSpItemList()){
			if (qiYeWxDaKaMonthSpItem.getId() == null){
				continue;
			}
			if (QiYeWxDaKaMonthSpItem.DEL_FLAG_NORMAL.equals(qiYeWxDaKaMonthSpItem.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxDaKaMonthSpItem.getId())){
					qiYeWxDaKaMonthSpItem.setMonth(qiYeWxDaKaMonth);
					qiYeWxDaKaMonthSpItem.preInsert();
					qiYeWxDaKaMonthSpItemMapper.insert(qiYeWxDaKaMonthSpItem);
				}else{
					qiYeWxDaKaMonthSpItem.preUpdate();
					qiYeWxDaKaMonthSpItemMapper.update(qiYeWxDaKaMonthSpItem);
				}
			}else{
				qiYeWxDaKaMonthSpItemMapper.delete(qiYeWxDaKaMonthSpItem);
			}
		}
		for (QiYeWxDaKaMonthSummary qiYeWxDaKaMonthSummary : qiYeWxDaKaMonth.getQiYeWxDaKaMonthSummaryList()){
			if (qiYeWxDaKaMonthSummary.getId() == null){
				continue;
			}
			if (QiYeWxDaKaMonthSummary.DEL_FLAG_NORMAL.equals(qiYeWxDaKaMonthSummary.getDelFlag())){
				if (StringUtils.isBlank(qiYeWxDaKaMonthSummary.getId())){
					qiYeWxDaKaMonthSummary.setMonth(qiYeWxDaKaMonth);
					qiYeWxDaKaMonthSummary.preInsert();
					qiYeWxDaKaMonthSummaryMapper.insert(qiYeWxDaKaMonthSummary);
				}else{
					qiYeWxDaKaMonthSummary.preUpdate();
					qiYeWxDaKaMonthSummaryMapper.update(qiYeWxDaKaMonthSummary);
				}
			}else{
				qiYeWxDaKaMonthSummaryMapper.delete(qiYeWxDaKaMonthSummary);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxDaKaMonth qiYeWxDaKaMonth) {
		super.delete(qiYeWxDaKaMonth);
		qiYeWxDakaMonthExceptionMapper.delete(new QiYeWxDakaMonthException(qiYeWxDaKaMonth));
		qiYeWxDaKaMonthOverworkMapper.delete(new QiYeWxDaKaMonthOverwork(qiYeWxDaKaMonth));
		qiYeWxDaKaMonthSpItemMapper.delete(new QiYeWxDaKaMonthSpItem(qiYeWxDaKaMonth));
		qiYeWxDaKaMonthSummaryMapper.delete(new QiYeWxDaKaMonthSummary(qiYeWxDaKaMonth));
	}
	@Autowired
	private SalaryKaoQinTongJiService salaryKaoQinTongJiService;
	@Transactional(readOnly = false)
	public void syncData(Date start,Date end,String ym){
		List<String> deleteids = mapper.findIdsByYm(ym);
		if(deleteids!=null){
			deleteids.forEach(id->{
				QiYeWxDaKaMonth qiYeWxDaKaMonth = new QiYeWxDaKaMonth(id);
				super.delete(qiYeWxDaKaMonth);
				qiYeWxDakaMonthExceptionMapper.delete(new QiYeWxDakaMonthException(qiYeWxDaKaMonth));
				qiYeWxDaKaMonthOverworkMapper.delete(new QiYeWxDaKaMonthOverwork(qiYeWxDaKaMonth));
				qiYeWxDaKaMonthSpItemMapper.delete(new QiYeWxDaKaMonthSpItem(qiYeWxDaKaMonth));
				qiYeWxDaKaMonthSummaryMapper.delete(new QiYeWxDaKaMonthSummary(qiYeWxDaKaMonth));
			});
		}
		salaryKaoQinTongJiService.deleteByYm(ym);
		// 同步数据
		List<String> useridList = employeeMapper.findIdOfAll();
		List<DaKaMonthData> data = DaKaRecordsUtils.findDaKaMonthData(start,end,useridList);
		if(data!=null && data.size()>0){
			data.forEach(d->{
				SalaryKaoQinTongJi tongJi = new SalaryKaoQinTongJi();
				// 基本信息
				QiYeWxDaKaMonth month = new QiYeWxDaKaMonth();
				month.setAcctid(d.getBase_info().getAcctid());
				month.setYm(ym);
				month.setRecordType(d.getBase_info().getRecord_type().toString());
				month.setDepartsName(d.getBase_info().getDeparts_name());
				month.setName(d.getBase_info().getName());

				tongJi.setYearmonth(ym);
				tongJi.setEmployee(new QiYeWxEmployee(d.getBase_info().getAcctid()));
				// 汇总信息
				if(d.getSummary_info()!=null){
					QiYeWxDaKaMonthSummary summary = new QiYeWxDaKaMonthSummary();
					summary.setExceptDays(d.getSummary_info().getExcept_days());
					summary.setRegularDays(d.getSummary_info().getRegular_days());
					summary.setRegularWorkSec(d.getSummary_info().getRegular_work_sec());
					summary.setStandardWorkSec(d.getSummary_info().getStandard_work_sec());
					summary.setWorkDays(d.getSummary_info().getWork_days());
					tongJi.setYingChuqinDay(summary.getWorkDays()*1.00);
					summary.setId("");summary.setDelFlag("0");
					month.getQiYeWxDaKaMonthSummaryList().add(summary);
				}
				// 异常信息
				if(d.getException_infos()!=null){
					d.getException_infos().forEach(e->{
						QiYeWxDakaMonthException exception = new QiYeWxDakaMonthException();
						exception.setCount(e.getCount());
						exception.setDuration(e.getDuration());
						exception.setException(e.getException().toString());
						exception.setId("");
						exception.setDelFlag("0");
						if(exception.getException().equals("4")){
							tongJi.setQueqinDay(exception.getCount()*1.00);
							double queqindays= tongJi.getYingChuqinDay()-tongJi.getQueqinDay();
							tongJi.setShijiChuqinDay(queqindays<0.00?0:queqindays);
						}
						month.getQiYeWxDakaMonthExceptionList().add(exception);
					});
				}
				if(tongJi.getQueqinDay()==null){
					tongJi.setQueqinDay(0.00);
					tongJi.setShijiChuqinDay(tongJi.getYingChuqinDay());
				}
				// 假勤统计信息
				if(d.getSp_items()!=null){
					d.getSp_items().forEach(e->{
						QiYeWxDaKaMonthSpItem sp = new QiYeWxDaKaMonthSpItem();
						sp.setCount(e.getCount());
						sp.setDuration(e.getDuration());
						sp.setName(e.getName());
						sp.setTimeType(e.getTime_type().toString());
						sp.setType(e.getType().toString());
						sp.setId("");sp.setDelFlag("0");
						if(sp.getName().equals("出差")){
							tongJi.setChuchaiDay(sp.getDuration()/86400.0);
						}
						if(sp.getName().equals("病假")){
							tongJi.setBingjiaDay(sp.getDuration()/86400.0);
						}
						month.getQiYeWxDaKaMonthSpItemList().add(sp);
					});
				}
				// 加班情况
				QiYeWxDaKaMonthOverwork work = new QiYeWxDaKaMonthOverwork();
				if(d.getOverwork_info()!=null&&d.getOverwork_info().getWorkday_over_sec()!=null){
					work.setHolidaysOverSec(d.getOverwork_info().getHolidays_over_sec());
					tongJi.setHolidayOverDay(work.getHolidaysOverSec()/(3600*8.00));
					work.setRestdaysOverSec(d.getOverwork_info().getRestdays_over_sec());
					tongJi.setRestOverDay(work.getRestdaysOverSec()/(3600*8.00));
					work.setWorkdayOverSec(d.getOverwork_info().getWorkday_over_sec());
					tongJi.setWorkOverDay(work.getWorkdayOverSec()/(3600*8.00));
					work.setId("");work.setDelFlag("0");
					month.getQiYeWxDaKaMonthOverworkList().add(work);
				}
				save(month);
				salaryKaoQinTongJiService.save(tongJi);
			});
		}

	}
}
/**
 * 
 */
package com.jeeplus.modules.qiyewx.base.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.qiyewx.base.TagUtil;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxTagUserid;
import com.jeeplus.modules.qiyewx.base.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxTag;
import com.jeeplus.modules.qiyewx.base.mapper.QiYeWxTagMapper;

/**
 * 微信标签Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class QiYeWxTagService extends CrudService<QiYeWxTagMapper, QiYeWxTag> {

	public QiYeWxTag get(String id) {
		return super.get(id);
	}
	
	public List<QiYeWxTag> findList(QiYeWxTag qiYeWxTag) {
		return super.findList(qiYeWxTag);
	}
	
	public Page<QiYeWxTag> findPage(Page<QiYeWxTag> page, QiYeWxTag qiYeWxTag) {
		return super.findPage(page, qiYeWxTag);
	}
	
	@Transactional(readOnly = false)
	public void save(QiYeWxTag qiYeWxTag) {
		super.save(qiYeWxTag);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiYeWxTag qiYeWxTag) {
		super.delete(qiYeWxTag);
	}
	@Transactional(readOnly = false)
	public void sychData(){
		List<Tag> tags = TagUtil.findTagList();
		List<QiYeWxTag> list = new ArrayList<>(tags.size());
		tags.forEach(tag -> {
			QiYeWxTag t = new QiYeWxTag();
			t.setName(tag.getName());
			t.setId(tag.getId());
			list.add(t);
		});
		mapper.deleteAll();
		batchInsertTag(list);
	}
	@Transactional(readOnly = false)
	public void sychTagUser(){
		List<String> tags = mapper.findAllTags();
		if(tags!=null && !tags.isEmpty()){
			List<QiYeWxTagUserid> list = Lists.newArrayList();
			tags.forEach(tid->{
				List<String> userids = TagUtil.findUseridByTag(tid);
				userids.forEach(uid->{
					QiYeWxTagUserid qiYeWxTagUserid = new QiYeWxTagUserid();
					qiYeWxTagUserid.setUserid(uid);
					qiYeWxTagUserid.setTagid(tid);
					list.add(qiYeWxTagUserid);
				});
			});
			mapper.deleteAllTagUserid();
			batchInsertTagUser(list);
			List<QiYeWxTag> userTags = mapper.findTagUser();
			if(userTags!=null){
				updateUserTagBatch(userTags);
			}
		}
	}
	@Transactional(readOnly = false)
	public void batchInsertTag(List<QiYeWxTag> list){
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
	@Transactional(readOnly = false)
	public void batchInsertTagUser(List<QiYeWxTagUserid> list){
		if (list == null || list.isEmpty()) {
			return;
		}
		int i = 0;
		int j = 0;
		int size = list.size();
		if(size<=300){
			mapper.batchInsertTagUser(list);
			return;
		}
		while(size>i){
			j++;
			if(j*300>size){
				mapper.batchInsertTagUser(list.subList(i,size));
			}else{
				mapper.batchInsertTagUser(list.subList(i,j*300));
			}
			i = j*300;
		}
	}
	@Transactional(readOnly = false)
	public void updateUserTagBatch(List<QiYeWxTag> list){
		if (list == null || list.isEmpty()) {
			return;
		}
		int i = 0;
		int j = 0;
		int size = list.size();
		if(size<=300){
			mapper.updateUserTagBatch(list);
			return;
		}
		while(size>i){
			j++;
			if(j*300>size){
				mapper.updateUserTagBatch(list.subList(i,size));
			}else{
				mapper.updateUserTagBatch(list.subList(i,j*300));
			}
			i = j*300;
		}
	}
}
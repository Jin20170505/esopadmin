package com.jeeplus.modules.qiyewx.shenpi;

import com.google.common.collect.Lists;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.shenpi.entity.*;
import com.jeeplus.modules.qiyewx.shenpi.entity.control.*;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 审批数据详情接口
 */
public class ShenPiDataDetailUtil {
    /** 获取审批申请详情 */
    private static String URI  =  "https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovaldetail?access_token=";

    /**
     * 获取审批申请详情
     * @param sp_no 审批单编号 (202108010005)
     * @return
     */
    public final static ShenPiDataDetail getShenPiDetail(String sp_no){
        try {
            String url = URI+ QiYeWeiXinAccessTokenUtil.getShenPiAccessToken();
            String param = "{\"sp_no\":\""+sp_no+"\"}";
            Connection.Response response = HttpsUtil.post(url,param);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return null;
            }
            ShenPiDataDetail detail = new ShenPiDataDetail();
            JSONObject info = json.getJSONObject("info");
            detail.setSp_no(info.getString("sp_no"));
            detail.setSp_name(info.getString("sp_name"));
            detail.setSp_status(info.getInt("sp_status"));
            detail.setTemplate_id(info.getString("template_id"));
            detail.setApply_time(info.getLong("apply_time"));
            /** 申请提交人 */
            detail.setApplyer(info.getJSONObject("applyer").getString("userid"));
            // 	抄送信息，可能有多个抄送节点
            JSONArray notifyerArr = info.getJSONArray("notifyer");
            List<String> notofyers = Lists.newArrayList();
            if(!notifyerArr.isEmpty()){
                notifyerArr.forEach(n->{
                    notofyers.add(JSONObject.fromObject(n).getString("userid"));
                });
            }
            detail.setNotifyer(notofyers);
            // 申请数据
            ApplyData applyData = new ApplyData();
            JSONObject applyJson = info.getJSONObject("apply_data");
            List<ApplyDataContent> contents = Lists.newArrayList();
            JSONArray contentsArr = applyJson.getJSONArray("contents");
            applyDataDealWith(contentsArr,contents);
            applyData.setContents(contents);
            detail.setApply_data(applyData);
            // 	审批流程信息，可能有多个审批节点。
            JSONArray spRecordArr = info.getJSONArray("sp_record");
            List<SpRecordItem> records = Lists.newArrayList();
            spRecordArr.forEach(r->{
                SpRecordItem item = new SpRecordItem();
                JSONObject rj = JSONObject.fromObject(r);
                item.setApproverattr(rj.getInt("approverattr"));
                item.setSp_status(rj.getInt("sp_status"));
                List<SpItemDetail> details = Lists.newArrayList();
                JSONArray detailArr = rj.getJSONArray("details");
                detailArr.forEach(d->{
                    JSONObject dj = JSONObject.fromObject(d);
                    JSONObject approverJson = dj.getJSONObject("approver");
                    if(approverJson.containsKey("userid")){
                        SpItemDetail de = new SpItemDetail();
                        de.setApprover(approverJson.getString("userid"));
                        de.setSp_status(dj.getInt("sp_status"));
                        de.setSpeech(dj.getString("speech"));
                        de.setSptime(dj.getLong("sptime"));
                        de.setMedia_id(dj.getJSONArray("media_id").join(",").replaceAll("\"",""));
                        details.add(de);
                    }
                });
                item.setDetails(details);
                records.add(item);
            });
            detail.setSp_record(records);
            // 备注
            List<SpComment> comments = Lists.newArrayList();
            JSONArray commentArr = info.getJSONArray("comments");
            commentArr.forEach(c->{
                SpComment comment = new SpComment();
                JSONObject cj = JSONObject.fromObject(c);
                comment.setCommentUserInfo(cj.getJSONObject("commentUserInfo").getString("userid"));
                comment.setCommentcontent(cj.getString("commentcontent"));
                comment.setCommentid(cj.getString("commentid"));
                comment.setCommenttime(cj.getLong("commenttime"));
                comment.setMedia_id(cj.getJSONArray("media_id").join(",").replaceAll("\"",""));
                comments.add(comment);
            });
            detail.setComments(comments);
            return detail;
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException("获取接口数据失败");
        }
    }
    private static void applyDataDealWith(JSONArray contentsArr,List<ApplyDataContent> contents){
        contentsArr.forEach(c->{
            JSONObject cj = JSONObject.fromObject(c);
            ApplyDataContent b =new ApplyDataContent();
            b.setControl(cj.getString("control"));
            b.setId(cj.getString("id"));
            JSONArray ta= cj.getJSONArray("title");
            List<FormTitle> titles = Lists.newArrayList();
            ta.forEach(t->{
                JSONObject tj = JSONObject.fromObject(t);
                FormTitle formTitle = new FormTitle();
                formTitle.setLang(tj.getString("lang"));
                formTitle.setText(tj.getString("text"));
                titles.add(formTitle);
            });
            b.setTitle(titles);
            FormValue formValue = new FormValue();
            JSONObject value= cj.getJSONObject("value");
            b.setValueJson(value.toString());
            valueDealWith(value,formValue,b.getControl());
            b.setValue(formValue);
            contents.add(b);
        });
    }
    private static  void  valueDealWith(JSONObject value,FormValue formValue,String control){
        if(control.equals("Vacation")){
            Vacation vacation = new Vacation();
            JSONObject vj = value.getJSONObject("vacation");
            JSONObject sj = vj.getJSONObject("selector");
            Selector selector = new Selector();
            selector.setType(sj.getString("type"));
            JSONArray optionarr = sj.getJSONArray("options");
            List<SelectorOption> options = Lists.newArrayList();
            optionarr.forEach(o->{
                SelectorOption option = new SelectorOption();
                JSONObject oj = JSONObject.fromObject(o);
                option.setKey(oj.getString("key"));
                JSONArray va= oj.getJSONArray("value");
                List<FormTitle> values = Lists.newArrayList();
                va.forEach(v->{
                    JSONObject vaj = JSONObject.fromObject(v);
                    FormTitle title = new FormTitle();
                    title.setLang(vaj.getString("lang"));
                    title.setText(vaj.getString("text"));
                    values.add(title);
                });
                option.setValue(values);
                options.add(option);
            });
            selector.setOptions(options);
            vacation.setSelector(selector);
            JSONObject aj = vj.getJSONObject("attendance");
            Attendance attendance = new Attendance();
            attendance.setType(aj.getInt("type"));
            JSONObject dateRangeJson = aj.getJSONObject("date_range");
            DateRange date_range =  new DateRange();
            date_range.setType(dateRangeJson.getString("type"));
            date_range.setNew_begin(dateRangeJson.getLong("new_begin"));
            date_range.setNew_end(dateRangeJson.getLong("new_end"));
            date_range.setNew_duration(dateRangeJson.getLong("new_duration"));
            attendance.setDate_range(date_range);
            vacation.setAttendance(attendance);
            formValue.setVacation(vacation);
        }
        if(control.equals("Textarea")||control.equals("Text")){
            formValue.setText(value.getString("text"));
        }
        if("Number".equals(control)){
            formValue.setText(value.getString("new_number"));
        }
        if("File".equals(control)){
            List<FileItem> files = Lists.newArrayList();
            JSONArray fileArr = value.getJSONArray("files");
            fileArr.forEach(f->{
                FileItem fileItem = new FileItem();
                fileItem.setFile_id(JSONObject.fromObject(f).getString("file_id"));
                files.add(fileItem);
            });
            formValue.setFiles(files);
        }
        if("Attendance".equals(control)){
            JSONObject aj = value.getJSONObject("attendance");
            Attendance attendance = new Attendance();
            attendance.setType(aj.getInt("type"));
            JSONObject dateRangeJson = aj.getJSONObject("date_range");
            DateRange date_range =  new DateRange();
            date_range.setType(dateRangeJson.getString("type"));
            date_range.setNew_begin(dateRangeJson.getLong("new_begin"));
            date_range.setNew_end(dateRangeJson.getLong("new_end"));
            date_range.setNew_duration(dateRangeJson.getLong("new_duration"));
            attendance.setDate_range(date_range);
            formValue.setAttendance(attendance);
        }
        /** 补卡 */
        if("PunchCorrection".equals(control)){
            JSONObject pj = value.getJSONObject("punch_correction");
            PunchCorrection punch_correction = new PunchCorrection();
            punch_correction.setState(pj.getString("state"));
            punch_correction.setTime(pj.getLong("time"));
            formValue.setPunch_correction(punch_correction);
        }
        if("Date".equals(control)){
            DateTimeControl dateTimeControl  = new DateTimeControl();
            JSONObject dj =  value.getJSONObject("date");
            dateTimeControl.setType(dj.getString("type"));
            dateTimeControl.setS_timestamp(dj.getString("s_timestamp"));
            formValue.setDate(dateTimeControl);
        }
        // TODO 其他类型的以后遇见再处理
    }
    public static void main(String[] args) {
        System.out.println(getShenPiDetail("202108310031"));
    }
}

package com.jeeplus.modules.api.baogong;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/baogong")
public class ApiBaoGongController {
    @Autowired
    private BusinessBaoGongOrderService businessBaoGongOrderService;
    @Autowired
    private BusinessBaoGongRecordService businessBaoGongRecordService;

    /**
     * 扫报工二维码 查询报工信息
     * @param bgcode 报工单号
     * @return
     */
    @RequestMapping("getBaoGongInfo")
    public AjaxJson getBaoGongInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {
            // 是否领料
            json.put("info",businessBaoGongOrderService.getBaoGongInfo(bgcode));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败,失败原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 员工·报工
     * @param bgid 报工单ID
     * @param bghid 报工单行ID
     * @param remarks 备注
     * @param userid 操作人id
     * @param opname 操作人名称
     * @param dbnum 待报数量
     * @param flnum 废料数量
     * @param fgnum 返工数量
     * @param gfnum 工废数量
     * @param bhgnum 不合格数量
     * @param hgnum 合格数量
     * @param complete 1：待报工数量 = 合格数量
     * @return
     */
    @RequestMapping("do")
    public AjaxJson baogong(String bgid,String bghid,String remarks,String userid,String opname,Double dbnum,Double flnum,Double fgnum,Double gfnum,Double bhgnum,Double hgnum,String complete){
        AjaxJson json = new AjaxJson();
        try{
            businessBaoGongRecordService.baogong(bgid,bghid, remarks, userid, opname,dbnum, flnum, fgnum,gfnum, bhgnum, hgnum,complete);
            json.setSuccess(true);
            json.setMsg("报工成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("报工失败.原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }

}

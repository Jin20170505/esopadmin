package com.jeeplus.modules.baobiao.web;

import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.baobiao.entity.CheJianShengChanBaoBiao;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;
import com.jeeplus.modules.business.product.archive.mapper.BusinessProductMapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/baobiao")
public class BaoBiaoController extends BaseController {

    /********** 车间生产报表 Start **********/
    @RequestMapping("goToCheJianShengChanBaoBiao")
    public String goToCheJianShengChanBaoBiao(){
        return "modules/baobiao/chejianshengchanbaobiao";
    }
    @Autowired
    private BusinessBaoGongOrderMapper businessBaoGongOrderMapper;
    @Autowired
    private BusinessShengChanDingDanMingXiMapper businessShengChanDingDanMingXiMapper;
    @Autowired
    private BusinessProductMapper businessProductMapper;
    @ResponseBody
    @RequestMapping(value = "cheJianShengChanBaoBiao")
    public Map<String, Object> cheJianShengChanBaoBiao(String schids) {
        Page<CheJianShengChanBaoBiao> page = new Page<>();
        List<CheJianShengChanBaoBiao> list = Lists.newArrayList();
        List<String> schidArray = Arrays.asList(schids.split(","));
        int i = 1;
        for (String schid:schidArray){
            BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanMingXiMapper.getCinvInfo(schid);
            CheJianShengChanBaoBiao cheJianShengChanBaoBiao = new CheJianShengChanBaoBiao();
            cheJianShengChanBaoBiao.setNo(i);
            i++;
            cheJianShengChanBaoBiao.setCinvcode(mingXi.getCinvcode());
            cheJianShengChanBaoBiao.setCinvname(mingXi.getCinvname());
            cheJianShengChanBaoBiao.setRemarks(mingXi.getRemarks());
            String daynum = businessProductMapper.getDayNumOfCinvcode(mingXi.getCinvcode());
            cheJianShengChanBaoBiao.setDaynum(daynum);
            cheJianShengChanBaoBiao.setNodonum(businessBaoGongOrderMapper.getNoDoneNumBySchid(schid));
            list.add(cheJianShengChanBaoBiao);
        }
        page.setCount(list.size());
        page.setPageSize(-1);
        page.setList(list);
        return getBootstrapData(page);
    }
    /********** 车间生产报表 End **********/
}

package com.jeeplus.modules.baobiao.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.baobiao.entity.CheJianShengChanBaoBiao;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
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
    private BusinessBaoGongOrderService businessBaoGongOrderService;
    @Autowired
    private BusinessProductMapper businessProductMapper;
    @ResponseBody
    @RequestMapping(value = "cheJianShengChanBaoBiao")
    public Map<String, Object> cheJianShengChanBaoBiao(String cinvcodes,String cinvnames) {
        Page<CheJianShengChanBaoBiao> page = new Page<>();
        if(StringUtils.isEmpty(cinvcodes)){
            return getBootstrapData(page);
        }
        List<CheJianShengChanBaoBiao> list = Lists.newArrayList();
        List<String> cinvcodeArray = Arrays.asList(cinvcodes.split(","));
        List<String> cinvnameArray = Arrays.asList(cinvnames.split("\\\\"));
        int i = 0;
        for (String cinvcode:cinvcodeArray){
            CheJianShengChanBaoBiao cheJianShengChanBaoBiao = new CheJianShengChanBaoBiao();
            cheJianShengChanBaoBiao.setCinvcode(cinvcode);
            cheJianShengChanBaoBiao.setCinvname(cinvnameArray.get(i));
            i++;
            cheJianShengChanBaoBiao.setNo(i);
            String daynum = businessProductMapper.getDayNumOfCinvcode(cinvcode);
            cheJianShengChanBaoBiao.setDaynum(daynum);
            cheJianShengChanBaoBiao.setNodonum(businessBaoGongOrderService.getNoDoneNumByCinvcode(cinvcode));
            list.add(cheJianShengChanBaoBiao);
        }
        page.setCount(list.size());
        page.setPageSize(-1);
        page.setList(list);
        return getBootstrapData(page);
    }
    /********** 车间生产报表 End **********/
}

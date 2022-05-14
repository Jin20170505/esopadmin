/**
 * 
 */
package com.jeeplus.modules.business.shengchan.bom.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.service.BusinessShengChanDingDanService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanDingdanMx;
import com.jeeplus.modules.business.shengchan.bom.service.BusinessShengChanDingdanMxService;

/**
 * 生产子件Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/bom/businessShengChanDingdanMx")
public class BusinessShengChanDingdanMxController extends BaseController {

	@Autowired
	private BusinessShengChanDingdanMxService businessShengChanDingdanMxService;

	@Autowired
	private BusinessShengChanDingDanService businessShengChanDingDanService;

	@ModelAttribute
	public BusinessShengChanDingdanMx get(@RequestParam(required=false) String id) {
		BusinessShengChanDingdanMx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanDingdanMxService.get(id);
		}
		if (entity == null){
			entity = new BusinessShengChanDingdanMx();
		}
		return entity;
	}
	
	/**
	 * 生产子件列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanDingdanMx businessShengChanDingdanMx, Model model) {
		model.addAttribute("businessShengChanDingdanMx", businessShengChanDingdanMx);
		return "modules/business/shengchan/bom/businessShengChanDingdanMxList";
	}
	
		/**
	 * 生产子件列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanDingdanMx businessShengChanDingdanMx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanDingdanMx> page = businessShengChanDingdanMxService.findPage(new Page<BusinessShengChanDingdanMx>(request, response), businessShengChanDingdanMx); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑生产子件表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode,String schid, BusinessShengChanDingdanMx businessShengChanDingdanMx, Model model) {
		model.addAttribute("mode", mode);
		BusinessShengChanDingDanMingXi mx = businessShengChanDingDanService.getMxId(schid);
		businessShengChanDingdanMx.setId(schid);
		businessShengChanDingdanMx.setCinvcode(mx.getCinv().getCode());
		businessShengChanDingdanMx.setCinvname(mx.getCinvname());
		businessShengChanDingdanMx.setCinvstd(mx.getStd());
		businessShengChanDingdanMx.setNum(mx.getNum()+"");
		businessShengChanDingdanMx.setUnit(mx.getUnit());
		businessShengChanDingdanMx.setSccode(mx.getP().getCode());
		businessShengChanDingdanMx.setLineno(mx.getNo()+"");
		businessShengChanDingdanMx.setBusinessShengChanBomList(businessShengChanDingdanMxService.findBomList(schid));
		model.addAttribute("businessShengChanDingdanMx", businessShengChanDingdanMx);
		return "modules/business/shengchan/bom/businessShengChanDingdanMxForm";
	}

	/**
	 * 保存生产子件
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanDingdanMx businessShengChanDingdanMx, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessShengChanDingdanMx);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessShengChanDingdanMxService.save(businessShengChanDingdanMx);//保存
		j.setSuccess(true);
		j.setMsg("保存生产子件成功");
		return j;
	}

	
	/**
	 * 批量删除生产子件
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanDingdanMxService.delete(businessShengChanDingdanMxService.get(id));
		}
		j.setMsg("删除生产子件成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanDingdanMx businessShengChanDingdanMx, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产子件"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanDingdanMx> page = businessShengChanDingdanMxService.findPage(new Page<BusinessShengChanDingdanMx>(request, response, -1), businessShengChanDingdanMx);
    		new ExportExcel("生产子件", BusinessShengChanDingdanMx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出生产子件记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanDingdanMx detail(String id) {
		return businessShengChanDingdanMxService.get(id);
	}
	

	/**
	 * 导入Excel数据
	 */
	@ResponseBody
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanDingdanMx> list = ei.getDataList(BusinessShengChanDingdanMx.class);
			for (BusinessShengChanDingdanMx businessShengChanDingdanMx : list){
				try{
					businessShengChanDingdanMxService.save(businessShengChanDingdanMx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条生产子件记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条生产子件记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入生产子件失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入生产子件数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产子件数据导入模板.xlsx";
    		List<BusinessShengChanDingdanMx> list = Lists.newArrayList(); 
    		new ExportExcel("生产子件数据", BusinessShengChanDingdanMx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}
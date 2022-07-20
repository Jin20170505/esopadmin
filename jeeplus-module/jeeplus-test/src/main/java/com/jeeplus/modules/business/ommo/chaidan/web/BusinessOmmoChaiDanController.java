/**
 * 
 */
package com.jeeplus.modules.business.ommo.chaidan.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoYongItem;
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDanMx;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoDetail;
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
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDan;
import com.jeeplus.modules.business.ommo.chaidan.service.BusinessOmmoChaiDanService;

/**
 * 委外拆单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ommo/chaidan/businessOmmoChaiDan")
public class BusinessOmmoChaiDanController extends BaseController {

	@Autowired
	private BusinessOmmoChaiDanService businessOmmoChaiDanService;
	
	@ModelAttribute
	public BusinessOmmoChaiDan get(@RequestParam(required=false) String id) {
		BusinessOmmoChaiDan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessOmmoChaiDanService.get(id);
		}
		if (entity == null){
			entity = new BusinessOmmoChaiDan();
		}
		return entity;
	}
	
	/**
	 * 委外拆单列表页面
	 */
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessOmmoChaiDan businessOmmoChaiDan, Model model) {
		model.addAttribute("businessOmmoChaiDan", businessOmmoChaiDan);
		return "modules/business/ommo/chaidan/businessOmmoChaiDanList";
	}

	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		model.addAttribute("order", businessOmmoChaiDanService.get(rid));
		return "modules/business/ommo/chaidan/print";
	}
	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String rid) {
		AjaxJson j = new AjaxJson();
		businessOmmoChaiDanService.print(rid);
		j.setSuccess(true);
		j.setMsg("成功");
		return j;
	}

	@RequestMapping("/bomqr")
	public void getBomImage(String rid,HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessOmmoChaiDanMx tagBean = businessOmmoChaiDanService.getMx(rid);
			String qr = "'cinvcode':'"+tagBean.getCinvcode()+"','cinvcodename':'"+tagBean.getCinvname()+"','batchno':'"+tagBean.getBatchno()+"','num':'"+tagBean.getNum()+"','unit':'"+tagBean.getUnit()+"'";
			out = response.getOutputStream();
			QRCodeUtil.encode(qr,out);
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
	}
	@RequestMapping("/qr")
	public void getQrImage(String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessOmmoChaiDan main = businessOmmoChaiDanService.getMain(rid);
			String qr = "{\"wwcode\":\""+main.getWwcode()+"\",\"lineno\":\""+main.getWwline()+"\",\"wwid\":\""+main.getWwid()+"\",\"wwhid\":\""+main.getWwhid()+"\",\"cdid\":\""+main.getId()+"\"}";
			out = response.getOutputStream();
			QRCodeUtil.encode(qr,out);
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
	}
		/**
	 * 委外拆单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessOmmoChaiDan businessOmmoChaiDan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessOmmoChaiDan> page = businessOmmoChaiDanService.findPage(new Page<BusinessOmmoChaiDan>(request, response), businessOmmoChaiDan); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑委外拆单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:ommo:chaidan:businessOmmoChaiDan:view","business:ommo:chaidan:businessOmmoChaiDan:add","business:ommo:chaidan:businessOmmoChaiDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessOmmoChaiDan businessOmmoChaiDan, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessOmmoChaiDan", businessOmmoChaiDan);
		return "modules/business/ommo/chaidan/businessOmmoChaiDanForm";
	}

	/**
	 * 保存委外拆单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ommo:chaidan:businessOmmoChaiDan:add","business:ommo:chaidan:businessOmmoChaiDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessOmmoChaiDan businessOmmoChaiDan, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessOmmoChaiDan);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessOmmoChaiDanService.save(businessOmmoChaiDan);//保存
		j.setSuccess(true);
		j.setMsg("保存委外拆单成功");
		return j;
	}

	
	/**
	 * 批量删除委外拆单
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessOmmoChaiDanService.delete(businessOmmoChaiDanService.get(id));
		}
		j.setMsg("删除委外拆单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessOmmoChaiDan businessOmmoChaiDan, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外拆单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessOmmoChaiDan> page = businessOmmoChaiDanService.findPage(new Page<BusinessOmmoChaiDan>(request, response, -1), businessOmmoChaiDan);
    		new ExportExcel("委外拆单", BusinessOmmoChaiDan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出委外拆单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessOmmoChaiDan detail(String id) {
		return businessOmmoChaiDanService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessOmmoChaiDan> list = ei.getDataList(BusinessOmmoChaiDan.class);
			for (BusinessOmmoChaiDan businessOmmoChaiDan : list){
				try{
					businessOmmoChaiDanService.save(businessOmmoChaiDan);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条委外拆单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条委外拆单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入委外拆单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入委外拆单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:chaidan:businessOmmoChaiDan:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外拆单数据导入模板.xlsx";
    		List<BusinessOmmoChaiDan> list = Lists.newArrayList(); 
    		new ExportExcel("委外拆单数据", BusinessOmmoChaiDan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}
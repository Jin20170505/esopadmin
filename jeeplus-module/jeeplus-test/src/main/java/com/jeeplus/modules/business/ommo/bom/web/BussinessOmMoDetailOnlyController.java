/**
 * 
 */
package com.jeeplus.modules.business.ommo.bom.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoYongItem;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
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
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import com.jeeplus.modules.business.ommo.bom.service.BussinessOmMoDetailOnlyService;

/**
 * 委外用料Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ommo/bom/bussinessOmMoDetailOnly")
public class BussinessOmMoDetailOnlyController extends BaseController {

	@Autowired
	private BussinessOmMoDetailOnlyService bussinessOmMoDetailOnlyService;
	
	@ModelAttribute
	public BussinessOmMoDetailOnly get(@RequestParam(required=false) String id) {
		BussinessOmMoDetailOnly entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bussinessOmMoDetailOnlyService.get(id);
		}
		if (entity == null){
			entity = new BussinessOmMoDetailOnly();
		}
		return entity;
	}
	@RequestMapping("/qr")
	public void getQrImage(String rid,HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BussinessOmMoYongItem tagBean = bussinessOmMoDetailOnlyService.getMx(rid);
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
	/**
	 * 委外用料列表页面
	 */
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:list")
	@RequestMapping(value = {"list", ""})
	public String list(BussinessOmMoDetailOnly bussinessOmMoDetailOnly, Model model) {
		model.addAttribute("bussinessOmMoDetailOnly", bussinessOmMoDetailOnly);
		return "modules/business/ommo/bom/bussinessOmMoDetailOnlyList";
	}
	
		/**
	 * 委外用料列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BussinessOmMoDetailOnly bussinessOmMoDetailOnly, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BussinessOmMoDetailOnly> page = bussinessOmMoDetailOnlyService.findPage(new Page<BussinessOmMoDetailOnly>(request, response), bussinessOmMoDetailOnly); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑委外用料表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BussinessOmMoDetailOnly bussinessOmMoDetailOnly, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bussinessOmMoDetailOnly", bussinessOmMoDetailOnly);
		return "modules/business/ommo/bom/bussinessOmMoDetailOnlyForm";
	}

	/**
	 * 保存委外用料
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ommo:bom:bussinessOmMoDetailOnly:add","business:ommo:bom:bussinessOmMoDetailOnly:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BussinessOmMoDetailOnly bussinessOmMoDetailOnly, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bussinessOmMoDetailOnly);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bussinessOmMoDetailOnlyService.save(bussinessOmMoDetailOnly);//保存
		j.setSuccess(true);
		j.setMsg("保存委外用料成功");
		return j;
	}

	
	/**
	 * 批量删除委外用料
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bussinessOmMoDetailOnlyService.delete(bussinessOmMoDetailOnlyService.get(id));
		}
		j.setMsg("删除委外用料成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BussinessOmMoDetailOnly bussinessOmMoDetailOnly, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外用料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BussinessOmMoDetailOnly> page = bussinessOmMoDetailOnlyService.findPage(new Page<BussinessOmMoDetailOnly>(request, response, -1), bussinessOmMoDetailOnly);
    		new ExportExcel("委外用料", BussinessOmMoDetailOnly.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出委外用料记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BussinessOmMoDetailOnly detail(String id) {
		return bussinessOmMoDetailOnlyService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BussinessOmMoDetailOnly> list = ei.getDataList(BussinessOmMoDetailOnly.class);
			for (BussinessOmMoDetailOnly bussinessOmMoDetailOnly : list){
				try{
					bussinessOmMoDetailOnlyService.save(bussinessOmMoDetailOnly);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条委外用料记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条委外用料记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入委外用料失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入委外用料数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:bom:bussinessOmMoDetailOnly:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外用料数据导入模板.xlsx";
    		List<BussinessOmMoDetailOnly> list = Lists.newArrayList(); 
    		new ExportExcel("委外用料数据", BussinessOmMoDetailOnly.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}
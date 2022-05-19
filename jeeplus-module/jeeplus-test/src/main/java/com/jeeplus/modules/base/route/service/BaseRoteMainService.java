/**
 * 
 */
package com.jeeplus.modules.base.route.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.u8data.prouting.entity.U8Prouting;
import com.jeeplus.modules.u8data.prouting.entity.U8ProutingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.mapper.BaseRoteMainMapper;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.base.route.mapper.BaseRouteMapper;

/**
 * 工艺路线Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseRoteMainService extends CrudService<BaseRoteMainMapper, BaseRoteMain> {

	@Autowired
	private BaseRouteMapper baseRouteMapper;
	
	public BaseRoteMain get(String id) {
		BaseRoteMain baseRoteMain = super.get(id);
		baseRoteMain.setBaseRouteList(baseRouteMapper.findList(new BaseRoute(baseRoteMain)));
		return baseRoteMain;
	}

	public List<BaseRoute> getRoutes(String rid){
		return baseRouteMapper.findList(new BaseRoute(new BaseRoteMain(rid)));
	}

	public List<BaseRoteMain> findList(BaseRoteMain baseRoteMain) {
		return super.findList(baseRoteMain);
	}
	
	public Page<BaseRoteMain> findPage(Page<BaseRoteMain> page, BaseRoteMain baseRoteMain) {
		return super.findPage(page, baseRoteMain);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseRoteMain baseRoteMain) {
		super.save(baseRoteMain);
		for (BaseRoute baseRoute : baseRoteMain.getBaseRouteList()){
			if (baseRoute.getId() == null){
				continue;
			}
			if (BaseRoute.DEL_FLAG_NORMAL.equals(baseRoute.getDelFlag())){
				if (StringUtils.isBlank(baseRoute.getId())){
					baseRoute.setP(baseRoteMain);
					baseRoute.setVersion(baseRoteMain.getVersion());
					baseRoute.setProduct(baseRoteMain.getProduct());
					baseRoute.preInsert();
					baseRouteMapper.insert(baseRoute);
				}else{
					baseRoute.preUpdate();
					baseRouteMapper.update(baseRoute);
				}
			}else{
				baseRouteMapper.delete(baseRoute);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseRoteMain baseRoteMain) {
		super.delete(baseRoteMain);
		baseRouteMapper.delete(new BaseRoute(baseRoteMain));
	}

	public List<BaseRoteMain> findVersion(String productid){
		return mapper.findVersion(productid);
	}

	public BaseRoteMain getRouteVersionByCinvCode(String cinvcode){
		return mapper.getRouteVersionByCinvCode(cinvcode);
	}

	@Transactional(readOnly = false)
    public void sychU8(List<U8Prouting> data, List<U8ProutingDetail> details) {
		data.stream().filter(d->null==mapper.hasById(d.getProutingid())).forEach(d->{
			BaseRoteMain m = new BaseRoteMain();
			m.preInsert();
			m.setId(d.getProutingid());
			m.setCode(d.getCinvcode());
			BusinessProduct product = new BusinessProduct();
			product.setName(d.getCinvname());
			m.setProduct(product);
			m.setVersion(d.getVersion());
			m.setStd(d.getCinvstd());
			m.setEnable("0");
			m.setCode(d.getCinvcode());
			mapper.insert(m);
		});

		details.stream().filter(d->null==baseRouteMapper.hasById(d.getProutingDId())).forEach(d->{
			BaseRoute r = new BaseRoute();
			r.preInsert();
			r.setId(d.getProutingDId());
			r.setNo(d.getOpSeq());
			r.setP(new BaseRoteMain(d.getProutingId()));
			r.setSite(new BaseSite(d.getOperationId()));
			baseRouteMapper.insert(r);
		});
    }
}
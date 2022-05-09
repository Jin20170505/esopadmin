/**
 *
 */
package com.jeeplus.modules.base.classgroup.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.factory.entity.BaseFactory;
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;
import com.jeeplus.modules.base.productionline.entity.BaseProductionLine;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 班组管理Entity
 * @author Jin
 */
public class BaseClassGroup extends DataEntity<BaseClassGroup> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 班组编号
	private String name;		// 班组名称
	private User leader;		// 班长
	private String leadercode;		// 班长工号
	private BaseFactory factory;		// 所属工厂
	private BaseWorkShop workshop;		// 所属车间
	private BaseProductionLine line;		// 所属产线
	private List<BaseClassGroupUser> baseClassGroupUserList = Lists.newArrayList();		// 子表列表
	
	public BaseClassGroup() {
		super();
	}

	public BaseClassGroup(String id){
		super(id);
	}

	@ExcelField(title="班组编号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="班组名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="班长不能为空")
	@ExcelField(title="班长", fieldType=User.class, value="leader.name", align=2, sort=8)
	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}
	
	@ExcelField(title="班长工号", align=2, sort=9)
	public String getLeadercode() {
		return leadercode;
	}

	public void setLeadercode(String leadercode) {
		this.leadercode = leadercode;
	}
	
	@NotNull(message="所属工厂不能为空")
	@ExcelField(title="所属工厂", fieldType=BaseFactory.class, value="factory.name", align=2, sort=10)
	public BaseFactory getFactory() {
		return factory;
	}

	public void setFactory(BaseFactory factory) {
		this.factory = factory;
	}
	
	@NotNull(message="所属车间不能为空")
	@ExcelField(title="所属车间", fieldType=BaseWorkShop.class, value="workshop.name", align=2, sort=11)
	public BaseWorkShop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(BaseWorkShop workshop) {
		this.workshop = workshop;
	}
	
	@ExcelField(title="所属产线", fieldType=BaseProductionLine.class, value="line.name", align=2, sort=12)
	public BaseProductionLine getLine() {
		return line;
	}

	public void setLine(BaseProductionLine line) {
		this.line = line;
	}
	
	public List<BaseClassGroupUser> getBaseClassGroupUserList() {
		return baseClassGroupUserList;
	}

	public void setBaseClassGroupUserList(List<BaseClassGroupUser> baseClassGroupUserList) {
		this.baseClassGroupUserList = baseClassGroupUserList;
	}
}
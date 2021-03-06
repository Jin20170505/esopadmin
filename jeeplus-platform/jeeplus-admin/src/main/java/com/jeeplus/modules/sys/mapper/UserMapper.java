/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.modules.sys.entity.CProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.User;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 用户MAPPER接口
 * @author Jin
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

	@Select("select case when exists (select 1 from sys_user where no = #{code} and del_flag = '0')  then 1 else 0 end")
	boolean isCode(@Param("code") String code);

	@Select("select remarks from sys_user where id = #{userid}")
	String getAppMenu(@Param("userid") String userid);

	/**
	 * 根据工号查询用户
	 * @param no
	 * @return
	 */
	User getByNo(@Param("no") String no);
	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 插入好友
	 */
	public int insertFriend(@Param("id") String id, @Param("userId") String userId, @Param("friendId") String friendId);
	
	/**
	 * 查找好友
	 */
	public User findFriend(@Param("userId") String userId, @Param("friendId") String friendId);
	/**
	 * 删除好友
	 */
	public void deleteFriend(@Param("userId") String userId, @Param("friendId") String friendId);
	
	/**
	 * 
	 * 获取我的好友列表
	 * 
	 */
	public List<User> findFriends(User currentUser);
	
	/**
	 * 
	 * 查询用户-->用来添加到常用联系人
	 * 
	 */
	public List<User> searchUsers(User user);
	
	/**
	 * 
	 */
	
	public List<User>  findListByOffice(User user);
	
	/**   新添加方法-sunping   */
	/**
	 * 提供资源共享的部门总数统计
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Long sharecompanyNumber();
    /**
     * 共享资源类别种类数
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Long resNumber();
    /**
     * 共享资源记录数量
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String sharevolNum();
    /**
     * 开放资源记录数量
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String publicvolNum();

}

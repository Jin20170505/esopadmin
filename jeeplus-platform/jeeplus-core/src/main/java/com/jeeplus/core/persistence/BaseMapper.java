/**
 *
 */
package com.jeeplus.core.persistence;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * DAO支持类实现
 * @author Jin
 * @version 2017-05-16
 * @param <T>
 */
public interface BaseMapper<T> {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public  T findUniqueByProperty(@Param(value = "propertyName") String propertyName, @Param(value = "value") Object value);


	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);

	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);

	/**
	 * 查询所有数据列表
	 * @see public List<T> findAllList(T entity)
	 * @return
	 */
	@Deprecated
	public List<T> findAllList();

	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);

	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);

	/**
	 * 删除数据（物理删除，从数据库中彻底删除）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);

	/**
	 * 批量删除数据（物理删除，从数据库中彻底删除）
	 * @see public int batchDelete(T entity)
	 * @return
	 */
	public int batchDelete(String[] ids);

	/**
	 * 删除数据（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int deleteByLogic(String id);

	/**
	 * 删除数据（物理删除，从数据库中彻底删除）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

	/**
	 * 删除数据（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 * @param entity
	 * @return
	 */
	public int deleteByLogic(T entity);

	@Select("${sql}")
	public List<Map<String, Object>>  execSelectSql(@Param(value="sql")String sql);

	@Update("${sql}")
	public void execUpdateSql(@Param(value = "sql") String sql);

	@Insert("${sql}")
	public void execInsertSql(@Param(value = "sql") String sql);

	@Delete("${sql}")
	public void execDeleteSql(@Param(value = "sql") String sql);
	
}
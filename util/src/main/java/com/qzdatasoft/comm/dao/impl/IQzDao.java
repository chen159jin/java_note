package com.qzdatasoft.comm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;

import com.qzdatasoft.comm.web.Pagination;

/**
 * DAO 接口，所有的需要跟数据库打交道的Javabean类，必须由工具类TableToClass生成，或者符合指定的格式
 * 
 * @author yangzhuang
 * 
 */
public interface IQzDao extends java.io.Serializable {
	/**
	 * 生成规定长度的主键，返回值为字符串类型
	 * 
	 * @param length
	 * @return
	 */
	String getSequence(int length);

	/**
	 * 生成主键，返回值为int类型
	 * 
	 * @return
	 */
	int getSequence1();

	/**
	 * 生成主键，返回值为String类型
	 * 
	 * @return
	 */
	String getSequence();

	/**
	 * 生成主键，返回值为int，需要自己定义seq
	 * 
	 * @param s
	 *            序号
	 * @return
	 */
	int getSequence(String seq);

	/**
	 * 获取事务，在DAO中直接获取事务 <br>
	 * 案例说明：这2个操作必须全部成功，才能真正递交 <br>
	 * TransactionStatus ts =
	 * qzDao.getTransactionStatus(); try { XX;(某个操作) xx2;（第二个操作)
	 * qzDao.commitTransaction(ts); } catch (Exception e) {
	 * qzDao.rollbackTransaction(ts); throw new
	 * ServiceException(e.getMessage()); } <br>
	 * 如果要手工控制事务，请不要使用spring
	 * 自动事务或者在spring中取消该方法的事务，然后手工控制事务
	 * 
	 * @return
	 */
	TransactionStatus getTransactionStatus();

	/**
	 * 回滚事务，在抛出异常之前回滚事务
	 * 
	 * @param status
	 */
	void rollbackTransaction(TransactionStatus status);

	/**
	 * 执行事务，在处理完业务逻辑之后，提交事务
	 * 
	 * @param status
	 */
	void commitTransaction(TransactionStatus status);

	/**
	 * 按照预定生成指定格式的Javabean，然后保存到数据库（Javabean，请使用工具类TableToClass生成）
	 * 
	 * @param object
	 */
	void save(Object object);

	// 增加对clob的支持
	void saveByClob(Object object, String clobValue);

	/**
	 * 删除对象，对象格式，必须遵循规定格式，（Javabean，请使用工具类TableToClass生成）
	 * 
	 * @param object
	 */
	void delete(Object object);

	/**
	 * 根据主键删除，支持联合主键删除，<br>
	 * primaryKeyValue的顺序必须跟Javabean中的联合主键的顺序一致
	 * 
	 * @param clazz
	 * @param primaryKeyValue
	 *            主键值（数组形式，顺序与规定相符合）
	 */
	void deleteByPrimaryKey(Class clazz, Object[] primaryKeyValue);

	/**
	 * 根据主键删除，主键只能有一个，
	 * 
	 * @param clazz
	 * @param primaryKeyValue
	 *            主键
	 */
	void deleteByPrimaryKey(Class clazz, Object primaryKeyValue);

	/**
	 * 更新一个对象 <br>
	 * 标准的更新步骤： 先从数据库中获取这一条记录，然后把需要修改的属性set进去，然后再执行此方法 这样就可以避免某些数据遗失
	 * 
	 * @param object
	 * @throws Exception
	 */
	void update(Object object);

	/**
	 * 更新一个对象(只更新不为空的属性) <br>
	 * 标准的更新步骤： 先从数据库中获取这一条记录，然后把需要修改的属性set进去，然后再执行此方法
	 * 这样就可以避免某些数据遗失
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void updateNotNull(Object object);

	/**
	 * clob 支持
	 * @param object
	 * @param clobValue
	 */
	void updateByClob(Object object, String clobValue);

	/**
	 * 获取该Class所有的对象
	 * 
	 * @param clazz
	 * @return
	 */
	List findAll(Class clazz);

	/**
	 * 尽量不要使用该方法,主键顺序存在不稳定性<br>
	 * 根据主键查询该类，支持联合主键查询,如果是联合主键，值的顺序，请与JavaBean 中的顺序一致
	 * 
	 * @param clazz
	 * @param object
	 *            主键值（数组形式，参数顺序与Javabean中的顺序一致）
	 * @return
	 */
	Object findByPrimaryKey(Class clazz, Object[] object);

	/**
	 * 根据主键查询该类，只支持单个主键值
	 * 
	 * @param clazz
	 *            类
	 * @param object
	 *            主键值
	 * @return
	 */
	Object findByPrimaryKey(Class clazz, Object object);

	/**
	 * 通过SQL语句查询并分页,得到的数据是以数组的形式组织, 如果sql中有order by ,请不要在传入oderStrs
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageIndex
	 *            起始页面
	 * @param pageSize
	 *            页面大小
	 * @param args
	 *            查询参数的值
	 * @param orderStrs
	 *            可变参数，当有排序字段时使用
	 * @return Pagination 分页对象
	 * @return
	 */
	public Pagination getSqlPagination(String sql, final Integer pageIndex, final Integer pageSize,
			final List args, List orderStrs);

	/**
	 * 通过SQL语句查询并分页,得到的数据是以JavaBean的形式组织 适用于简单的Javabean， 需要手工传入sql
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageIndex
	 *            起始页面
	 * @param pageSize
	 *            页面大小
	 * @param args
	 *            查询参数的值
	 * @param orderStrs
	 *            可变参数，当有排序字段时使用
	 * @return Pagination 分页对象
	 * @return
	 */
	public Pagination getSqlPaginationToBean(Class clazz, String sql, final Integer pageIndex,
			final Integer pageSize, final List args, List orderStrs);

	/**
	 * 获取分页信息 通过SQL语句查询并分页,得到的数据是以JavaBean的形式组织 适用于简单的Javabean 不需sql
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageIndex
	 *            起始页面
	 * @param pageSize
	 *            页面大小
	 * @param args
	 *            查询参数的值
	 * @param orderStrs
	 *            可变参数，当有排序字段时使用
	 * @return Pagination 分页对象
	 * @return
	 */
	public Pagination getSqlPaginationToBean(Class clazz, final Integer pageIndex, final Integer pageSize,
			List orderStrs);

	/**
	 * 查询数据，返回List 结果集为封装成数组的List 支持通配符，参数顺序必须跟sql中一致 通过遍历数组，可以得到数据。
	 * 必须记得查询的顺序，才能正确遍历数组 此方法性能比较好，比execSqlQueryToMap高
	 * 
	 * @param sqlStr
	 * @param list
	 *            通配符的参数，顺序必须跟SQL一致，并且个数一样
	 * @return
	 */
	public List execSqlQueryToArrays(String sqlStr, List list) throws SQLException;

	public List execSqlQueryToArrays(String sqlStr, List list, RowMapper rowMapper) throws SQLException;

	/**
	 * 查询数据 ，结果集为封装成数组的List 通过遍历数组，可以得到数据。 必须记得查询的顺序，才能正确遍历数组
	 * 此方法性能比较好，比execSqlQueryToMap高
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List execSqlQueryToArrays(String sqlStr);

	/**
	 * 查询数据，结果集为封装成Map的List 支持通配符，参数顺序必须跟sql中一致
	 * 在Map中，键是列名，值是该列名的值，通过遍历Map，可以得到数据。 此方法性能比execSqlQueryToArrays低
	 * 
	 * @param sqlStr
	 * @param list
	 * @return
	 */
	public List execSqlQueryToMap(String sqlStr, List list);

	/**
	 * 查询数据 ，结果集为封装成Map的List 在Map中，键是列名，值是该列名的值，通过遍历Map，可以得到数据。
	 * 此方法性能比execSqlQueryToArrays低
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List execSqlQueryToMap(String sqlStr);

	/**
	 * 原生态的Sql查询，结果集是封装后的Class 的集合List
	 * 
	 * @param clazz
	 * @param sqlStr
	 *            查询sql
	 * @return
	 */
	public List execSqlQuery(Class clazz, String sqlStr);

	/**
	 * 原生态的Sql查询，支持通配符，查询出的数据会封装好class传入List
	 * 
	 * @param clazz
	 * @param sqlStr
	 * @param list
	 * @return
	 */
	public List execSqlQuery(Class clazz, String sqlStr, List list);

	/**
	 * 原生态SQL修改语句 ，支持新增，删除，修改，sql中支持通配符，参数list，是参数，必须与sql中的顺序一致
	 * 
	 * @param sql
	 * @param list
	 *            操作的参数，顺序和个数与通配符想对应
	 * @return
	 */
	public Integer execSqlUpdate(String sql, List list);

	/**
	 * 原生态SQL修改语句，支持新增，删除，修改
	 * 
	 * @param sql
	 * @return
	 */
	public Integer execSqlUpdate(String sql);

	/**
	 * 执行存储过程
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int execProcedure(String sql, List args);

	/**
	 * 当实体对象不存在时候检查属性的唯一性
	 * 
	 * @param clazz
	 *            POJO对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查的值
	 * @return boolean true唯一，false不唯一
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value);

	/**
	 * 当实体对象不存在时候检查属性的唯一性
	 * 
	 * @param clazz
	 *            Javabean对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查的值
	 * @return boolean true唯一，false不唯一
	 */
	public boolean validateOnly(String clazz, String property, Integer value);

	/**
	 * 当实体对象不存在时候检查属性的唯一性
	 * 
	 * @param clazz
	 *            POJO对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查的值
	 * @return boolean true唯一，false不唯一
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, Long value);

	/**
	 * 当实体对象存在时使用该方法来验证唯一性
	 * 
	 * @param clazz
	 *            POJO对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查值
	 * @param pk
	 *            主键属性
	 * @param pkValue
	 *            主键值
	 * @return boolean true唯一，false不唯一
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, String pkValue);

	/**
	 * 当实体对象存在时使用该方法来验证唯一性
	 * 
	 * @param clazz
	 *            POJO对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查值
	 * @param pk
	 *            主键属性
	 * @param pkValue
	 *            主键值
	 * @return boolean true唯一，false不唯一
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, Integer pkValue);

	/**
	 * 当实体对象存在时使用该方法来验证唯一性
	 * 
	 * @param clazz
	 *            POJO对象名称
	 * @param property
	 *            属性
	 * @param value
	 *            检查值
	 * @param pk
	 *            主键属性
	 * @param pkValue
	 *            主键值
	 * @return boolean true唯一，false不唯一
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, Long pkValue);

	/**
	 * sql批处理
	 * 
	 * @param sql
	 * @param size
	 *            记录的大小
	 * @param args
	 *            多个数组，每个数组对应一个?号
	 */
	public int[] execSqlUpdateBatch(String sql, final int size, final Object[]... args);

	/**
	 * 批处理
	 * 
	 * @param sql
	 * @param size
	 *            记录的大小
	 * @param args
	 *            记录集合, 2维数组记录存放方式如下：
	 *            {{arg0,arg1,arg2},{arg0,arg1,arg2},{arg0,arg1,arg2}...},
	 *            其中每个一维数组对应一条数据
	 */
	public int[] execSqlUpdateBatchFixed(String sql, final int size, final Object[]... args);

	/**
	 * 获取JDBC连接
	 * 
	 * @return Connection
	 */
	public Connection getConnection();
	
	/**
	 * 根据键值进行查询<br>
	 * param 以 字段 对 值 的形式
	 * @param clazz
	 * @param object
	 * @return
	 */
	public List findByKeyValue(Class clazz, Map param);
}

package com.qzdatasoft.comm.dao.impl;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.qzdatasoft.comm.dao.util.ConvertUtil;
import com.qzdatasoft.comm.dao.util.Sequence;

//import com.qzdatasoft.comm.framework.execption.ServiceException;
//import com.qzdatasoft.comm.generic.Sequence;
//import com.qzdatasoft.comm.web.Pagination;
/**
 * <p>
 * Title: DAO
 * <p>
 * Description:
 *  如果service层使用DAO，要进行事务控制的话，使用baseTransaction,如果要回滚事务，请catch 并且抛出ServiceException ,RuntimeException,Exception,
 * 这3个异常会回滚此方法
 * 如果想手工控制事务，请不要使用baseTransaction，手工获得事务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * <p>
 * author: 陈文
 * </p>
 * <p>
 * Date: 2011-5-31
 * </p>
 * 
 * @version 1.0
 */
public class QzDao implements IQzDao { 
	
	private static final long serialVersionUID = 8706329868820330087L;
	
	private static final Logger log = LoggerFactory.getLogger(QzDao.class);

	private JdbcTemplate jdbcTemplate;

	private JDBCDataSourceTransactionManager transaction;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void delete(Object object) {
		String sql = ConvertUtil.deleteSql(object);
		jdbcTemplate.update(sql);
		log.debug(sql);
	}

	public void deleteByPrimaryKey(Class clazz, Object[] primaryKeyValue){		
		String sql = ConvertUtil.deleteSqlByPkParam(clazz);
		jdbcTemplate.update(sql, primaryKeyValue);		
		log.debug(sql);
	}

	public void deleteByPrimaryKey(Class clazz, Object primaryKeyValue){
		Object[] o = { primaryKeyValue };
		deleteByPrimaryKey(clazz, o);
	}

	public int execProcedure(String sql, List args) {
		Object[] obj = ConvertUtil.convertList2Array(args);
		int i = this.jdbcTemplate.update(sql, obj);
		log.debug(sql);
		return i;
	}

	public List execSqlQuery(Class clazz, String sqlStr){
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Date startTime = new Date();
		List list = jdbcTemplate.query(sqlStr, new CommonRowMapper(clazz));
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return list;
	}

	public List execSqlQuery(Class clazz, String sqlStr, List list){
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Object[] obj = ConvertUtil.convertList2Array(list);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, obj, new CommonRowMapper(clazz));
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;
	}

	public List execSqlQueryToArrays(String sqlStr, List list){
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Object[] obj = ConvertUtil.convertList2Array(list);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, obj, new ArrayRowMapper());
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;
	}

	public List execSqlQueryToArrays(String sqlStr) {
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, new ArrayRowMapper());
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime(); 
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;
	}

	public List execSqlQueryToMap(String sqlStr, List list) {
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Object[] obj = ConvertUtil.convertList2Array(list);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, obj, new MapRowMapper());
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;
	}

	public List execSqlQueryToMap(String sqlStr){
		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, new MapRowMapper());
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;
	}

	public Integer execSqlUpdate(String sql, List list){
		Object[] obj = ConvertUtil.convertList2Array(list);
		int i = jdbcTemplate.update(sql, obj);
		log.debug(sql);
		return i;
	}

	public Integer execSqlUpdate(String sql){
		try {
			int i = jdbcTemplate.update(sql);
			log.debug(sql);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
	//		throw new ServiceException(e.getMessage());
		}
		return null;
	}

	/**
	 * 批处理
	 * 
	 * @param sql
	 * @param args
	 *            记录集合
	 */
	public int[] execSqlUpdateBatch(String sql, final int size,
			final Object[]... args) {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				for (int j = 0; j < args.length; j++) {
					Object[] obj = args[j];
					ps.setObject(j + 1, obj[i]);
				}

			}

			public int getBatchSize() {
				return size;
			}
		};
		int[] retval = jdbcTemplate.batchUpdate(sql, setter);
		log.debug(sql);
		return retval;
	}

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
	public int[] execSqlUpdateBatchFixed(String sql, final int size,
			final Object[]... args) {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				for (int j = 0; j < args[i].length; j++) {
					ps.setObject(j + 1, args[i][j]);
				}
			}

			public int getBatchSize() {
				return size;
			}
		};
		int[] retval = jdbcTemplate.batchUpdate(sql, setter);
		log.debug(sql);
		return retval;
	}

	public List findAll(Class clazz) {

		String sql = ConvertUtil.findAllSql(clazz);
		sql = ConvertUtil.valBeginning(sql);
		Date startTime = new Date();
		List list = jdbcTemplate.query(sql, new CommonRowMapper(clazz));
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime(); 
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sql);
		return list;
	}

	public List findAllBy(Class clazz) throws Exception {

		String sql = ConvertUtil.findAllSql(clazz);
		sql = ConvertUtil.valBeginning(sql);
		Date startTime = new Date();
		List list = jdbcTemplate.query(sql, new CommonRowMapper(clazz));
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime(); 
		log.debug("time: " + String.valueOf(hssj) + " ms;  " + sql);
		return list;
	}

	public Object findByPrimaryKey(Class clazz, Object[] object){		
		Map<String,Object> map = ConvertUtil.findByPrimaryKeySqlParam(clazz, object);
		String sql = map.get("sql").toString();
		List param = (List)map.get("parameter"); 
		//log.debug(sql);
		List list = this.execSqlQuery(clazz, sql,param);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public List findByKeyValue(Class clazz, Map object){
		Map<String,Object> map = ConvertUtil.findByKeyValueSqlParam(clazz, object);
		String sql = map.get("sql").toString();
		List param = (List)map.get("parameter"); 
		log.debug(sql);
		List list = this.execSqlQuery(clazz, sql,param);
		return list;
	}

	public Object findByPrimaryKey(Class clazz, Object object) {
		Object[] obj = { object };
		Object o = findByPrimaryKey(clazz, obj);
		return o;
	}

	public String getSequence(int length) {
		return Sequence.getInstance().getSequence(length);
	}

	public int getSequence1() {
		return getSequence("SEQ_QZJW");
	}

	public int getSequence(String seq) {
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(seq.trim()).append(".NEXTVAL").append(
				" from dual");
		int sequ = jdbcTemplate.queryForInt(sb.toString());
		log.debug(sb.toString());
		return sequ;
	}

	public Pagination getSqlPagination(String sql, Integer pageIndex,
			Integer pageSize, List list, List orderStrs) {
		return getSqlPaginationToBean(null, sql, pageIndex, pageSize, list,
				orderStrs);
	}

	public Pagination getSqlPaginationToBean(Class clazz, String sql,
			Integer pageIndex, Integer pageSize, List list, List orderStrs){
		list = (null != list ? list : new ArrayList());
		if (null != list) {
			list = Collections.unmodifiableList(list); // 此list只读，不可更改，避免影响调用方法的变量
		}

		if (sql.startsWith("f") || sql.startsWith("F")) {
			sql = "select * " + sql;
		}
		Pagination pagination = new Pagination();
		// 获得总记录数
		Date startCTime = new Date();
		int rss = getRowSize(sql, list);
		Date endCTime = new Date();
		long ctsj = endCTime.getTime() - startCTime.getTime();
		log.debug("cTime:" + String.valueOf(ctsj) + " ms   cRows:" + rss + " rows");
		
		pagination.setRowSize(rss);
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		for (int i = 0; null != orderStrs && i < orderStrs.size(); i++) {
			if (sb.indexOf("order by") == -1) {
				sb.append(" order by " + orderStrs.get(i));
			} else {
				sb.append("," + orderStrs.get(i));
			}
		}
		// 初始的sql出来了，必须把原来的sql 改写 select * from a where …… 变为 select b.*,rownum
		// rowno from a b where ……
		String sqlstr = ConvertUtil.getSqlPagination(sb.toString());
		int firstResult = pageSize.intValue() * (pageIndex.intValue() - 1);
		int maxResult = firstResult + pageSize.intValue();
		List sqlArgs = new ArrayList();
		if (null != list) {
			sqlArgs.addAll(list);
		}

		sqlArgs.add(maxResult);
		sqlArgs.add(firstResult);
		List lsl = null;
		Object[] object = ConvertUtil.convertList2Array(sqlArgs);
		Date startTime = new Date();
		if (clazz == null) {
			lsl = jdbcTemplate.query(sqlstr, object, new ArrayRowMapper());
		} else {
			lsl = jdbcTemplate.query(sqlstr, object, new CommonRowMapper(clazz));
		}

		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		log.debug("eTime:" + String.valueOf(hssj) + " ms:   " + sqlstr);
		
		pagination.setPageIndex(pageIndex.intValue());
		pagination.setPageSize(pageSize.intValue());
		pagination.setRowSet(lsl);

		// 建立分页信息
		pagination.buildPagination();
		return pagination;
	}

	public Pagination getSqlPaginationToBean(Class clazz, Integer pageIndex,
			Integer pageSize, List orderStrs) {
		Pagination pagination = new Pagination();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(clazz.getSimpleName());
		// 获得总记录数
		String rowSql = "select * from " + clazz.getSimpleName();
		pagination.setRowSize(getRowSize(rowSql, null));
		int firstResult = pageSize.intValue() * (pageIndex.intValue() - 1);
		int maxResult = firstResult + pageSize.intValue();
		List list = new ArrayList();
		list.add(maxResult);
		list.add(firstResult);
		for (int i = 0; null != orderStrs && i < orderStrs.size(); i++) {
			if (sb.indexOf("order by") == -1) {
				sb.append(" order by " + orderStrs.get(i));
			} else {
				sb.append("," + orderStrs.get(i));
			}
		}
		String sqlstr = ConvertUtil.getSqlPagination(sb.toString());
		List lsl = execSqlQuery(clazz, sqlstr, list);
		pagination.setPageIndex(pageIndex.intValue());
		pagination.setPageSize(pageSize.intValue());
		pagination.setRowSet(lsl);

		// 建立分页信息
		pagination.buildPagination();
		return pagination;
	}

	private int getRowSize(String sql, List list) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ");
		sb.append("(");
		sb.append(sql);
		sb.append(")");
		Object[] obj = ConvertUtil.convertList2Array(list);
		log.debug(sb.toString());
		return jdbcTemplate.queryForInt(sb.toString(), obj);
	}

	public TransactionStatus getTransactionStatus() {
		try {
			TransactionDefinition definition = new DefaultTransactionDefinition();
			TransactionStatus status = transaction.getTransactionManager()
					.getTransaction(definition);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	public void commitTransaction(TransactionStatus status) {
		try {
			transaction.getTransactionManager().commit(status);
		} catch (Exception e) {
			this.rollbackTransaction(status);
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	public void rollbackTransaction(TransactionStatus status) {
		try {
			transaction.getTransactionManager().rollback(status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void save(Object object) {	 	 
		Map map = ConvertUtil.createSqlParam(object); 
		String sql = map.get("sql").toString();
		List param = (List)map.get("parameter");
		 Iterator it = param.iterator();   
	        while(it.hasNext()){   
	            System.out.println(it.next());   
	        }  
		jdbcTemplate.update(sql,param == null ? null : param.toArray());
		log.debug(sql);
	}

	public void saveByClob(Object object, String clobValue) {
		String sql = ConvertUtil.createSql(object);
		LobHandler lobHandler = new DefaultLobHandler();
		SimpleNativeJdbcExtractor nativeJdbcExtractor = new SimpleNativeJdbcExtractor();
		final String content = clobValue; // Clob的值
		OracleLobHandler handler = new OracleLobHandler();
		handler.setNativeJdbcExtractor(nativeJdbcExtractor);

		jdbcTemplate.execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						lobCreator.setClobAsString(ps, 1, content); // 设置详细的CLOB字段的内容
					}
				});
		log.debug(sql);

	}

	public void updateByClob(Object object, String clobValue) {
		Map map = ConvertUtil.updateSql(object);
		String sql = (String) map.get("sql");
		LobHandler lobHandler = new DefaultLobHandler();
		SimpleNativeJdbcExtractor nativeJdbcExtractor = new SimpleNativeJdbcExtractor();
		final String content = clobValue; // Clob的值
		OracleLobHandler handler = new OracleLobHandler();
		handler.setNativeJdbcExtractor(nativeJdbcExtractor);

		jdbcTemplate.execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						lobCreator.setClobAsString(ps, 1, content); // 设置详细的CLOB字段的内容
					}
				});
		log.debug(sql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(Object object) {
		Map map = ConvertUtil.updateSqlParam(object);
		String sql = (String) map.get("sql");
		List<Object> param = (List<Object>)map.get("parameter");
		jdbcTemplate.update(sql,param == null ? null : param.toArray());
		log.debug(sql);
	}

	public void updateNotNull(Object object) {
		Map map = ConvertUtil.updateSqlNotNullParam(object);
		String sql = (String) map.get("sql");
		List<Object> param = (List<Object>)map.get("parameter");
		jdbcTemplate.update(sql,param == null ? null : param.toArray());
		log.debug(sql);
	}

	public boolean validateOnly(String clazz, String property, String value)
			throws DataAccessException {
		List args = new ArrayList();
		args.add(value);
		StringBuffer sf = new StringBuffer();
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? ");
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args); // this.find(sf.toString(),
			// args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("判定对象是否唯一异常");
		}
	}

	public boolean validateOnly(String clazz, String property, String value,
			String pk, String pkValue) throws DataAccessException {
		StringBuffer sf = new StringBuffer();
		sf.append("select * ");
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? and ").append(pk).append(" <> ?");
		List args = new ArrayList();
		args.add(value);
		args.add(pkValue);
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("判定唯一性报错");
		}
	}

	public boolean validateOnly(String clazz, String property, String value,
			String pk, Integer pkValue) throws DataAccessException {
		StringBuffer sf = new StringBuffer();
		sf.append("select * ");
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? and ").append(pk).append(" <> ?");
		List args = new ArrayList();
		args.add(value);
		args.add(pkValue);
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("判定唯一性报错");
		}
	}

	public boolean validateOnly(String clazz, String property, String value,
			String pk, Long pkValue) throws DataAccessException {
		StringBuffer sf = new StringBuffer();
		sf.append("select * ");
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? and ").append(pk).append(" <> ?");
		List args = new ArrayList();
		args.add(value);
		args.add(pkValue);
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("判定唯一性报错");
		}
	}

	public boolean validateOnly(String clazz, String property, Integer value)
			throws DataAccessException {
		List args = new ArrayList();
		args.add(value);
		StringBuffer sf = new StringBuffer();
		sf.append("select * ");
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? ");
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("判定唯一性报错");
		}
	}

	public boolean validateOnly(String clazz, String property, Long value)
			throws DataAccessException {
		List args = new ArrayList();
		args.add(value);
		StringBuffer sf = new StringBuffer();
		sf.append("select * ");
		sf.append("from ").append(clazz).append(" where ").append(property)
				.append(" = ? ");
		try {
			List ls = this.execSqlQueryToArrays(sf.toString(), args);
			if (null == ls || ls.size() == 0)
				return true;
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("判定唯一性报错");
		}
	}

	public JDBCDataSourceTransactionManager getTransaction() {
		return transaction;
	}

	public void setTransaction(JDBCDataSourceTransactionManager transaction) {
		this.transaction = transaction;
	}

	public String getSequence() {
		return Sequence.getInstance().getSequence(32);
	}

	public List execSqlQueryToArrays(String sqlStr, List list,
			RowMapper rowMapper) throws SQLException {

		sqlStr = ConvertUtil.valBeginning(sqlStr);
		Object[] obj = ConvertUtil.convertList2Array(list);
		Date startTime = new Date();
		List l = jdbcTemplate.query(sqlStr, obj, rowMapper);
		Date endTime = new Date();
		long hssj = endTime.getTime() - startTime.getTime();
		// log.debug(sqlStr);
		log.warn("time: " + String.valueOf(hssj) + " ms;  " + sqlStr);
		return l;

	}

	public Connection getConnection() {
		try {
			return jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

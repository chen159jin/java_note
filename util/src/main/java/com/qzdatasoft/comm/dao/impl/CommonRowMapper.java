package com.qzdatasoft.comm.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.qzdatasoft.comm.framework.execption.ServiceException;

public class CommonRowMapper implements RowMapper {
	private static final Logger log = LoggerFactory
			.getLogger(CommonRowMapper.class);
	static {
		ConvertUtils.register(new SqlTimestampConverter(null),
				java.sql.Timestamp.class);
	}

	private Class clz;

	public CommonRowMapper(Class clazz) {
		this.clz = clazz;
	}

	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		try {
			Object obj = clz.newInstance();

			ResultSetMetaData meta = arg0.getMetaData();
			int iCount = meta.getColumnCount();
			for (int i = 1; i <= iCount; i++) {
				String colName = meta.getColumnName(i).toLowerCase();
				Object value = arg0.getObject(i);
				if (value != null && !colName.equals("rownum_"))
					BeanUtils.setProperty(obj, colName, value);
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();

			log.error("动态bean反射出现异常：" + e.toString());
			throw new ServiceException(e.getMessage());
		}
	}

}

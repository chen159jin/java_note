package com.qzdatasoft.comm.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

//Map 形式的返回

public class MapRowMapper implements RowMapper {

	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		Map mapOfColValues = new HashMap();
		ResultSetMetaData meta = arg0.getMetaData();
		int iCount = meta.getColumnCount();
		for (int i = 1; i <= iCount; i++) {
			String colName = meta.getColumnName(i).toLowerCase();
			Object value = arg0.getObject(i);
			if (colName != null && !colName.equals(""))
				mapOfColValues.put(colName, value);
		}
		return mapOfColValues;
	}

}

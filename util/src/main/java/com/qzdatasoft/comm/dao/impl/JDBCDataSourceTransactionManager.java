package com.qzdatasoft.comm.dao.impl;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class JDBCDataSourceTransactionManager {
	private DataSourceTransactionManager transactionManager;

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}

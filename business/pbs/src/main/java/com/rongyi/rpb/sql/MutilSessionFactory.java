package com.rongyi.rpb.sql;

import com.rongyi.core.framework.spring.context.utils.SpringContextUtil;
import com.rongyi.rpb.datasource.CustomerContextHolder;
import org.apache.ibatis.session.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.util.StringUtils;

import java.sql.Connection;

public class MutilSessionFactory implements SqlSessionFactory {

	private SqlSessionFactory sqlSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(MutilSessionFactory.class);


	public SqlSessionFactory getSqlSessionFactory() {
		return getSqlSessionFactory(CustomerContextHolder.getCustomerType());
	}

	public SqlSessionFactory getSqlSessionFactory(String sessionFactoryName) {
		LOGGER.debug("sessionFactoryName:" + sessionFactoryName);
		try {
			if (StringUtils.isEmpty(sessionFactoryName)) {
				return sqlSessionFactory;
			}
			return (SqlSessionFactory) SpringContextUtil.getBean(sessionFactoryName);
		} catch (BeansException e) {
			LOGGER.error("There is no sessionFactory", e);
			return null;
		}
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public SqlSession openSession() {
		return getSqlSessionFactory().openSession();
	}

	@Override
	public SqlSession openSession(boolean autoCommit) {
		return getSqlSessionFactory().openSession(autoCommit);
	}

	@Override
	public SqlSession openSession(Connection connection) {
		return getSqlSessionFactory().openSession(connection);
	}

	@Override
	public SqlSession openSession(TransactionIsolationLevel level) {
		return getSqlSessionFactory().openSession(level);
	}

	@Override
	public SqlSession openSession(ExecutorType execType) {
		return getSqlSessionFactory().openSession(execType);
	}

	@Override
	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		return getSqlSessionFactory().openSession(execType, autoCommit);
	}

	@Override
	public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
		return getSqlSessionFactory().openSession(execType, level);
	}

	@Override
	public SqlSession openSession(ExecutorType execType, Connection connection) {
		return getSqlSessionFactory().openSession(execType, connection);
	}

	@Override
	public Configuration getConfiguration() {
		return getSqlSessionFactory().getConfiguration();
	}

}

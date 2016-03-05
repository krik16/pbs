package com.shouyingbao.datasource;

public class CustomerContextHolder {

	private CustomerContextHolder() {

	}

	public static final String DATA_SOURCE_RPB = "sqlSessionFactoryRpb";

	public static final String DATA_SOURCE_RMMM = "sqlSessionFactoryRmmm";

	public static final String DATA_SOURCE_OSM = "sqlSessionFactoryOsm";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		return contextHolder.get();
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}

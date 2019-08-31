package com.meisw.systemChek.vo;

public class CpuRateVo {

	/**
	 * CPU系统使用率
	 */
	private String userRate;
	/**
	 * CPU系统使用率
	 */
	private String systemRate;
	/**
	 * CPU等待率
	 */
	private String waitRate;
	/**
	 * CPU当前错误率
	 */
	private String errorRate;
	/**
	 * CPU当前空闲率
	 */
	private String freeReate;
	/**
	 * CPU总的使用率
	 */
	private String totaUsedlRate;
	public String getUserRate() {
		return userRate;
	}
	public void setUserRate(String userRate) {
		this.userRate = userRate;
	}
	public String getSystemRate() {
		return systemRate;
	}
	public void setSystemRate(String systemRate) {
		this.systemRate = systemRate;
	}
	public String getWaitRate() {
		return waitRate;
	}
	public void setWaitRate(String waitRate) {
		this.waitRate = waitRate;
	}
	public String getErrorRate() {
		return errorRate;
	}
	public void setErrorRate(String errorRate) {
		this.errorRate = errorRate;
	}
	public String getFreeReate() {
		return freeReate;
	}
	public void setFreeReate(String freeReate) {
		this.freeReate = freeReate;
	}
	public String getTotaUsedlRate() {
		return totaUsedlRate;
	}
	public void setTotaUsedlRate(String totaUsedlRate) {
		this.totaUsedlRate = totaUsedlRate;
	}
	
	
}

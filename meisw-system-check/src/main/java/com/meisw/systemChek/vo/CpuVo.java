package com.meisw.systemChek.vo;

public class CpuVo {

	/**
	 * CPU的总量MHz
	 */
	private String Mhz;
	/**
	 * CPU生产商
	 */
	private String vendor;
	/**
	 * CPU类别
	 */
	private String model;
	/**
	 * CPU缓存数量
	 */
	private String cacheSize;
	/**
	 * CPU使用使用率集合
	 */
	private CpuRateVo cpuRate;
	public String getMhz() {
		return Mhz;
	}
	public void setMhz(String mhz) {
		Mhz = mhz;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
	}
	public CpuRateVo getCpuRate() {
		return cpuRate;
	}
	public void setCpuRate(CpuRateVo cpuRate) {
		this.cpuRate = cpuRate;
	}
	
	
}

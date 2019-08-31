package com.meisw.systemChek.vo;

public class Os {
	/**
	 * 操作系统
	 */
	private String os;
	private String cpuEndian;
	private String dataModel;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 卖主
	 */
	private String vendor;
	/**
	 * 卖主名
	 */
	private String vendorCodeName;
	/**
	 * 系统名称
	 */
	private String vendorName;
	/**
	 * 卖主类型
	 */
	private String vendorVersion;
	/**
	 * 系统版本号
	 */
	private String version;

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCpuEndian() {
		return cpuEndian;
	}

	public void setCpuEndian(String cpuEndian) {
		this.cpuEndian = cpuEndian;
	}

	public String getDataModel() {
		return dataModel;
	}

	public void setDataModel(String dataModel) {
		this.dataModel = dataModel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorCodeName() {
		return vendorCodeName;
	}

	public void setVendorCodeName(String vendorCodeName) {
		this.vendorCodeName = vendorCodeName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorVersion() {
		return vendorVersion;
	}

	public void setVendorVersion(String vendorVersion) {
		this.vendorVersion = vendorVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}

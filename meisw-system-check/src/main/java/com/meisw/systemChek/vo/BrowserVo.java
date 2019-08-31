package com.meisw.systemChek.vo;

public class BrowserVo {
	/**
	 * 浏览器类型
	 */
	private String browserType;
	/**
	 * 浏览器名称
	 */
	private String name;
	/**
	 * 浏览器厂商
	 */
	private String manufacturer;
	/**
	 * 浏览器产品系列
	 */
	private String group;
	/**
	 * 浏览器引擎
	 */
	private String renderingEngine;
	/**
	 * 浏览器主板本
	 */
	private String majorVersion;
	/**
	 * 浏览器小版本
	 */
	private String minorVersion;
	/**
	 * 浏览器完整版本
	 */
	private String version;
	/**
	 * 系统名称
	 */
	private String systemName;
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getRenderingEngine() {
		return renderingEngine;
	}
	public void setRenderingEngine(String renderingEngine) {
		this.renderingEngine = renderingEngine;
	}
	public String getMajorVersion() {
		return majorVersion;
	}
	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	public String getMinorVersion() {
		return minorVersion;
	}
	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
}

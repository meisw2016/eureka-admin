package com.meisw.systemChek.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.meisw.systemChek.util.OutputData;
import com.meisw.systemChek.util.SystemUtil;
import com.meisw.systemChek.vo.BrowserVo;
import com.meisw.systemChek.vo.CpuVo;
import com.meisw.systemChek.vo.MemVo;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

@Service
public class IpService {
	
	private static final Logger log = LoggerFactory.getLogger(IpService.class);
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public OutputData getBrowser(HttpServletRequest request) {
		String ua = request.getHeader("User-Agent");
		log.info("操作系统及浏览器信息：{}",ua);
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		//获取浏览器信息
		Browser browser = userAgent.getBrowser();
		log.info("浏览器信息");
		log.info("浏览器类型：{}",browser.getBrowserType());
		log.info("浏览器名称：{}",browser.getName());
		log.info("浏览器厂商：{}",browser.getManufacturer());
		log.info("浏览器产品系列：{}",browser.getGroup());
		log.info("浏览器引擎：{}",browser.getRenderingEngine());
		Version version = userAgent.getBrowserVersion();
		log.info("浏览器主版本：{}",version.getMajorVersion());
		log.info("浏览器小版本：{}",version.getMinorVersion());
		log.info("浏览器完整版本：{}",version.getVersion());
		//系统信息
		OperatingSystem os = userAgent.getOperatingSystem();
		log.info("系统信息：{}",os);
		//系统名称
		String systemName = os.getName();
		log.info("系统名称：{}",systemName);
		//浏览器名称
		String browserName = browser.getName();
		log.info("浏览器名称：{}",browserName);
		log.info("设备类型：{}",os.getDeviceType());
		log.info("产品系列：{}",os.getGroup());
		log.info("生产厂商：{}",os.getManufacturer());
		OutputData out = new OutputData().returnSuccess();
		BrowserVo vo = getBrowserInfo(userAgent);
		out.setData(vo);
		return out;
	}
	private BrowserVo getBrowserInfo(UserAgent userAgent){
		Browser browser = userAgent.getBrowser();
		BrowserVo vo = new BrowserVo();
		vo.setBrowserType(browser.getBrowserType().name());
		vo.setName(browser.getName());
		vo.setManufacturer(browser.getManufacturer().name());
		vo.setGroup(browser.getGroup().name());
		vo.setRenderingEngine(browser.getRenderingEngine().name());
		Version version = userAgent.getBrowserVersion();
		vo.setMajorVersion(version.getMajorVersion());
		vo.setMinorVersion(version.getMinorVersion());
		vo.setVersion(version.getVersion());
		OperatingSystem os = userAgent.getOperatingSystem();
		vo.setSystemName(os.getName());
		return vo;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public OutputData getMemory() {
		OutputData out = new OutputData().returnSuccess();
		try {
			MemVo vo = SystemUtil.memory();
			out.setData(vo);
		} catch (SigarException e) {
			log.error("获取内存信息异常：errorMessage={}",e);
			out.returnFail(e.getMessage());
		}
		return out;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public OutputData getCpu() {
		OutputData out = new OutputData().returnSuccess();
		try {
			List<CpuVo> list = SystemUtil.cpu();
			out.setData(list);
		} catch (SigarException e) {
			log.error("获取CPU存信息异常：errorMessage={}",e);
			out.returnFail(e.getMessage());
		}
		return out;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public OutputData getOs(){
		OutputData out = new OutputData().returnSuccess();
		out.setData(SystemUtil.os());
		return out;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OutputData getJVM(){
		OutputData out = new OutputData().returnSuccess();
		out.setData(SystemUtil.getSystem());
		return out;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OutputData getenv(){
		OutputData out = new OutputData().returnSuccess();
		out.setData(SystemUtil.getUser());
		return out;
	}
}

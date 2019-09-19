package com.meisw.systemChek.util;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.bytedeco.javacv.CanvasFrame;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Cache;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.meisw.systemChek.vo.CpuRateVo;
import com.meisw.systemChek.vo.CpuVo;
import com.meisw.systemChek.vo.MemVo;
import com.meisw.systemChek.vo.Os;

public class SystemUtil {

	private static final Logger log = LoggerFactory.getLogger(SystemUtil.class);
	private static Sigar sigar = new Sigar();

	public static void main(String[] args) {
		// String path = System.getenv("java.library.path");
		String path = System.getProperty("java.library.path");
		log.info("java.library.path:{}", path);
		try {
			memory();
			cpu();
			os();
			// getUser();
			// net();
			// ethernet();
			getFileInfo();
		} catch (SigarException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取系统JVM参数
	 */
	@SuppressWarnings("rawtypes")
	public static Properties getSystem() {
		Properties p = System.getProperties();
		Iterator it = p.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		return p;
	}

	/**
	 * 获取内存信息
	 * 
	 * @throws SigarException
	 */
	public static MemVo memory() throws SigarException {
		Mem mem = sigar.getMem();
		long total = mem.getTotal() / (1024 * 1024L);
		long used = mem.getUsed() / (1024 * 1024L);
		long free = mem.getFree() / (1024 * 1024L);
		Swap swap = sigar.getSwap();
		long swapTotal = swap.getTotal() / (1024 * 1024L);
		long swapUsed = swap.getUsed() / (1024 * 1024L);
		long swapFree = swap.getFree() / (1024 * 1024L);
		log.info("内存总量：{}", total + "M av");
		log.info("当前内存使用量：{}", used + "M used");
		log.info("当前内存剩余量：{}", free + "M free");

		log.info("交换区总量：{}", swapTotal + "M av");
		log.info("当前交换区使用量：{}", swapUsed + "M used");
		log.info("当前交换区剩余量：{}", swapFree + "M free");
		MemVo vo = new MemVo();
		vo.setTotal(String.valueOf(total));
		vo.setUsed(String.valueOf(used));
		vo.setFree(String.valueOf(free));
		vo.setSwapTotal(String.valueOf(swapTotal));
		vo.setSwapUsed(String.valueOf(swapUsed));
		vo.setSwapFree(String.valueOf(swapFree));
		return vo;
	}

	public static List<CpuVo> cpu() throws SigarException {
		List<CpuVo> list = new ArrayList<CpuVo>();
		CpuVo cpuVo = null;
		CpuInfo infos[] = sigar.getCpuInfoList();
		CpuPerc cpuList[] = sigar.getCpuPercList();
		CpuInfo info = null;
		for (int i = 0; i < cpuList.length; i++) {
			cpuVo = new CpuVo();
			log.info("第{}块CPU信息", (i + 1));
			info = infos[i];
			log.info("CPU的总量MHz:{}", info.getMhz());
			log.info("CPU生产商：", info.getVendor());
			log.info("CPU类别：", info.getModel());
			log.info("CPU缓存数量：{}", info.getCacheSize());
			CpuRateVo cpuRateVo = printCpuPerc(cpuList[i]);
			cpuVo.setMhz(String.valueOf(info.getMhz()));
			cpuVo.setVendor(info.getVendor());
			cpuVo.setModel(info.getModel());
			cpuVo.setCacheSize(String.valueOf(info.getCacheSize()));
			cpuVo.setCpuRate(cpuRateVo);
			list.add(cpuVo);
		}
		return list;
	}

	private static CpuRateVo printCpuPerc(CpuPerc cpu) {
		CpuRateVo cpuRateVo = new CpuRateVo();
		log.info("CPU用户使用率：{}", CpuPerc.format(cpu.getUser()));
		log.info("CPU系统使用率：{}", CpuPerc.format(cpu.getSys()));
		log.info("CPU当前等待率：{}", CpuPerc.format(cpu.getWait()));
		log.info("CPU当前错误率：{}", CpuPerc.format(cpu.getNice()));
		log.info("CPU当前空闲率：{}", CpuPerc.format(cpu.getIdle()));
		log.info("CPU总的使用率：{}", CpuPerc.format(cpu.getCombined()));
		cpuRateVo.setUserRate(CpuPerc.format(cpu.getUser()));
		cpuRateVo.setSystemRate(CpuPerc.format(cpu.getSys()));
		cpuRateVo.setWaitRate(CpuPerc.format(cpu.getWait()));
		cpuRateVo.setErrorRate(CpuPerc.format(cpu.getNice()));
		cpuRateVo.setFreeReate(CpuPerc.format(cpu.getIdle()));
		cpuRateVo.setTotaUsedlRate(CpuPerc.format(cpu.getCombined()));
		return cpuRateVo;
	}

	public static Os os() {
		OperatingSystem os = OperatingSystem.getInstance();
		log.info("操作系统：{}", os.getArch());
		log.info("操作系统CpuEndian(){}", os.getCpuEndian());
		log.info("操作系统DataModel(){}", os.getDataModel());
		log.info("操作系统的描述：{}", os.getDescription());
		log.info("操作系统的卖主：{}", os.getVendor());
		log.info("操作系统的卖主名：{}", os.getVendorCodeName());
		log.info("操作系统名称：{}", os.getVendorName());
		log.info("操作系统卖主类型：{}", os.getVendorVersion());
		log.info("操作系统的版本号：{}", os.getVersion());
		Os vo = new Os();
		vo.setOs(os.getArch());
		vo.setCpuEndian(os.getCpuEndian());
		vo.setDataModel(os.getDataModel());
		vo.setDescription(os.getDescription());
		vo.setVendor(os.getVendor());
		vo.setVendorCodeName(os.getVendorCodeName());
		vo.setVendorName(os.getVendorName());
		vo.setVendorVersion(os.getVendorVersion());
		vo.setVersion(os.getVersion());
		return vo;
	}

	public static Map<String,String> getUser() {
		Map<String, String> map = System.getenv();
		log.info("用户名：{}", map.get("USERNAME"));
		log.info("计算机名：{}", map.get("COMPUTERNAME"));
		log.info("计算机域名：{}", map.get("USERDOMAIN"));
		log.info("自定义变量getev CONF_LOCATION：{}", map.get("conf.location"));
		log.info("操作系统名称：{}", System.getProperty("os.name"));
		return map;
	}

	public static void who() throws SigarException {
		Who who[] = sigar.getWhoList();
		if (who != null && who.length > 0) {
			for (int i = 0; i < who.length; i++) {
				Who _who = who[i];
				log.info("用户控制台：{}", _who.getDevice());
				log.info("用户host:{}", _who.getHost());
				log.info("当前系统进程表中的用户名：{}", _who.getUser());
			}
		}
	}

	private static void net() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			System.out.println("网络设备名:	" + name);// 网络设备名
			System.out.println("IP地址:	" + ifconfig.getAddress());// IP地址
			System.out.println("子网掩码:	" + ifconfig.getNetmask());// 子网掩码
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				System.out.println("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
			System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
			System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
			System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
			System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
			System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
			System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
			System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
			System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
		}
	}

	private static void ethernet() throws SigarException {
		Sigar sigar = null;
		sigar = new Sigar();
		String[] ifaces = sigar.getNetInterfaceList();
		for (int i = 0; i < ifaces.length; i++) {
			NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
			if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
					|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
				continue;
			}
			System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
			System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
			System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
			System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
			System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
			System.out.println(cfg.getName() + "网卡类型" + cfg.getType());//
		}
	}

	// UserAgent userAgent =
	// UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	// Browser browser = userAgent.getBrowser();
	// OperatingSystem os = userAgent.getOperatingSystem();

	public static void getFileInfo() {
		File[] roots = File.listRoots();
		for (File file : roots) {
			long freeSpace = file.getFreeSpace();
			long totalSpace = file.getTotalSpace();
			long usableSpace = totalSpace - freeSpace;
			log.info("path:{}", file.getPath());
			log.info("freeSpace:{}", freeSpace / 1024 / 1024 / 1024 + "G");
			log.info("usableSpace:{}", usableSpace / 1024 / 1024 / 1024 + "G");
			log.info("totalSpace:{}", totalSpace / 1024 / 1024 / 1024 + "G");
		}
	}

	public static void portScan(String domain, int startport, int endport) {
		if (startport > endport) {
			log.error("输入参数错误!");
			return;
		}

		LinkedList<Thread> threadPool = new LinkedList<Thread>();
		for (int i = startport; i <= endport; i++) {
			int port = i;
			Socket socket = new Socket();
			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						socket.connect(new InetSocketAddress(domain, port));
						log.info("端口{}开放", port);
					} catch (IOException e) {
						if (port == 8888) {
							log.info("端口{}关闭", port);
						}
					}
				}
			};
			Thread th = new Thread(run);
			th.start();
			threadPool.add(th);
		}

		Runnable runobserv = new Runnable() {
			@Override
			public void run() {
				boolean flag = false;
				while (!flag) {
					flag = true;
					for (Thread thread : threadPool) {
						if (thread.isAlive()) {
							flag = false;
							break;
						}
					}
				}
			}
		};
		Thread t1 = new Thread(runobserv);
		t1.start();
	}

	public static void check() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		process = runtime
				.exec("cmd /c reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

		String string = null;
		while ((string = in.readLine()) != null) {
			System.out.println(string);
			process = runtime.exec("cmd /c reg query " + string + " /v DisplayName");
			System.out.println(process);

			BufferedReader name = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			String[] message = queryValue(string);
			for (int i = 0; i < message.length; i++) {
				System.out.println(message[i]);
			}
		}
		in.close();
		process.destroy();
	}

	private static String[] queryValue(String string) throws IOException {
		String nameString = "";
		String versionString = "";

		String publisherString = "";
		String uninstallPathString = "";

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		BufferedReader br = null;

		process = runtime.exec("cmd /c reg query " + string + " /v DisplayName");
		br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
		br.readLine();
		br.readLine();// 去掉前两行无用信息
		if ((nameString = br.readLine()) != null) {
			nameString = nameString.replaceAll("DisplayName    REG_SZ    ", ""); // 去掉无用信息
		}

		process = runtime.exec("cmd /c reg query " + string + " /v DisplayVersion");
		br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
		br.readLine();
		br.readLine();// 去掉前两行无用信息
		if ((versionString = br.readLine()) != null) {
			versionString = versionString.replaceAll("DisplayVersion    REG_SZ    ", ""); // 去掉无用信息
		}

		process = runtime.exec("cmd /c reg query " + string + " /v Publisher");
		br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
		br.readLine();
		br.readLine();// 去掉前两行无用信息
		if ((publisherString = br.readLine()) != null) {
			publisherString = publisherString.replaceAll("Publisher    REG_SZ    ", ""); // 去掉无用信息
		}

		process = runtime.exec("cmd /c reg query " + string + " /v UninstallString");
		br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
		br.readLine();
		br.readLine();// 去掉前两行无用信息
		if ((uninstallPathString = br.readLine()) != null) {
			uninstallPathString = uninstallPathString.replaceAll("UninstallString    REG_SZ    ", ""); // 去掉无用信息
		}

		String[] resultString = new String[4];
		resultString[0] = nameString;// == null ? null : new
										// String(nameString.getBytes(),"GB-2312");
		resultString[1] = versionString;// == null ? null : new
										// String(versionString.getBytes(),"GB-2312");
		resultString[2] = publisherString;// == null ? null : new
											// String(publisherString.getBytes(),"GB-2312");
		resultString[3] = uninstallPathString;// == null ? null : new
												// String(uninstallPathString.getBytes(),"GB-2312");
		if (resultString[0] == null)
			resultString = null; // 没有名字的不显示
		return resultString;
	}

	public static void captureScree() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获取当前屏幕大小
		log.info("显示器分辨率：{}*{}", screenSize.getWidth(), screenSize.getHeight());
		log.info("显示器像素：{}", Toolkit.getDefaultToolkit().getScreenResolution());
		Rectangle rectangle = new Rectangle(screenSize);// 指定捕获屏幕区域大小，这里使用全屏捕获
		// 做好自己!--eguid，eguid的博客是:blog.csdn.net/eguid_1
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();// 本地环境
		GraphicsDevice[] gs = ge.getScreenDevices();// 获取本地屏幕设备列表
		System.err.println("eguid温馨提示，找到" + gs.length + "个屏幕设备");
		Robot robot = null;
		int ret = -1;
		for (int index = 0; index < 10; index++) {
			GraphicsDevice g = gs[index];
			try {
				robot = new Robot(g);
				BufferedImage img = robot.createScreenCapture(rectangle);
				if (img != null && img.getWidth() > 1) {
					ret = index;
					break;
				}
			} catch (AWTException e) {
				System.err.println("打开第" + index + "个屏幕设备失败，尝试打开第" + (index + 1) + "个屏幕设备");
			}
		}
		System.err.println("打开的屏幕序号：" + ret);
		CanvasFrame frame = new CanvasFrame("eguid屏幕录制");// javacv提供的图像展现窗口
		int width = 800;
		int height = 600;
		frame.setBounds((int) (screenSize.getWidth() - width) / 2, (int) (screenSize.getHeight() - height) / 2, width,
				height);// 窗口居中
		frame.setCanvasSize(width, height);// 设置CanvasFrame窗口大小
		while (frame.isShowing()) {
			BufferedImage image = robot.createScreenCapture(rectangle);// 从当前屏幕中读取的像素图像，该图像不包括鼠标光标
			frame.showImage(image);

			try {
				Thread.sleep(45);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		frame.dispose();
	}

	public static List<String> getDNS(String host) {
		List<String> list = new ArrayList<String>();
		try {
			Resolver resolver = new SimpleResolver("114.114.114.114");
			Lookup lookup = new Lookup(host,Type.A);
			lookup.setResolver(resolver);
			Cache cache = new Cache();
			lookup.setCache(cache);
			lookup.run();
			if(lookup.getResult()==Lookup.SUCCESSFUL) {
				String[] results = cache.toString().split("\\n");
				for(String result:results) {
					System.out.println(result);
					list.add(result);
				}
			}else {
				System.out.println(lookup.getErrorString());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (TextParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Test
	public  void getBrowerVersion() {
		try {
			Runtime.getRuntime().exec("cmd /c start iexplore http://www.baidu.com" );
			Runtime.getRuntime().exec("rundll32url.dll,FileProtocolHandler http://www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1() {
		URI uri = java.net.URI.create("www.baidu.com");
		Desktop dp = java.awt.Desktop.getDesktop();
		if(dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
			try {
				dp.browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void test2() {
		ProcessBuilder proc = new ProcessBuilder("C:\\Program Files\\Internet Explorer\\iexplore.exe","http://localhost:9000/system/getBrowser");
		try {
			proc.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		System.out.println(tmpDir);
	}
}

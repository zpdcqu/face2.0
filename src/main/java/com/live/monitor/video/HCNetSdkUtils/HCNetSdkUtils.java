package com.live.monitor.video.HCNetSdkUtils;

import com.live.monitor.config.Constants;
import com.live.monitor.video.entity.FaceDeviceInfoTable;
import com.live.monitor.video.hikvision.HCNetSDK;
import com.sun.jna.NativeLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author jiang
 * @ClassName HCNetSdkUtils
 * @description 获取摄像头里的视频
 * @Date 2019/10/22 0022 下午 5:34
 */
@Component
@PropertySource(value = {"file:./properties/init.properties"})
public class HCNetSdkUtils {
	// sdk初始化实例
	public static HCNetSDK sdk;
	private static NativeLong userId = new NativeLong(-1);
	private static Logger logger = LoggerFactory.getLogger(HCNetSdkUtils.class);
	@Autowired
	private Constants constants;
	public static HCNetSdkUtils hCNetSdkUtils;

	@PostConstruct
	public void init() {
		hCNetSdkUtils = this;
	}

	@Value("${nvrAddress}")
	private String nvrAddress;

	/**
	 * 注册设备
	 *
	 * @param nvrInfo nvr的实体类 包含 ip 端口 账号 密码
	 * @return 用户id
	 */
	public static NativeLong loginDvr(FaceDeviceInfoTable nvrInfo) {
		if (userId.intValue() > -1) {
			return userId;
		}
		sdk = HCNetSDK.INSTANCE;
		if (!sdk.NET_DVR_Init()) {
			logger.info("SDKInitFail");
			return null;
		}
		// 设备信息
		HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// 注册设备
		try {
			userId = sdk.NET_DVR_Login_V30(nvrInfo.getDeviceIpAddr(), nvrInfo.getDevicePort().shortValue(), nvrInfo.getDeviceAccount(), nvrInfo.getDevicePassword(), devinfo);
		} catch (Exception e) {
			logger.error(String.valueOf(sdk.NET_DVR_GetLastError()));
		}

		if (userId.intValue() < 0) {
			logger.info("registerFail" + sdk.NET_DVR_GetLastError());
			return null;
		} else {
			logger.info("registerSuccess！returnId：" + userId.intValue());
		}

		HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();

		if (!sdk.NET_DVR_GetDVRWorkState_V30(userId, devwork)) {
			// 返回Boolean值，判断是否获取设备能力
			logger.info("Failed to return to device status");
		}

		return userId;
	}

	/**
	 * NET_DVR_TIME 转换成Date类型
	 *
	 * @param struTime
	 * @return
	 * @throws ParseException
	 */
	private Date toDate(HCNetSDK.NET_DVR_TIME struTime) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(struTime.dwYear + "-" + struTime.dwMonth + "-" + struTime.dwDay + "-" + struTime.dwHour + "-" + struTime.dwMinute + "-" + struTime.dwSecond);
	}

	/**
	 * NET_DVR_TIME类型转换时间字符串
	 *
	 * @param struTime
	 * @return
	 * @throws ParseException
	 */
	private String toTextTime(HCNetSDK.NET_DVR_TIME struTime) throws ParseException {
		Date date = toDate(struTime);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 转换设备时间
	 *
	 * @param timeMills 时间戳 毫秒级
	 * @return
	 */
	private HCNetSDK.NET_DVR_TIME toNetDvrTime(long timeMills) {
		HCNetSDK.NET_DVR_TIME net_dvr_time = new HCNetSDK.NET_DVR_TIME();
		net_dvr_time.dwYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(timeMills));
		net_dvr_time.dwMonth = Integer.parseInt(new SimpleDateFormat("MM").format(timeMills));
		net_dvr_time.dwDay = Integer.parseInt(new SimpleDateFormat("dd").format(timeMills));
		net_dvr_time.dwHour = Integer.parseInt(new SimpleDateFormat("HH").format(timeMills));
		net_dvr_time.dwMinute = Integer.parseInt(new SimpleDateFormat("mm").format(timeMills));
		net_dvr_time.dwSecond = Integer.parseInt(new SimpleDateFormat("ss").format(timeMills));

		return net_dvr_time;
	}

	public String getVideoByTime(Integer channelNum, Date comeTime, String cameraId, String faceId, String buildingProjectId, String nvrAddress) throws InterruptedException {
		FaceDeviceInfoTable infoTable = new FaceDeviceInfoTable();
		infoTable.setDeviceAccount("admin");
		infoTable.setDevicePort(8000);
		infoTable.setDeviceIpAddr(nvrAddress);
		infoTable.setDevicePassword("2b3e201690");
		NativeLong userId = HCNetSdkUtils.loginDvr(infoTable);

		HCNetSDK.NET_DVR_TIME struStartTime;
		HCNetSDK.NET_DVR_TIME struStopTime;
		System.out.println("nvrAddress"+nvrAddress);
		struStartTime = new HCNetSDK.NET_DVR_TIME();
		struStopTime = new HCNetSDK.NET_DVR_TIME();

		struStartTime = toNetDvrTime(comeTime.getTime() - 1000 * 5);
		struStopTime = toNetDvrTime(comeTime.getTime() + 1000 * 5);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Date date = new Date();
		String dateString = format.format(date);
		String sFileName = "/usr/local/video/" + buildingProjectId + "_" + cameraId + "_" + faceId + "_" + dateString + ".mp4";
		System.out.println("sFileName:" + sFileName);
		/**
		 * 0xff-全部，0-定时录像，1-移动侦测，2-报警触发，3-报警触发或移动侦测，4-报警触发和移动侦测，5-命令触发，6-手动录像，7-智能录像，
		 * 10-PIR报警，11-无线报警，12-呼救报警，13-移动侦测、PIR、无线、呼救等所有报警类型的"或"，14-智能交通事件，15-越界侦测，
		 * 16-区域入侵侦测，17-音频异常侦测，18-场景变更侦测，19-智能侦测（越界侦测|区域入侵侦测|进入区域侦测|离开区域侦测|人脸侦测）
		 * ，20-人脸侦测，21-信号量，22-回传，23-回迁录像，24-遮挡，25-POS录像，26-进入区域侦测，27-离开区域侦测，28-徘徊侦测，
		 * 29-人员聚集侦测，30-快速运动侦测，31-停车侦测，32-物品遗留侦测，33-物品拿取侦测，34-火点检测，35-防破坏检测，36-船只检测，37-测温预警，38-测温报警，
		 * 39-打架斗殴报警，40-起身检测，41-瞌睡检测，42-温差报警，43-离线测温报警，44-防区报警，45-紧急求助，46-业务咨询，47-起身检测，48-折线攀高，49-如厕超时
		 */
		NativeLong m_lLoadHandle = sdk.NET_DVR_GetFileByTime(userId, new NativeLong(channelNum), struStartTime, struStopTime, sFileName);
		System.out.println("-----m_lLoadHandle:" + m_lLoadHandle.intValue());
		System.out.println("--error" + sdk.NET_DVR_GetLastError());
		if (m_lLoadHandle.intValue() >= 0) {
			boolean b = sdk.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null);
			int pos = 0;
			System.out.println("----b:" + b);
			System.out.println("getDown" + sdk.NET_DVR_GetDownloadPos(m_lLoadHandle));
			while ((pos = sdk.NET_DVR_GetDownloadPos(m_lLoadHandle)) < 98) {
				System.out.println(pos);
				Thread.sleep(120);
			}
			sdk.NET_DVR_StopGetFile(m_lLoadHandle);
			m_lLoadHandle.setValue(-1);
			System.out.println(sdk.NET_DVR_GetLastError());
		}
		return sFileName;
	}

	/**
	 * 下载历史回溯视频
	 *
	 * @param channelNum
	 * @param start
	 * @param end
	 * @return
	 * @throws InterruptedException
	 */
	@Async("downloadVideo")
	public String getVideoTimeHistory(String buildingProjectId, Integer channelNum, Date start, Date end, String cameraId) throws InterruptedException {

		FaceDeviceInfoTable infoTable = new FaceDeviceInfoTable();
		infoTable.setDeviceAccount("admin");
		infoTable.setDevicePort(8000);
		infoTable.setDeviceIpAddr(nvrAddress);
		infoTable.setDevicePassword("2b3e201690");
		NativeLong userId = HCNetSdkUtils.loginDvr(infoTable);

		HCNetSDK.NET_DVR_TIME struStartTime;
		HCNetSDK.NET_DVR_TIME struStopTime;

		struStartTime = new HCNetSDK.NET_DVR_TIME();
		struStopTime = new HCNetSDK.NET_DVR_TIME();
		System.out.println("start" + start.getTime());
		System.out.println("end" + end.getTime());
		System.out.println("buildingProjectId" + buildingProjectId);
		System.out.println("channelNum" + channelNum);
		struStartTime = toNetDvrTime(start.getTime());
		struStopTime = toNetDvrTime(end.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		//format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String dateString = format.format(start);
		String sFileName = "/usr/local/historyvideo/" + buildingProjectId + "_" + channelNum + "_" + cameraId + "_" + dateString + ".mp4";
		System.out.println("--sFileName--"+sFileName);
		/**
		 * 0xff-全部，0-定时录像，1-移动侦测，2-报警触发，3-报警触发或移动侦测，4-报警触发和移动侦测，5-命令触发，6-手动录像，7-智能录像，
		 * 10-PIR报警，11-无线报警，12-呼救报警，13-移动侦测、PIR、无线、呼救等所有报警类型的"或"，14-智能交通事件，15-越界侦测，
		 * 16-区域入侵侦测，17-音频异常侦测，18-场景变更侦测，19-智能侦测（越界侦测|区域入侵侦测|进入区域侦测|离开区域侦测|人脸侦测）
		 * ，20-人脸侦测，21-信号量，22-回传，23-回迁录像，24-遮挡，25-POS录像，26-进入区域侦测，27-离开区域侦测，28-徘徊侦测，
		 * 29-人员聚集侦测，30-快速运动侦测，31-停车侦测，32-物品遗留侦测，33-物品拿取侦测，34-火点检测，35-防破坏检测，36-船只检测，37-测温预警，38-测温报警，
		 * 39-打架斗殴报警，40-起身检测，41-瞌睡检测，42-温差报警，43-离线测温报警，44-防区报警，45-紧急求助，46-业务咨询，47-起身检测，48-折线攀高，49-如厕超时
		 */
		try {
			NativeLong m_lLoadHandle = sdk.NET_DVR_GetFileByTime(userId, new NativeLong(channelNum), struStartTime, struStopTime, sFileName);
			System.out.println("----m_lLoadHandle:" + m_lLoadHandle);
			System.out.println("bad" + sdk.NET_DVR_GetLastError());
//		if (sdk.NET_DVR_GetLastError() != 0) {
//			for (int i = 0; i < 3; i++){
//				m_lLoadHandle = sdk.NET_DVR_GetFileByTime(userId, new NativeLong(channelNum), struStartTime, struStopTime, sFileName);
//				if( sdk.NET_DVR_GetLastError() == 0 ) {
//					break;
//				}
//			}
//		}
			if (m_lLoadHandle.intValue() >= 0) {
				boolean b = sdk.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null);
				int pos = 0;
				while ((pos = sdk.NET_DVR_GetDownloadPos(m_lLoadHandle)) < 98) {
//				System.out.println("pos:" + pos);
					Thread.sleep(120);
				}
				sdk.NET_DVR_StopGetFile(m_lLoadHandle);
				m_lLoadHandle.setValue(-1);
				//System.out.println(sFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000 * 60 * 3);
			System.out.println("NVR NO NUll");
		}

		System.out.println("downloadSuccess：" + sFileName);
		return sFileName;
	}
}

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LiveTest
 * Author:   pc
 * Date:     2019/10/24 9:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.tencentapi;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/10/29 15:04
 * @since 1.0.0
 */
public class GetPushPullAddress {
	private static TencentCloudAPITC3Demo tencentCloudAPITC3Demo;
	private static final char[] DIGITS_LOWER =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	/**
	 * 输出Url
	 */
	public static Map<String, String> printUrls(String cameraId, String buildingProjectId) throws Exception {

		//过期时间
		String expirationTime = "2100-10-28 23:59:59";

		//Unix时间戳
		Long unixTime = getUnixTime(expirationTime);
		//bizid
		String bizId = "43502";
		//随机码 随机码自己随意填写
		String randomStr = String.valueOf(100 + (int) (Math.random() * (99999 - 100 + 1)));
		System.out.println("randomStr" + randomStr);
		//推流防盗链
		String key = tencentCloudAPITC3Demo.authentication();
		String streamId = "building_" + buildingProjectId + "_cameraId" + "_" + cameraId + "_" + randomStr;
		System.out.println("key:"+key);
		//时间戳16进制
		long txTime = Calendar.getInstance().getTimeInMillis();
		//获取md5 txSecret
		String txSecret = getMd5(key + streamId + txTime);
		//视频推送url
		String pushUrl = "rtmp://43502.livepush.myqcloud.com/live/" + streamId +"?"+ getSafeUrl(key, streamId, txTime);
		//视频播放url rtmp
		//String playUrlRtmp = "rtmp://" + bizId + ".liveplay.myqcloud.com/live/" + streamId;
		//视频播放url flv
		String playUrlFlv = "rtmp://v.yuejiayun.com/live/" + streamId + ".flv";
		System.out.println("推流地址=" + pushUrl);
		//System.out.println("播放Rtmp=" + playUrlRtmp);
		System.out.println("播放Flv=" + playUrlFlv);
		Map<String, String> map = new HashMap<>();
		map.put("pushUrl", pushUrl);
		map.put("playUrlFlv", playUrlFlv);
		return map;
	}

	/**
	 * 获取unix时间戳
	 *
	 * @return
	 * @throws Exception
	 */
	public static Long getUnixTime(String dateStr) {

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long epoch = df.parse(dateStr).getTime();
			return epoch / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 获取md5字符串
	 *
	 * @param str
	 * @return
	 */
	public static String getMd5(String str) {

		MessageDigest md5 = null;
		try {

			md5 = MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bs = md5.digest(str.getBytes());
		StringBuilder sb = new StringBuilder(40);
		for (byte x : bs) {
			if ((x & 0xff) >> 4 == 0) {
				sb.append("0").append(Integer.toHexString(x & 0xff));
			} else {
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}

	/*
	 * KEY+ streamName + txTime
	 */
	private static String getSafeUrl(String key, String streamName, long txTime) {
		String input = new StringBuilder().
				append(key).
				append(streamName).
				append(Long.toHexString(txTime).toUpperCase()).toString();

		String txSecret = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			txSecret  = byteArrayToHexString(
					messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return txSecret == null ? "" :
				new StringBuilder().
						append("txSecret=").
						append(txSecret).
						append("&").
						append("txTime=").
						append(Long.toHexString(txTime).toUpperCase()).
						toString();
	}

	private static String byteArrayToHexString(byte[] data) {
		char[] out = new char[data.length << 1];

		for (int i = 0, j = 0; i < data.length; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[0x0F & data[i]];
		}
		return new String(out);
	}
}
/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GetPhotoServiceImpl
 * Author:   pc
 * Date:     2019/11/22 14:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import java.io.*;
import java.util.Properties;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.GetPhotoService;
import com.live.monitor.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * 〈功能：〉<br>
 * 〈获取COS存储对象里面的图片服务类〉
 *
 * @author liuhaoran
 * @create 2019/11/22 14:32
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class GetPhotoServiceImpl implements GetPhotoService {
	@Value("${buildingProjectId}")
	private String buildingProjectId;
	@Value("${upAddress}")
	private String upAddress;
	@Value("${ffmpegurl}")
	private String ffmpegUrl;
	Properties properties = new Properties();

	@Override
	public void picListObjectsPic() {
		File file = new File("./properties/camera.properties");
		try {
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<String> set = properties.stringPropertyNames();
		for (String cameraId : set) {
			String value = properties.getProperty(cameraId);
			String picPath = "/usr/local/images/" + buildingProjectId + "_" + cameraId + ".jpg";
			// 调用截取视频图片的方法
			Boolean flag = executeCodecs(ffmpegUrl, value, picPath);
			if (flag == true) {
				String picFile = buildingProjectId + "/" + buildingProjectId + "_" + cameraId + ".jpg";
				// 上传视频封面图片
				try{
					HttpClientUtil.doLivenessAuthByFile(picPath, "http://"+upAddress + "admin/face/api/data/upLoadCoverPicture/");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Server is not turned on"); //服务器未开启
				}
				// 向远程API发送信息 保存直播监控的信息
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("buildingProjectId", buildingProjectId);
				jsonobj.put("cameraId", cameraId);
				jsonobj.put("coverPicture", "http://"+upAddress + "uploads/yuejiaface/coverPicture/" + picFile);
				try {
					HttpClientUtil.postRequestSaveLiveInfo(jsonobj, upAddress);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Server is not turned on"); //服务器未开启
				}
			}
		}
	}

	/**
	 * 截取视频封面图
	 *
	 * @param filePath
	 * @param videoPath
	 * @param PicPath
	 * @return
	 */
	public boolean executeCodecs(String filePath, String videoPath,
								 String PicPath) {
		String picCommend = filePath + "ffmpeg -ss 00:00:03 -i " + videoPath
				+ " -f image2 -y " + PicPath;
		runCmd(picCommend);
		return true;
	}

	// 执行ffmpeg 适用于Liunx
	public void runCmd(String command) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			int exitVal = proc.waitFor();
		} catch (Throwable t) {
			System.out.println(t);
			t.printStackTrace();
		}

	}
}
/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Receiver
 * Author:   pc
 * Date:     2019/11/5 16:26
 * Description: 消费者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.rabbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.HistoryTaskService;
import com.live.monitor.service.ListLibraryService;
import com.live.monitor.utils.HttpClientUtil;
import com.live.monitor.video.HCNetSdkUtils.HCNetSdkUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 〈功能：〉<br>
 * 〈消费者〉
 *
 * @author liuhaoran
 * @create 2019/11/5 16:26
 * @since 1.0.0
 */
@Component
@PropertySource(value = {"file:./properties/init.properties"})
@Configuration
public class Receiver {
	private static Logger logger = Logger.getLogger("QueueConsumer");
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Autowired
	ListLibraryService listLibraryService;
	@Autowired
	HistoryTaskService historyTaskService;
	@Value("${nvrAddress}")
	private String nvrAddress;
	@Value("${qizhiLocalIp}")
	private String qizhiLocalIp;
	@Value("${buildingProjectId}")
	private String buildingProjectId;
	@Value("${upAddress}")
	private String upAddress;
	private String queues_camera;
	private String queues_return;
	private String address_queues_return;
	private String address_queues;
	private String requestUrl;// 接口地址
	private static HCNetSdkUtils sdkUtils = new HCNetSdkUtils();

	/**
	 * 声明队列
	 *
	 * @return
	 */
	@Bean
	public Queue queues_return() {
		queues_return = "camera_" + buildingProjectId + "_response";
		return new Queue(queues_return);
	}

	@Bean
	public Queue address_queues_return() {
		address_queues_return = "play_back_" + buildingProjectId;
		return new Queue(address_queues_return);
	}

	@Bean
	public Queue queues_camera() {
		queues_camera = "camera_" + buildingProjectId;
		return new Queue(queues_camera);
	}

	@Bean
	public Queue address_queues() {
		address_queues = "play_" + buildingProjectId;
		return new Queue(address_queues);
	}

	/**
	 * 监听消息队列
	 *
	 * @param msg
	 */
	@RabbitListener(queues = "${queues}")
	public void process(String msg) throws Exception {
		String getAttribute = ""; // 属性值
		JSONObject object = null;
		try {
			object = JSON.parseObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 避免消息发送过快阻塞
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 创建表单实例
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		JSONObject param = JSON.parseObject((String) object.get("param"));
		String postRequestUrl = qizhiLocalIp + object.get("api");
		// 判断请求的方式 get还是post
		if (object.get("method").equals("post")) {
			// 判断是不是以图搜图的接口
			if (object.get("api").equals("/api/Face/searchFaceByFace")) {
				JSONObject newparam = listLibraryService.getNewMsg(param.get("repoType").toString(), param);
				String content = null;
				JSONArray data = null;
				// 如果没有获取值,JSON不能解析,程序会中断, 处理跳过异常
				try {
					content = HttpClientUtil.postRequest(postRequestUrl, newparam);
					// MQ发送消息
					rabbitTemplate.convertAndSend(queues_return, content);
					// 下载视频
					JSONObject contentObject = JSON.parseObject(content);
					data = contentObject.getJSONArray("data");//获取数组
					System.out.println("contentObject:" + contentObject);
				} catch (Exception e) {
					e.printStackTrace();
					Thread.sleep(1000 * 60 * 3);
					System.out.println("Server is not turned on"); //服务器未开启
				}
				if (data == null || data.equals("")) {
					logger.info("------ dataNull");
				} else {
					for (int i = 0; i < data.size(); i++) { //提取出所有
						SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						JSONObject dataContent = data.getJSONObject(i);
						//System.out.println("data_Contentmsg:" + dataContent);
						String beginDate = String.valueOf(dataContent.get("captureTime"));
						String utcDate = beginDate;
						utcDate = utcDate.replace("Z", " UTC"); //注意UTC前有空格
						Date date = utcFormat.parse(utcDate);
						String dateString = sdf.format(date);
						Date comeTime = sdf.parse(dateString);
						String channelNumString = String.valueOf(dataContent.get("url"));
						String[] channel = channelNumString.split("\\_");
						String faceId = String.valueOf(dataContent.get("faceId"));
						//下载视频 channel[1] 是cameraId channel[2] 是通道号
						try {
							System.out.println("-----chnnel:" + channel[2]);
							String videoPath = sdkUtils.getVideoByTime(Integer.valueOf(channel[2]), comeTime, channel[1], faceId, buildingProjectId, nvrAddress);
							System.out.println("---downVideo" + i + ":" + videoPath + "----");
							// 上传视频
							HttpClientUtil.doLivenessAuthByFile(videoPath, "http://" + upAddress + "admin/face/api/data/upLoadVideo");
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Server is not turned on"); //服务器未开启
						}
					}
				}
			} else {
				// 向Rabbit发送消息
				String api = object.get("api").toString();
				switch (api) {
					case "/api/CustomRepo/addImageToRepo":
						rabbitTemplate.convertAndSend(queues_return, HttpClientUtil.postRequest(postRequestUrl, param));
						break;
					case "/api/Task/addTask":
						rabbitTemplate.convertAndSend(queues_return, HttpClientUtil.postRequest(postRequestUrl, param));
						break;
					case "/api/Task/updateTaskUseToCounter":
						rabbitTemplate.convertAndSend(queues_return, HttpClientUtil.postRequest(postRequestUrl, param));
						break;
					case "/api/Task/historyVideo":
						String startTimeHistory = param.get("startDateTime").toString();
						String endTimeHistory = param.get("endDateTime").toString();
						JSONObject msgs = new JSONObject();
						rabbitTemplate.convertAndSend(queues_return, "Success");
						historyTaskService.downloadAndHandelVideoTask(startTimeHistory, endTimeHistory);
						break;
					default:
						JSONObject newparam = listLibraryService.getNewMsg(param.get("repoType").toString(), param);
						rabbitTemplate.convertAndSend(queues_return, HttpClientUtil.postRequest(postRequestUrl, newparam));
						break;
				}
			}
		} else {
			// 查找json键名
			for (Object key : object.keySet()) {
				if (!key.equals("method") && !key.equals("api")) {
					getAttribute = (String) key;
					System.out.println("getAttribute:" + getAttribute);
				}
			}
			requestUrl = qizhiLocalIp + object.get("api") + "?" + getAttribute + "=" + object.get(getAttribute);
			System.out.println("requestUrl:" + requestUrl);
			System.out.println("num:" + object.get(getAttribute));
			// 向Rabbit发送消息
			rabbitTemplate.convertAndSend(queues_return, HttpClientUtil.getHttpApi(requestUrl));
		}
	}
}

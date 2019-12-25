/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Sender
 * Author:   pc
 * Date:     2019/11/5 16:25
 * Description: 消息队列发送
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.rabbit;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈功能：〉<br>
 * 〈消息队列发送〉
 *
 * @author liuhaoran
 * @create 2019/11/5 16:25
 * @since 1.0.0
 */
@Component
public class Sender {
	@Autowired
	private AmqpTemplate amqpTemplate;

	public String send() {
		Map<String, String> map = new HashMap<String, String>();
		String repoIds = "2";
		// Map 对象存入 用户名,密码,电话号码
		map.put("param", "{ \"repoIds\": [2], \"repoType\": 4,\"startDateTime\": \"2019-10-01\",\"endDateTime\": \"2019-11-06\"}");
		map.put("api", "/api/Face/getFaceNum");
		map.put("method", "post");
		// Map 转成  JSONObject 字符串
		String message = JSON.toJSONString(map);
		amqpTemplate.convertAndSend("camera_155", message);
		return "发送成功";
	}
}
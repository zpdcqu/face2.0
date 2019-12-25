/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestVideo
 * Author:   pc
 * Date:     2019/11/19 10:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.impl.FfmepegServiceImpl;
import com.live.monitor.utils.SkipHttpsUtil;
import com.live.monitor.video.HCNetSdkUtils.HCNetSdkUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/11/19 10:00
 * @since 1.0.0
 */
@Controller
public class TestVideo {
	private static HCNetSdkUtils sdkUtils = new HCNetSdkUtils();
	@Autowired
	FfmepegServiceImpl ffmepegService;

	@ResponseBody
	@RequestMapping("/startVideoPush")
	public String  startVideoPush() throws Exception {
		ffmepegService.pushTencent();
		return "开始推流";
	}
	@ResponseBody
	@RequestMapping("/stopVideoPush")
	public String  stopVideoPush() throws Exception {
		ffmepegService.stop();
		return "暂停推流";
	}
	@ResponseBody
	@RequestMapping("/restartVideoPush")
	public String  restartVideoPush() throws Exception {
		ffmepegService.restart();
		return "重启推流";
	}
}
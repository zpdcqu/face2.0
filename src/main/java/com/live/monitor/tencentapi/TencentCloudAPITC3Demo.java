/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TencentCloudAPITC3Demo
 * Author:   pc
 * Date:     2019/10/23 17:30
 * Description: 腾讯云接口鉴权
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.tencentapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.DescribeLivePlayAuthKeyRequest;
import com.tencentcloudapi.live.v20180801.models.DescribeLivePlayAuthKeyResponse;
import com.tencentcloudapi.live.v20180801.models.DescribeLivePushAuthKeyRequest;
import com.tencentcloudapi.live.v20180801.models.DescribeLivePushAuthKeyResponse;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * 〈功能：〉<br>
 * 〈腾讯云接口鉴权〉
 *
 * @author liuhaoran
 * @create 2019/10/29 15:04
 * @since 1.0.0
 */
@Component
public class TencentCloudAPITC3Demo {
	private final static Charset UTF8 = StandardCharsets.UTF_8;
	private final static String SECRET_ID = "AKIDE4h4CCKRJzQeojdP6MdnodJoBKvKiR8Z";
	private final static String SECRET_KEY = "NUN9mAICqFOHwObXJq8C0bz84kAKEtKc";
	private final static String CT_JSON = "application/json; charset=utf-8";

	public static byte[] hmac256(byte[] key, String msg) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
		mac.init(secretKeySpec);
		return mac.doFinal(msg.getBytes(UTF8));
	}

	public static String sha256Hex(String s) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] d = md.digest(s.getBytes(UTF8));
		return DatatypeConverter.printHexBinary(d).toLowerCase();
	}

	public static void main(String[] args) throws Exception {
		authentication();
	}

	public static String authentication() throws Exception {
		String key = null;
		try {

			Credential cred = new Credential("AKIDE4h4CCKRJzQeojdP6MdnodJoBKvKiR8Z", "NUN9mAICqFOHwObXJq8C0bz84kAKEtKc");

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("live.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			LiveClient client = new LiveClient(cred, "ap-chongqing", clientProfile);

			String params = "{\"DomainName\":\"43502.livepush.myqcloud.com\"}";
			DescribeLivePlayAuthKeyRequest req = DescribeLivePlayAuthKeyRequest.fromJsonString(params, DescribeLivePlayAuthKeyRequest.class);

			DescribeLivePlayAuthKeyResponse resp = client.DescribeLivePlayAuthKey(req);
			System.out.println(DescribeLivePlayAuthKeyRequest.toJsonString(resp));
			String keyString = DescribeLivePushAuthKeyRequest.toJsonString(resp);
			JSONObject object = null;
			try {
				object = JSON.parseObject(keyString);
				System.out.println("-----keyString:"+keyString);
				JSONObject PushAuthKeyInfo = (JSONObject) JSON.parseObject(String.valueOf(object.get("PushAuthKeyInfo")));
				key = PushAuthKeyInfo.get("MasterAuthKey").toString();
				System.out.println("key"+key);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return key;
	}
}
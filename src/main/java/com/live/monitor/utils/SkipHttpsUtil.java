/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: skipHttpsUtil
 * Author:   pc
 * Date:     2019/11/25 11:35
 * Description: 上传视频工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

/**
 * 〈功能：〉<br>
 * 〈上传视频工具类〉
 *
 * @author liuhaoran
 * @create 2019/11/25 11:35
 * @since 1.0.0
 */
public class SkipHttpsUtil {
	private static Logger logger = Logger.getLogger(SkipHttpsUtil.class);

	//绕过证书
	public static HttpClient wrapClient() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0,
											   String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0,
											   String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[]{tm}, null);
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
					ctx, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(ssf).build();
			return httpclient;
		} catch (Exception e) {
			return HttpClients.createDefault();
		}
	}

	public static void main(String[] args) {

	}
}
/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CosUtil
 * Author:   pc
 * Date:     2019/12/3 15:58
 * Description: 腾讯云存储对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.region.Region;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈功能：〉<br> 
 * 〈腾讯云存储对象〉
 * @author liuhaoran
 * @create 2019/12/3 15:58
 * @since 1.0.0
 */
public class CosUtil {
	// 1 初始化用户身份信息(appid, secretId, secretKey)
	// 1 初始化用户身份信息（secretId, secretKey）。
//	String secretId = "AKIDSggQuACpCKBl5XOcGdjarqb4B1arMcHi";
//	String secretKey = "KO3T3rQimaQVNp673oLd9PO7ZcS2MzLR";
//	COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//	// 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
//	ClientConfig clientConfig = new ClientConfig(new Region("ap-chongqing"));
//	// 3 生成cos客户端
//	COSClient cosclient = new COSClient(cred, clientConfig);
//	// bucket名称, 需包含appid
//	String bucketName = "testlivetv-1300388045";
//	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
	//ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
//			// 设置bucket名称
//			listObjectsRequest.setBucketName(bucketName);
//			// prefix表示列出的object的key以prefix开始
//			listObjectsRequest.setPrefix(date+"/building_"+buildingProjectId+"_cameraId"+"_"+cameraId);
//			// 设置最大遍历出多少个对象, 一次listobject最大支持1000
//			listObjectsRequest.setMaxKeys(1);
//			ObjectListing objectListing  = cosclient.listObjects(listObjectsRequest);
//			// common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
//			List<String> commonPrefixs = objectListing.getCommonPrefixes();
//			// object summary表示所有列出的object列表
//			List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
//			for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
//				// 文件的路径key
//				String key = cosObjectSummary.getKey();
//				//System.out.println("keyone"+key);
//				// 文件的etag
//				String etag = cosObjectSummary.getETag();
//				// 文件的长度
//				long fileSize = cosObjectSummary.getSize();
//				// 文件的存储类型
//				String storageClasses = cosObjectSummary.getStorageClass();
//				// 向远程API发送信息 保存直播监控的信息
//				JSONObject jsonobj=new JSONObject();
//				jsonobj.put("buildingProjectId", buildingProjectId);
//				jsonobj.put("cameraId", cameraId);
//				jsonobj.put("coverPicture", COSAddress+key);
//				try {
//					HttpClientUtil.postRequestSaveLiveInfo(jsonobj);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println("key: " + COSAddress+ key);
//			}
//cosclient.shutdown();
}
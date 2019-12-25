/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GetRidService
 * Author:   pc
 * Date:     2019/11/26 10:27
 * Description: 获取库id的公共服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 〈功能：〉<br> 
 * 〈获取库id的公共服务〉
 * @author liuhaoran
 * @create 2019/11/26 10:27
 * @since 1.0.0
 */
public interface ListLibraryService {

	/**
	 * 获取对应的库文件
	 * @return
	 */
	void ListLibrary () throws InterruptedException;

	/**
	 * 创建一个新的JSONObject数据
	 * @return
	 */
	JSONObject getNewMsg(String repoType, JSONObject param) throws InterruptedException;

}
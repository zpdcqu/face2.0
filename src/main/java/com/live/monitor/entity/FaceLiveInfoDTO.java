/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FaceLiveInfoDTO
 * Author:   pc
 * Date:     2019/11/21 9:52
 * Description: 直播信息实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈功能：〉<br> 
 * 〈直播信息实体类〉
 * @author liuhaoran
 * @create 2019/11/21 9:52
 * @since 1.0.0
 */
@Data
public class FaceLiveInfoDTO implements Serializable {
	private Integer buildingProjectId;
	private Integer cameraId;
	private String liveAddress;
}
package com.live.monitor.video.entity;

import java.io.Serializable;

/**
 * @author jiang
 * @ClassName FaceDeviceInfoTable
 * @description TODO
 * @Date 2019/11/18 0018 下午 2:19
 */
public class FaceDeviceInfoTable implements Serializable {
        private static final long serialVersionUID = 3384062557256243669L;

        private Integer deviceId;
        private String deviceNo;
        private String deviceName;
        private Integer deviceBrand;
        private String deviceIpAddr;
        private Integer devicePort;
        private String deviceAccount;
        private String devicePassword;
        private Integer deviceType;
        private Integer cameraChannelNo;
        private Integer cameraDataRate;
        private Integer cameraPixel;
        private Integer cameraFps;
        private Integer isRecord;
        private Integer isPush;
        private Integer deviceStatus;

        public void setDeviceId(Integer deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getDeviceNo() {
            return this.deviceNo;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceBrand(Integer deviceBrand) {
            this.deviceBrand = deviceBrand;
        }

        public Integer getDeviceBrand() {
            return this.deviceBrand;
        }

        public void setDeviceIpAddr(String deviceIpAddr) {
            this.deviceIpAddr = deviceIpAddr;
        }

        public String getDeviceIpAddr() {
            return this.deviceIpAddr;
        }

        public void setDevicePort(Integer devicePort) {
            this.devicePort = devicePort;
        }

        public Integer getDevicePort() {
            return this.devicePort;
        }

        public void setDeviceAccount(String deviceAccount) {
            this.deviceAccount = deviceAccount;
        }

        public String getDeviceAccount() {
            return this.deviceAccount;
        }

        public void setDevicePassword(String devicePassword) {
            this.devicePassword = devicePassword;
        }

        public String getDevicePassword() {
            return this.devicePassword;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public Integer getDeviceType() {
            return this.deviceType;
        }

        public void setCameraChannelNo(Integer cameraChannelNo) {
            this.cameraChannelNo = cameraChannelNo;
        }

        public Integer getCameraChannelNo() {
            return this.cameraChannelNo;
        }

        public void setCameraDataRate(Integer cameraDataRate) {
            this.cameraDataRate = cameraDataRate;
        }

        public Integer getCameraDataRate() {
            return this.cameraDataRate;
        }

        public void setCameraPixel(Integer cameraPixel) {
            this.cameraPixel = cameraPixel;
        }

        public Integer getCameraPixel() {
            return this.cameraPixel;
        }

        public void setCameraFps(Integer cameraFps) {
            this.cameraFps = cameraFps;
        }

        public Integer getCameraFps() {
            return this.cameraFps;
        }

        public void setIsRecord(Integer isRecord) {
            this.isRecord = isRecord;
        }

        public Integer getIsRecord() {
            return this.isRecord;
        }

        public void setIsPush(Integer isPush) {
            this.isPush = isPush;
        }

        public Integer getIsPush() {
            return this.isPush;
        }

        public void setDeviceStatus(Integer deviceStatus) {
            this.deviceStatus = deviceStatus;
        }

        public Integer getDeviceStatus() {
            return this.deviceStatus;
        }

        @Override
        public String toString() {
            return this.getDeviceName();
        }
    }


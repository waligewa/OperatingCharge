package com.example.operatingcharge.entity;

/**
 *
 * Author : 赵彬彬
 * Date   : 2019/6/9
 * Time   : 23:13
 * Desc   : 设备实体类
 */
public class RtuInfo {

	private String DeviceID;
	private String DeviceName;
	private String ComAddress;
	private String PumpNum;

	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	public String getComAddress() {
		return ComAddress;
	}

	public void setComAddress(String comAddress) {
		ComAddress = comAddress;
	}

	public String getPumpNum() {
		return PumpNum;
	}

	public void setPumpNum(String pumpNum) {
		PumpNum = pumpNum;
	}

}

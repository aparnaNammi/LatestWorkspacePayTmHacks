package com.geolocation;

public class LocationCoordinates {

	private Integer mcc;
	private Integer mnc;
	private Integer cellId;
	private Integer lac;
	private String radioType;
	public Integer getMcc() {
		return mcc;
	}
	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}
	public Integer getMnc() {
		return mnc;
	}
	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}
	public Integer getCellId() {
		return cellId;
	}
	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}
	public Integer getLac() {
		return lac;
	}
	public void setLac(Integer lac) {
		this.lac = lac;
	}
	public String getRadioType() {
		return radioType;
	}
	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}
	
	
	
}

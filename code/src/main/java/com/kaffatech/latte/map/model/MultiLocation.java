package com.kaffatech.latte.map.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.map.util.LocationUtils;

public class MultiLocation extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2627609491238771858L;

	private Double gcj02Lng;

	private Double gcj02Lat;

	private Double bd09llLng;

	private Double bd09llLat;

	public boolean isSupportGcj02() {
		if (gcj02Lng == null || gcj02Lat == null) {
			return false;
		}
		return true;
	}

	public boolean isSupportBd09ll() {
		if (bd09llLng == null || bd09llLng == null) {
			return false;
		}
		return true;
	}

	public String getGcj02Location() {
		if (!isSupportGcj02()) {
			throw new IllegalArgumentException("location is null!");
		}
		return LocationUtils.getString(gcj02Lng, gcj02Lat);
	}

	public String getBd09llLocation() {
		if (!isSupportBd09ll()) {
			// 需要进行转换
			if (!isSupportGcj02()) {
				throw new IllegalArgumentException("location is null!");
			} else {
				String bd09ll = LocationUtils.gcj02ToBd09llByApi(LocationUtils
						.getString(gcj02Lng, gcj02Lat));
				String[] array = bd09ll.split(",");
				this.bd09llLng = Double.parseDouble(array[0]);
				this.bd09llLat = Double.parseDouble(array[1]);
			}
		}
		return LocationUtils.getString(bd09llLng, bd09llLat);
	}

	/**
	 * @return the gcj02Lng
	 */
	public Double getGcj02Lng() {
		return gcj02Lng;
	}

	/**
	 * @param gcj02Lng
	 *            the gcj02Lng to set
	 */
	public void setGcj02Lng(Double gcj02Lng) {
		this.gcj02Lng = gcj02Lng;
	}

	/**
	 * @return the gcj02Lat
	 */
	public Double getGcj02Lat() {
		return gcj02Lat;
	}

	/**
	 * @param gcj02Lat
	 *            the gcj02Lat to set
	 */
	public void setGcj02Lat(Double gcj02Lat) {
		this.gcj02Lat = gcj02Lat;
	}

	/**
	 * @return the bd09llLng
	 */
	public Double getBd09llLng() {
		return bd09llLng;
	}

	/**
	 * @param bd09llLng
	 *            the bd09llLng to set
	 */
	public void setBd09llLng(Double bd09llLng) {
		this.bd09llLng = bd09llLng;
	}

	/**
	 * @return the bd09llLat
	 */
	public Double getBd09llLat() {
		return bd09llLat;
	}

	/**
	 * @param bd09llLat
	 *            the bd09llLat to set
	 */
	public void setBd09llLat(Double bd09llLat) {
		this.bd09llLat = bd09llLat;
	}

}

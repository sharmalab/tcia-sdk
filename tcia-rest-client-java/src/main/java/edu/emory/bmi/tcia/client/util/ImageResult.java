package edu.emory.bmi.tcia.client.util;

import java.io.InputStream;

/**
 * The ImageResult class for the getImage methods.
 */
public class ImageResult {
	private InputStream rawData;
	private Integer imageCount;
	public InputStream getRawData() {
		return rawData;
	}
	void setRawData(InputStream rawData) {
		this.rawData = rawData;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
}

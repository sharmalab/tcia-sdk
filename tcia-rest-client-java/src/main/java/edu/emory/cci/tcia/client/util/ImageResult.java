package edu.emory.cci.tcia.client.util;

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
	public void setRawData(InputStream rawData) {
		this.rawData = rawData;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
}

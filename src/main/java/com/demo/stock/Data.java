package com.demo.stock;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable {

	@JsonProperty("Date")
	private long date;
	@JsonProperty("Open")
	private double open;
	@JsonProperty("Close")
	private double close;
	@JsonProperty("High")
	private double high;
	@JsonProperty("Low")
	private double low;
	@JsonProperty("Volume")
	private long volume;

	public Data() {
	}

	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}
	
	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}
		
	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}
	
	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "{" + "date=" + date + ", open=" + open + 
				", close=" + close + ", high=" + high +
				", low=" + low + ", volume=" + volume + '}';
	}
}



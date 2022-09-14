package com.demo.stock.model;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "date")
	private long date;
	
	@Column(name = "open")
	private double open;
	
	@Column(name = "close")
	private double close;
	
	@Column(name = "high")
	private double high;
	
	@Column(name = "low")
	private double low;
	
	@Column(name = "volume")
	private long volume;

	public Stock() {
	}
	
	public Stock(long date, double open, double close, double high, double low, long volume) {
		
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
		
	}

	public long getId() {
		return id;
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



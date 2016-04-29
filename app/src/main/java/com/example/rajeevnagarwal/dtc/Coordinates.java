package com.example.rajeevnagarwal.dtc;

public class Coordinates {
	private double latitude;
	private double longitude;
	public Coordinates(double latitude,double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	public double getLatitude()
	{
		return this.latitude;
	}
	public double getLongitude()
	{
		return this.longitude;
	}
}

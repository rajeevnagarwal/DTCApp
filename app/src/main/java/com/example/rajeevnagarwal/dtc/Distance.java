package com.example.rajeevnagarwal.dtc;

public class Distance implements Comparable<Distance>{
	private double value;
	private String unit;
	public Distance(double val,String unit)
	{
		this.value = val;
		this.unit = unit;
	}
	public double getValue()
	{
		return this.value;
		
	}
	public String getUnit()
	{
		return this.unit;
	}
	public void setValue(double value)
	{
		this.value = value;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	@Override
	public int compareTo(Distance arg0) {
		
		return 0;
	}

}

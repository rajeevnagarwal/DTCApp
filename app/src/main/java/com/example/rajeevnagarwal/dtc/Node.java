package com.example.rajeevnagarwal.dtc;

import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Comparable<Node>{
	private String name;
	private Coordinates location;
	private ArrayList<AdjNode> adjacent;
	private int visited;
	private Node parent;
	public Distance dist;
	public Node()
	{
		this.name="";
		this.location = null;
		this.adjacent=null;
		this.visited=-1;
		this.dist = new Distance(Double.MAX_VALUE,"km");
		this.parent = null;
	}
	public Node(String name,Coordinates location)
	{
		this.name = name;
		this.location = location;
		this.adjacent = new ArrayList<AdjNode>();
		this.visited = 0;
		this.dist = new Distance(Double.MAX_VALUE,"km");
		this.parent = new Node();
	}
	public Node(String name,double lat,double lon)
	{
		this.name = name;
		this.location = new Coordinates(lat,lon);
		this.adjacent = new ArrayList<AdjNode>();
		this.visited = 0;
		this.dist = new Distance(Double.MAX_VALUE,"km");
		this.parent = new Node();
	}
	public Node getParent()
	{
		return this.parent;
	}
	public void setParent(Node n)
	{
		this.parent = n;
		
	}
	public String getName()
	{
		return this.name;
	}
	public Coordinates getLocation()
	{
		return this.location;
	}
	public ArrayList<AdjNode> getAdjacent()
	{
		return this.adjacent;
	}
	public int isVisited()
	{
		return this.visited;
	}
	public void setVisited(int x)
	{
		this.visited = x;
	}
	public int compareTo(Node x) {
		if(this.dist.getUnit().equals(x.dist.getUnit()))
		{
		if(this.dist.getValue()<x.dist.getValue())
			return -1;
		else if(this.dist.getValue()>x.dist.getValue())
			return 1;
		else
			return 0;
		}
		else
		{
			if(this.dist.getUnit().equals("km"))
			{
				if(this.dist.getValue()*1000<x.dist.getValue())
				{
					return -1;
				}
					
				else if(this.dist.getValue()*1000>x.dist.getValue())
				{
					return 1;
				}
				else 
					return 0;
			}
			else if(this.dist.getUnit().equals("m"))
			{
				if(this.dist.getValue()/1000<x.dist.getValue())
				{
					return -1;
				}
					
				else if(this.dist.getValue()/1000>x.dist.getValue())
				{
					return 1;
				}
				else 
					return 0;
			}
		}
		return 0;
	}
	public boolean equals(Node x)
	{
		if(this.getName().equals(x.getName())&&this.getLocation().getLatitude()==x.getLocation().getLatitude()&&this.getLocation().getLongitude()==x.getLocation().getLongitude())
			return true;
		else
			return false;
	}


}

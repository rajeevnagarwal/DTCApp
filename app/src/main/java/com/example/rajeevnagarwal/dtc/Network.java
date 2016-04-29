package com.example.rajeevnagarwal.dtc;

import java.util.ArrayList;
import java.util.HashMap;

public class Network {
	private HashMap<Pair,Double> SP;
	private ArrayList<Node> stations;
	public ArrayList<Node> getStation()
	{
		return this.stations;
	}
	public Network()
	{
		this.stations = new ArrayList<Node>();	
		SP = new HashMap<Pair, Double>();
	}
	public void insertStation(Node n)
	{
		if(stations.contains(n)==false)
			stations.add(n);
	}
	public void insertStation(String name,Coordinates location)
	{
		Node n = new Node(name,location);
		if(stations.contains(n)==false)
			stations.add(n);
	}
	public void insertStation(String name,int lat,int lon)
	{
		Node n = new Node(name,lat,lon);
		if(stations.contains(n)==false)
			stations.add(n);
	}
	public void insertlink(Node a,Node b,Distance x)
	{
		insertStation(a);
		insertStation(b);
		a.getAdjacent().add(new AdjNode(b,x));
		
	}
	public void insertlink(Node a,Node b,int val,String unit)
	{
		insertStation(a);
		insertStation(b);
		a.getAdjacent().add(new AdjNode(b,val,unit));
		
	}
	public ArrayList<Node> sp(Node src,Node dest)
	{
		ArrayList<Node> S = new ArrayList<Node>();
		dijkstra(src);
		Node temp = dest;
		while(temp.getParent()!=null)
		{
			S.add(temp);
			temp = temp.getParent();
		}
		if(temp.getParent()==null)
			if(!temp.getName().equals(""))
				S.add(temp);
			
		return S;
	}
	public Integer Cost(Node src, Node dest)
	{
		ArrayList<Node> S = sp(src,dest);
		double d = S.get(S.size()-1).dist.getValue();
		if(S.get(S.size()-1).dist.getUnit().equals("km"))
		{
			d = d*1000;
		}
		if(d<400)
		{
			return 5;
		}
		else if(d<800)
		{
			return 10;
		}
		else
		{
			return 15;
		}

	}

	
	private void dijkstra(Node src)
	{
		Heap<Node> queue = new Heap<Node>();
		src.dist.setValue(0);
		src.setParent(null);
		queue.add(src);
		for(int i=0;i<stations.size();i++)
			if(!stations.get(i).equals(src))
				queue.add(stations.get(i));
		while(!queue.isEmpty())
		{
			Node u = queue.extractMin();
			for(int i=0;i<u.getAdjacent().size();i++)
			{
				Node v = u.getAdjacent().get(i).getV();
				if(v.dist.getUnit().equals(u.dist.getUnit())&&v.dist.getUnit().equals(u.getAdjacent().get(i).getDist().getUnit()))
				{
					if(v.dist.getValue()>u.dist.getValue()+u.getAdjacent().get(i).getDist().getValue())
					{
						v.dist.setValue(u.dist.getValue()+u.getAdjacent().get(i).getDist().getValue());
						v.setParent(u);
							queue.Update(v);
					}
				}
					else
					{
						if(!u.getAdjacent().get(i).getDist().getUnit().equals(u.dist.getUnit()))
						{
							Node x = new Node();
							if(u.dist.getUnit().equals("km"))
							{
								x.dist.setValue(u.dist.getValue()*1000+u.getAdjacent().get(i).getDist().getValue());
								x.dist.setUnit("m");
								if(v.compareTo(x)==1)
								{
									if(v.dist.getUnit().equals("km"))
									{
										v.dist.setValue(x.dist.getValue()/1000);
										v.setParent(u);
										queue.Update(v);
									}
									else
									{
										
										v.dist.setValue(x.dist.getValue());
										v.setParent(u);
										queue.Update(v);
									}
								}
								
								
							}
							else
							{
								x.dist.setValue(u.dist.getValue()+u.getAdjacent().get(i).getDist().getValue()*1000);
								x.dist.setUnit("m");
								if(v.compareTo(x)==1)
								{
									if(v.dist.getUnit().equals("km"))
									{
										v.dist.setValue(x.dist.getValue()/1000);
										v.setParent(u);
										queue.Update(v);
									}
									else
									{
										v.dist.setValue(x.dist.getValue());
										v.setParent(u);
										queue.Update(v);
									}
									
								}
							}
							
						}
						else
						{
							Node x = new Node();
							if(u.dist.getUnit().equals("km"))
							{
								x.dist.setValue(u.dist.getValue()+u.getAdjacent().get(i).getDist().getValue());
								x.dist.setUnit("km");
								if(v.compareTo(x)==1)
								{
									if(v.dist.getUnit().equals("km"))
									{
										v.dist.setValue(x.dist.getValue());
										v.setParent(u);
										queue.Update(v);
									}
									else if(v.dist.getUnit().equals("m"))
									{
										v.dist.setValue(x.dist.getValue()*1000);
										v.setParent(u);
										queue.Update(v);
									}
									
								}
									

							}
							else
							{
								x.dist.setValue(u.dist.getValue()+u.getAdjacent().get(i).getDist().getValue());
								x.dist.setUnit("m");
								if(v.compareTo(x)==1)
								{
									if(v.dist.getUnit().equals("km"))
									{
										v.dist.setValue(x.dist.getValue()/1000);
										v.setParent(u);
										queue.Update(v);
									}
									else if(v.dist.getUnit().equals("m"))
									{
										v.dist.setValue(x.dist.getValue());
										v.setParent(u);
										queue.Update(v);
									}
									
								}
								
								
							}
							
						}
					}			
				
				
			}
			
		}
		
	}
	public void FloydWarshal()
	{
		for(int i=0;i<stations.size();i++)
		{
			for(int j=0;j<stations.get(i).getAdjacent().size();j++)
			{
			}
		}
		
	}
	public static void main(String[] args)
	{
		Network Z = new Network();
		ArrayList<Node> busstops=new ArrayList<Node>();
		Node a = new Node("a Govind Puri",28.545108,77.263953);
		busstops.add(a);
		Node b = new Node("a Kalkaji Depot",28.539430,77.266187);
		busstops.add(b);
		Node c = new Node("a C Lal Chowk",28.534574,77.268152);
		busstops.add(c);
		Node d = new Node("b Okhla Industrial Area",28.533135,77.269067);
		busstops.add(d);
		Node f = new Node("b C Lal Chowk",28.534001,77.268680);
		busstops.add(f);
		Node g = new Node("b Kalkaji Depot",28.537976,77.267588);
		busstops.add(g);
		Node h = new Node("b Govind Puri",28.545756,77.263979);
		busstops.add(h);
		Distance atoh = new Distance(89.74,"m");
		Z.insertlink(a,h,atoh);
		Distance btoa = new Distance(656.63,"m");
		Z.insertlink(b,a,btoa);
		Distance htog = new Distance(935.18,"m");
		Z.insertlink(h,g,htog);
		Distance gtof = new Distance(454.27,"m");
		Z.insertlink(g,f,gtof);
		Distance dtoc=new Distance(192.14,"m");
		Z.insertlink(d,c,dtoc);
		Distance ftod=new Distance(92.97,"m");
		Z.insertlink(f,d,ftod);
		ArrayList<Node> S = Z.sp(a,g);
		for(int i=S.size()-1;i>=0;i--)
			System.out.println(S.get(i).getName());
		return ;
	}

	
}

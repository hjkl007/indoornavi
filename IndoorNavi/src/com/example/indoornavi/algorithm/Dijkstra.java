package com.example.indoornavi.algorithm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.PointF;

public class Dijkstra {	
	

	ArrayList<Node> naviNodes = new ArrayList<Node>();	//所有的导航点
	/*open有两层含义：
	 * 1、key表示该节点已被遍历过；
	 * 2、value表示引入key节点的内圈节点*/
	Map<Node, ArrayList<Node>> open = new HashMap<Node, ArrayList<Node>>();	
	
	Node startNode;
	Node targetNode;
	boolean naviNodesIncludeStart = false;
	boolean naviNodesIncludeTarget = false;
	
	Map<ArrayList<Node>, Integer> allPath;
	
	public Dijkstra(){
		
	}

	
	public Dijkstra(ArrayList<Node> nodes, Node start, Node target){
		
		this.naviNodes = nodes;			

		initNode(start, target);
		
		
		/*
		if(naviNodesIncludeStart){
			this.startNode = start;
		}else{
			for(int i=0; i<naviNodes.size(); i++){
				boolean isInLine = false;
				ArrayList childs = naviNodes.get(i).getChild();
				for (int j=0; j<childs.size(); j++) {
					int x = (int)start.position.x;
					int y = (int)start.position.y;
					int x1 = (int)naviNodes.get(i).position.x;
					int y1 = (int)naviNodes.get(i).position.y;
					int x2 = (int)childs.get(j).position.x;
					int y2 = (int)childs.get(j).position.y;
					
					if((x-x1)*(y-y2) == (x-x2)*(y-y1))
						if((x-x1)*(x-x2) < 0){
							isInLine = true;
							
						}
							
				}
			}
		}
		if(naviNodesIncludeTarget){
			this.targetNode = target;
		}else{
			
		}
		*/
	}
	
	public void initNode(Node start, Node target){
		
		for(Node n : naviNodes){
			if(n.position.equals(start.position)){
				naviNodesIncludeStart = true;
				this.startNode = n;
			}
			if(n.position.equals(target.position)){
				naviNodesIncludeTarget = true;
				this.targetNode = n;
			}
			if(naviNodesIncludeStart && naviNodesIncludeTarget) 
				break;
		}
		
		if(!naviNodesIncludeStart){
			this.startNode = start;
			
			for(Node nextNode1 : naviNodes){

				ArrayList<Node> childs = nextNode1.getChild();
				for (Node nextNode2 : childs) {
					int x = (int)startNode.position.x;
					int y = (int)startNode.position.y;
					int x1 = (int)nextNode1.position.x;
					int y1 = (int)nextNode1.position.y;
					int x2 = (int)nextNode2.position.x;
					int y2 = (int)nextNode2.position.y;
					
					if((x-x1)*(y-y2) == (x-x2)*(y-y1) && (x-x1)*(x-x2) < 0){
						
						nextNode1.getChild().remove(nextNode2);
						nextNode1.getChild().add(startNode);
						nextNode2.getChild().remove(nextNode1);
						nextNode2.getChild().add(startNode);
						startNode.getChild().add(nextNode1);
						startNode.getChild().add(nextNode2);
						naviNodes.add(startNode);
						
					}							
				}
			}
		}
		
		if(!naviNodesIncludeTarget){
			this.targetNode = target;
			boolean helperFlag = false;
			for(Node nextNode1 : naviNodes){

				ArrayList<Node> childs = nextNode1.getChild();	//childs为何为空？
				int x = (int)targetNode.position.x;
				int y = (int)targetNode.position.y;
				int x1 = (int)nextNode1.position.x;
				int y1 = (int)nextNode1.position.y;
				for (Node nextNode2 : childs) {
					
					int x2 = (int)nextNode2.position.x;		//x2和y2为何都等于0
					int y2 = (int)nextNode2.position.y;
					
					if((x-x1)*(y-y2) == (x-x2)*(y-y1) && (x-x1)*(x-x2) <= 0){
						
						nextNode1.getChild().remove(nextNode2);
						nextNode1.getChild().add(targetNode);
						nextNode2.getChild().remove(nextNode1);
						nextNode2.getChild().add(targetNode);
						targetNode.getChild().add(nextNode1);
						targetNode.getChild().add(nextNode2);
						helperFlag = true;
						break;
						
					}							
				}
				if(helperFlag) break;
			}
			if(helperFlag){
				naviNodes.add(targetNode);
			}
		}
		
		open.put(startNode, null);
		
	}
	
	public ArrayList<Node> getNaviNodes(){
		return this.naviNodes;
	}
	
	public void setStartNode(Node n){
		this.startNode = n;
	}
	
	public Node getStartNode(){
		return this.startNode;
	}
	
	public void setTargetNode(Node n){
		this.targetNode = n;
	}
	
	public Node getTargetNode(){
		return this.targetNode;
	}
	

	
	public ArrayList<Node> computeDistance(Node n){
		//Float distance = null;
		ArrayList<Node> theBestPath = new ArrayList<Node>();
		
		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(n);
		
		ArrayList<Node> circleNodes = getNextCircle(temp);
		while(true){
			
			if(circleNodes.isEmpty()){
				
				break;
			}
			circleNodes = getNextCircle(circleNodes);
		}
		//取其中一条试试看
		theBestPath.add(targetNode);
		Node tem = targetNode;
		System.out.print(targetNode.getName()+" <- ");
		while(true){
			Node node = open.get(tem).get(0);
			
			theBestPath.add(node);
			System.out.print(node.getName()+" <- ");
			if(node.equals(startNode))
				break;
			tem = node;
		}
		
		return theBestPath;
		
		
		//return distance;
	}
	
	
	public ArrayList<Node> getNextCircle(ArrayList<Node> insideNodes) {	
		ArrayList<Node> res = new ArrayList<Node>();
		res.clear();
		for(Node n : insideNodes){
			ArrayList<Node> childs = n.getChild();
			/* 判断某一相邻节点是否已在open里			
			 * 如果不在open里，则返回的外圈节点res应包含该节点，
			 * 同时将之添加到open里；
			 * 如果是这圈刚添加进open里的，添加相应的内圈节点即可*/
			for(Node child : childs){	
				if(!open.containsKey(child)){						
					ArrayList<Node> tem = new ArrayList<Node>();
					tem.add(n);
					open.put(child, tem);
					res.add(child);
				}else if(res.contains(child)){					
					open.get(child).add(n);
				}
			}
			
		}
		
		return res;
	}
	
	

	public static class Node {
		private String name;
		private PointF position = new PointF();
		private ArrayList<Node> child = new ArrayList<Node>();
		
		public Node(){
			
		}
		
		public Node(String name){
			this.name = name;
			//this.position = null;
		}
		public Node(String name, PointF p) {
			this.name = name;
			this.position = p;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setPosition(PointF p) {
			this.position.set(p);
		}

		public PointF getPosition() {
			return position;
		}

		public ArrayList<Node> getChild() {
			return child;
		}

		public void setChild(ArrayList<Node> child) {
			this.child = child;
		}
	}
	

}










package com.example.indoornavi.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.graphics.PointF;

public class Dijkstra {

	final List<Node> naviNodes;	//所有的导航点
	List<Node> open = new ArrayList<Node>();	//当某点的相邻节点被遍历后，则从该点的child中移除
	final Node startNode;
	final Node targetNode;
	boolean naviNodesIncludeStart = false;
	boolean naviNodesIncludeTarget = false;
	
	Map<ArrayList<Node>, Integer> allPath;
	ArrayList<Node> currentPath = new ArrayList<Node>();
	Node currentNode;
	
	public Dijkstra(ArrayList<Node> nodes, Node start, Node target){
		this.naviNodes = nodes;	
		this.startNode = start;
		this.targetNode = target;
		
		open = nodes;
		currentNode = start;
		currentPath.add(start);
		
		for(Node n : nodes){
			if(n.position.equals(start)){
				naviNodesIncludeStart = true;
				
			}
			if(n.position.equals(target)){
				naviNodesIncludeTarget = true;
			}
			if(naviNodesIncludeStart && naviNodesIncludeTarget) 
				break;
		}
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
	
	
	
	public ArrayList<Node> computePath(Node current){

		Node tempNode = getNextNode(current);
		if(tempNode != null){
			currentPath.add(tempNode);
			if(tempNode.getName().equals(targetNode.getName())){
				tempNode = current;
				
			}
			return computePath(tempNode);
		}else{
			
			return null;
		}
		
		
		
	}
	
	public Float computeDistance(ArrayList<Node> node){
		Float distance = null;
		
		return distance;
	}
	
	
	public Node getNextNode(Node node) {	//此node应该是open里的node
		Node res = null;
		
		for(int i=0; i < open.size(); i++){	//最保险的判断方法，在牺牲效率的前提下
			if(node.name.equals(open.get(i).name)){
				if(open.get(i).child != null){
					res = open.get(i).child.get(0); //是否应该反向删除？
					open.get(i).child.remove(0);
					break;
				}
			}
		}
		
		return res;
	}
	
	

	public class Node {
		private String name;
		private PointF position;
		private List<Node> child = new ArrayList<Node>();

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
			this.position = p;
		}

		public PointF getPosition() {
			return position;
		}

		public List<Node> getChild() {
			return child;
		}

		public void setChild(ArrayList<Node> child) {
			this.child = child;
		}
	}
}
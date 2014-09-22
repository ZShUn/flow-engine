package com.duowan.flowengine.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 代表一张有方向的图,各个节点连接有方向
 * 
 * @author badqiu
 *
 */
public class Graph <NODE extends GraphNode>{

	private List<NODE> nodes = new ArrayList<NODE>();
	private List<GraphEdge> edges = new ArrayList<GraphEdge>();
	
	public Graph(){
	}

	/**
	 * 初始化图
	 */
	public void init() {
		initAllNodeDepends();
	}

	private void initAllNodeDepends() {
		for(GraphNode node : nodes) {
			addDepends(node.getGraphNodeId(), node.getDepends());
		}
	}
	
	public List<NODE> getNodes() {
		return nodes;
	}

	public void setNodes(List<NODE> nodes) {
		this.nodes = nodes;
	}

	public List<GraphEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<GraphEdge> edges) {
		this.edges = edges;
	}

	public NODE getNode(String id) {
		for(NODE node : nodes) {
			if(node.getGraphNodeId().equals(id)) {
				return node;
			}
		}
		return null;
	}
	
	public NODE getRequiredNode(String id) {
		NODE n = getNode(id);
		if(n == null) 
			throw new IllegalArgumentException("not found Node by id:"+id);
		return n;
	}
	
	public void addNode(NODE n) {
		if(!nodes.contains(n)) 
			nodes.add(n);
	}
	
	/**
	 * 得到没有任何依赖的所有节点
	 * @return
	 */
	public List<NODE> getNoDependNodes() {
		List<NODE> result = new ArrayList<NODE>();
		for(NODE t : nodes) {
			if(CollectionUtils.isEmpty(t.getParents())) {
				result.add(t);
			}
		}
		return result;
	}
	
	/**
	 * 增加依赖
	 * @param id
	 * @param depends
	 */
	private void addDepends(String id,String depends) {
		if(StringUtils.isBlank(depends)) {
			return;
		}
		for(String depend : depends.split("[,\\s]+")){
			if(StringUtils.isNotBlank(depend)) {
				GraphEdge edge = new GraphEdge(depend,id);
				addEdge(edge);
			}
		}
	}
	
	/**
	 * 增加图的边
	 * @param id
	 * @param depends
	 */
	public void addEdge(GraphEdge edge) {
		if(edges.contains(edge)) {
			return;
		}
		
		GraphNode beginNode = getRequiredNode(edge.getBegin());
		GraphNode endNode = getRequiredNode(edge.getEnd());
		beginNode.addChild(endNode);
		endNode.addParent(beginNode);
		edges.add(edge);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(NODE n : getNoDependNodes()) {
			sb.append(n.dump(0)+"\n");
		}
		return sb.toString();
	}
}

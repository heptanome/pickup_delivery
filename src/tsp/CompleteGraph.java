package tsp;
import model.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompleteGraph implements Graph {
	
	/**
	 * Attributes
	 * @param nbVertices : number of vertices of the CostGraph array
	 * @param SetOfRequests sor : list of requests
	 * @param map : 2D array representing the full map
	 * @param cost : 2D array representing a complete graph, where each vertex is a pickup or delivery point, or the depot
	 * @param costTmp : 2D array allowing us to make a link between map and cost
	 * @param precedence : Map storing a the precedence array of each vertex of cost after Djikstra algorithm
	 * @param nameNodeCost : Map allowing us to make a link between an Intersection and the integer used to represent it
	 * 
	 * First, the constructor creates an array representing the city map. It is a rather big array, and not complete at all
	 * Then, it creates the CostGraph array, which represents a complete graph, where each vertex is a delivery or pickup point, or the depot
	 */
	private static final float INFINITE = Float.MAX_VALUE;
	int nbVertices;
	float[][] map;
	float[][] costTmp;
	float[][] cost;
	private Map<Integer,int[]> precedence;
	private Map<Integer,Intersection> nameNodeCost;
	private SetOfRequests sor;
	private CityMap cityMap;

	
	/**
	 * Constructor
	 * @param CityMap cm
	 * @param SetOfRequests sor
	 * 
	 * First, the constructor creates an array representing the city map. It is a rather big array, and not complete at all
	 * Then, it creates the CostGraph array, wich represents a complete graph, where each vertex is a delivery or pickup point, or the depot
	 */
	public CompleteGraph(CityMap cm, SetOfRequests sor){
		this.nbVertices = cm.getNbVertices();
		this.sor = sor;
		this.cityMap = cm;
		this.createMapGraph(cityMap.getNumberIdMap(), cityMap.getSegments());
		List<Intersection> requestNodes = sor.getRequestNodes();
		this.initCostGraph(requestNodes.size());
		int[] requestNodesInt = new int[requestNodes.size()];
		this.precedence = new HashMap<Integer,int[]>();;
		for(int index = 0; index < requestNodesInt.length; index++) {
			requestNodesInt[index] = cityMap.getNumberIdMap().get(requestNodes.get(index));
		}
		
		this.createCompleteShortestGraph(requestNodesInt,cityMap.getNumberIdMap());
	}
	
	/**
	 * Constructor
	 * @param CityMap cm
	 * @param List<Intersection> points : list of delivery or pickup points, and the depot
	 * 
	 * First, the constructor creates an array representing the city map. It is a rather big array, and not complete at all
	 * Then, it creates the CostGraph array, wich represents a complete graph, where each vertex is a delivery or pickup point, or the depot
	 */
	public CompleteGraph(CityMap cm, List<Intersection> points){
		this.nbVertices = cm.getNbVertices();
		this.cityMap = cm;
		this.createMapGraph(cityMap.getNumberIdMap(), cityMap.getSegments());
		List<Intersection> requestNodes = points;
		this.initCostGraph(requestNodes.size());
		int[] requestNodesInt = new int[requestNodes.size()];
		this.precedence = new HashMap<Integer,int[]>();;
		for(int index = 0; index < requestNodesInt.length; index++) {
			requestNodesInt[index] = cityMap.getNumberIdMap().get(requestNodes.get(index));
		}
		
		this.createCompleteShortestGraph(requestNodesInt,cityMap.getNumberIdMap());
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public float getCost(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return -1;
		return cost[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return false;
		return i != j;
	}
	
	@Override
	public float minArcCost() {
		float min = INFINITE;
		for(int i=0; i < cost.length; i++) {
			for(int j=0; j < cost.length; j++) {
				if(cost[i][j] < min)
					min = cost[i][j];
			}
		}
		return min;
	}
	
	@Override
	public boolean isDeliveryAddress(int i) {
		Intersection deliveryAddress = sor.getRequestNodes().get(i);
		return sor.isDeliveryPoint(deliveryAddress);
	}
	
	@Override
	public List<Integer> getPickUpFromDelivery(int i) {
		Intersection deliveryAddress = sor.getRequestNodes().get(i);
		List<Request> requests = sor.getRequestsFromDelivery(deliveryAddress);
		List<Integer> pickUpAddressInt = new LinkedList<Integer>();
		for(Request r : requests) {
			Intersection pickUpAddress = r.getPickup();
			int index = 0;
			for(Intersection inter : sor.getRequestNodes()) {
				if(pickUpAddress == inter) {
					pickUpAddressInt.add(index);
					break;
				}
				index ++;
			}
		}
		return pickUpAddressInt;
	}

	/**
	 * Convert the map into a 2D array of intersections containing distances
	 * between the origin and the destination of the segment
	 * @param numberToIdMap 		: Map between the number of an intersection and its id
	 * @type  Map<Intersection, Integer>
	 * @param segments 				: Loaded map's segments 
	 * @type  List<Segment>
	 */
	private void createMapGraph(Map<Intersection, Integer> numberToIdMap, List<Segment> segments){
		this.map = new float[this.nbVertices][this.nbVertices];
		for (int i= 0; i< this.nbVertices; i++) {
			for (int j = 0; j< this.nbVertices; j++)
			{
				this.map[i][j] = INFINITE;
			}
		}
		for (Segment s : segments){
			int idOrigin = numberToIdMap.get(s.getOrigin());
			int idDestination = numberToIdMap.get(s.getDestination());
			this.map[idOrigin][idDestination] = s.getLength();
		}
	}
	
	private void initCostGraph(int nbNodes){
		this.costTmp = new float[this.nbVertices][this.nbVertices];
		for (int i= 0; i< costTmp.length; i++) {
			for (int j = 0; j< costTmp.length; j++)
			{
				this.costTmp[i][j] = INFINITE;
			}
		}
		this.cost = new float[nbNodes][nbNodes];
		for (int i= 0; i< cost.length; i++) {
			for (int j = 0; j< cost.length; j++)
			{
				this.cost[i][j] = INFINITE;
			}
		}
	}
	
	/**
	 * Create a complete graph of a shortest paths between each vertex, where each vertex is
	 * a delivery or pickup point, or the depot.
	 * @param  requestNodes		   : Request nodes's numbers
	 * @type   int[]
	 * @param  numberToIdMap	   : Map between the number of an intersection and its id
	 * @type   Map<Intersection,Integer>
	 */
	private void createCompleteShortestGraph(int[] requestsNodes, Map<Intersection,Integer> numberToIdMap) {
		Set<Map.Entry<Intersection,Integer>> set = numberToIdMap.entrySet();
		nameNodeCost = new HashMap<Integer,Intersection>();
		for(int nodeOrigin : requestsNodes) {
			float[] coutDijkstra = DijkstraFromANode(nodeOrigin);
			for(int nodeDestination : requestsNodes) {
				costTmp[nodeOrigin][nodeDestination] = coutDijkstra[nodeDestination];
			}
		}
		int indexI = 0;
		for(int i : requestsNodes) {
			Intersection nameNode = null;
			for(Map.Entry<Intersection,Integer> paire : set) {
				if(paire.getValue() == i) {
					nameNode = paire.getKey();
					break;
				}
			}
			nameNodeCost.put(indexI, nameNode);
			
			int indexJ = 0;
			for(int j : requestsNodes) {
				cost[indexI][indexJ]=costTmp[i][j];
				indexJ++;
			}
			indexI++;
		}
		this.nbVertices = requestsNodes.length;
	}
		
	/**
	 * Apply the Dijkstra algorithm from firstNode. This method also compute the precedence array of the node and 
	 * stores it in the precedence attribute of <code>this</code>
	 * 
	 * @return float[] : an array containing the shortest distance from firstNode to any other node in cost graph
	 */
/*	private float[] DijkstraFromANodeWithoutList(int firstNode) {
		if (firstNode >= this.nbVertices) {
			throw new IllegalArgumentException("This node doesn't exist!");
		}
		float[] d = new float[this.nbVertices];
		int[] pi = new int[this.nbVertices];
		int colorNodes[] = new int[this.nbVertices]; //{white :0, grey:1 , black:-1}
		
		//Initialisation
		for (int i= 0; i< nbVertices; i++) {
			d[i] = INFINITE;
			pi[i] = -1;
		}
		
		//Visiting first node
		d[firstNode] = 0;
		int indexBegin = 0;
		int indexEnd = 1;
		colorNodes[firstNode] = 1;
		
		//While all nodes arre not visited
		while (!this.isDijkstraFinished(indexBegin, indexEnd)) {
			System.out.println("bonjou");
			//choosing the closest node
			int currentNode = findIndexOfMinCostOfVisitedNodes(colorNodes, d);
			Intersection currentNodeIntersection = cityMap.getIntersectionFromIdMap(currentNode);
			
			List<Intersection> neighbours = currentNodeIntersection.getNeighbours();
			for (Intersection n : neighbours) {
				// finding neighbours
				int neighbour = cityMap.getIntFromIntersectionMap(n);
				//Relachement
				if(!(colorNodes[neighbour] == -1)) {
					float newCost =  map[currentNode][neighbour] + d[currentNode];
					if ( newCost < d[neighbour]) {
						 d[neighbour] = newCost;
						 pi[neighbour] = currentNode;
					}
					//node becomes grey
					if (!(colorNodes[neighbour] == 1)){
						colorNodes[neighbour] = 1;
						indexEnd++;
					}
				}
			}
			//Node becomes black
			colorNodes[currentNode]=-1;
			indexBegin++;
		}
		this.precedence.put(Integer.valueOf(firstNode), pi);
		
		return d;
	}*/
	
	private float[] DijkstraFromANode(int firstNode) {
		if (firstNode >= this.nbVertices) {
			throw new IllegalArgumentException("This node doesn't exist!");
		}
		float[] d = new float[this.nbVertices];
		int[] pi = new int[this.nbVertices];
		TreeMap<Float, List<Integer>> greyNodes = new TreeMap<Float, List<Integer> >();
		List<Integer> blackNodes = new LinkedList<Integer>();
		
		//Initialisation
		for (int i= 0; i< nbVertices; i++) {
			d[i] = INFINITE;
			pi[i] = -1;
		}
		
		//Visiting first node
		d[firstNode] = 0;
		List<Integer> tmpList = new LinkedList<Integer>();
		tmpList.add(firstNode);
		greyNodes.put(d[firstNode], tmpList);
		
		//While all nodes are not visited
		while (!greyNodes.isEmpty()) {
			
			//choosing the closest node
			int currentNode = greyNodes.firstEntry().getValue().remove(0);
			if(greyNodes.firstEntry().getValue().isEmpty())
				greyNodes.pollFirstEntry();
			Intersection currentNodeIntersection = cityMap.getIntersectionFromIdMap(currentNode);
			
			List<Intersection> neighbours = currentNodeIntersection.getNeighbours();
			for (Intersection n : neighbours) {
				// finding neighbours
				int neighbour = cityMap.getIntFromIntersectionMap(n);
				//Relachement
				if(!blackNodes.contains(neighbour)) {
					float newCost =  map[currentNode][neighbour] + d[currentNode];
					if ( newCost < d[neighbour]) {
						 d[neighbour] = newCost;
						 pi[neighbour] = currentNode;
					}
					//node becomes grey
					if (!(isNodeGrey(neighbour,greyNodes))){
						if(greyNodes.containsKey(d[neighbour])) {
							greyNodes.get(d[neighbour]).add(neighbour);
						} else {
							List<Integer> tmpListNode = new LinkedList<Integer>();
							tmpListNode.add(neighbour);
							greyNodes.put(d[neighbour], tmpListNode);
						}
					}
				}
			}
			//Node becomes black
			blackNodes.add(currentNode);
		}
		
		this.precedence.put(Integer.valueOf(firstNode), pi);
		
		return d;
	}
	
	/*private boolean isDijkstraFinished(int indexBegin, int indexEnd) {
		return (indexBegin == indexEnd);
	}
	
	private int findIndexOfMinCostOfVisitedNodes(int[] colorNodes, float[] d) {
		float min = INFINITE;
		int index = 0;
		for (int i = 0; i< colorNodes.length; i++) {
			int currentNode = i;
			if (d[currentNode] < min && (colorNodes[i] > 0)) {
				min = d[currentNode];
				index = currentNode;
			}
		}
		return index;
	}*/
	
	private boolean isNodeGrey(int n, TreeMap<Float,List<Integer>> greyNodes) {
		
		for(Map.Entry<Float,List<Integer>> entry : greyNodes.entrySet())
			for(Integer i : entry.getValue())
				if(i == n)
					return true;
		return false;
	}
	
	public String toString() {
		String message ="";
		for(int i = 0; i < costTmp.length; i++) {
			for(int j = 0; j < costTmp.length; j++) {
				message += costTmp[i][j] + " | ";
			}
			message += "\n";
		}
		return message;
	}
	
	public Map<Integer,Intersection> getNodeNames() {
		return nameNodeCost;
	}
	
	public int[] getPrecedenceOfANode(int idNode){
		return precedence.get(idNode);
	}
}

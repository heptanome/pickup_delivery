package tsp;
import model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompleteGraph implements Graph {
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
	 * @param nbVertices : number of nodes in a map
	 * @type  int
	 * @param numberToIdMap 		: Map between the number of an intersection and its id
	 * @type  Map<Intersection, Integer>
	 * @param segments 				: Loaded map's segments 
	 * @type  List<Segment>
	 * @param requestNodes 			: Numbers of the pickup and delivery intersections of every requests
	 * @type  Intersection[]
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
	public int getPickUpFromDelivery(int i) {
		Intersection deliveryAddress = sor.getRequestNodes().get(i);
		Intersection pickUpAddress = sor.getPickUpFromDelivery(deliveryAddress);
		int pickUpAddressInt = -1;
		int index = 0;
		for(Intersection inter : sor.getRequestNodes()) {
			if(pickUpAddress == inter) {
				pickUpAddressInt = index;
				break;
			}
			index ++;
		}
		return pickUpAddressInt;
	}

	/**
	 * Convert the map into a table 2D of intersections containing distances
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
	 * Convert the request nodes's numbers into request nodes's ids
	 * @param  requestNodes		   : Request nodes's numbers
	 * @type   Intersection[]
	 * @param  numberToIdMap	   : Map between the number of an intersection and its id
	 * @type   Map<Intersection,Integer>
	 * @return int[]			   : Request nodes's ids
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
		//printPrecedence();
		int indexI = 0;
		for(int i : requestsNodes) {
			//get Intersection corresponding to I value:
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
		this.nbVertices = requestsNodes.length; //ça c'est vraiment pas ouf. Pb de conception
	}
		
	private float[] DijkstraFromANode(int firstNode) {
		if (firstNode >= this.nbVertices) {
			throw new IllegalArgumentException("This node doesn't exist!");
		}
		float[] d = new float[this.nbVertices]; // Matrice de cout au depart du sommet firstNode
		int[] pi = new int[this.nbVertices]; // Matrice de précédence
		int colorNodes[] = new int[this.nbVertices]; //{white :0, grey:1 , black:-1}
		
		//Initialisation
		for (int i= 0; i< nbVertices; i++) {
			d[i] = INFINITE;
			pi[i] = -1;
		}
		
		//On commence par visiter le sommet firstNode, il devient gris
		d[firstNode] = 0;
		int indexBegin = 0;
		int indexEnd = 1;
		colorNodes[firstNode] = 1;
		
		//Tant qu'on a pas visite tous les sommets
		while (!this.isDijkstraFinished(indexBegin, indexEnd)) {
			//Choisir le noeud parmi les sommets gris dont la distance est la plus courte
			int currentNode = findIndexOfMinCostOfVisitedNodes(colorNodes, d);
			Intersection currentNodeIntersection = cityMap.getIntersectionFromIdMap(currentNode);
			
			List<Intersection> neighbours = currentNodeIntersection.getNeighbours();
			for (Intersection n : neighbours) {
				// On cherche les successeurs (intersection destination) du sommet actuel (intersection origine)
				int neighbour = cityMap.getIntFromIntersectionMap(n);
				//Relachement
				if(!(colorNodes[neighbour] == -1)) {
					float newCost =  map[currentNode][neighbour] + d[currentNode];
					if ( newCost < d[neighbour]) {
						 d[neighbour] = newCost;
						 pi[neighbour] = currentNode;
					}
					//On colorise en gris ce nouveau noeud si besoin
					if (!(colorNodes[neighbour] == 1)){
						colorNodes[neighbour] = 1;
						indexEnd++;
					}
				}
			}
			//On a fini le sommer actuel, on le colore en noir
			colorNodes[currentNode]=-1;
			indexBegin++;
		}
		this.precedence.put(Integer.valueOf(firstNode), pi);
		
		return d;
	}
	
	private boolean isDijkstraFinished(int indexBegin, int indexEnd) {
		//Alors oui ca a lair inutile mais si on change de modelisation pour visited ce sera plus simple a modifier
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

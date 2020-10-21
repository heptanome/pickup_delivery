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
	private Map<Integer,String> nameNodeCost;

	
	/**
	 * 
	 * 
	 */
	public CompleteGraph(int nbVertices, Map<String, Integer> numberToIdMap, List<Segment> segments, String[] requestNodes){
		this.nbVertices = nbVertices;
		this.createMapGraph(numberToIdMap, segments);
		this.initCostGraph(requestNodes.length);
		int[] requestNodesInt = new int[requestNodes.length];
		this.precedence = new HashMap<Integer,int[]>();;
		for(int index = 0; index < requestNodesInt.length; index++) {
			requestNodesInt[index] = numberToIdMap.get(requestNodes[index]);
		}
		
		this.createCompleteShortestGraph(requestNodesInt,numberToIdMap);
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

	/**
	 * Convert the map into a table 2D of intersections containing distances
	 * between the origin and the destination of the segment
	 * @param numberToIdMap
	 * @param segments
	 */
	
	private void createMapGraph(Map<String, Integer> numberToIdMap, List<Segment> segments){
		this.map = new float[this.nbVertices][this.nbVertices];
		for (int i= 0; i< this.nbVertices; i++) {
			for (int j = 0; j< this.nbVertices; j++)
			{
				this.map[i][j] = INFINITE;
			}
		}
		for (Segment s : segments){
			int idOrigin = numberToIdMap.get(s.getNumberOrigin());
			int idDestination = numberToIdMap.get(s.getNumberDestination());
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
	
	private void createCompleteShortestGraph(int[] requestsNodes, Map<String,Integer> numberToIdMap) {
		Set<Map.Entry<String,Integer>> set = numberToIdMap.entrySet();
		nameNodeCost = new HashMap<Integer,String>();
		for(int nodeOrigin : requestsNodes) {
			float[] coutDijkstra = DijkstraFromANode(nodeOrigin);
			for(int nodeDestination : requestsNodes) {
				costTmp[nodeOrigin][nodeDestination] = coutDijkstra[nodeDestination];
			}
		}
		//printPrecedence();
		int indexI = 0;
		for(int i : requestsNodes) {
			//get String corresponding to I value:
			String nameNode = "";
			for(Map.Entry<String,Integer> paire : set) {
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
			
			for (int neighbour = 0; neighbour<this.nbVertices; neighbour++) {
				// On cherche les successeurs (intersection destination) du sommet actuel (intersection origine)
				
				if (this.map[currentNode][neighbour] != INFINITE) {
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
			}
			//On a fini le sommer actuel, on le colore en noir
			colorNodes[currentNode]=-1;
			indexBegin++;
		}
		this.precedence.put(new Integer(firstNode), pi);
		
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
	
	public Map<Integer,String> getNodeNames() {
		return nameNodeCost;
	}
	
	public int[] getPrecedenceOfANode(int idNode){
		return precedence.get(idNode);
	}
	private void printPrecedence() {
		for (Map.Entry<Integer, int[]> me : precedence.entrySet()) { 
            System.out.print(me.getKey() + ":"); 
            int[] tab = me.getValue();
            for(int i=0; i< tab.length; i++) {
            	System.out.print(tab[i]+" | ");
            }
            System.out.println();
        } 
	}
}

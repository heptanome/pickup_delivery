package tsp;

import java.util.List;

public interface Graph {
	/**
	 * @return the number of vertices in <code>this</code>
	 */
	public abstract int getNbVertices();

	/**
	 * @param i first vertex
	 * @param j second vertex
	 * @return the cost of arc (i,j) if (i,j) is an arc; -1 otherwise
	 */
	public abstract float getCost(int i, int j);

	/**
	 * @param i first vertex
	 * @param j second vertex
	 * @return true if <code>(i,j)</code> is an arc of <code>this</code>
	 */
	public abstract boolean isArc(int i, int j);

	/**
	 * @return cost of the shortest arc in <code>this</code>
	 */
	public abstract float minArcCost();

	/**
	 * @param i the delivery point (vertex)
	 * @return true if <code>i</code> is a point where is delivery has to be made
	 * @throws Exception the delivery point (vertex)
	 */
	public abstract boolean isDeliveryAddress(int i) throws Exception;

	/**
	 * @param i the delivery point (vertex)
	 * @return a list of integers where each integer of the list is a point whose
	 *         delivery point is <code>i</code>
	 * @throws Exception thrown if i is not found in the graph
	 */
	public abstract List<Integer> getPickUpFromDelivery(int i) throws Exception;

}

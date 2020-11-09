package tsp;

public interface TSP {
	/**
	 * Search for a shortest cost Hamiltonian circuit in <code>g</code> within
	 * <code>timeLimit</code> milliseconds (returns the best found tour whenever the
	 * time limit is reached) Warning: The computed tour always start from vertex 0
	 * 
	 * @param timeLimit the maximum amount of time it can take to compute a tour
	 * @param g         the graph to search the solution on
	 */
	public void searchSolution(int timeLimit, Graph g);

	/**
	 * @param i the i-th visited vertex
	 * @return the i-th visited vertex in the solution computed by
	 *         <code>searchSolution</code> (-1 if <code>searcheSolution</code> has
	 *         not been called yet, or if i less than 0 or i greater or equal to
	 *         g.getNbSommets())
	 */
	public Integer getSolution(int i);

	/**
	 * @return the total cost of the solution computed by
	 *         <code>searchSolution</code> (-1 if <code>searcheSolution</code> has
	 *         not been called yet).
	 */
	public float getSolutionCost();

}

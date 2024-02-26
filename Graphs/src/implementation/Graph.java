package implementation;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated
 * with a vertex.
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph() {
		adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
		dataMap = new HashMap<String, E>();
	}

	public void addVertex(String vertexName, E data) throws IllegalArgumentException {
		if (adjacencyMap.containsKey(vertexName)) {
			throw new IllegalArgumentException("Vertex exists");
		} else {
			adjacencyMap.put(vertexName, new HashMap<String, Integer>());
			dataMap.put(vertexName, data);
		}
	}

	public void addDirectedEdge(String startVertexName, String endVertexName, int cost)
			throws IllegalArgumentException {
		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("A vertex is not in graph");
		}
		adjacencyMap.get(startVertexName).put(endVertexName, cost);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		TreeMap<String, E> sortedData = new TreeMap<>(dataMap);

		sb.append("Vertices: ");
		sb.append(sortedData.keySet().toString()).append("\nEdges: \n");

		for (String vertex : sortedData.keySet()) {
			sb.append("Vertex");
			sb.append("(").append(vertex);
			sb.append(")").append("--->");
			sb.append(adjacencyMap.get(vertex).toString() + "\n");
		}
		return sb.toString();
	}

	public Map<String, Integer> getAdjacentVertices(String vertexName) {
		if (adjacencyMap.get(vertexName).isEmpty()) {
			return new HashMap<String, Integer>();
		}
		return adjacencyMap.get(vertexName);
	}

	public int getCost(String startVertexName, String endVertexName) throws IllegalArgumentException {
		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("A vertex is not in graph");
		}
		return adjacencyMap.get(startVertexName).get(endVertexName);
	}

	public Set<String> getVertices() {
		return dataMap.keySet();
	}

	public E getData(String vertex) throws IllegalArgumentException {
		if (!dataMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Vertex not in graph");
		}
		return dataMap.get(vertex);
	}

	public void doDepthFirstSearch(String startVertexName, CallBack<E> callBack) {
		if (!dataMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Set<String> visited = new HashSet<>();
		Stack<String> discovered = new Stack<>();
		discovered.push(startVertexName);

		while (!discovered.isEmpty()) {
			String searching = discovered.pop();
			if (!visited.contains(searching)) {
				visited.add(searching);
				callBack.processVertex(searching, dataMap.get(searching));
				for (String s : adjacencyMap.get(searching).keySet()) {
					if (!visited.contains(s)) {
						discovered.push(s);
					}
				}
			}
		}
	}

	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callBack) {
		if (!dataMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex not found");
		}
		Set<String> visited = new HashSet<>();
		Queue<String> discovered = new LinkedList<>();
		discovered.add(startVertexName);

		while (!discovered.isEmpty()) {
			String searching = discovered.poll();
			if (!visited.contains(searching)) {
				visited.add(searching);
				callBack.processVertex(searching, dataMap.get(searching));
				for (String s : adjacencyMap.get(searching).keySet()) {
					if (!visited.contains(s)) {
						discovered.add(s);
					}
				}
			}
		}

	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		int max = Integer.MAX_VALUE;
		if (!dataMap.containsKey(startVertexName) || !dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException();
		}
		if (startVertexName.equals(endVertexName)) {
			shortestPath.add(startVertexName);
			return 0;
		}

		HashMap<String, Integer> distance = new HashMap<>();
		HashMap<String, String> predecessor = new HashMap<>();
		for (String s : dataMap.keySet()) {
			distance.put(s, max);
			predecessor.put(s, "None");
		}
		PriorityQueue<String> pQueue = new PriorityQueue<>(Comparator.comparing(distance::get));
		pQueue.add(startVertexName);

		distance.put(startVertexName, 0);

		while (!pQueue.isEmpty()) {
			String currVertex = pQueue.poll();
			if (currVertex.equals(endVertexName)) {
				break;
			}
			if (distance.get(currVertex) == max) {
				break;
			}

			Map<String, Integer> adjacentVertex = adjacencyMap.get(currVertex);
			if (adjacentVertex != null) {
				for (String s : adjacentVertex.keySet()) {
					int cost = distance.get(currVertex) + adjacentVertex.get(s);
					if (cost < distance.get(s)) {
						distance.put(s, cost);
						predecessor.put(s, currVertex);
						pQueue.add(s);
					}
				}
			}
		}

		if (predecessor.get(endVertexName).equals("None")) {
			shortestPath.add("None");
			return -1;
		}

		String currVertex = endVertexName;
		while (!currVertex.equals("None")) {
			shortestPath.add(0, currVertex);
			currVertex = predecessor.get(currVertex);
		}
		return distance.get(endVertexName);
	}
}
package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (comparator == null || maxEntries < 1) {
			throw new IllegalArgumentException("Invalid argument");
		}
		this.root = null;
		this.treeSize = 0;
		this.maxEntries = maxEntries;
		this.comparator = comparator;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if(key == null || value == null) {
			throw new IllegalArgumentException("Parameters are null");
		}
		if(isFull()) {
			throw new TreeIsFullException("FULL TREE");
		}
		Node node = new Node(key, value);
		root = addHelper(root, node);
		return this;
	}

	private Node addHelper(Node curr, Node addNode) throws TreeIsFullException{
		if (curr == null) {
			treeSize++;
			return addNode;
		}
		
		if(comparator.compare(addNode.key, curr.key) == 0) {
			curr.value = addNode.value;
			return curr;
		} else if(comparator.compare(addNode.key, curr.key) < 0) {
			if(curr.left == null) {
				treeSize++;
				curr.left = new Node(addNode.key, addNode.value);
			} else {
				curr.left = addHelper(curr.left, addNode);
			}
		} else {
			if(curr.right == null) {
				treeSize++;
				curr.right = new Node(addNode.key, addNode.value);
			} else {
				curr.right = addHelper(curr.right, addNode);
			}
		}
		return curr;
	}

	public String toString() {
		if (root == null) {
			return "EMPTY TREE";
		} else {
			return toStringHelper(root);
		}
	}

	private String toStringHelper(Node node) {
		if(node == null) {
			return "";
		} else {
			return toStringHelper(node.left) + "{" + node.key + ":" + node.value + "}" + toStringHelper(node.right);
		}

	}

	/* Provided: */
	public boolean isEmpty() {
		return root == null;
	}

	/* Provided: */
	public int size() {
		return treeSize;
	}

	/* Provided: */
	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (root == null) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}

		return new KeyValuePair<>(getMinimumHelper(root).key, getMinimumHelper(root).value);
	}

	private Node getMinimumHelper(Node n) {
		if (n.left != null) {
			return getMinimumHelper(n.left);
		}
		return n;
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (root == null) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}

		return new KeyValuePair<>(getMaximumHelper(root).key, getMaximumHelper(root).value);
	}

	private Node getMaximumHelper(Node n) {
		if (n.right != null) {
			return getMaximumHelper(n.right);
		}
		return n;

	}

	public KeyValuePair<K, V> find(K key) {
		if (key == null) {
			return null;
		}
		Node foundNode = findHelper(key, root);
		if (foundNode == null) {
			return null;
		}
		return new KeyValuePair<>(foundNode.key, foundNode.value);
	}

	private Node findHelper(K key, Node curr) {
		if (curr == null) {
			return null;
		}
		if (comparator.compare(key, curr.key) < 0) {
			return findHelper(key, curr.left);
		} else if (comparator.compare(key, curr.key) > 0) {
			return findHelper(key, curr.right);
		} else {
			return curr;
		}
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (this.isEmpty() || root == null) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}
		if (key == null) {
			throw new IllegalArgumentException("Error");
		}
		root = deleteHelper(key, root);
		treeSize--;
		return this;
	}

	private BinarySearchTree<K, V>.Node deleteHelper(K key, Node curr) {
		if (curr == null) {
			return null;
		}
		if (comparator.compare(key, curr.key) < 0) {
			curr.left = deleteHelper(key, curr.left);
		} else if (comparator.compare(key, curr.key) > 0) {
			curr.right = deleteHelper(key, curr.right);
		} else {
			if (curr.left == null && curr.right == null) {
				return null;
			}
			if (curr.left == null) {
				return curr.right;
			}
			if (curr.right == null) {
				return curr.left;
			}
			curr.key = getMinimumHelper(curr.right).key;
			curr.value = getMinimumHelper(curr.right).value;
			curr.right = deleteHelper(getMinimumHelper(curr.right).key, curr.right);

		}
		return curr;
	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Callback is null");
		}
		processInorderHelper(root, callback);
	}

	private void processInorderHelper(Node curr, Callback<K, V> callback) {
		if (curr == null) {
			return;
		}
		processInorderHelper(curr.left, callback);
		callback.process(curr.key, curr.value);
		processInorderHelper(curr.right, callback);

	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
	    if (lowerLimit == null || upperLimit == null) {
	        throw new IllegalArgumentException("lowerLimit and upperLimit cannot be null.");
	    }

	    if (comparator.compare(lowerLimit, upperLimit) > 0) {
	        throw new IllegalArgumentException("lowerLimit cannot be greater than upperLimit.");
	    }

	    BinarySearchTree<K, V> subTree = new BinarySearchTree<>(comparator, maxEntries);
	    subTree.root = subTreeHelper(root, lowerLimit, upperLimit);
	    return subTree;
	}

	private Node subTreeHelper(Node node, K lowerLimit, K upperLimit) {
	    if (node == null) {
	        return null;
	    }
	    if (comparator.compare(lowerLimit, node.key) <= 0 && comparator.compare(upperLimit, node.key) >= 0) {
	        Node newNode = new Node(node.key, node.value);
	        newNode.left = subTreeHelper(node.left, lowerLimit, upperLimit);
	        newNode.right = subTreeHelper(node.right, lowerLimit, upperLimit);
	        return newNode;
	    } else if (comparator.compare(lowerLimit, node.key) < 0) {
	        return subTreeHelper(node.left, lowerLimit, upperLimit);
	    } else {
	        return subTreeHelper(node.right, lowerLimit, upperLimit);
	    }
	}
	

	public TreeSet<V> getLeavesValues() {
	    TreeSet<V> leafValues = new TreeSet<V>();
	    if(root == null) {
	    	return leafValues;
	    }
	    getLeavesHelper(root, leafValues);
	    return leafValues;
	}

	private void getLeavesHelper(Node curr, TreeSet<V> t){
		if (curr.left != null) {
			getLeavesHelper(curr.left, t);
		}
		if (curr.left == null && curr.right == null) {
			t.add(curr.value);
		}
	    if (curr.right != null) {
	    	getLeavesHelper(curr.right, t);   
		}
	}
}

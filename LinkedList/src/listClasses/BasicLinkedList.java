package listClasses;

import java.util.*;

public class BasicLinkedList<T> implements Iterable<T> {

	/* Node definition */
	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}
	/* We have both head and tail */
	protected Node head, tail;
	
	/* size */
	protected int listSize;

	public BasicLinkedList() {
		head = null;
		tail = null;
		listSize = 0;
	}

	public int getSize() {
		return listSize;
	}

	public BasicLinkedList<T> addToEnd(T data) {
		Node node = new Node(data);
		if (head != null) {
			tail.next = node;
			tail = node;
			listSize++;
		} else {
			head = node;
			tail = node;
			listSize++;
		}
		return this;
	}

	public BasicLinkedList<T> addToFront(T data) {
		Node node = new Node(data);
		if (head != null) {
			node.next = head;
			head = node;
		} else {
			head = node;
		}
		listSize++;
		return this;
	}

	public T getFirst() {
		return (listSize == 0) ? null : head.data;
	}

	public T getLast() {
		return (listSize == 0) ? null : tail.data;
	}

	public T retrieveFirstElement() {
		if (listSize == 0) {
			return null;
		} else {
			T info = head.data;
			if (listSize == 1) {
				listSize--;
				head = null;
			} else {
				listSize--;
				head = head.next;
			}
			return info;
		}
	}

	public T retrieveLastElement() {
		if (listSize == 0) {
			return null;
		} else {
			if (listSize == 1) {
				T info = head.data;
				head = null;
				tail = null;
				listSize--;
				return info;
			} else {
				Node node = head;
				T info = tail.data;
				while (node.next.next != null) {
					node = node.next;
				}
				tail = node;
				node = null;
				listSize--;
				return info;
			}
		}

	}

	public BasicLinkedList<T> remove(T targetData, Comparator<T> comparator) {
		if (listSize == 0) {
			return this;
		}
		Node node = head;
		Node before = null;

		while (node != null) {
			if (comparator.compare(node.data, targetData) == 0) {
				if (before == null) {
					head = head.next;
				} else {
					before.next = node.next;
				}
				listSize--;
				node = before != null ? before.next : head;
				continue;
			}

			before = node;
			node = node.next;
		}
		return this;
	}

	public ArrayList<T> getReverseArrayList() {
		ArrayList<T> list = new ArrayList<>();
		reverseArrayList(head, list);
		return list;
	}

	private void reverseArrayList(Node node, ArrayList<T> list) {
		if (node == null) {
			return;
		}
		reverseArrayList(node.next, list);
		list.add(node.data);
	}

	public BasicLinkedList<T> getReverseList() {
		BasicLinkedList<T> reversedList = new BasicLinkedList<>();
		reverseList(head, reversedList);
		return reversedList;
	}

	private void reverseList(Node node, BasicLinkedList<T> list) {
		if (node == null) {
			return;
		}
		reverseList(node.next, list);
		list.addToEnd(node.data);
	}


	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node node = head;

			public boolean hasNext() {
				if (node == null) {
					return false;
				} else {
					return true;
				}
			}

			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				T info = node.data;
				node = node.next;
				return info;
			}
		};
	}
	
	

}
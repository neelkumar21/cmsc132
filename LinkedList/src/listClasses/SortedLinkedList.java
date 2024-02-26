package listClasses;

import java.util.*;

/**
 * Implements a generic sorted list using a provided Comparator. It extends
 * BasicLinkedList class.
 * 
 * @author Dept of Computer Science, UMCP
 * 
 */

public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> comparator;

	public SortedLinkedList(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	public SortedLinkedList<T> add(T element) {
		listSize++;
		Node node = head;
		Node add = new Node(element);

		if (head == null) {
			head = add;
			tail = add;
		} else {
			if (comparator.compare(element, head.data) < 0) {
				add.next = head;
				head = add;
				return this;
			}
			while (node != null) {
				if (node.next == null) {
					node.next = add;
					tail = add;
					break;
				} else if (comparator.compare(element, node.data) == 0) {
					add.next = node.next;
					node.next = add;
					break;
				} else if (comparator.compare(element, node.data) > 0) {
					if (node.next == null) {
						tail.next = add;
						tail = add;
						break;
					} else if (comparator.compare(element, node.next.data) < 0) {
						add.next = node.next;
						node.next = add;
						break;
					}
				}
				node = node.next;
			}
		}
		return this;
	}

	public SortedLinkedList<T> remove(T targetData) {
		super.remove(targetData, comparator);
		return this;
	}

	@Override
	public BasicLinkedList<T> addToEnd(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}

	@Override
	public BasicLinkedList<T> addToFront(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}

}
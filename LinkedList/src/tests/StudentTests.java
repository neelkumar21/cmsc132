package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;

/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {
	BasicLinkedList bList = new BasicLinkedList();
	Comparator<String> comparator = String::compareToIgnoreCase;
	SortedLinkedList<String> sList = new SortedLinkedList<>(comparator);
	
	@Before
	public void createList() {
		bList.addToEnd("messi");
		bList.addToEnd("ronaldo");
		bList.addToEnd("saka");
		bList.addToEnd("neymar");
		sList.add("football");
		sList.add("tennis");
		sList.add("basketball");
		sList.add("baseball");
	}
	@Test
	public void basicGetSize() {
		assertEquals(4, bList.getSize());
	}
	
	@Test
	public void basicAddToEnd() {
		bList.addToEnd("Lionel");
		assertEquals(5, bList.getSize());
		assertEquals("Lionel", bList.getLast());
	}
	
	@Test
	public void basicAddToFront() {
		bList.addToFront("soccer");
		assertEquals(5, bList.getSize());
		assertEquals("soccer", bList.getFirst());
	}
	
    @Test
    public void basicGetFirst() {
        assertEquals("messi", bList.getFirst());
    }

    @Test
    public void basicGetLast() {
        assertEquals("neymar", bList.getLast());
    }
    
    @Test
    public void basicRetrieveFirstElement() {
        assertEquals("messi", bList.retrieveFirstElement());
        assertEquals(3, bList.getSize());
    }

    @Test
    public void basicRetrieveLastElement() {
        assertEquals("neymar", bList.retrieveLastElement());
        assertEquals(3, bList.getSize());
    }
    
    @Test
    public void basicRemove() {
    	bList.remove("messi",comparator);
    	assertEquals(3, bList.getSize());
    }
    
    @Test
    public void basicGetReverseArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("neymar");
        list.add("saka");
        list.add("ronaldo");
        list.add("messi");
        assertEquals(list, bList.getReverseArrayList());
    }
    
    @Test
    public void basicIterator() {
        Iterator<String> iterator = bList.iterator();
        assertEquals("messi", iterator.next());
        assertEquals("ronaldo", iterator.next());
        assertEquals("saka", iterator.next());
        assertEquals("neymar", iterator.next());
    }
    
    @Test
    public void sortedAdd() {
        sList.add("soccer");
        assertEquals(5, sList.getSize());
        String string = "";
        for(String sport: sList) {
        	string += sport;
        }
        assertEquals(string, "baseballbasketballfootballsoccertennis");
    }
    
    @Test
    public void sortedRemove() {
    	sList.remove("baseball");
    	sList.remove("tennis");
    	assertEquals(2, sList.getSize());
        assertEquals("basketball", sList.getFirst());
        String string = "";
        for(String sport: sList) {
        	string += sport;
        }
        assertEquals(string, "basketballfootball");
    }
    
    @Test
    public void addToEnd() {
    	boolean failed = false;
    	try{
    		sList.addToEnd("swimming");
    		failed = false;
    	}
    	catch(UnsupportedOperationException exception) {
    		failed = true;
    	}
    	assertEquals(failed, true);
    }
    
    @Test
    public void addToFront() {
    	boolean failed = false;
    	try{
    		sList.addToFront("swimming");
    		failed = false;
    	}
    	catch(UnsupportedOperationException exception) {
    		failed = true;
    	}
    	assertEquals(failed, true);
    }
}

package model;

import java.util.ArrayList;

/**
 * Represents the &lt;ul&gt and the &lt;ol&gt; tags. An ArrayList is used to
 * keep track of the Element objects of the list.
 * 
 * @author UMCP
 *
 */
public class ListElement extends TagElement {
	private ArrayList<Element> items;
	boolean ordered;

	public ListElement(boolean ordered, String attributes) {
		super(isOrdered(ordered), true, null, attributes);
		items = new ArrayList<>();
		this.ordered = ordered;
	}
	
	public void addItem(Element item) {
		items.add(item);
	}

	public static String isOrdered(boolean ordered) {
		if(ordered) {
			return "ol";
		}
		return "ul";
	}
	
	public String genHTML(int indentation) {
		String html = getStartTag() + ">";
		for(int i = 0; i < items.size(); i ++) {
			html += "\n" + "<li>" + "\n" + items.get(i).genHTML(indentation) + "</li>";
			
		}
		return html + "\n </" + isOrdered(ordered) + ">";
	}
}

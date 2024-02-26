package model;

import java.util.*;

/**
 * Represents a web page. Web page elements are stored in an ArrayList of
 * Element objects. A title is associated with every page. This class implements
 * the Comparable interface. Pages will be compared based on the title.
 * 
 * @author UMCP
 *
 */
public class WebPage implements Comparable<WebPage> {
	private ArrayList<Element> elements;
	private String title;
	private int list;
	private int paragraph;
	private int table;
	private double utility;

	public WebPage(String title) {
		this.title = title;
		elements = new ArrayList<>();
	}
	
	public int addElement(Element element) {
		elements.add(element);
		if(element instanceof TagElement) {
			return ((TagElement) element).getId();
		}
		return -1;
	}
	
	public String getWebPageHTML(int indentation) {
		String html = "<!doctype html> \n <html> \n  <head>\n";
		html += "<meta charset=\"utf-8\"/>\n   <title>" + title + "</title>\n  </head>\n  <body>\n";
		for(int i = 0; i < elements.size(); i ++) {
			html += elements.get(i).genHTML(indentation);
		}
		return html + "\n</body>\n</html>";
	}
	
	public void writeToFile(String filename, int indentation) {
		Utilities.writeToFile(filename, getWebPageHTML(indentation));
	}
	
	public Element findElem(int id) {
		for(int i = 0; i < elements.size(); i++) {
			if(id == ((TagElement) elements.get(i)).getId()) {
				return elements.get(i);
			}
		}
		return null;
	}
	
	public String stats() {
		String stats = "";
		for(int i = 0; i < elements.size(); i ++) {
			if(elements.get(i) instanceof ListElement) {
				list ++;
			}
			else if(elements.get(i) instanceof ParagraphElement) {
				paragraph ++;
			}
			else if(elements.get(i) instanceof TableElement) {
				table ++;
				utility += ((TableElement) elements.get(i)).getTableUtilization();
			}
		}
		stats += "List Count: " + list;
		stats += "\nParagraph Count: " + paragraph;
		stats += "\nTable Count: " + table;
		stats += "\nTableElement Utilization: " + utility/table;
		return stats;
	}
	
	public static void enableId(boolean choice) {
		TagElement.enableId(choice);
	}
	public int compareTo(WebPage o) {
		return this.title.compareTo(o.title);
	}

}

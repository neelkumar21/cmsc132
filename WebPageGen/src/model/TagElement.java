package model;

/**
 * 
 * Represents an HTML tag element (<&lt;p&gt;, &lt;ul&gt;, etc.). Each tag has
 * an id (ids start at 1). By default the start tag will have an id (e.g.,
 * <&lt;p id="a1"&gt;&lt;/p&gt;) when the HTML for the tag is generated. This
 * can be disabled by using enableId.
 * 
 * @author UMCP
 *
 */
public class TagElement implements Element {
	String tagName;
	boolean endTag;
	Element content;
	String attributes;
	static int id = 0;
	static boolean enabled;
	private int idcounter = 0;

	public TagElement(String tagName, boolean endTag, Element content, String attributes) {
		this.tagName = tagName;
		this.endTag = endTag;
		this.content = content;
		this.attributes = attributes;
		idcounter = id;
		id++;

	}

	public int getId() {
		return idcounter;
	}

	public String getStringId() {
		if(enabled == false) {
			return "";
		}
		return "id=" + "\"" + tagName + idcounter + "\"";
	}

	public String getStartTag() {
		if(attributes == null) {
			if(getStringId().equals("")) {
				return "<" + tagName;
			}
			return "<" + tagName + getStringId(); 
		} 
		if(getStringId().equals("")) {
			return "<" + tagName + attributes;
		}
		return "<" + tagName + getStringId() + attributes;
	}

	public String getEndTag() {
		if(endTag) {
			return "</"+ tagName+">";
		}
		return "";
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public static void resetIds() {
		id = 1;
	}

	public static void enableId(boolean choice) {
		enabled = choice;
	}

	public String genHTML(int indentation) {
		if(content== null) {
			return getStartTag() + ">" + getEndTag();
		}
		return getStartTag() + ">" + content.genHTML(indentation) + getEndTag();
	}

}
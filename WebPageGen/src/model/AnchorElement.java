package model;

/**
 * Represents the &lt;a&gt; tag.
 * 
 * @author UMCP
 *
 */
public class AnchorElement extends TagElement {
	private String linkText;
	private String url;
	private String attributes;
	
	public AnchorElement(String url, String linkText, String attributes) {
		super("a", true, null, (attributes == null )? "" : attributes);
		this.linkText = linkText;
		this.url = url;
		this.attributes = attributes;
	}
	
	public String getLinkText() {
		return linkText;
	}
	
	public String getUrlText() {
		return url;
	}
	
	public String genHTML(int indentation) {
		if(attributes == null) {
			return "<" + tagName + " href=" + "\"" + url + "\"" + ">" + linkText + "<" +  "/" + tagName + ">";
		}
		return "<" + tagName + " href=" + "\"" + url + "\"" + attributes + ">" + linkText + "<" +  "/" + tagName + ">";
	}


}

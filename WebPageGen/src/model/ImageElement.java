package model;

/**
 * Represents an &lt;img&gt; tag. For this project you can assume we will not
 * update any of the attributes associated with this tag.
 * 
 * @author UMCP
 *
 */
public class ImageElement extends TagElement {
	private String imageURL, alt, attributes;
	private int width, height;
	
	public ImageElement(String imageURL, int width, int height, String alt, String attributes) {
		super("img", false, null, attributes);
		this.imageURL = imageURL;
		this.width = width;
		this.height = height;
		this.alt = alt;
		this.attributes = attributes;
		
	}

	public String getImageURL() {
		return imageURL;
	}
	
	public String genHTML(int indentation) {
		return getStartTag() + " src = \"" + imageURL + "\" " + "width=" + "\"" + width + "\" " + "height=" + "\"" + height + "\" " + "alt=" + "\"" + alt + "\"" + ">";
	}

}

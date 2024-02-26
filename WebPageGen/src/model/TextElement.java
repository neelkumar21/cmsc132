package model;

/**
 * 
 * Represents text that can appear in an HTML document
 * @author UMCP
 *
 */
public class TextElement implements Element {
	private String text;
	
	public TextElement(String text) {
		this.text = text;
	}

	
	public String genHTML(int indentation) {
		String html = "";
		for(int i = 0; i < indentation; i ++) {
			html += " ";
		}
		html += text;
		return html;
	}
	
}

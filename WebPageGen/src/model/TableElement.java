package model;

/**
 * Represents the &lt;table&gt tag. A two dimensional array is used to keep
 * track of the Element objects of table.
 * 
 * @author UMCP
 *
 */
public class TableElement extends TagElement {
	private Element[][] items;
	int rows;
	int cols;
	String attributes;

	public TableElement(int rows, int cols, String attributes) {
		super("table", true, null, attributes);
		this.rows = rows;
		this.cols = cols;
		this.attributes = attributes;
		items = new Element[rows][cols];

	}

	public void addItem(int rowIndex, int colIndex, Element item) {
		items[rowIndex][colIndex] = item;
	}

	public double getTableUtilization() {
		double toReturn = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (items[i][j] == null) {
					toReturn += 0;
				} else {
					toReturn++;
				}
			}
		}
		toReturn = toReturn * 100.00 / (rows * cols);
		return toReturn;
	}

	public String genHTML(int indentation) {
		String html = getStartTag() + ">\n";
		for (int i = 0; i < rows; i++) {
			html += "<tr>";
			for (int j = 0; j < cols; j++) {
				if (items[i][j] != null) {
					html += "<td>" + items[i][j].genHTML(indentation) + "</td>";
				} else {
					html += "<td> </td>";
				}

			}
			html+= "</tr>" + "\n";
		}
		html += "</table> \n";
		return html;

	}

}

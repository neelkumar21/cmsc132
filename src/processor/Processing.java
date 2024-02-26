package processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Processing {
	HashMap<String, Double> itemsMap = new HashMap<String, Double>();
	TreeMap<Integer, TreeMap<String, Integer>> ordersMap = new TreeMap<Integer, TreeMap<String, Integer>>();

//	public void run () {
//		
//	}
	public String rString() {
		StringBuilder sb = new StringBuilder();
		TreeMap<String, Integer> itemQuantityMap = new TreeMap<>();

		for (int clientId : ordersMap.keySet()) {
			double totalCost = 0;
			TreeMap<String, Integer> orderDetails = ordersMap.get(clientId);
			sb.append("----- Order details for client with Id: ").append(clientId).append(" -----\n");

			for (String itemName : orderDetails.keySet()) {
				int quantity = orderDetails.get(itemName);
				double costPerItem = itemsMap.get(itemName);
				double itemTotalCost = costPerItem * quantity;
				totalCost += itemTotalCost;

				sb.append("Item's name: ").append(itemName).append(", Cost per item: ")
						.append(NumberFormat.getCurrencyInstance().format(costPerItem)).append(", Quantity: ")
						.append(quantity).append(", Cost: ")
						.append(NumberFormat.getCurrencyInstance().format(itemTotalCost)).append("\n");

				itemQuantityMap.put(itemName, itemQuantityMap.getOrDefault(itemName, 0) + quantity);
			}

			sb.append("Order Total: ").append(NumberFormat.getCurrencyInstance().format(totalCost))
					.append("\n");
		}

		sb.append("***** Summary of all orders *****\n");
		double grandTotalCost = 0;

		for (String itemName : itemQuantityMap.keySet()) {
			int itemQuantity = itemQuantityMap.get(itemName);
			double costPerItem = itemsMap.get(itemName);
			double itemTotalCost = costPerItem * itemQuantity;
			grandTotalCost += itemTotalCost;

			sb.append("Summary - Item's name: ").append(itemName).append(", Cost per item: ")
					.append(NumberFormat.getCurrencyInstance().format(costPerItem)).append(", Number sold: ")
					.append(itemQuantity).append(", Item's Total: ")
					.append(NumberFormat.getCurrencyInstance().format(itemTotalCost)).append("\n");
		}

		sb.append("Summary Grand Total: ").append(NumberFormat.getCurrencyInstance().format(grandTotalCost))
				.append("\n");

		return sb.toString();
	}

	public void rOrder(String orderFile) {

		File file = new File(orderFile);
		ArrayList<String> items = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(file);
			String line = scanner.nextLine();
			System.out.println("Reading order for " + line.substring(line.indexOf(" ") + 1));
			while (scanner.hasNextLine()) {
				String item = scanner.nextLine();
				items.add(item.substring(0, item.indexOf(" ")));
			}
			scanner.close();
			Collections.sort(items);
			TreeMap<String, Integer> orderedItemsMap = new TreeMap<String, Integer>();
			for (String item : items) {
				int count = 0;
				for (Object obj : items) {
				    if (obj.equals(item)) {
				        count++;
				    }
				}
				orderedItemsMap.put(item, count);
			}
			int i = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
			synchronized(this) {
				ordersMap.put(i, orderedItemsMap);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void rItem(String dataFile) {
		try {
			File file = new File(dataFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				itemsMap.put(line.substring(0,line.indexOf(" ")), Double.parseDouble(line.substring(line.indexOf(" "))));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
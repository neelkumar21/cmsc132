package processor;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class OrdersProcessor {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter item's data file name: ");
		String dataFileName = scanner.nextLine();

		System.out.print("Enter 'y' for multiple threads, any other character otherwise: ");
		String useMultipleThreads = scanner.nextLine();

		System.out.print("Enter number of orders to process: ");
		int numOrders = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Enter order's base filename: ");
		String orderBaseName = scanner.nextLine();

		System.out.print("Enter result's filename: ");
		String resultFileName = scanner.nextLine();
		scanner.close();
		long startTime = System.currentTimeMillis();
		long endTime = 0;

		if (!useMultipleThreads.equals("y")) {
			Processing itemOrders = new Processing();
			itemOrders.rItem(dataFileName);

			for (int i = 1; i <= numOrders; i++) {
				String orderFileName = orderBaseName + i + ".txt";
				itemOrders.rOrder(orderFileName);
			}
			try {
				FileWriter fw = new FileWriter(resultFileName);
				fw.write(itemOrders.rString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			endTime = System.currentTimeMillis();
		} else if(useMultipleThreads.equals("y")){
			Processing itemOrders = new Processing();
			itemOrders.rItem(dataFileName);
			Thread[] threads = new Thread[numOrders];
			for (int i = 0; i < numOrders; i++) {
				ThreadStarter t = new ThreadStarter(itemOrders, orderBaseName + (i + 1) + ".txt");
				Thread t1 = new Thread(t);
				t1.start();
				threads[i] = t1;
			}
			for (Thread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				FileWriter fw = new FileWriter(resultFileName);
				fw.write(itemOrders.rString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			endTime = System.currentTimeMillis();
		}

		System.out.println("Processing time (msec): " + (endTime - startTime));
		System.out.println("Results can be found in the file: " + resultFileName);
	}
}
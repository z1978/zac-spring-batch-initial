package com.zac.batch.sample.process.bar;

public class CeshiProcessBar {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		double ceshi = 0;
		double b = Math.random() * 10000;
		int a = (int) b;
		for (int i = 0; i < 5; i++) {
			System.out.println(a + ":");
			for (int j = 0; j <= a;) {
				ceshi = Math.random() * 100;
				if (ceshi > 99.99) {
					j++;
					ProcessBar.processbarshow(j, a);
					// if(j>6000)
					// break;
				}
			}
			b = Math.random() * 10000;
			a = (int) b;
		}
	}
}

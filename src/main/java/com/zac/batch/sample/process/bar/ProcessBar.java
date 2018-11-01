package com.zac.batch.sample.process.bar;

public class ProcessBar {
	/**
	 * 显示一个进度条
	 */
	private static int count = 1;
	private static boolean isStart = false;

	public static void processbarshow(int num, int total) {
		/**
		 * 总共显示100个
		 * ____________________________________________________________________________________________________
		 * ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
		 */ 
		int process = num * 100 / total;
		if (!isStart) {
			System.out.println("____________________________________________________________________________________________________");
			isStart = true;
		}
		if (count > 100) {
			count = 1;
			isStart = false;
		}
		if (process == count) {
			System.out.print("■");
			count++;
		}
		if (process == 100) {
			System.out.println();
		}
	}
}
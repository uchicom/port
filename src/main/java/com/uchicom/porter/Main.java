// (c) 2017 uchicom
package com.uchicom.porter;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 2 || args.length == 3 || args.length == 4) {
			Porter porter = new Porter();
			if (args.length == 2) {
				porter.execute(args[0], Integer.parseInt(args[1]));
			} else if (args.length == 3) {
				porter.execute( Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]));
			} else {
				porter.execute(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
			}
		}

	}

}

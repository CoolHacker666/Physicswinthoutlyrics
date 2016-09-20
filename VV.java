package olymp;

import java.util.Scanner;

public class VV {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		double n = in.nextDouble(), m = in.nextDouble();
		String l = "", r = "";
		for (int i = 0; i < n; i++) {
			if (m % 3 == 0) {
				m /= 3;
				continue;
			} else if ((m - 1) % 3 == 0) {
				r += (" " + (int) Math.pow(3, i));
				m = (m - 1) / 3;
				continue;
			} else {
				l += (" " + (int) Math.pow(3, i));
				m = (m + 1) / 3;
				continue;
			}
		}
		if (m > 0) {
			System.out.println("Нет решений");
			return;
		}
		System.out.println("L" + l);
		System.out.println("R" + r);
	}

}
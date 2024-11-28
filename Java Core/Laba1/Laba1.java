import static java.lang.Math.*;
public class Laba1 {
    public static void main(String[] args) {
        long[] c = new long[16];
        for (int i = 0; i < 16; i++) {
            c[i] = (long) (i + 1);
        }
        float[] x = new float[15];
        for (int i = 0; i < 15; i++) {
            x[i] = (float) ((random() * 5) - 3);
        }
        double[][] d = new double[16][15];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 15; j++) {
				switch ((int)c[i]) {
					case 1:
					case 3:
					case 5:
					case 7:
					case 9:
					case 10:
					case 14:
					case 15:
						d[i][j] = asin(1 / (pow(E, pow(pow(sin(x[j]), 2), 1 / 2))));
						break;
					case 11:
						d[i][j] = sin(pow(((2 / (3 - x[j])) / x[j]), 3));
						break;
					default:
						d[i][j] = pow(E, sin(atan(pow((x[j] - (1 / 2)) / 5, 2))));
				}
            }
        }
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 15; j++) {
				if (d[i][j] > 0) {
					String format = String.format("       %7.5f", d[i][j]);
					System.out.print(format);
				} else {
					String format = String.format("      %7.5f", d[i][j]);
					System.out.print(format);
				}
			}
			System.out.println();
		}
    }
}

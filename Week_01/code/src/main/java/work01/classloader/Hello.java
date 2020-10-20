package work01.classloader;

public class Hello {

    public static void main(String[] args) {
        int a = 10;
        int b = 2;
        int c = a + b;
        int d = a - b;
        int e = a * b;
        int f = a / b;
        int g = 0;
        int h = 0;
        for (int i = 0; i < 10; i++) {
            g = b +i;
            System.out.println(a + i);
        }
        if (g > 10) {
            h = g;
            System.out.println(h);
        }
    }
}
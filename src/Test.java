import java.util.Scanner;

public class Test {
        public static void main(String[] args) {
            String s = (new Scanner(System.in)).nextLine();
            int y = (int)((s.charAt(1) - 48) * 10 + s.charAt(2) - 49);
            System.out.println(y);
        }

}

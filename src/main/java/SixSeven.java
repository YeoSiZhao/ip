import java.util.Scanner;

public class SixSeven {
    public static void main(String[] args) {
        String name = "SixSeven";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");

        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        if (line.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
        }
        else {
            System.out.println(line);
        }
    }
}


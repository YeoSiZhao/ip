import java.util.Scanner;

public class SixSeven {

    private static String[] items = new String[100];
    private static int itemCount = 0;

    public static void main(String[] args) {
        String name = "SixSeven";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");

        while (true) {
            String line;
            Scanner in = new Scanner(System.in);
            line = in.nextLine();
            if (line.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (line.equals("list")) {
                listItem();
            } else {
                addItem(line);
            }
        }
    }

    public static void addItem(String item) {
        if (item == null || item.isEmpty()) {
            System.out.println("Invalid string. Try again");
            return;
        }
        if (itemCount < 100) {
            items[itemCount] = item;
            itemCount++;
            System.out.println("added: " + item);
        } else {
            System.out.println("Max capacity has reached");
        }
    }

    public static void listItem() {
        if (itemCount == 0) {
            System.out.println("No item available");
        } else {
            for (int i = 0; i < itemCount; i++) {
                System.out.println(i+1 + ". " + items[i]);
            }
        }
    }
}


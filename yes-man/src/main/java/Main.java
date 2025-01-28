import java.util.Objects;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    while (true) {
      System.out.println("Yes or No?");
      String input = scan.nextLine();
      if (input.equalsIgnoreCase("yes")) {

      } else if (input.equalsIgnoreCase("no")) {
        break;
      }
    }
  }

}

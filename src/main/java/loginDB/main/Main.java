package loginDB.main;

import loginDB.manager.DBManager;
import loginDB.model.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to my program. Here you can manage database of users:");
        while(true) {
            System.out.println("\n1 - check all users\n2 - add user\n3 - delete user" +
                    "\n4 - get user\n5 - exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    dbManager.findAllUsers().forEach(System.out::println);
                    break;
                case 2:
                    dbManager.addUser(getUserFromKeyBoard(scanner));
                    break;
                case 3:
                    String[] data = getLoginAndPasswordFromKeyBoard(scanner);
                    if(!dbManager.deleteUser(data[0], data[1])) {
                        System.err.println("No such user!");
                    }
                    break;
                case 4:
                    System.out.println(dbManager.getUser(getUserLoginFromKeyBoard(scanner)));
                    break;
                case 5:
                    dbManager.shutdown();
                    return;
            }
        }
    }

    private static User getUserFromKeyBoard(Scanner scanner) {
        String login;
        String password;
        String email;
        scanner.nextLine();
        System.out.print("Enter login: ");
        login = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();

        System.out.print("Enter email: ");
        email = scanner.nextLine();

        return User.createUser(login, password, email);
    }

    private static String getUserLoginFromKeyBoard(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter login of user: ");
        String login = scanner.nextLine();
        return login;
    }

    private static String[] getLoginAndPasswordFromKeyBoard(Scanner scanner) {
        scanner.nextLine();
        String[] data = new String[2];
        System.out.print("Enter login: ");
        data[0] = scanner.nextLine();
        System.out.print("Enter password: ");
        data[1] = scanner.nextLine();
        return data;
    }
}

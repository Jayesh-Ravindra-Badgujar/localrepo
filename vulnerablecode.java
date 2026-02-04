import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.security.MessageDigest;

public class VulnerableApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String userInput = scanner.nextLine();

        try {

            // ❌ Hardcoded database credentials (real-world critical issue)
            String dbUser = "root";
            String dbPassword = "password123";
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb", dbUser, dbPassword);

            Statement stmt = conn.createStatement();

            // ❌ SQL Injection (OWASP A03:2021)
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            System.out.println("Executing query: " + query);
            stmt.executeQuery(query);

            // ❌ Weak hashing algorithm (MD5)
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("secretPassword".getBytes());
            byte[] digest = md.digest();
            System.out.println("Weak Hash: " + digest);

            // ❌ Logging sensitive info (API key leakage)
            System.out.println("DEBUG: API Key = XYZ-SECRET-KEY-12345");

        } catch (Exception e) {
            // ❌ Information disclosure (leaks stack trace)
            e.printStackTrace();
        }
    }
}

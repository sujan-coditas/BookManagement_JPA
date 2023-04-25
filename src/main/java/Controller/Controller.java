package Controller;

import Dao.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class Controller {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sujan");
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, ParseException {
        boolean flag = true;
        DaoImp daoImp=new DaoImp();
        while (flag) {
            System.out.println("Book Application");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book");
            System.out.println("4. Add Library");
            System.out.println("*************Queries**********");
            System.out.println("5. price > 400 ");
            System.out.println("6. group by publisher");
            System.out.println("7. Price range ");
            System.out.println("8. Books published between (date range) ");

            System.out.println("0. Quit");

            System.out.print("Enter your choice: ");
             int choice = Integer.parseInt(reader.readLine());


            switch (choice) {
                case 1:
                    daoImp.insert();
                    break;
                case 2:
                    daoImp.delete();
                    break;
                case 3:
                    daoImp.update();
                    break;
                case 4:
                    daoImp.insertLibrary();
                    break;
                case 5:
                    daoImp.greaterPrice();
                    break;
                case 6:
                    daoImp.groupByPublisher();
                    break;
                case 7:
                    daoImp.priceRange();
                    break;
                case 8:
                    daoImp.dateRange();
                    break;


                case 0:
                    emf.close();
                    flag=false;
                    System.out.println("Goodbye!");
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}

import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Scanner;



/**
 * Write a description of class Preferences here.
 *
 * @author Meetkumar Prajapati (000922812)
 * @version v100
 */
public class TableTestProgram
{
    public static void main(String[] args ) throws Exception {

        Scanner in = new Scanner(System.in);
        String tableName;
        String choice = "";
        int row = -1;
        String key = "";
        String s_colNum = "";
        String newValue = "";
        String target = "";

        System.out.print("Enter the name of the tab delimited text file you wish to manage (e.g. codes.txt) > ");
        tableName = in.next();
        Table t = new Table(tableName);
        System.out.println("Successfully loaded: " + t);


        while (1==1) {
            System.out.println("\n\nTable Testing Menu\n");

            System.out.println("1. Display all data");
            System.out.println("2. Lookup");
            System.out.println("3. Search");
            System.out.println("4. Change");
            System.out.println("5. Save data to " + tableName);
            System.out.println("6. Get Single Cell Value");
            System.out.println("7. Save Single Cell Value");
            System.out.println("8. Quit");
            System.out.print("Select > ");
            choice = in.nextLine();

            if(choice.equals("1"))t.display();

            else if (choice.equals("2")) {
                System.out.println("Enter the primary key> ");
                target = in.nextLine();
                int rowNum = t.lookup(target);
                if (rowNum == -1)
                    System.out.println(target + " not found.");
                else
                    System.out.println(target + " found at row number " + String.format("%d",rowNum));
            }
            else if (choice.equals("3")) {
                String [] str = new String[t.getNumCols()];
                System.out.println("Enter the primary key> ");
                target = in.nextLine();
                int rowNum = t.lookup(target);
                if (rowNum == -1)
                    System.out.println(target + " not found.");
                else
                    str = t.getMatches(target);
                for(int s= 0; s<str.length; s++)
                    System.out.printf("[%d] - %s\n",s,str[s]);
            }

            else if(choice.equals("4")){
                System.out.println("Enter row : ");
                int r = in.nextInt();
                in.nextLine();
                System.out.println("Enter column: ");
                int c = in.nextInt();
                in.nextLine();
                target = t.getSingleCellValue(r,c);
                System.out.println("your cell value:  " + target  );

                System.out.print("What value you would like to enter: ");
                String str = in.nextLine();
                target = str;
                System.out.println(target);

            }

            else if (choice.equals("5")) {
                t.updateFile();
                System.out.println("Your file has been saved.");
            }

            else if(choice.equals("6")){
                System.out.println("Enter the number of row.");
                int rowNum = in.nextInt();
                System.out.println("Enter the number of column.");
                int columnNum = in.nextInt();
                System.out.println(t.getSingleCellValue(rowNum+row,columnNum+row));
            }

            else if (choice.equals("7")) {
                System.out.println("Enter the number of row where you want to change the value of cell");
                int rowNum = in.nextInt();
                System.out.println("Enter the number of column where you want to change the value of cell");
                int columnNum = in.nextInt();
                System.out.println("Value at entered location is: "+t.getSingleCellValue(rowNum+row,columnNum+row));
                System.out.println("Enter the value you want to change");
                String changeValue;
                in.nextLine();
                changeValue = in.nextLine();
                t.setSingleCellValue(rowNum,columnNum,changeValue);
                t.updateFile();
                System.out.println("Your value has been changed.");
            }

            else if (choice.equals("8")){
                break;
            }


        }
        System.out.println("Thank-you, good bye!");

    }
}
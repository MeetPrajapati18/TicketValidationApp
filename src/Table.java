import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class manages anu tab delimited text file, e.g.  codes.txt
 *
 * @author Meet Prajapati (000922812)
 * @version v100
 */
public class Table
{ //class

    //instance variables
    private String tablename;
    private int numRows;
    private int numCols;
    private String filename;
    public String[][] grid;
    public String lab;



    /**
     *
     * Initialize the class with the name of the tab delimited text file you wish to manage.
     *
     * @param filename  the name of tab delimited text file.
     */
    public Table( String filename )
    { //table
        this.filename = filename;
        tablename = filename;
        numRows=0;
        numCols=0;
        String s;
        int r;
        String[] item;



        //Pass1:  Go through the text file in order to ascertain the
        //        numRows and numCols
        try {

            Scanner theFile = new Scanner(new FileInputStream(new File (tablename)));
            while ( theFile.hasNextLine() )
            {
                s = theFile.nextLine();
                item = s.split("\t", 0);


                if (item.length > numCols)
                    numCols = item.length;
                numRows++;
            }
            theFile.close();
        }
        catch (FileNotFoundException  e)
        {
            System.out.println("Table class Error 1: file not found.");
        }

        grid = new String[numRows][numCols];

        //Pass2:  populate the grid array
        try {

            Scanner theFile = new Scanner(new FileInputStream(new File (tablename)));
            r=0;
            while ( theFile.hasNextLine() )
            {
                s = theFile.nextLine();
                item = s.split("\t", 0);

                for(int c=0; c < numCols; c++) {

                    if ( item[c].length() == 0)
                        grid[r][c] = "";
                    else
                        grid[r][c] = item[c];
                }
                r++;

            }
            theFile.close();
        }
        catch (Exception e)
        {
            System.out.println("Table class error 2: file not found.");
        }


    } //table

    public void display(){
        for(int i=0; i<grid.length;i++){
            for(int j = 0; j < grid[i].length; j++){
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public int lookup(String key){
        for(int r=0; r< grid.length; r++){
            if(key.equals(grid[r][0]))
                return r;
        }
        return -1;
    }

    public String getSingleCellValue(int row,int col){
        if(row>=0 && row<numRows)
            if(col>=0 && col<numCols)
                return grid[row][col];
        return "";
    }

    public void setSingleCellValue(int row, int col, String newVal){
        if (row <= numRows && col <= numCols){
            this.grid[row][col] = newVal;
        }

    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public String[] getMatches(String key){
        String[] str = new String[numCols];
        int rowNum = lookup(key);
        if (rowNum == -1)
            return str;

        for(int c=0 ; c<numCols; c++)
            str[c] = grid[rowNum][c];
        return str;
    }

    public void updateFile() {
        try (FileWriter writer = new FileWriter(new File(filename))) {
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    writer.write(grid[r][c]);
                    if (c < numCols - 1)
                        writer.write("\t");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating file.");

        }
    }

    public String toString() {

        return ("Table: " + tablename + "  rows = " + numRows + "  cols = " + numCols);
    }

} //class




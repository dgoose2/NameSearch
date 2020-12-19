
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class BabyNames {
    private static String NAME = "";
    private static String GENDER = "";
    private static String OUTPUTLINE = "";
    private static String OUTPUTLINE2 = "";

    //class constants for open area in graphics (Do NOT change)
    private static final Integer OPEN_AREA_WIDTH = 780;
    private static final Integer OPEN_AREA_HEIGHT = 500;

    //prompt msg class constants (Do NOT change)
    private static final String MESSAGE_PREFIX = "This program allows you to search through the\n" +
            "data from the Social Security Administration\n" +
            "to see how popular a particular name has been\n" +
            "since ";

    // class constant for meaning file (Do NOT change)
    private static final String MEANING_FILENAME = "meanings.txt";

    // class constant for name file (Change the value only. Do NOT change the names of the constant)
    // Test with both "names.txt" and "names2.txt" (Before submission, change back to "names.txt")
    private static final String NAME_FILENAME = "names.txt";

    // Other class constants (Change the value only. Do NOT change the names of the constants)
    private static final Integer STARTING_YEAR = 1890; // change the value according to spec
    private static final Integer DECADE_WIDTH = 60; // change the value according to spec
    private static final Integer LEGEND_HEIGHT = 30; // change the value according to spec
    // YOU ARE NOT ALLOWED TO ADD ANY OTHER CONSTANTS THAN ABOVE

    // In the main method i kept it as clean as possible and only called what was neccesary to call
    public static void main(String[] args) throws FileNotFoundException {
        console();
        graph();
    }

    // In this method i combined all of the methods that dealt with the console so that i could make it more simple and just call one method into the main rather than three different methods
    private static void console() throws FileNotFoundException{
        getUserData();
        popularityFile();
        meaningFile();
    }

    // I created this method in order to ask the user for data from the console and then i stored the users unput into fields so that i could access these values later down in my code
    private static void getUserData(){
        System.out.println(MESSAGE_PREFIX + STARTING_YEAR);
        Scanner userInput = new Scanner(System.in);
        System.out.println();
        System.out.print("Name: ");
        String name = userInput.next();
        NAME = name;
        System.out.print("Gender (M or F): ");
        String gender = userInput.next();
        GENDER = gender;
    }

    // I created this method to take the users data and search through names.txt & names2.txt and then find the name if it is found it is then outputed
    private static void popularityFile() throws FileNotFoundException {
        File nameFile = new File(NAME_FILENAME);
        Scanner scan = new Scanner(nameFile);
        while (scan.hasNextLine()){
            String line = scan.nextLine();
            Scanner scanLine = new Scanner(line);
            if (scanLine.next().equalsIgnoreCase(NAME) && scanLine.next().equalsIgnoreCase(GENDER)){
                OUTPUTLINE = line;
                System.out.println(OUTPUTLINE);
            }
        }
    }

    // I created this method in order to test whether the OUTPUTLINE from popularity file had a value and if it did then it the scanned through the meanings.txt and then found the data and stored it in OUTPUTLINE2
    private static void meaningFile() throws FileNotFoundException{
        File meaningsFile = new File(MEANING_FILENAME);
        Scanner meanings = new Scanner(meaningsFile);
        if (OUTPUTLINE.equals("")){
            System.out.println("\"" + NAME + "\" not found.");
        }else{
            while (meanings.hasNextLine()){
                String line2 = meanings.nextLine();
                Scanner scanLine = new Scanner(line2);
                if (scanLine.next().equalsIgnoreCase(NAME) && scanLine.next().equalsIgnoreCase(GENDER)){
                    OUTPUTLINE2 = line2;
                    System.out.println(OUTPUTLINE2);
                }
            }
        }
    }

    // In this method I called all of the methods that went into graphing the graph in order to keep the main method and clean as possible
    // I also scanned through the OUTPUTLINE field that i set the popularity value equal to in order to skip past the first two strings and just have the number values
    private static void graph(){
        if(!OUTPUTLINE.equals("")) {
            Scanner test = new Scanner(OUTPUTLINE);
            test.next();
            OUTPUTLINE = OUTPUTLINE.substring(OUTPUTLINE.lastIndexOf(test.next()) + 1);
            DrawingPanel panel = initializePanel(OPEN_AREA_WIDTH, OPEN_AREA_HEIGHT + LEGEND_HEIGHT * 2, Color.white);
            Graphics g = panel.getGraphics();
            topBar(g, 0, 0, 2);
            bars(g);
        }
    }

    // In this method i initialized the drawing panel in order to have it have a set beckground and then i called this metthod into the panel method
    private static DrawingPanel initializePanel(int width, int height, Color byColor){
        DrawingPanel panel = new DrawingPanel(width, height);
        panel.setBackground(byColor);
        return panel;
    }

    // In this method i created a for loop that i used to draw the top and bottom gray bars and also the black lines
    private static void topBar(Graphics g, int x, int y, int numPairs){
        for (int i = 0; i < numPairs; i++){
            int yPos = (OPEN_AREA_HEIGHT + LEGEND_HEIGHT * 2);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, (yPos - LEGEND_HEIGHT) * i, OPEN_AREA_WIDTH, y + LEGEND_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawLine(x, (yPos - LEGEND_HEIGHT * numPairs) * i + LEGEND_HEIGHT, OPEN_AREA_WIDTH, (yPos - LEGEND_HEIGHT * numPairs) * i + LEGEND_HEIGHT);
        }
    }

    // In this method i created the green bars with the dates at the bottom of the graph as well as the popularity number on the top of the green bars
    // I first outputed the meaning on the top gray bar and then i created a while loop in order to test if there was a popularity number by using .next()
    // I then went on to create equations that i then called into my code in order to keep it cleaner as these equations were called many times
    private static void bars(Graphics g){
        Scanner search = new Scanner(OUTPUTLINE);
        int year = 0;
        g.setColor(Color.BLACK);
        g.drawString(OUTPUTLINE2, 0, 16);

        while(search.hasNext()){
            int ranking = search.nextInt();
            int xPos = year * DECADE_WIDTH;
            int yPos = LEGEND_HEIGHT + (ranking / 2);
            int width = DECADE_WIDTH / 2;

            g.setColor(Color.BLACK);
            g.drawString("" + (STARTING_YEAR + (year * 10)), xPos, OPEN_AREA_HEIGHT + (LEGEND_HEIGHT * 2) - 8);
            if(ranking > 0){
                g.setColor(Color.GREEN);
                g.fillRect(xPos, yPos, width, OPEN_AREA_HEIGHT - (ranking / 2));
                g.setColor(Color.BLACK);
                g.drawString("" + ranking, xPos, yPos);
            }else{
                g.drawString("" + ranking, xPos, OPEN_AREA_HEIGHT + LEGEND_HEIGHT);
                g.setColor(Color.GREEN);
                g.fillRect(xPos, yPos, width, 0);
            }
            year++;
        }
    }
}
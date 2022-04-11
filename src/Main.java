import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Reporter");
        while(true) {   //Infinity cycle of program (0 = quit)
            MonthlyReport monthlyReport = new MonthlyReport();
            YearReport yearReport = new YearReport();

            System.out.println("Type the command:");
            System.out.println("1. Load months reports");
            System.out.println("2. Load year report");
            System.out.println("3. Check reports");
            System.out.println("4. Show month reports info");
            System.out.println("5. Show year report info");

            Scanner scanner = new Scanner(System.in); // scan obeject
            int userInput = -1; // Number of command
            if (scanner.hasNextInt()){
                userInput = scanner.nextInt();
            } else {
                String quitcommand = scanner.next();
                quitcommand = quitcommand.toLowerCase(); //convert to Lower Case
                if (quitcommand.equals("quit")) { //password to quit (Only Lower Case use)
                    break;
                } else
                System.out.println("Incorrect command. Try again");
            }
            switch (userInput) {
                case 1:
                    System.out.println(" Command 1");
                    break;
                case 2:
                    System.out.println(" Command 2");
                    yearReport.readData(2020, "C:\\Users\\xUser\\IdeaProjects\\Sprint_1_task_2\\Reports");
                    break;
                case 3:
                    System.out.println(" Command 3");
                    break;
                case 4:
                    System.out.println(" Command 4");
                    break;
                case 5:
                    System.out.println(" Command 5");
                    break;
                default:
                    System.out.println("Incorrect number of command. Try again");
                    break;
            }
        }
    }
}



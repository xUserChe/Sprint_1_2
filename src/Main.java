import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Reporter");
        String pathPrj = "C:\\Users\\xUser\\IdeaProjects\\Sprint_1_task_2\\Reports"; //path with .csv files
        int yearOfReport = 2020; //Year to work
        boolean monthReportIsLoad = false;
        boolean yearReportIsLoad = false;
        MonthlyReport monthlyReport = new MonthlyReport();
        YearReport yearReport = new YearReport();

        while(true) {   //Infinity cycle of program (0 = quit)


            System.out.println("Type the command:");
            System.out.println("1. Load months reports");
            System.out.println("2. Load year report");
            System.out.println("3. Check reports");
            System.out.println("4. Show month reports info");
            System.out.println("5. Show year report info");

            Scanner scanner = new Scanner(System.in); // scan object
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
                    monthReportIsLoad = monthlyReport.readData(yearOfReport, pathPrj);
                    //System.out.println("Read consumption records: " + monthlyReport.consumption.size() + " and income: " + monthlyReport.income.size());
                    break;
                case 2:
                    System.out.println(" Command 2");
                    yearReportIsLoad = yearReport.readData(yearOfReport, pathPrj);
                    break;
                case 3:
                    if (monthReportIsLoad && yearReportIsLoad){
                        System.out.println(" Command 3. Checking Reports:");
                        //System.out.println("Consumption records: " + monthlyReport.consumption.size() + " and income: " + monthlyReport.income.size());
                        if (checkingDataReport(monthlyReport, yearReport) == 0) {
                            System.out.println("The data in the reports converge !");
                        }
                    } else {
                        System.out.println("Reports not loaded yet!");
                    }
                    break;
                case 4:
                    if (monthReportIsLoad && yearReportIsLoad){
                        System.out.println(" Command 4. Summarise monthly report.");
                        int j = 0;
                        for (int i = 0; i < monthlyReport.income.size(); i++) {
                            //System.out.println( " " + monthlyReport.income.size());
                            if (monthlyReport.reportByIndex(i)) {
                                j++;
                            }
                            if (j == 0) {
                                System.out.println("No any incomes and consumptions in reports");
                            }
                        }
                    } else {
                        System.out.println("Reports not loaded yet!");
                    }
                    break;
                case 5:
                    if (monthReportIsLoad && yearReportIsLoad){
                        System.out.println(" Command 5. Information about years report.");
                        System.out.println("Report year: " + yearOfReport);
                        for (int i = 0; i < monthlyReport.income.size(); i++) {
                            System.out.println(monthlyReport.printNameMonthByIndex(i)+ " : profit: " + (monthlyReport.sumMoneyIncome(i) - monthlyReport.sumMoneyConsumption(i)));

                        }
                        System.out.println("Average monthly consumption: " + monthlyReport.averageConsumption());
                        System.out.println("Average monthly income: " + monthlyReport.averageIncome());

                    } else {
                        System.out.println("Reports not loaded yet!");
                    }
                    break;
                default:
                    System.out.println("Incorrect number of command. Try again");
                    break;
            }
        }
    }
    private static int checkingDataReport(MonthlyReport mR, YearReport yR){
        int countDiscrepancy = 0;

        for(int i = 0; i < mR.consumption.size(); i++) { //numbers of records in List mR.consumption = mR.income
            double t = 0.0; //temporary sum of consumptions
            double p = 0.0; //temporary sum of income
            int j = mR.consumption.get(i).size();
            int k = mR.income.get(i).size();
            //System.out.println("c: " + j + "i" + k);
            if (j > 0) {
                t = mR.sumMoneyConsumption(i);
            }
            if (k > 0) {
                p = mR.sumMoneyIncome(i);
            }
            if (yR.consumption[i] != t ) {
                countDiscrepancy++;
                System.out.println("Month: " + (i+1) + " consumptions is different: Years report is " + yR.consumption[i] + " but Month report is " + t);
            }
            if (yR.income[i] != p ) {
                countDiscrepancy++;
                System.out.println("Month: " + (i+1) + " income is different: Years report is " + yR.income[i] + " but Month report is " + p);
            }
        }
        return countDiscrepancy;
    }
}



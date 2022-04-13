import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;


public class MonthlyReport {
ArrayList<HashMap<String,Double>> income = new ArrayList<>();
ArrayList<HashMap<String,Double>> consumption = new ArrayList<>();


    private String readFileContentsOrNull (String path) { //read the file
        try {
            return Files.readString(Path.of(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            //System.out.println("Error: File not found or Not correct structure!");
            return null;
        }
    }
    public boolean readData(Integer yearRead, String pathFile) {
        String[] files = new String[12];
        this.consumption.clear();
        this.income.clear();
        for (int i = 0; i <= 11; i++) {
            String monthInString;
            //month in path part forming
            if (i < 9) {
                monthInString = "0" + (i+1);
            } else {
                monthInString = String.valueOf(i+1);
            }
            //try to read the file
            //System.out.println(pathFile + "\\m." + yearRead.toString() + monthInString + ".csv");
            files[i] = readFileContentsOrNull((pathFile + "\\m." + yearRead.toString() + monthInString + ".csv"));
            if (files[i] != null) {
                System.out.println("Read year:" + yearRead + " month: " + monthInString + " is complete.");
                //parsing file
                String[] parseFilesToLines = files[i].split("\n");
                //HashMaps for incoming in this month
                HashMap<String,Double> thisMonthIncome = new HashMap<>();
                //HashMaps for consumption in this month
                HashMap<String,Double> thisMonthConsumption = new HashMap<>();
                if (parseFilesToLines.length > 1) {
                    //parsing all lines to temporary HashMaps
                    for (int j = 1; j < parseFilesToLines.length; j++){
                        String[] texts = parseFilesToLines[j].split(",");
                        //System.out.println("line number: " + i + ": " + texts[0] + " " + texts[1] + " " + texts[2]);
                        String itemName = texts[0];
                        boolean t = Boolean.parseBoolean(texts[1].replaceAll("[^\\p{L}]+",""));
                        double quantity = Double.parseDouble(texts[2]);
                        double sumOfOne = Double.parseDouble(texts[3]);
                        //System.out.println("line number: " + i + ": " + itemName + " " + quantity + " " + sumOfOne + " " + t);
                        if (t) {
                            thisMonthConsumption.put(itemName, (quantity*sumOfOne));
                        } else {
                            thisMonthIncome.put(itemName, (quantity*sumOfOne));
                        }
                    }
                    //Add temporary HashMaps to Object
                    this.income.add(i,thisMonthIncome);
                    this.consumption.add(i,thisMonthConsumption);
                    //System.out.println("Income have a " + this.income.size() + "records");
                    //System.out.println("Income have a " + this.consumption.size() + "records");
                }

            }
        }
        System.out.println("Load month reports complete");
        //System.out.println(income);
        //System.out.println(consumption);
        return (!(this.income.isEmpty() && this.consumption.isEmpty()));
        /*if(this.income.isEmpty() && this.consumption.isEmpty()){
            return false;
        } else {
            return true;
        }*/
    }
    // This method summarise consumptions by index of month
    public double sumMoneyConsumption (int i){
        //System.out.println(this.consumption);
        if (this.consumption.get(i).isEmpty()) {
            return 0.0;
        } else{
            double sumM = 0.0;
            for (Double d : this.consumption.get(i).values() ) {
                sumM += d;
            }
            return sumM;
        }
    }
    // This method summarise income by index of month
    public double sumMoneyIncome (int i){
        if (this.income.get(i).isEmpty()) {
            return 0.0;
        } else{
            double sumM = 0.0;
            for (Double d : this.income.get(i).values() ) {
                sumM += d;
            }
            return sumM;
        }
    }
    // method print name month + Max Income and Consumption
    public boolean reportByIndex(int i) {
        //System.out.println(" " + this.income.get(i).isEmpty());
        if (this.income.get(i).isEmpty()) {
            return false;
        } else {
            System.out.println(printNameMonthByIndex(i));
            double maxIncomeValue = Collections.max( this.income.get(i).values());
            for(Entry <String, Double> entry : this.income.get(i).entrySet()) {
                if (entry.getValue() == maxIncomeValue){
                    System.out.println("The " + entry.getKey() + " take max income: " + entry.getValue());
                }
            }
            double maxConsumptionValue = Collections.max( this.consumption.get(i).values());
            for(Entry <String, Double> entry : this.consumption.get(i).entrySet()) {
                if (entry.getValue() == maxConsumptionValue){
                    System.out.println("The " + entry.getKey() + " take max consumption: " + entry.getValue());
                }
            }
            return true;
        }
    }
    //method return average Income by month
    public double averageIncome() {
        if (this.income.size() > 0) {
            double result = 0.0;
            for (int i = 0; i < this.income.size(); i++) {
                result += this.sumMoneyIncome(i);
            }
            return (result / this.income.size());
        } else {
            return 0.0;
        }
    }
    //method return average Consumption by month
    public double averageConsumption() {
        if (this.consumption.size() > 0) {
            double result = 0.0;
            for (int i = 0; i < this.consumption.size(); i++) {
                result += this.sumMoneyConsumption(i);
            }
            return (result / this.consumption.size());
        } else {
            return 0.0;
        }
    }

    public String printNameMonthByIndex(int i) {
        String[] textMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return textMonth[i];

    }
}

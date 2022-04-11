import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class YearReport {
int year = 0;
double [] income = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
double [] consumption = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
double [] profit = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
double averageIncome;
double averageConsumption;

    private String readFileContentsOrNull (String path) { //read the file
        try {
            return Files.readString(Path.of(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error: File not found or Not correct structure!");
            return null;
        }
    }
    private void calcProfit() { // calculation of profit and averages
        for (int i = 0; i < 12; i++) {
            this.profit[i] = this.income[i] - this.consumption[i];
            System.out.println(this.profit[i]);
        }
    }

    public boolean readData(Integer yearRead, String pathFile) {
        String files  = readFileContentsOrNull((pathFile+ "\\y." + yearRead.toString() + ".csv"));
        if (files == null) {
            return false;
        } else {
            //System.out.println(files);
            String[] lines = files.split("\n");
            //System.out.println(lines[0]);
            System.out.println("Read: " + (lines.length - 1) + " lines.");
            if (lines.length > 1) {
                this.year = yearRead;   //save Year
                for (int i = 1; i < lines.length; i++) {
                    String[] texts = lines[i].split(",");
                    //System.out.println("line number: " + i + ": " + texts[0] + " " + texts[1] + " " + texts[2]);
                    int month = Integer.parseInt(texts[0]);
                    double money = Double.parseDouble(texts[1]);
                    boolean t = Boolean.parseBoolean(texts[2].replaceAll("[^\\p{L}]+",""));
                    System.out.println("line number: " + i + ": " + month + " " + money + " " + t);
                    if (t) {
                        consumption[month - 1] = money;
                    } else {
                        income[month - 1] = money;
                    }
                }
                System.out.println("File reading successful finished!");
                calcProfit();
            return true;
            } else {
            System.out.println("Error: File is empty!");
            return false;
            }
        }
    }
}

package utils_graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatasetParser {

    public static void main(String[] args) {

        DatasetParser dataparser = new DatasetParser();

        dataparser.parse();
    }

    public DatasetParser() {
    }

    public void parse() {
        String csvFile = "resources/Dataset_tarea1/data_Mar_64.txt";
        String line = "";

        ArrayList<String> classes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] leaf_sample_data = line.split(",");

                //System.out.println("Leaf [name= " + leaf_sample_data[0] + " , first record=" + leaf_sample_data[1] + "]");
                String leaf_name = leaf_sample_data[0];

                if (classes.contains(leaf_name) == false) {

                    classes.add(leaf_name);
                }


            }

            System.out.println("n: " + classes.size());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

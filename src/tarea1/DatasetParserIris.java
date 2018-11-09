package tarea1;

import neural_network.DataExample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DatasetParserIris {

    /**
     * Returns list of DataExample items for the iris types data set
     */
    public static ArrayList<DataExample> parseDataset(String fileName) {

        ArrayList<DataExample> dataset = new ArrayList<>();

        String csvFile = "resources/iris/" + fileName;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] record = line.split(",");

                DataExample dataExample = new DataExample();

                // Add inputs of the record to dataExample
                for (int c = 0; c < 4; c++) {
                    double btype = Double.parseDouble(record[c]);
                    dataExample.inputs.add(btype);
                }

                // Add output of the record to dataExample
                String iris_type = record[4];


                int typeindex;

                if (iris_type.equals("Iris-setosa")) {
                    typeindex = 0;
                } else if (iris_type.equals("Iris-versicolor")) {
                    typeindex = 1;
                } else if (iris_type.equals("Iris-virginica")) {
                    typeindex = 2;
                } else {
                    System.err.println("Class not identified while parsing: -" + iris_type + "-");
                    return null;
                }

                dataExample.desiredOutputs = new ArrayList<Double>(Collections.<Double>nCopies(3, (double) 0));
                dataExample.desiredOutputs.set(typeindex, (double) 1);

                // Add dataExample to dataset
                dataset.add(dataExample);

            }

            return dataset;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

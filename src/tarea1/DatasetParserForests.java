package tarea1;

import neural_network.DataExample;
import neural_network.Dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DatasetParserForests {

    /**
     * Returns list of DataExample items for the forests types data set
     */
    public static Dataset parseDataset(String fileName) {

        Dataset datasetForests = new Dataset();

        String csvFile = "resources/forests/" + fileName;
        String line;
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // Skip definitions line
                if(i++ == 0) {
                    continue;
                }

                // use comma as separator
                String[] record = line.split(",");

                DataExample dataExample = new DataExample();

                // Add inputs of the record to dataExample
                for (int c = 1; c < 10; c++) {
                    double btype = Double.parseDouble(record[c]);
                    dataExample.inputs.add(btype);
                }

                // Add output of the record to dataExample
                String forest_type = record[0];

                //**
                // s = 0, d = 1, h = 2, o = 3
                // */

                int typeindex;

                switch (forest_type) {
                    case "s ":
                        typeindex = 0;
                        break;

                    case "d ":
                        typeindex = 1;
                        break;

                    case "h ":
                        typeindex = 2;
                        break;

                    case "o ":
                        typeindex = 3;
                        break;

                    default:
                        System.err.println("Class not identified while parsing: -" + forest_type + "-");
                        return null;
                }

                dataExample.desiredOutputs = new ArrayList<Double>(Collections.<Double>nCopies(4, (double) 0));
                dataExample.desiredOutputs.set(typeindex, (double) 1);

                // Add dataExample to dataset
                datasetForests.examples.add(dataExample);

            }

            return datasetForests;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

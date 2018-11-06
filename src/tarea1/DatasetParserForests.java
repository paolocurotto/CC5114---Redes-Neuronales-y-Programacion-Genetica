package tarea1;

import neural_network.DataValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DatasetParserForests {

    /**
     * Returns list of DataValue items for the forests types data set
     */
    public static ArrayList<DataValue> parseDataset(String fileName) {

        ArrayList<DataValue> dataset = new ArrayList<>();

        String csvFile = "resources/forests/" + fileName;
        String line;
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                if(i++ == 0) {
                    continue;
                }

                // use comma as separator
                String[] record = line.split(",");

                DataValue dataValue = new DataValue();

                // Add inputs of the record to dataValue
                for (int c = 1; c < 10; c++) {
                    double btype = Double.parseDouble(record[c]);
                    dataValue.inputs.add(btype);
                }

                // Add output of the record to dataValue
                String forest_type = record[0];

                //**
                // s = 0, d = 1, h = 2, o = 3
                // */

                int typeindex;

                if (forest_type.equals("s ")) {
                    typeindex = 0;
                } else if (forest_type.equals("d ")) {
                    typeindex = 1;
                } else if (forest_type.equals("h ")) {
                    typeindex = 2;
                } else if (forest_type.equals("o ")) {
                    typeindex = 3;
                } else {
                    System.err.println("Class not identified while parsing: -" + forest_type + "-");
                    return null;
                }

                dataValue.desiredOutputs = new ArrayList<Double>(Collections.<Double>nCopies(4, (double) 0));
                dataValue.desiredOutputs.set(typeindex, (double) 1);

                // Add dataValue to dataset
                dataset.add(dataValue);

            }

            return dataset;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

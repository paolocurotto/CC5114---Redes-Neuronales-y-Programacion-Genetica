package tarea1;

import neural_network.DataValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DatasetParserPokerHands {

    public static void main(String[] args) {

        DatasetParserPokerHands dataparser = new DatasetParserPokerHands();

    }

    public DatasetParserPokerHands() {
    }

    /**
     * Returns list of DataValue items for the poker hands data set
     */
    public static ArrayList<DataValue> parseDataset(String fileName) {

        ArrayList<DataValue> dataset = new ArrayList<>();

        String csvFile = "resources/dataset_poker_hands/" + fileName;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] record = line.split(",");
                if (Integer.parseInt(record[10]) >= 2)
                    continue;

                DataValue dataValue = new DataValue();

                // Add inputs of the record to dataValue
                for (int c = 0; c < record.length - 1; c += 2) {
                    double suit = Double.parseDouble(record[c]) - 1;
                    double rank = Double.parseDouble(record[c + 1]) - 1;
                    double uniqueCard = (suit * 13) + rank;
                    //dataValue.inputs.add(uniqueCard);
                    dataValue.inputs.add(rank);
                }


                /*
                // Add inputs of the record to dataValue
                for (int c = 0; c < record.length - 1; c++) {
                    dataValue.inputs.add(Double.valueOf(record[c]));
                }*/

                // Add output of the record to dataValue
                int pokerHand = Integer.parseInt(record[10]); //  [0: 'High card', 1: 'One pair']

                dataValue.desiredOutputs = new ArrayList<Double>(Collections.<Double>nCopies(2, (double) 0));
                dataValue.desiredOutputs.set(pokerHand, (double) 1);

                // Add dataValue to dataset
                dataset.add(dataValue);

            }

            // Make n highcard == n 1pair
            Collections.shuffle(dataset);
            int nhighremoved = 0;
            Iterator itr = dataset.iterator();
            while (itr.hasNext()) {
                if (((DataValue) (itr.next())).desiredOutputs.get(0) == 1) {
                    itr.remove();
                    nhighremoved++;
                }
                if (nhighremoved == 1894) { // there are 1894 more 'high card' in data set than '1 pair'
                    break;
                }

            }

            return dataset;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

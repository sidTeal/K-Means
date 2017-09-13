
import java.io.IOException;
import javax.swing.WindowConstants;

public class KMeans {

    public static void main(String[] args) throws IOException {
        ClusterGUI gui = new ClusterGUI();
        gui.setVisible(true);
        gui.setTitle("KMeans Cluster");
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

// Console testing below before implementing GUI
/*        
        Calculate calc = new Calculate();
        ExcelReader excelReader = new ExcelReader();
        
        ArrayList<State> states = new ArrayList<>();
        int NUM_OF_CLUSTERS = 50; 
        double clusterCoords[][] = new double[NUM_OF_CLUSTERS][2]; // 2d array holding [x][y] coords of clusters
        Random rand = new Random();
        int clusterCountEmpty = 0; // tracks the number of clusters that have no state assigned to it (these default to 0,0)
        int clusterAdjustCount = 0; // tracks number of times clusters are adjusted
        boolean stateChangedCluster; // control variable for readjusting clusters
  
        //read input file into arrayLists
        ArrayList<String> stateNames = excelReader.columnToArrayListAsString("input.xlsx", 0);
        ArrayList<Double> population = excelReader.columnToArrayListAsDouble("input.xlsx", 1);
        ArrayList<Double> crime = excelReader.columnToArrayListAsDouble("input.xlsx", 2);
        ArrayList<Double> degree = excelReader.columnToArrayListAsDouble("input.xlsx", 3);
        ArrayList<Double> income = excelReader.columnToArrayListAsDouble("input.xlsx", 4);
        ArrayList<Double> crimeRate = new ArrayList<>();
        ArrayList<Double> degreeAndIncome = new ArrayList<>();

        // the following normalizes degree and income to values between 0 and 1
        // set crimeRate to the rate of crime/population
        // set degreeAndIncome to the average of degree and income
        // normalizes crimeRate and degreeAndIncome to values between 0 and 1
        degree = calc.normalize(degree);
        income = calc.normalize(income);
        for (int i = 0; i < 50; i++) {
            crimeRate.add(crime.get(i) / population.get(i));
            degreeAndIncome.add(calc.calculateAverage(degree.get(i), income.get(i)));
        }
        crimeRate = calc.normalize(crimeRate);
        degreeAndIncome = calc.normalize(degreeAndIncome);

// ******************************************************************** 
// Write state names and x,y coords to output excel file     
//        ExcelWriter excelWriter = new ExcelWriter();
//        excelWriter.setStringArrayListInColumn(stateNames, 0, "E:\\ai\\KMeans\\output.xlsx");
//        excelWriter.setDoubleArrayListInColumn(degreeAndIncome, 1, "E:\\ai\\KMeans\\output.xlsx");
//        excelWriter.setDoubleArrayListInColumn(crimeRate, 2, "E:\\ai\\KMeans\\output.xlsx");
        
        //populate states list with values from other lists using constructor
        for (int i = 0; i < 50; i++) {
            State tempState = new State(stateNames.get(i), degreeAndIncome.get(i), crimeRate.get(i));
            states.add(tempState);
        }

        // set the x,y coordinates of clusters to a random state's x,y coords
        for (int i = 0; i < NUM_OF_CLUSTERS; i++) {
            int index = rand.nextInt(states.size());
            clusterCoords[i][0] = states.get(index).getX();
            clusterCoords[i][1] = states.get(index).getY();
        }

        // prints initial cluster coords
        System.out.println("*************************************************************");
        System.out.println("INITIAL CLUSTER COORDS");
        for (int i = 0; i < NUM_OF_CLUSTERS; i++) {
            System.out.println("Cluster " + i + ": " + +clusterCoords[i][0] + ", " + clusterCoords[i][1]);
        }

        // assign states to nearest cluster
        // if a state changes which cluster it is nearest to, do again
        do {
            stateChangedCluster = false;
            for (int i = 0; i < 50; i++) { // for each state
                int tempCluster = 0;
                double minDistance = Double.MAX_VALUE;
                for (int j = 0; j < NUM_OF_CLUSTERS; j++) { // for each cluster
                    // calc distance to clusters
                    double tempDistance = calc.calcDistance(states.get(i).getX(), states.get(i).getY(),
                            clusterCoords[j][0], clusterCoords[j][1]);
                    // set this distance to minDistance if it is less
                    // hold tempCluster int for later
                    if (tempDistance < minDistance) {
                        minDistance = tempDistance;
                        tempCluster = j;
                    }
                } // end for NUM_OF_CLUSTERS

                if (tempCluster == states.get(i).getCluster()) { // do nothing
                } 
                else { // update state's cluster and set stateChangedCluster to true to repeat loop
                    states.get(i).setCluster(tempCluster);
                    stateChangedCluster = true;
                    clusterAdjustCount++;
                }
                states.get(i).setDistanceToCluster(minDistance); // update distance to nearest cluster
            } // end for 50 states

            // calculate the sum of all x and y values of states belonging to each cluster, as well as the number of states in each cluster
            if (stateChangedCluster) {
                double[][] sumClusterCoords = new double[NUM_OF_CLUSTERS][3];
                for (int i = 0; i < 50; i++) {
                    int index = states.get(i).getCluster();
                    sumClusterCoords[index][0] += states.get(i).getX(); // x val
                    sumClusterCoords[index][1] += states.get(i).getY(); // y val
                    sumClusterCoords[index][2]++; // count of states in that cluster
                }
                // update coords of a cluster to the average of x, y for all states in that cluster
                for (int i = 0; i < NUM_OF_CLUSTERS; i++) {
                    clusterCoords[i][0] = (double)Math.round((sumClusterCoords[i][0] / sumClusterCoords[i][2]) * 10000) / 10000;
                    clusterCoords[i][1] = (double)Math.round((sumClusterCoords[i][1] / sumClusterCoords[i][2]) * 10000) / 10000;
                }
            }
        } while (stateChangedCluster);

        // prints final cluster coords
        System.out.println("*************************************************************");
        System.out.println("FINAL CLUSTER COORDS");
        for (int i = 0; i < NUM_OF_CLUSTERS; i++) {
            System.out.println("Cluster " + i + ": " + clusterCoords[i][0] + ", " + clusterCoords[i][1]);
        }

        // prints clusters with states that belong to that cluster
        System.out.println("*************************************************************");
        for (int i = 0; i < NUM_OF_CLUSTERS; i++) {  
            System.out.println("CLUSTER " + i);
            for (int j = 0; j < 50; j++) {
                if (states.get(j).getCluster() == i) {
                    System.out.println("\t" + states.get(j).getName());
                }
            }
        }
        
        //counter and print number of empty clusters & number of times clusters were adjusted
        for(int i = 0; i < NUM_OF_CLUSTERS; i++) {
            if(clusterCoords[i][0] == 0 && clusterCoords[i][1] == 0)
                clusterCountEmpty++;
        }
        System.out.println("**********************");
        System.out.println("Clusters Empty:\t" + clusterCountEmpty);
        System.out.println("Times adjusted:\t" + clusterAdjustCount);
*/
    }

}

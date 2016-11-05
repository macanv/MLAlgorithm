package canma.dmml.logisticRegression;

import org.junit.Test;

import java.util.*;

/**
 * Created by macan on 2016/11/5.
 */
public class TestLR {
    @Test
    public void TestLR(){
        String  path = "data/LR/train.txt";
        LogisticRegression logisticRegression = new LogisticRegression();
        Map<Integer, List<Integer>> data = logisticRegression.loadData(path);
        Map<Integer, List<Integer>> trainData = new HashMap<>();
        Map<Integer, List<Integer>> testData = new HashMap<>();
        partitionData(data,trainData, testData);
        System.out.println(data);
    }

    public static void partitionData(Map<Integer, List<Integer>> data, Map<Integer, List<Integer>> trainData, Map<Integer, List<Integer>> testData){
        trainData = data;
        int numberOfTest = (int)(data.size() * 0.2);
        Random random = new Random(data.size());
        Set<Integer> index = data.keySet();

        int temp = 0;
        for (Integer i : index){
            if (temp++ > numberOfTest){
                break;
            }
            int r = random.nextInt();
            testData.put(i, trainData.get(i));
            trainData.remove(i);
        }

    }
}

package canma.dmml.logisticRegression;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by macan on 2016/11/5.
 */
public class TestLR {
    @Test
    public void TestLR(){
        String path = "data/LR/train.txt";

        Map<Integer, List<Integer>> data = loadData(path);
        Map<Integer, List<Integer>> trainData = new HashMap<>();
        Map<Integer, List<Integer>> testData = new HashMap<>();
        partitionData(data,trainData, testData);

        LogisticRegression logisticRegression = new LogisticRegression();
        logisticRegression.trainLR(trainData, 100);
        double p_rigth = logisticRegression.LRClassification(testData);
        System.out.printf("p_right = " + p_rigth);
    }

    /**
     * 加载数据
     * @param path  数据所在的目录
     * @return  返回数据
     */
    public static Map<Integer, List<Integer>> loadData(String path){
        Map<Integer, List<Integer>> data  = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line);
                ArrayList<Integer> linedata = new ArrayList<>();
                while (tokenizer.hasMoreTokens()){
                    Integer word = Integer.parseInt(tokenizer.nextToken());
                    linedata.add(word);
                }
                //这个可以将sublist的返回值转化为ArrayList
//                ArrayList<Integer> d = new ArrayList<Integer>(linedata.subList(1, linedata.size()));
                data.put(linedata.get(0), linedata.subList(1,linedata.size()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }


    /**
     * 将数据集分位80%的训练集，20%的测试集
     * @param data 总体数据
     * @param trainData   训练数据
     * @param testData  测试数据
     */
    public static void partitionData(Map<Integer, List<Integer>> data, Map<Integer, List<Integer>> trainData, Map<Integer, List<Integer>> testData){
        trainData = new HashMap<>(data);
        int numberOfTest = (int)(data.size() * 0.2);
        Random random = new Random();
        int[] index = new int[numberOfTest];

        for (int i = 0; i < numberOfTest ; ++i){
            index[i] = random.nextInt(data.size());
        }

        int temp = 0;
        int i = 0;
        for (Map.Entry<Integer, List<Integer>> entry : data.entrySet()){
            if (temp++ == index[i]) {
                testData.put(entry.getKey(), entry.getValue());
                trainData.remove(entry.getKey());
                i++;
            }

        }

    }
}

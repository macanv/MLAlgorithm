package canma.dmml.logisticRegression;


import org.junit.Test;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by macan on 2016/11/4.
 */
public class LogisticRegression {

    /**
     * 梯度算法中的增长率
     */
    private double alpha = 0.0001;
    /**
     * 设置的最小值，认为误差小于这个值的时候，收敛
     */
    private static double MIN = Math.pow(0.1, -6);

    /**
     * 迭代次数
     */
    private int Iter;

    /**
     * 测试数据集
     */
    private ArrayList<int[]> trainData;
    /**
     * 训练数据集
     */
    private ArrayList<int[]> testData;

    private double[] theta;
    /**
     * 初始化Model 设置训练集和测试数据集
     * @param trainData  训练数据集
     * @param testData  测试数据集
     */

    public  LogisticRegression(){
        theta = new double[10];
    }
    public LogisticRegression(ArrayList<int[]> trainData, ArrayList<int[]> testData, int iter){
        this.trainData = trainData;
        this.testData = testData;
        Iter = iter;

        theta = new double[trainData.size()];
    }


    public void trainLR(){

    }


    public Map<Integer, List<Integer>> loadData(String path){
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
                data.put(linedata.get(0), linedata.subList(1,linedata.size() -1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }




}

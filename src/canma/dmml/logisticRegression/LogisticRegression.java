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
    private Map<Integer, List<Integer>> trainData;
    /**
     * 训练数据集
     */
    private Map<Integer, List<Integer>> testData;

    private double[] theta;
    /**
     * 初始化Model 设置训练集和测试数据集
     */
    public LogisticRegression(){
        //theta = new double[trainData.size()];
    }


    /**
     * 根据训练集，求解LR的参数
     * @param trainData  训练数据集
     * @param iter  训练的迭代次数
     */
    public void trainLR(Map<Integer, List<Integer>> trainData, int iter){
        theta = new double[trainData.size()];
        //迭代iter次训练数据
        for (int i = 0; i < iter; ++i){
            for (Map.Entry<Integer, List<Integer>> entry : trainData.entrySet()){ //对于每一个样本
                int label = entry.getKey();
                List<Integer> data = entry.getValue();
                //计算当前样本的预测值
                double prediction = clacClassification(data);

                double[] preTheta = new double[theta.length];
                for (int j = 0; j < theta.length; ++j){
                    preTheta[j] = theta[j];
                    theta[j] = theta[j] + alpha * (label - prediction) * data.get(j);
                }

                //如果已经迭代收敛了，停止训练
                int j = 0;
                for (; j < theta.length; ++j) {
                    if (preTheta[j] - theta[j] > MIN){
                        break;
                    }
                }
                if (j >= theta.length){
                    break;
                }
            }
        }
    }

    /**
     * 计算每一个样本的预测值
     * @param x
     * @return
     */
    public double  clacClassification(List<Integer> x){
        double logit = .0;
        for (int i=0; i< theta.length;i++)  {
            logit += theta[i] * x.get(i);
        }
        return sigmoid(logit);
    }

    /**
     * 计算最后的sigmoid 函数值
     * @param z 计算出来的线性值
     * @return 返回预测值
     */
    public double sigmoid(double z){
        return 1.0 / (1.0 + Math.exp(-z));
    }




    /**
     * 利用训练好的逻辑回归参数，来进行数据的二分类
     * @param testData  需要分类的数据
     * @return 返回分类的正确率
     */
    public double LRClassification(Map<Integer, List<Integer>> testData){
        int p_right = 0;
        double[] reslut = new  double[testData.size()];

        int i = 0;
        for (Map.Entry<Integer, List<Integer>> entry : testData.entrySet()){
            double res = clacClassification(entry.getValue());
            //为了查看每次预测值，可以不保存
            reslut[i++] = res;
            res -= entry.getKey();
//            如果结果在误差范围内，认为预测正确 并记下正确的次数
            if (Math.abs(res) < alpha) {
                p_right ++;
            }

        }

        return (p_right * 1.0) / testData.size();
    }



}

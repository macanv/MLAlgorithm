package com.dmml.linear;

/**
 * Created by macan on 2016/10/12.
 */

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.ArrayList;

/**
 * 梯度下降算法
 * 1. batch gradient descent
 * 2. stochastic gradient descent
 * 3. gradient descent by matrix
 *
 * 假设： 特殊是N维的， 结果是一维的
 *
 */
public class GradientDescent {

    public final  static  int maxIterator = 1000;
    /**
     * 学习data set
     */
    private ArrayList<int[]> trainData;
    /**
     *
     */
    private ArrayList<Integer> targetData;

    /**
     * learning rate
     */
    private static double alpha = 0.00001;

    /**
     * 特征的维度
     */
    private  int N;
    /**
     * 样本的个数
     */
    private  int M;

    /**
     * theta 参数
     * double
     */
    private double[] theta;
    /**
     * 构造方法
     * @param trainData 训练数据集
     * @param testData 测试数据集
     */
    public GradientDescent(ArrayList<int[]> trainData, ArrayList<Integer> testData){
        this.trainData = trainData;
        this.targetData = testData;
        M  = trainData.size();
        N = trainData.get(1).length;
        //初始化theta
        theta = new double[N];
    }


    /**
     * batch gradient descent algorithm
     */
    public void batchGradientDescent(){
        //计算
        //迭代，设置最大的迭代次数为1000
        for (int n = 0; n < maxIterator; ++n) {
            for (int i = 0; i < N; ++i){
                theta[i] += batchOndGradient(i);
            }
        }
    }

    /**
     * stochastic gradient descent
     */
    public void stochasticGradientDescent(){
        //计算
        //迭代，设置最大的迭代次数为1000
        for (int n = 0; n < maxIterator; ++n) {
            //for each feature
            for (int i = 0; i < N; ++i){
                //for each row data
                for (int m = 0; m < M; ++m) {
                    theta[i] += stochasticOneGradient(m,i);
                }
            }
        }
    }

    /**
     * 利用矩阵的思路，来解决gradient descent
     */
    public void matrixInGradientDescet(){
        double[][] train = toArray(trainData);
        double[][] target = toArray2(targetData);
        double[][] x1 = Matrix.trans(train);
        //计算
        double[][] res = Matrix.times(Matrix.times(Matrix.inv(Matrix.times(x1, train)), x1), target);
        for (int i = 0; i < res.length; ++i){
            for (int j = 0; j <res[i].length; ++j){
               theta[i] = res[i][j];
            }
        }
    }


    /**
     * 计算sigma(y - h(x))x(i,j)
     * @param f 特征index
     * @return 返回计算的结果
     */
    public double batchOndGradient(int f) {
        double result = 0.0;
        double temp = 0.0;
        for (int m = 0; m < M; ++m) {
            //计算h(x)
            double hx = 0.0;
            int[] X = trainData.get(m);
            for (int i = 0; i < N; ++i) {
                hx += theta[i] * X[i];
            }
            //计算(h - hx) * x
            temp += (targetData.get(m) - hx) * X[f];
        }
        return alpha * temp;
    }

    public double stochasticOneGradient(int m, int f){
        double result = 0.0;
        double temp = 0.0;
        //计算h(x)
        double hx = 0.0;
        int[] X = trainData.get(m);
        for (int i = 0; i < N; ++i) {
            hx += theta[i] * X[i];
        }
        //计算(h - hx) * x
        return alpha * ((targetData.get(m) - hx) * X[f]);
    }


    public static double[][] toArray(ArrayList<int[]> data){
        double[][] res = new double[data.size()][data.get(1).length];
        for (int i = 0;i < data.size(); ++i){
            for (int j = 0; j < data.get(i).length; ++j){
                res[i][j] = (double)data.get(i)[j];
            }
        }
        return res;
    }

    public static  double[][] toArray2(ArrayList<Integer> data){
        double[][] res = new double[data.size()][1];
        for (int i = 0;i < data.size(); ++i){
            res[i][0] = data.get(i).doubleValue();
        }
        return res;
    }

    public void print(){
        for (int i = 0; i < theta.length; ++i){
            System.out.print("theta" + i + " = " + theta[i]);
        }
        System.out.println();
    }

    public void  initTheta(){
        for (int i = 0; i < N; ++i){
            theta[i] = 0.0;
        }
    }

    public double[] getTheta(){
        return theta;
    }
}

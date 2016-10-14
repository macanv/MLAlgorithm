package com.dmml.linear;

/**
 * Created by macan on 2016/10/12.
 */

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.ArrayList;

/**
 * �ݶ��½��㷨
 * 1. batch gradient descent
 * 2. stochastic gradient descent
 * 3. gradient descent by matrix
 *
 * ���裺 ������Nά�ģ� �����һά��
 *
 */
public class GradientDescent {

    public final  static  int maxIterator = 1000;
    /**
     * ѧϰdata set
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
     * ������ά��
     */
    private  int N;
    /**
     * �����ĸ���
     */
    private  int M;

    /**
     * theta ����
     * double
     */
    private double[] theta;
    /**
     * ���췽��
     * @param trainData ѵ�����ݼ�
     * @param testData �������ݼ�
     */
    public GradientDescent(ArrayList<int[]> trainData, ArrayList<Integer> testData){
        this.trainData = trainData;
        this.targetData = testData;
        M  = trainData.size();
        N = trainData.get(1).length;
        //��ʼ��theta
        theta = new double[N];
    }


    /**
     * batch gradient descent algorithm
     */
    public void batchGradientDescent(){
        //����
        //�������������ĵ�������Ϊ1000
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
        //����
        //�������������ĵ�������Ϊ1000
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
     * ���þ����˼·�������gradient descent
     */
    public void matrixInGradientDescet(){
        double[][] train = toArray(trainData);
        double[][] target = toArray2(targetData);
        double[][] x1 = Matrix.trans(train);
        //����
        double[][] res = Matrix.times(Matrix.times(Matrix.inv(Matrix.times(x1, train)), x1), target);
        for (int i = 0; i < res.length; ++i){
            for (int j = 0; j <res[i].length; ++j){
               theta[i] = res[i][j];
            }
        }
    }


    /**
     * ����sigma(y - h(x))x(i,j)
     * @param f ����index
     * @return ���ؼ���Ľ��
     */
    public double batchOndGradient(int f) {
        double result = 0.0;
        double temp = 0.0;
        for (int m = 0; m < M; ++m) {
            //����h(x)
            double hx = 0.0;
            int[] X = trainData.get(m);
            for (int i = 0; i < N; ++i) {
                hx += theta[i] * X[i];
            }
            //����(h - hx) * x
            temp += (targetData.get(m) - hx) * X[f];
        }
        return alpha * temp;
    }

    public double stochasticOneGradient(int m, int f){
        double result = 0.0;
        double temp = 0.0;
        //����h(x)
        double hx = 0.0;
        int[] X = trainData.get(m);
        for (int i = 0; i < N; ++i) {
            hx += theta[i] * X[i];
        }
        //����(h - hx) * x
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

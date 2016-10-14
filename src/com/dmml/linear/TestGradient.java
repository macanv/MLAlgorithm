package com.dmml.linear;

/**
 * Created by macan on 2016/10/12.
 */

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 测试梯度下降算法的正确性
 */
public class TestGradient {

    /**
     * 产生测试数据
     * @param N  特征的维度
     * @param M 数据集的大小
     * @return 产生的数据集，最后一维是目标
     */
    public ArrayList<int[]> createDataSet(int N, int M){
        // 假设数据产生是根据y = N/10 + N-1/10 + ... + (-1)^2 * (i%3) 得到的，这样不是一个线性关系。也方便进行结果的分析
        //特征值有1-10之间随机产生 10-20，

        ArrayList<int[]> data= new ArrayList<>();
        //对于所有的数据集M
        for (int i = 0; i < M ; ++i){
            //产生特征
            int[] X = new int[N + 1];
            for (int n = 0 ; n < N; ++n) {
                //x[0] = 1
                X[n] = (int)(Math.random() * 10 * n) + 1;
            }
            for (int j = 1; j < N; ++j) {
                X[N] += ((N + j) / 10.0) * X[j] + Math.pow(-1,j) * (j % 5);
            }
            data.add(X);
        }
        return  data;
    }


    @Test
    public void testGradientDescent(){
        //输入的数据格式为:一共M*(N+1) N1 = x0 都是1 ，1-N-1 列是特征，N列是目标
        int N = 4;
        int M = 300;
        ArrayList<int[]>  data = createDataSet(N, M);
        ArrayList<int[]> train = new ArrayList<int[]>();
        ArrayList<Integer> test = new ArrayList<Integer>();

        for (int[] sub : data){
            int[] t = new  int[N];
            for (int i = 0; i < N; ++i){
                t[i] = sub[i];
            }
            train.add(t);
            test.add(new Integer(sub[N]));
        }
        //System.out.println(data);

        GradientDescent gradientDescent = new GradientDescent(train, test);
        System.out.println("batch Gradient descent...");
        gradientDescent.batchGradientDescent();
        gradientDescent.print();
        double[] th1 = gradientDescent.getTheta();

        System.out.println("stochastic Gradient Descent");
        GradientDescent sgd = new GradientDescent(train, test);
        sgd.stochasticGradientDescent();
        sgd.print();
        double th2[] = sgd.getTheta();

        System.out.println("matrix In Gradient Descet");
        GradientDescent mgd = new GradientDescent(train, test);
        mgd.matrixInGradientDescet();
        mgd.print();
        double[] th3 = mgd.getTheta();

        saveData(data, th1, th2, th3);

    }

    public void  saveData(ArrayList<int[]> data, double[] th1, double[] th2, double[] th3)
    {
        String path = "data.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
            for (int[] rows : data){
                for (int i = 0; i< rows.length; ++i){
                    writer.write(rows[i] + "\t");
                }
                writer.write("\n");
            }

            for (int i = 0; i < th1.length; ++i){
                writer.write("theta" + i + " = " + th1[i] + "\t");
            }
            writer.write("\n");
            for (int i = 0; i < th2.length; ++i){
                writer.write("theta" + i + " = " + th2[i] + "\t");
            }
            writer.write("\n");
            for (int i = 0; i < th3.length; ++i){
                writer.write("theta" + i + " = " + th3[i] + "\t");
            }
            writer.write("\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

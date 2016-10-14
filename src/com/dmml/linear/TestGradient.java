package com.dmml.linear;

/**
 * Created by macan on 2016/10/12.
 */

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * �����ݶ��½��㷨����ȷ��
 */
public class TestGradient {

    /**
     * ������������
     * @param N  ������ά��
     * @param M ���ݼ��Ĵ�С
     * @return ���������ݼ������һά��Ŀ��
     */
    public ArrayList<int[]> createDataSet(int N, int M){
        // �������ݲ����Ǹ���y = N/10 + N-1/10 + ... + (-1)^2 * (i%3) �õ��ģ���������һ�����Թ�ϵ��Ҳ������н���ķ���
        //����ֵ��1-10֮��������� 10-20��

        ArrayList<int[]> data= new ArrayList<>();
        //�������е����ݼ�M
        for (int i = 0; i < M ; ++i){
            //��������
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
        //��������ݸ�ʽΪ:һ��M*(N+1) N1 = x0 ����1 ��1-N-1 ����������N����Ŀ��
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

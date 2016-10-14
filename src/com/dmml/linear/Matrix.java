package com.dmml.linear;

/**
 * copy from www.oscchina.com
 *
 */

import java.util.Scanner;

/**
 * 矩阵相关处理工具类
 */
public class Matrix {


    /**
     * 矩阵乘法
     * @param data1 矩阵A
     * @param data2 矩阵B
     * @return AB的结果
     */
    public  static double[][] times(double[][] data1, double[][] data2){
        double[][] res = new double[data1.length][data2[0].length];
        for(int i=0;i<data1.length;i++) {
            for(int j=0;j<data2[0].length;j++) {
                double sum = 0;
                for(int k=0;k<data2.length;k++) {
                    sum += data1[i][k] * data2[k][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    //public static double[][]
    /**
     * 计算两个矩阵的成绩
     * @param data
     * @param i
     * @param j
     * @return
     */
    public static double[][] get_complement(double[][] data, int i, int j) {
		/* x和y为矩阵data的行数和列数 */
        int x = data.length;
        int y = data[0].length;

		/* data2为所求剩余矩阵 */
        double data2[][] = new double[x - 1][y - 1];
        for (int k = 0; k < x - 1; k++) {
            if (k < i) {
                for (int kk = 0; kk < y - 1; kk++) {
                    if (kk < j) {
                        data2[k][kk] = data[k][kk];
                    } else {
                        data2[k][kk] = data[k][kk + 1];
                    }
                }

            } else {
                for (int kk = 0; kk < y - 1; kk++) {
                    if (kk < j) {
                        data2[k][kk] = data[k + 1][kk];
                    } else {
                        data2[k][kk] = data[k + 1][kk + 1];
                    }
                }
            }
        }
        return data2;
    }

    /**
     * 计算矩阵行列式
     * @param data  需要计算的矩阵
     * @return  data的行列式值 real value
     */
    public static double cal_det(double[][] data) {
        double ans=0;
		/*若为2*2的矩阵可直接求值并返回*/
        if(data[0].length==2){
            ans=data[0][0]*data[1][1]-data[0][1]*data[1][0];
        }
        else{
            for(int i=0;i<data[0].length;i++){
				/*若矩阵不为2*2那么需求出矩阵第一行代数余子式的和*/
                double[][] data_temp=get_complement(data, 0, i);
                if(i%2==0){
					/*递归*/
                    ans=ans+data[0][i]*cal_det(data_temp);
                }
                else{
                    ans=ans-data[0][i]*cal_det(data_temp);
                }
            }
        }
        return ans;

    }

    /**
     * 计算矩阵的伴随矩阵
     * @param data 原矩阵
     * @return data的伴随矩阵
     */
    public static double[][] ajodouble(double[][] data) {
        int M=data.length;
        int N=data[0].length;
        double data2[][]=new double[M][N];
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                if((i+j)%2==0){
                    data2[i][j]=cal_det(get_complement(data, i, j));
                }
                else{
                    data2[i][j]=-cal_det(get_complement(data, i, j));
                }
            }
        }
        return trans(data2);
    }

    /**
     * 转置矩阵
     * @param data 原矩阵
     * @return data 的转置矩阵
     */
    public static double [][]trans(double[][] data){
        int i=data.length;
        int j=data[0].length;
        double[][] data2=new double[j][i];
        for(int k2=0;k2<j;k2++){
            for(int k1=0;k1<i;k1++){
                data2[k2][k1]=data[k1][k2];
            }
        }
		/*将矩阵转置便可得到伴随矩阵*/
        return data2;
    }


    /**
     * 求矩阵的逆，输入参数为原矩阵
     * @param data 原矩阵
     * @return data的逆矩阵
     */
    public static double[][] inv(double [][] data){
        int M=data.length;
        int N=data[0].length;
        double data2[][]=new double[M][N];
        double det_val=cal_det(data);
        data2=ajodouble(data);
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                data2[i][j]=data2[i][j]/det_val;
            }
        }
        return data2;
    }

}

package com.dmml.linear;

/**
 * copy from www.oscchina.com
 *
 */

import java.util.Scanner;

/**
 * ������ش�������
 */
public class Matrix {


    /**
     * ����˷�
     * @param data1 ����A
     * @param data2 ����B
     * @return AB�Ľ��
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
     * ������������ĳɼ�
     * @param data
     * @param i
     * @param j
     * @return
     */
    public static double[][] get_complement(double[][] data, int i, int j) {
		/* x��yΪ����data������������ */
        int x = data.length;
        int y = data[0].length;

		/* data2Ϊ����ʣ����� */
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
     * �����������ʽ
     * @param data  ��Ҫ����ľ���
     * @return  data������ʽֵ real value
     */
    public static double cal_det(double[][] data) {
        double ans=0;
		/*��Ϊ2*2�ľ����ֱ����ֵ������*/
        if(data[0].length==2){
            ans=data[0][0]*data[1][1]-data[0][1]*data[1][0];
        }
        else{
            for(int i=0;i<data[0].length;i++){
				/*������Ϊ2*2��ô����������һ�д�������ʽ�ĺ�*/
                double[][] data_temp=get_complement(data, 0, i);
                if(i%2==0){
					/*�ݹ�*/
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
     * �������İ������
     * @param data ԭ����
     * @return data�İ������
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
     * ת�þ���
     * @param data ԭ����
     * @return data ��ת�þ���
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
		/*������ת�ñ�ɵõ��������*/
        return data2;
    }


    /**
     * �������棬�������Ϊԭ����
     * @param data ԭ����
     * @return data�������
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

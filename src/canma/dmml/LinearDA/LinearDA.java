package canma.dmml.LinearDA;


import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;

import java.util.ArrayList;
import java.util.List;

import static org.ujmp.core.calculation.Calculation.Ret.*;

/**
 * Created by macan on 2016/11/6.
 *
 * linear discrimination analysis  线性语义分析
 */
public class LinearDA {
    /**
     * 每一个样本那的均值
     */
    private Matrix u_1;
    private Matrix u_0;

    /**
     * 整体样本的均值
     */
    private double u;

    /**
     * 类内离散度矩阵
     */
    private  Matrix s_w;
    /**
     * 类间离散度矩阵
     */
    private  Matrix s_b;

    private Matrix w;

    private org.ujmp.core.Matrix p_data;
    private org.ujmp.core.Matrix n_data;

    private int numberOfPoistition;
    private int numberOfNevagation;

    private int numberOfFeature;
    /**
     *
     */
    public LinearDA(ArrayList<List<Integer>> pdata, ArrayList<List<Integer>> ndata){
        numberOfPoistition = pdata.size();
        numberOfNevagation = ndata.size();
        numberOfFeature = pdata.get(0).size();

        u_1 = DenseMatrix.Factory.zeros(1, numberOfFeature);
        u_0 = DenseMatrix.Factory.zeros(1, numberOfFeature);

        s_w = DenseMatrix.Factory.zeros(numberOfFeature, numberOfFeature);
        s_b = DenseMatrix.Factory.zeros(numberOfFeature, numberOfFeature);
        //将array转化为数组
        ArrayToMatrix(pdata, ndata);
        calcMeans();

        //求w w = S_W.inv(u_0 - u_1)  其中对s_w进行奇异值分解，求解s_w.inv
        Matrix[] svd = s_w.svd();
        w = svd[2].transpose().mtimes(svd[1].inv()).mtimes(svd[0].transpose()).mtimes(u_0.minus(u_1).transpose());
    }


    /**
     * 计算样本的均值，每一个样本的均值和整体的均值
     * @return
     */
    public void calcMeans(){
        //计算总体的均值

        u = (p_data.getAbsoluteValueSum() + n_data.getAbsoluteValueSum()) / (numberOfNevagation + numberOfPoistition);
        //计算每一个样本的均值

        //计算正例中每一个样本的均值
        for (int i = 0; i < numberOfFeature; ++i){
            u_1.setAsDouble(p_data.selectColumns(LINK, i).getAbsoluteValueMean(), 0, i);
            u_0.setAsDouble(n_data.selectColumns(LINK, i).getAbsoluteValueMean(), 0, i);
        }

        //计算Sw
        Matrix p_temp = DenseMatrix.Factory.zeros(p_data.getRowCount(), p_data.getColumnCount());
        for (int i = 0; i < numberOfPoistition; ++i){
            Matrix a = p_data.selectRows(NEW, i).minus(u_1.transpose());
            System.out.println(a);
            for (int j = 0; j < numberOfFeature; ++j){
                p_temp.setAsDouble(a.getAsDouble(0, j), i, j);
            }
            //p_temp.setAsMatrix(a, i);
        }
        Matrix n_temp = DenseMatrix.Factory.zeros(n_data.getRowCount(), p_data.getColumnCount());
        for (int i = 0; i < numberOfNevagation; ++i){
            Matrix a = n_data.selectRows(NEW, i).minus(u_0.transpose());
            for (int j = 0; j < numberOfFeature; ++j){
                n_temp.setAsDouble(a.getAsDouble(0, j), i, j);
            }
//            n_temp.setAsMatrix(a, i);
        }

        s_w = p_temp.transpose().mtimes(p_temp).plus(n_temp.transpose().mtimes(n_temp));

        //计算Sb
        Matrix a = u_0.minus(u_1);
        s_b = a.transpose().mtimes(a);
    }


    /**
     * 将二维数组转化为矩阵转化为矩阵
     * @param pdata  正例的数据
     * @param ndata  反例的数据
     */
    public void ArrayToMatrix(ArrayList<List<Integer>> pdata, ArrayList<List<Integer>> ndata){
        p_data = DenseMatrix.Factory.zeros(pdata.size(), pdata.get(0).size());
        for (int i = 0; i < pdata.size(); ++i){
            for (int j = 0; j < pdata.get(i).size(); ++j){
                p_data.setAsInt(pdata.get(i).get(j), i ,j);
            }
        }

        n_data = DenseMatrix.Factory.zeros(ndata.size(), ndata.get(0).size());
        for (int i = 0; i < ndata.size(); ++i){
            for (int j = 0; j < ndata.get(i).size(); ++j){
                n_data.setAsInt(ndata.get(i).get(j), i ,j);
            }
        }
    }

    /**
     * 预测数据是属于哪一类
     * @param data 需要预测的数据
     * @return ture 正例，0 反例
     */
    public void predict(Matrix data){
        Matrix a = w.transpose().mtimes(data.transpose());
        System.out.println("   \n" + a + "\n");

    }
}

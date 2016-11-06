package canma.dmml.LinearDA;

import Jama.Matrix;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;

import java.util.ArrayList;

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
    private double[] u_1;
    private double[] u_2;

    /**
     * 整体样本的均值
     */
    private double u;

    /**
     * 类内离散度矩阵
     */
    private  double s_w;
    /**
     * 类间离散度矩阵
     */
    private  double s_b;

    private org.ujmp.core.Matrix p_data;
    private org.ujmp.core.Matrix n_data;

    private int numberOfPoistition;
    private int numberOfNevagation;
    /**
     *
     */
    public LinearDA(ArrayList<ArrayList<Integer>> pdata, ArrayList<ArrayList<Integer>> ndata){
        numberOfPoistition = pdata.size();
        numberOfNevagation = ndata.size();

        u_1 = new double[numberOfPoistition];
        u_2 = new  double[numberOfNevagation];

        //将array转化为数组
        trainToMatrix(pdata, ndata);
    }

    /**
     * 计算LDA参数
     * @param pdata
     * @param ndata
     */
    public void train_lda(ArrayList<ArrayList<Integer>> pdata, ArrayList<ArrayList<Integer>> ndata) {

    }

    /**
     * 计算样本的均值，每一个样本的均值和整体的均值
     * @return
     */
    public void calcMeans(){
        //计算总体的均值
        u = p_data.getAbsoluteValueSum() + n_data.getAbsoluteValueSum() / (numberOfNevagation + numberOfPoistition);
        //计算每一个样本的均值

        //计算正例中每一个样本的均值
        for (int i = 0; i < p_data.getRowCount(); ++i){
            u_1[i] += p_data.selectColumns(LINK, i).getAbsoluteValueMean();
        }

        //计算负例中每一个样本的均值
        for (int i = 0; i < numberOfNevagation; ++i){
            u_2[i] += n_data.selectColumns(LINK, i).getAbsoluteValueMean();
        }
    }

    /**
     * 计算方差
     */
    public void  calcS(){

    }

    /**
     * 将二维数组转化为矩阵转化为矩阵
     * @param pdata  正例的数据
     * @param ndata  反例的数据
     */
    public void trainToMatrix(ArrayList<ArrayList<Integer>> pdata, ArrayList<ArrayList<Integer>> ndata){
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



}

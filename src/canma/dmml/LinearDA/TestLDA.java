package canma.dmml.LinearDA;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by macan on 2016/11/6.
 * linear discrimination analysis test
 */
public class TestLDA {

    @Test
    public void  testLDA(){
        String path = "data/LR/train.txt";
        ArrayList<List<Integer>> postionData, navigationData;
        postionData = new ArrayList<>();
        navigationData = new ArrayList<>();
        loadData(path, postionData, navigationData);  //数据集训练出来后正例有260个，负例有28个，不知道是否会出现数据集不平衡问题，这里暂时先不作考虑

        LinearDA linearDA = new LinearDA(postionData, navigationData);
        Matrix test = DenseMatrix.Factory.zeros(10, 5);
        for (int i =0; i< 10; i++){
            if (i < 5) {
                for (int j = 0; j < 5; j++) {
                    test.setAsDouble(postionData.get(i).get(j), i, j);
                }
            }else {
                for (int j = 0; j < 5; j++) {
                    test.setAsDouble(navigationData.get(i).get(j), i, j);
                }
            }

        }
        boolean is = linearDA.predict(test);
        System.out.println(""+ is);
    }

    public void loadData(String path, ArrayList<List<Integer>> postionData, ArrayList<List<Integer>> navigationData){
        int dSize = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line);
                ArrayList<Integer> data = new ArrayList<>();
                int i = 0;
                while (tokenizer.hasMoreTokens()){
                    int word = Integer.parseInt(tokenizer.nextToken());
                    if (i++ == 0){
                        continue;
                    }
                    data.add(word);

                }
                if (data.get(data.size() - 1) == 1) {
                    postionData.add(data.subList(0, data.size() - 1));
                }else {
                    navigationData.add(data.subList(0, data.size() -1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

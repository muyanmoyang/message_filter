package muyanmoyang.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

/**
 *  KNN �㷨-----���ƶȼ���
 * @author moyang
 *
 */
public class KNN {
	
    //ѵ�����ĵ���������ʾ
    static HashMap<String, Double> dist = new HashMap<String, Double>();  // �ȷ����ĵ���ÿ��ѵ�����ĵ��ľ���
//    static Vector<Double[]> testVector = new Vector<Double[]>(); // �������ĵ���������ʾ 

    /**
     *  ��ȡ���Լ�TFIDFֵ
     * @param testFileDir
     * @param threshold
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    public static Vector<Double[]> initTestVector(String testFileDir,int threshold) throws NumberFormatException, IOException {
    	Vector<Double[]> testVector = new Vector<Double[]>(); // �������ĵ���������ʾ 
        FileReader fr = new FileReader(new File(testFileDir));
        BufferedReader br = new BufferedReader(fr);
        
        String line ;
        int count = 0 ;
        while ((line = br.readLine()) != null) {
        	
        	Double weightDouble[] = new Double[threshold];
        	count ++ ;
        	System.out.println("���ش������ĵ�������,���ص��˵ڣ�" + count + "ƪ�����ĵ�......") ;
        	String weight[] = line.split(" ") ;
        	for(int i=0; i<weight.length ;i++){
        		weightDouble[i] = Double.parseDouble(weight[i]) ;
        	}
        	testVector.add(weightDouble) ;
        	System.gc();
        } 
//        writer.close();
        
        br.close() ;
        return testVector ;
    }
    
    /**
     *  ��ȡѵ����TFIDFֵ
     * @param testFileDir
     * @param threshold
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    public static Vector<Double[]> initTrainVector(String trainFile,int threshold) throws NumberFormatException, IOException {
    	Vector<Double[]> trainVector = new Vector<Double[]>();
    	FileWriter writer = null ; 
    	
    	FileReader fr = new FileReader(trainFile);
    	BufferedReader br = new BufferedReader(fr);
    	
    	int count = 0; 
    	String line ;
    	while ((line = br.readLine()) != null) {
    		Double weightDouble[] = new Double[threshold];
    		count ++ ;
    		System.out.println("����ѵ���ĵ�������,���ص��˵ڣ�" + count + "ƪѵ���ĵ�......") ;
    		String weight[] = line.split(" ") ;
        	for(int i=0; i<weight.length ;i++){
        		weightDouble[i] = Double.parseDouble(weight[i]) ;
        	}
        	trainVector.add(weightDouble);
    		System.gc();
    	}
        
        br.close() ;
        return trainVector ;
    }

    /**
     * �������ƶ�
     * @param trainFile
     * @param testVector
     * @param distanceFileDir
     * @param threshold
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void caculateDist(Vector<Double[]> trainVector ,Vector<Double[]> testVector,String distanceFileDir, int threshold) throws NumberFormatException, IOException {
       
//    	Vector<Double[]> trainVector = new Vector<Double[]>();
//    	FileWriter writer = null ; 
//    	
//    	FileReader fr = new FileReader(trainFile);
//    	BufferedReader br = new BufferedReader(fr);
//    	
//    	int count = 0; 
//    	String line ;
//    	while ((line = br.readLine()) != null) {
//    		Double weightDouble[] = new Double[threshold];
//    		count ++ ;
//    		System.out.println("����ѵ���ĵ�������,���ص��˵ڣ�" + count + "ƪѵ���ĵ�......") ;
//    		String weight[] = line.split(" ") ;
//        	for(int i=0; i<weight.length ;i++){
//        		weightDouble[i] = Double.parseDouble(weight[i]) ;
//        	}
//        	trainVector.add(weightDouble);
//    		System.gc();
//    	}
    	FileWriter writer = null ;  
    	double distance = 0.0 ;     //
    	boolean flag = false ;     // ����Ƿ��Ѿ�����������ƶ�
    	
    	for(int i=0; i<testVector.size(); i++){
    		System.out.println("�������ÿƪ�����ĵ���ѵ���ĵ������ƶ�,���㵽�˵�:" + (i+1) + "ƪ�����ĵ�......") ;
    		Double[] testLine = testVector.get(i) ;
    		
    		 
    		writer = new FileWriter(new File(distanceFileDir + "��" + (i+1) +"ƪ�����ı����ƶ�.txt")) ;
    		for(int j=0; j<trainVector.size(); j++){
//    			System.out.println("���ں͵�" + (j+1) + "ƪѵ���ĵ��������ƶȼ���......") ;
    			Double[] trainLine = trainVector.get(j) ;
    			for(int k=0;k<trainLine.length; k++){
    				if(flag==false && trainLine[k]!=0.0 && testLine[k]!=0.0){
    					distance = cos(trainLine, testLine);       // ���ƶ�ֵ
    					flag = true ;
    				}
    			}
    			if(distance > 0.0){
    				writer.write((j+1) + "\t" + distance);
    				writer.write("\n"); 
    				writer.flush();
    			}
    			distance = 0.0 ;
    			flag = false ;  // ���������һƪ�����ı������ƶȣ���ʼ������һƪ����flag��Ϊfalse
    		 }
    	 }
    	 writer.close();
    	
//    	 dist.put(filename, d);
//    	 System.out.println("��"+filename+"�ľ�����"+d);
    }

    // �������������ļнǵ����ҡ������ֵ�ľ���ֵԽ��˵���н�ԽС��Խ���ƣ�����Խ����
    public static double cos(Double[] trainLine, Double[] testLine) { 
        double res = 0.0;
        double mul = 0.0;
        double p1 = 0.0, p2 = 0.0;
        int len = trainLine.length ;
        
        for (int i = 0; i < len; i++) {
//        	System.out.println("�ڶ�ȡ��" + (i+1) + "������......"); 
            Double x = trainLine[i] ;
            Double y = testLine[i] ;
            mul += x * y;                // (Xi * Yi) ���
            p1 += Math.pow(x, 2);          //  (Xi)��ƽ�����
            p2 += Math.pow(y, 2);          //  (Yi)��ƽ�����
        }	
        res = Math.abs(mul) / Math.sqrt(p1 * p2);  //cos(��) ���ƶȼ���
        return res;
    }

  //Map��value�������� 
	public static ArrayList<Entry<String,Double>> sort(HashMap<String,Double> arg){
		ArrayList<Entry<String,Double>> al=new ArrayList<Entry<String,Double>>(arg.entrySet());
		Collections.sort(al, new Comparator<Map.Entry<String,Double>>(){
			public int compare(Map.Entry<String, Double> o1,Map.Entry<String,Double> o2){
				double res = o2.getValue()-o1.getValue();
				if(res < 0)
					return -1;
				else if(res > 0)
					return 1;
					else
						return 0;
			}
		});
		return al;
	}
    /**
     * KNN�㷨����ѡ��
     * @param k
     */
    public static void knnClassification(int k){
        //��������������ѵ�������ľ��밴�Ӵ�С����
        ArrayList<Entry<String,Double>> dist_list = sort(dist);
        int c1 = 0, c2 = 0;
        for(int i=0; i<k ; i++){
            Entry<String,Double> entry = dist_list.get(i);
            int fileNum = Integer.parseInt(entry.getKey());
            if(fileNum>=0 && fileNum<3)
                c1 ++ ;
            else if(fileNum>=3 && fileNum<6)
                c2 ++ ;
        }
        if(c1 > c2)
            System.out.println("���ڵ�1�࣡");
        else if(c2 > 1)
            System.out.println("���ڵ�2�࣡");
        else
            System.out.println("��������Ŀ�����һ����");
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException{
        
    	long start = System.currentTimeMillis() ;
    	int threshold = 800 ; // ѡȡ������������
    	
        String testFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�TFIDF_" + threshold + "������/TFIDF.txt" ;   // ���Լ��������ļ�
//        String testFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/����_���Լ�TFIDF.txt" ;   // ���Լ��������ļ�
        Vector<Double[]> testVector = initTestVector(testFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
        
        String trainFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/"+ threshold + "������/TFIDF.txt";   // ѵ�����������ļ�
//        String trainFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/����_ѵ����TFIDF.txt";   // ѵ�����������ļ�
        Vector<Double[]> trainVector = initTrainVector(trainFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
        
//        System.out.println(testVector.size()) ; 
//        FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�/����.txt")) ;
//        for(int i=0; i<testVector.size(); i++){
//        	Double[] tmp = testVector.get(i);
//        	for(int j=0; j<tmp.length; j++){
//        		writer.write(tmp[j] + " ");
//        	}
//        	writer.write("\n");
//        	writer.flush();
//        }
//        writer.close();
       
        String distanceDir = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/" ;
        caculateDist(trainVector,testVector,distanceDir, threshold);  // �������ƶ�
//        knnClassification(3);      // KNN����
        long end = System.currentTimeMillis() ;
        System.out.println("����ʱ�䣺" + (end - start)/1000 + "��......");
    }
    
    
    
}
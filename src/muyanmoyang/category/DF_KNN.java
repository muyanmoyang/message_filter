package muyanmoyang.category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class DF_KNN {
	public static void main(String[] args) throws NumberFormatException, IOException {
		long start = System.currentTimeMillis() ; 
//    	int threshold = 100 ; // ѡȡ������������
//    	
//        String testFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF_TFIDF_���Լ�/DF_tfidf_test.txt" ;   // ���Լ��������ļ�
////        String testFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/����_���Լ�TFIDF.txt" ;   // ���Լ��������ļ�
//        Vector<Double[]> testVector = KNN.initTestVector(testFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
//        
//        String trainFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF_TFIDF/DF_tfidf.txt";   // ѵ�����������ļ�
////        String trainFile = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/����_ѵ����TFIDF.txt";   // ѵ�����������ļ�
//        Vector<Double[]> trainVector = KNN.initTrainVector(trainFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
//       
//        String distanceDir = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/" ;
//        KNN.caculateDist(trainVector,testVector,distanceDir, threshold);  // �������ƶ�
//        knnClassification(3);      // KNN����
		
		classification() ;   // ����
		
        long end = System.currentTimeMillis() ;
        System.out.println("����ʱ�䣺" + (end - start)/1000 + "��......");
	}
	
	private static void classification() throws NumberFormatException, IOException{
		int threshold = 100 ;
		int k = 10 ;
		int classLabel = 0 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/����_" + k + "/���.txt"));
		for(int i=1; i<=8000; i++){
			
			//  �����ƶ�����
//			System.out.println("��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......") ;
//			KNN_Classification.sortOfDistance(threshold, "C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/��" + i +"ƪ�����ı����ƶ�.txt",
//					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/������/������_��" + i +"ƪ�����ı����ƶ�.txt") ;
			
			//  �� ѡȡK����
//			System.out.println("ѡȡKNN���ڣ���8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			KNN_Classification.selectKNeighbors(k ,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/������/������_��" + i +"ƪ�����ı����ƶ�.txt",
//					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/����_"+ k +"/��" + i +"ƪ�����ı����ƶ�.txt") ;
			
			 //  �� ������
//			System.out.println("���࣬��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			int predictLabel = KNN_Classification.classification("C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt");
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		// �� ����ָ��
		System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
		// ��ʵ�������
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)���Լ�8000��.txt") ;
		
		// �ٻ���
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/����_" + k + "/���.txt", testResultMap);
		
		// ׼ȷ��
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF���ƶ�/����_" + k + "/���.txt", testResultMap);
		
		// Fֵ
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("���# " + classLabel + " #��Fֵ��" + Fscore) ;
		
//		writer.close();
	}
}

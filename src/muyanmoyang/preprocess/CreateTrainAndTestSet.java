package muyanmoyang.preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * ����ѵ�����ݼ��Ͳ��Լ�,���ֱ�д�������ļ�,�ܹ�28000 �����ţ�ѵ����ȡ20000(�����ʼ�10000) �� ���Լ�ȡ8000(�����ʼ�4000) ��
 * @author moyang
 *
 */
public class CreateTrainAndTestSet {
	
	public static void main(String[] args) throws IOException { 
//		divideTrainAndTest("C:/Users/Administrator/Desktop/����(��С������ݼ�)/��������ѵ����80W��.txt",
//							"C:/Users/Administrator/Desktop/����(��С������ݼ�)/ѵ����2W��.txt",
//				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�8000��.txt");
//		getNumOfSpamAndNormalMessage("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�8000��.txt");
//		getNumOfSpamAndNormalMessage("C:/Users/Administrator/Desktop/����(��С������ݼ�)/ѵ����2W��.txt");
	
	}
	
	/*
	 *  ����ѵ���� �� ���Լ�
	 */
	private static void divideTrainAndTest(String fileDir,String trainDir,String testDir) throws IOException{
		File readerFile = new File(fileDir);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"UTF-8") ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		FileWriter trainWriter = new FileWriter(new File(trainDir)) ;
		FileWriter testWriter = new FileWriter(new File(testDir)) ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			if(count <= 20000){
				trainWriter.write(line);
				trainWriter.write("\n");
				trainWriter.flush();
			}
			if(count>20000 && count <=28000 ){
				testWriter.write(line);
				testWriter.write("\n");
				testWriter.flush();
			}
		}
		trainWriter.close();
		testWriter.close();
	} 
	
	/*
	 *  ͳ�Ʋ��Լ����ж����������ź���������
	 */
	private static void getNumOfSpamAndNormalMessage(String testDir) throws IOException{
		//TODO ͳ�Ʋ��Լ����ж����������ź���������
		File readerFile = new File(testDir);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"UTF-8"); 
		BufferedReader BR = new BufferedReader(reader) ;
		String line ;
		int count = 0;
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			if(str[1].equals("1")){
				count ++ ;
			}
		}
//		System.out.println("���Լ��������ʼ�������" + count); 
		System.out.println("���Լ��������ʼ�������" + count); 
	}
}

package muyanmoyang.featureSelection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import muyanmoyang.utils.KeyUtils;

/**
 *  CHI��������������ȡ�㷨
 * @author moyang
 *
 */
public class CHI {
	
	/*
	 *   ת������б�
	 */
	public static List<String> getLabel() throws IOException{
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)ѵ����2W��.txt")) ;
		BufferedReader BR = new BufferedReader(reader) ;
		int count = 0 ;
		List<String> list = new ArrayList<String>() ;
		String textLine ;
		while((textLine = BR.readLine())!= null){
			String str[] = textLine.split("\t") ;
//			System.out.println(count + ":" + str.length); 
			list.add(str[1]);
			count ++ ;
		}
		return list ;
	}
	
	/**
	 *  ��ȡ�ʴ�ģ�ͣ��洢������Map
	 * @param fileOfBag   �ʴ��ļ�
	 * @return Map<String, Vector<KeyUtils>> �ʴ�ģ�ͼ���   ʵ����[  Ҫ�� :11458 1;12248 1;111129 1;146004 1;315091 1;390447 1;446011 1;500701 1;625715 1; 9 ]
	 * @throws IOException 
	 */
	private static Map<String, Vector<KeyUtils>> readBofWords(String fileOfBag) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Vector<KeyUtils>>  bagMap = new HashMap<String, Vector<KeyUtils>>() ; // �ʴ�ģ��
		Vector<KeyUtils> vector = new Vector<KeyUtils>() ;
		FileReader reader = new FileReader(new File(fileOfBag)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		int count = 0 ;
		String line ;
		while((line=BR.readLine())!=null){
			count ++ ;
			System.out.println("��ȡ�ʴ��ļ����ؽ�Map���ϣ��ʴ��ļ���35248�У���ȡ���˵�" + count + "��......") ;
			if(vector == null)
			{
				vector = new Vector<KeyUtils>() ;
			}
			String str[] = line.split(" :") ;
			String str2 = str[1] ;
			String keyUtilsStr[] = str2.split(";") ;
			for(int i=0; i<keyUtilsStr.length-1; i++){
				String tempStr[] = keyUtilsStr[i].split(" ") ;
				KeyUtils keyUtils = new KeyUtils(Integer.parseInt(tempStr[0]),Integer.parseInt(tempStr[1])) ;
				vector.add(keyUtils) ;
			}
			bagMap.put(str[0],vector) ;
			vector = null ;
		}
		return bagMap;
	}
	
	/**
	 *  ��ȡ���Ｏ��
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	private static Vector<String> getCharacterList(String characterDir) throws IOException {
		// TODO Auto-generated method stub
		Vector<String> vector = new Vector<String>() ;
		FileReader reader = new FileReader(new File(characterDir)) ;
		BufferedReader BR = new BufferedReader(reader) ; 
		String line ;
		while((line=BR.readLine())!=null){
			vector.add(line) ;
		}
		return vector;
	}
	
	/**
	 * ����ĳ����������ͳ����
	 * @param word          �� String                        ���Ｏ��
	 * @param bagOfWordsMap �� Map<String,Vector<KeyUtils>>  �ʴ�ͳ����ģ��
	 * @param classLabel    �� List<String>                  �������
	 * @param label         �� String                        ���
	 */
	private static Double computeBasicStatistics(String word, Map<String,Vector<KeyUtils>> bagOfWordsMap, List<String> classLabel,String label){
		
		Double A = 0.0 ,B =0.0 , C = 0.0 , D = 0.0 ;
		Double chiSquare ;
		List<Integer> tempList = new ArrayList<Integer>() ; // ��¼word���ֵ��ı����
		List<Integer> articleList = new ArrayList<Integer>() ; // ������word���ı����
		for(int i=1; i<=20000; i++){
			articleList.add(i) ;
		}
		
		Vector<KeyUtils> vectorKeyUtils = bagOfWordsMap.get(word) ;
			
		for(int k=0; k<vectorKeyUtils.size(); k++){
			tempList.add(vectorKeyUtils.get(k).getX()) ; //��¼word���ֵ��ı����
		}
		articleList.removeAll(tempList) ; //��������word���ı���ż��� , tempList ��¼����wordû�г��ֹ����ı����
		
		for(int j=0; j<vectorKeyUtils.size(); j++){  // ����word���ı��� label��ĿA �ͷ�label�����ĿB
			KeyUtils keyUtils = vectorKeyUtils.get(j) ;
			if(classLabel.get(keyUtils.getX()-1).equals(label)){
				A ++ ;
			}else{
				B ++ ;
			}
		}	
		
		for(int i=0; i<articleList.size(); i++){   // ������word���ı��� ��label��Ŀ C �� ��label��ĿD
			if(classLabel.get(articleList.get(i)-1).equals(label)){   // 
				C ++  ;
			}else{
				D ++ ;
			}
		}
		chiSquare = 20000.0 * Math.pow((A*D - B*C), 2) / ((A + B) * (C + D)) ;
		return chiSquare ;         // ����word������label��CHIֵ
	}
	
	
	public static void main(String[] args) throws IOException {
		String label = "1" ;
		Double chiSquare ;
		String word ;
		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHIofWords_���" + label + ".txt")) ;
		//��ȡ���Ｏ��
		Vector<String> characterList = getCharacterList("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Ｏ��.txt") ; 
		// ��ȡ�ʴ�ģ��
		Map<String,Vector<KeyUtils>> bagOfWordsMap = readBofWords("C:/Users/Administrator/Desktop/����(��С������ݼ�)/�ʴ�.txt") ;
		// ��ȡ�������
		List<String> classLabel = getLabel() ;
		
		for(int i=0; i<characterList.size(); i++){
			System.out.println("����ÿ�������CHIֵ����35248��������㵽�˵�" + (i+1) + "������......") ;
			word = characterList.get(i) ;  // ��ȡÿһ��word
			chiSquare = computeBasicStatistics(word, bagOfWordsMap, classLabel, label) ; // �����word����label���CHIֵ
			System.out.println(chiSquare);
			writer.write(word + "\t" + chiSquare) ;
			writer.write("\n");
			writer.flush();
		}
		writer.close();
	}
	

}




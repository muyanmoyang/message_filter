package muyanmoyang.preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
import muyanmoyang.utils.KeyUtils;

/**
 *  �����ʴ�ģ��
 * @author moyang
 *
 */
public class BagOfWords {
	
	public static void main(String[] args) throws IOException, SQLException {
		Vector<String> characterList = getCharacterList("C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt",
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Ｏ��.txt") ;   // ��ȡ���Ｏ��
		Map<String,Vector<KeyUtils>> bagOfWordsMap = ConstructBofWords("C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt", characterList) ;
	}
	
	/**
	 * ��ȡ�����б�,�����������еĴ������ȥ�ش�����д�����Ĵ��Ｏ��.txt�ļ�,��Ϊͳ�ƴ�����ִ���������
	 * @return Vector<String> ���ش����Vector����
	 * @throws IOException
	 */
	public static Vector<String> getCharacterList(String fileDir,String characterListDir) throws IOException
	{
		FileWriter fileWriter = new FileWriter(new File(characterListDir)) ;
		FileReader fileReader = new FileReader(new File(fileDir)) ;
	
		BufferedReader BR = new BufferedReader(fileReader) ;
		ArrayList<String> list = new ArrayList<String>() ;
		long count = 0 ;
		long count2 = 0 ;
		String line ;
		Vector<String> newVector = new Vector<String>();
		while((line=BR.readLine()) != null)
		{
			count ++ ;
			System.out.println("��ȡ���Ｏ�ϣ��������ı�����ӵ���" + count + "ƪ�ı�");
			String str[] = line.split(" ");
			for(int i=0; i<str.length; i++){
				list.add(str[i]) ;
			}
		}
		for(int i=0; i<list.size(); i++)
	    {
			count2 ++ ;
			System.out.println("�Դ������ȥ�ش��������˵�" + count2 + "������");
			if(!newVector.contains(list.get(i)))
				newVector.add(list.get(i));
	    }
		
		for(int i=0; i<newVector.size(); i++)
		{
			fileWriter.write(newVector.get(i));
			fileWriter.write("\n") ;
		}
		fileWriter.close();
		return newVector;  
	}
	
	//TODO  -----------------------�����ʴ�ģ��---------------------------------	
		/**
		 * �������ű� key=word,val= a list of pairs which consists of articleid ,and count, count=tf
		 * �ʴ���ģ�͵ĸ�ʽMap<String,Vector<KeyUtils>>������Ϊ�ôʣ�KeyUtils�еĵ�һ��int Ϊ���±�ţ��ڶ�����Ϊ�ڸ����г��ֵĴ�����
		 * Map<String,Vector<KeyUtils>>ͳ�Ƶ������������Щ�����г��֣����ֹ����Ρ�
		 * @param textDir             -------->  ���Ԥ������ı����ݵ��ļ� 
		 * @param characterFeatureDir -------->  ��Ŵ����б���ļ�
		 * @return  Map<String,Vector<KeyUtils>>        ���شʴ�ģ��Map , ��ʽ���£�
		 * 												Map{ ���� : Vector[KeyUtils(���±��1�����ִ���1) , KeyUtils(���±��2�����ִ���2)......]  }
		 * @throws SQLException 
		 * @throws IOException 
		 */
		public static Map<String,Vector<KeyUtils>> ConstructBofWords(String textDir,Vector<String> characterList) throws IOException, SQLException
		{
			// ���ʴ�ģ����ص�����
			FileWriter mapWriter = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/�ʴ�.txt")) ;
			
			
			Vector vec = new Vector() ;
			ArrayList list3 = new ArrayList() ;
			ArrayList tmpList = new ArrayList() ; 
			HashMap<String,Vector<KeyUtils>> myMap = new HashMap<String,Vector<KeyUtils>>() ;
			int articleNo = 0 ; //���±��
			int timeOfCharacter = 0 ;//������ִ���
			
			
			ArrayList<String[]> tempList = new ArrayList<String[]>();//ÿƪ���µĴ������һ��String[]���棬��List��ʽ����
			
			// ��ȡÿƪ�ı������ݣ��Ѿ����ִʴ���ͣ�ôʴ��������ַ�����
			FileReader fileReader = new FileReader(new File(textDir)); 
			BufferedReader BR = new BufferedReader(fileReader) ;
			String  line;
			StringBuffer buffer = new StringBuffer();
			int count = 0 ;
			while((line=BR.readLine()) != null)
			{
				buffer.append(line); //�õ����˺���ַ���
				String[] resultTxtString = buffer.toString().split(" ") ;//�зֳ������String[]����
				tempList.add(resultTxtString) ;
				buffer.delete(0,buffer.length()-1);
			}	
			
			System.out.println("��ʼ�������ű�....");
			for(int i=0 ;i<characterList.size() ;i++) // -----------����ÿ������-------------
			{
				System.out.println("����ʼͳ�Ƶ�" + (i+1) + "������"); 
				//ͳ�ƴ�����ִ���
				String character = characterList.get(i) ;
				for(int j=0; j<tempList.size(); j++) // ------------����ÿƪ����
				{
					String[] str = tempList.get(j) ;
					for(int k=0; k<str.length; k++) //  -------------����ÿƪ�����е�ÿ����
					{
						if(character.equals(str[k]))
						{
							timeOfCharacter ++ ;
//							count = timeOfCharacter ;
						}
					}
					if(vec == null)
					{
						vec = new Vector() ;
					}
					if(timeOfCharacter != 0)
					{
						vec.add(new KeyUtils(j+1,timeOfCharacter)) ;
					}
					myMap.put(character,vec) ;
					timeOfCharacter = 0 ; 
				}
				vec = null ;
			}
			
			int showInHowManyArticles = 0 ;//ͳ��ÿ�����ڶ���ƪ�����г��ֹ�
			Set<Entry<String, Vector<KeyUtils>>> set = myMap.entrySet() ;
			Iterator<Entry<String, Vector<KeyUtils>>> it = set.iterator() ;
			while(it.hasNext())
			{
				count ++ ;
				System.out.println("���ʴ�д���ļ�����ʼд���" + count + "������");
				Entry<String, Vector<KeyUtils>> entry = it.next() ;
				Vector<KeyUtils> list2 = entry.getValue() ;
//				System.out.println("list2�Ĵ�С:" + list2.size()); 
				mapWriter.write(entry.getKey() + " :");
				for(int i=0; i<list2.size(); i++)
				{
					showInHowManyArticles ++ ; //�ôʳ��ֵ�������+1
					mapWriter.write(list2.get(i).getX() + " " + list2.get(i).getY() + ";"); //���ô�ͳ�ƵĽ��д���ļ���Ӧ��
				}
				mapWriter.write(" " + showInHowManyArticles); 
				mapWriter.write("\n") ;
				mapWriter.flush();
				showInHowManyArticles = 0 ;
			}	
			
			mapWriter.close();
			return myMap ;
		}
}

package muyanmoyang.fpgrowth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
 
public class FpTree {
     
    private static int support = 100;
    
    public static void main(String[] args) throws IOException{
    	long start = System.currentTimeMillis() ;
        //���ļ��ж�ȡ�������ݼ�
    	FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/Ƶ���.txt")) ;
        String file = "C:/Users/Administrator/Desktop/����(��С������ݼ�)/textWithWordNum.txt";
        Iterator<String> lineIte = FileUtils.lineIterator(new File(file));
        List<List<String>> transactions = new ArrayList<List<String>>();
        while(lineIte.hasNext()){
            String line = lineIte.next();
            if(StringUtils.isNotEmpty(line)){
                String[] subjects = line.split(" ");
                List<String> list = new ArrayList<String>(Arrays.asList(subjects));
                transactions.add(list);
            }
        }
        //��ʼһ��Ƶ��ģʽ��
        List<String> frequences = new LinkedList<String>();
        //��ʼ�ݹ�
        digTree(transactions,frequences,writer);
        writer.close(); 
        long end = System.currentTimeMillis() ;
        System.out.println("��ʱ��" + (end -start)+ "����......") ;
    }
     
    public static void digTree(List<List<String>> transactions,List<String> frequences,FileWriter writer) throws IOException{
    	
        //ɨ���������ݼ������� 
        final Map<String,Integer> sortedMap = scanAndSort(transactions);
        //û��������֧����С֧�ֶ��ˣ�����ֹͣ��
        if(sortedMap.size() == 0){
            return;
        }
        Map<String,List<TreeNode>> index = new HashMap<String,List<TreeNode>>();
        TreeNode root = buildTree(transactions,index,sortedMap);
 
        //����ʼ��������͵��ʼ �������ģʽ�����ݹ��ھ�
        List<String> headTable = new ArrayList<String>(sortedMap.keySet());
        Collections.sort(headTable,new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                int i = sortedMap.get(o2)-sortedMap.get(o1); 
                return i != 0 ? i : o1.compareTo(o2);
            }});
        //����ͷ�����һ�ʼ�ھ�
        for(int i=headTable.size()-1;i>=0;i--){
            String subject = headTable.get(i);
            List<List<String>> frequentModeBases = extract(index.get(subject),root);
             
            LinkedList<String> nextFrequences = new LinkedList<String>(frequences);
            nextFrequences.add(subject);
            if(nextFrequences.size()>=3/*>1 && nextFrequences.size()<5*/){
            	writer.write(StringUtils.join(nextFrequences,",")+"\t"+sortedMap.get(subject)) ;
            	writer.write("\n") ;
            	writer.flush();
                System.out.println(StringUtils.join(nextFrequences,",")+"\t"+sortedMap.get(subject));
            }
            digTree(frequentModeBases,nextFrequences,writer);
        }
    }
     
    /**
     * �ھ�һ���������Ƶ��ģʽ��
     * @param list
     * @param root
     * @return
     */
    public static List<List<String>> extract(List<TreeNode> list,TreeNode root){
        List<List<String>> returnList = new ArrayList<List<String>>();
        for(TreeNode node:list){
            TreeNode parent = node.getParent();
            if(parent.getCount() != -1){
                ArrayList<String> tranc = new ArrayList<String>();
                while(parent.getCount() != -1){
                    tranc.add(parent.getName());
                    parent = parent.getParent();
                }
                for(int i=0;i<node.getCount();i++){
                    returnList.add(tranc);
                }
            }
        }
        return returnList;
    }
     
    /**
     * ����pf��
     * @param file
     * @param index
     * @param sortedMap
     * @return
     * @throws IOException
     */
    public static TreeNode buildTree(List<List<String>> transactions,
            Map<String,List<TreeNode>> index,
            final Map<String,Integer> sortedMap){
        TreeNode root = new TreeNode(null,"root",-1);
        for(List<String> subjects:transactions){
            Iterator<String> ite = subjects.iterator();
            while(ite.hasNext()){
                String subject = ite.next();
                if(!sortedMap.containsKey(subject)){
                    ite.remove();
                }
            }
            Collections.sort(subjects,new Comparator<String>(){
                @Override
                public int compare(String o1, String o2) {
                    int i = sortedMap.get(o2)-sortedMap.get(o1); 
                    return i != 0 ? i : o1.compareTo(o2);
                }});
             
            TreeNode current = root;
            for(int i=0;i<subjects.size();i++){
                String subject = subjects.get(i);
                TreeNode next = current.findChild(subject);
                if(next == null){
                    TreeNode newNode = new TreeNode(current,subject,1);
                    current.addChild(newNode);
                    current = newNode;
                    List<TreeNode> thisIndex = index.get(subject);
                    if(thisIndex == null){
                        thisIndex = new ArrayList<TreeNode>();
                        index.put(subject, thisIndex);
                    }
                    thisIndex.add(newNode);
                }else{
                    next.incrementCount(1);
                    current = next;
                }
            }
        }
        return root;
    }
     
    /**
     * ɨ������
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String,Integer> scanAndSort(List<List<String>> transactions){
        Map<String,Integer> map = new HashMap<String,Integer>();
        //�յľͲ�ɨ��
        if(transactions.size()==0){
            return map;
        }
        for(List<String> basket:transactions){
            for(String subject:basket){
                Integer count = map.get(subject);
                if (count == null) {
                    map.put(subject, 1);
                } else {
                    map.put(subject, count + 1);
                }
            }
        }
        Iterator<Entry<String,Integer>> ite = map.entrySet().iterator();
        while(ite.hasNext()){
            Entry<String,Integer> entry = ite.next();
            if(entry.getValue() < support){
                ite.remove();
            }
        }
        return map;
    }
}
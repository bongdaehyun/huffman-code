package huffman;

import java.io.*;
import java.util.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

class Node // 허프만 트리, 힙 구조에서 쓰이는 노드 객체
{
   public int freq; //빈도수
   public char alphabet;//알파벳
   public Node leftNode;//왼쪽 자식노드
   public Node rightNode;//오른쪽 자식노드
   
   public StringBuffer huffmancode;//허프만 부호화 코드를 저장하는 스트링 버퍼
   
   public Node()
   {
	   huffmancode=new StringBuffer();
   }
   public Node(int freq, char alphabet)
   {
      this.freq = freq;
      this.alphabet = alphabet;
      leftNode = rightNode = null;
      huffmancode=new StringBuffer();
   }
   public void setHuffmanCode(String code){huffmancode.append(code);}
   public StringBuffer getHuffmanCode(){return huffmancode;}
}

class MinHeap // 최소 힙
{
   private ArrayList<Node> heap = new ArrayList<Node>();   

   public MinHeap()
   {
      heap.add(null);
   }
   public void insert(Node n)
   {
      heap.add(n);

      int childPos = heap.size()-1;
      int parentPos = childPos/2;

      // freq를 기준으로 heap 구성
      while(parentPos >= 1 && heap.get(childPos).freq < heap.get(parentPos).freq)
      {
         Collections.swap(heap, childPos, parentPos);
         childPos = parentPos;
         parentPos = childPos/2;
      }
   }
   // heap이 비어있으면 true
   public boolean isEmpty()
   {
      return (heap.size() <= 1);
   }
   // 최소 노드 반환.
   public Node getMinNode()
   {
      if(isEmpty()) return null; // heap이 비어있을 경우

      Node min = heap.get(1);

      int top = heap.size()-1;

      heap.set(1, heap.get(top));
      heap.remove(top); // 맨마지막 원소로 대체

      int parentPos = 1;
      int leftPos = parentPos*2;
      int rightPos = parentPos*2 + 1;

     
      while(leftPos <= heap.size()-1)
      {
         int targetPos;
         if(rightPos > heap.size()-1) // 오른쪽 자식이 없는 경우
         {
            if(heap.get(leftPos).freq >= heap.get(parentPos).freq) // 왼쪽 자식이 더 크면 종료
               break;
            targetPos = leftPos;
         }
         else // 왼쪽 오른쪽 전부 있는 경우
         {
            if(heap.get(leftPos).freq >= heap.get(parentPos).freq &&
                  heap.get(rightPos).freq >= heap.get(parentPos).freq)
                  break; // 두 자식 노드가 더 크거나 같으면 종료
            
            if(heap.get(leftPos).freq>heap.get(rightPos).freq)   
                targetPos=leftPos;
            else
                targetPos=rightPos;
         }
         Collections.swap(heap, targetPos, parentPos);
         parentPos = targetPos;
         leftPos = parentPos*2;
         rightPos = parentPos*2 + 1;
      }
      return min;
   }
}

public class HuffmanTree {
   
   public  HashMap<Character, Integer> huffmantable = new HashMap<Character, Integer>();
   public  HashMap<Character, String> hufcode = new HashMap<Character,String>();
   public  Node rootnode=null;
   String result=new String();//허프만 부호화 코드 저장
   int textsize=0;
 
   // 알파벳 빈도수 count하는 함수
   public  void countfreq(JTextField filename, JTextArea infor)
   {
      try {
        //파일 읽어오기
    	 FileReader file = new FileReader(filename.getText());
         BufferedReader in = new BufferedReader(file,2000);
         
         int c;
         while((c=in.read())!=-1)//문자 하나씩 읽습니다.
         {
        	 if( huffmantable.containsKey((char)c))  
                 huffmantable.put((char)c,  huffmantable.get((char)c)+1);
              else  
                 huffmantable.put((char)c, 1);
         }
      }
      catch(IOException e) {
          System.err.println(e); // 에러가 있다면 메시지 출력
      }
      printfreq(infor);
   }
   public void printfreq( JTextArea infor)
   {
      for(char key:huffmantable.keySet())
      {
         if (huffmantable.get(key) != null) {
            if ((int)key == 32) {
               infor.append("-Space-\t");
            } else if ((int)key == 13) {
               infor.append("-Enter-\t");
            } else if ((int)key == 10) {
               infor.append("-Newline-\t");
            } else if ((int)key == 9) {
               infor.append("-Tab-\\t");
            } else {
               infor.append(key+"\t");
            }      
            infor.append(huffmantable.get(key)+"\n");         
         }
      }
   }
   public  void makeHuffmanTree()
   {
      MinHeap mh = new MinHeap();

      if(huffmantable.isEmpty()) // 빈도 수 센 것이 없으면  return
         return;

      // 최소 힙에 각각의 빈도 수 및 알파벳을 저장합니다.
      for(char key : huffmantable.keySet())
         mh.insert(new Node(huffmantable.get(key), key));

      while(true)
      {
         // 최소 노드 2개 추출
         Node leftChild = mh.getMinNode();
         Node rightChild = mh.getMinNode();

         // 새로운 부모 노드를 만듭니다. 부모 노드는 양쪽 자식의 freq의 합 입니다.
         rootnode = new Node(leftChild.freq+rightChild.freq, '.');

         rootnode.leftNode = leftChild;
         rootnode.rightNode = rightChild;

         if(mh.isEmpty()) return; // 힙이 비어있으면 huffman 트리의 완성.

         mh.insert(rootnode);
      }
   }
    
   public void makehuffmancode(Node Root)
   {
      // left를 탐색할 경우 0을 출력하고, right를 탐색할 경우 1을 출력을 합니다.
      // 단말 노드를 만났을 경우, 즉, left right 모두 null일 경우 단말 노드의 character를 출력합니다.
	 
      if(Root.leftNode != null)
      {   	 
    	  Root.leftNode.setHuffmanCode(Root.getHuffmanCode()+"0");//왼쪽이므로 허프만 코드에 0을 추가    	      
    	  makehuffmancode(Root.leftNode);//왼쪽 탐색
      }
      if(Root.rightNode != null)
      {   	 
    	 Root.rightNode.setHuffmanCode(Root.getHuffmanCode()+"1");//오른쪽이므로 허프만 코드에 1을 추가       
    	 makehuffmancode(Root.rightNode);//오른쪽 탐색
      }

      if(Root.leftNode == null && Root.rightNode == null) // 단말 노드를 만났을 경우
      {
       hufcode.put(Root.alphabet,Root.getHuffmanCode().toString());// hufcode에 노드의 문자와 노드의 허프만 코드를 저장
      }
   }
  
   public void makecode(JTextField filename, JTextArea infor)
   {
      try
      {
         FileReader file = new FileReader(filename.getText());
           int c;         
            infor.append("--------문자 빈도수------------\n");
            countfreq(filename,infor);   
            makeHuffmanTree();//허프만 트리 생성
            makehuffmancode(rootnode);//허프만 트리를 이용한 허프만 부호화 코드 생성
            infor.append("--------허프만 부호화 코드------\n");
           while ((c = file.read()) != -1)
            {
               textsize++;
               result+=hufcode.get((char)c);//허프만 부호화 코드 저장
               infor.append(hufcode.get((char)c));//허프만 부호화 코드 출력
               if(c==10)//개행이면 출력하고 개행
               { 
                  infor.append("\n");
               }
               
            }
           incoding incode=new incoding(result,filename,infor,huffmantable);
           incode.makebit(filename, infor);
           int zipsize=incode.textsize(filename, infor);
           int rate=(1-(zipsize/textsize))*100;
           infor.append("\n업축률"+rate+"%");
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }             
   } 

}
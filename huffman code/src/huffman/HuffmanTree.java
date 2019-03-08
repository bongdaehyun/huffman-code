package huffman;

import java.io.*;
import java.util.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

class Node // ������ Ʈ��, �� �������� ���̴� ��� ��ü
{
   public int freq; //�󵵼�
   public char alphabet;//���ĺ�
   public Node leftNode;//���� �ڽĳ��
   public Node rightNode;//������ �ڽĳ��
   
   public StringBuffer huffmancode;//������ ��ȣȭ �ڵ带 �����ϴ� ��Ʈ�� ����
   
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

class MinHeap // �ּ� ��
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

      // freq�� �������� heap ����
      while(parentPos >= 1 && heap.get(childPos).freq < heap.get(parentPos).freq)
      {
         Collections.swap(heap, childPos, parentPos);
         childPos = parentPos;
         parentPos = childPos/2;
      }
   }
   // heap�� ��������� true
   public boolean isEmpty()
   {
      return (heap.size() <= 1);
   }
   // �ּ� ��� ��ȯ.
   public Node getMinNode()
   {
      if(isEmpty()) return null; // heap�� ������� ���

      Node min = heap.get(1);

      int top = heap.size()-1;

      heap.set(1, heap.get(top));
      heap.remove(top); // �Ǹ����� ���ҷ� ��ü

      int parentPos = 1;
      int leftPos = parentPos*2;
      int rightPos = parentPos*2 + 1;

     
      while(leftPos <= heap.size()-1)
      {
         int targetPos;
         if(rightPos > heap.size()-1) // ������ �ڽ��� ���� ���
         {
            if(heap.get(leftPos).freq >= heap.get(parentPos).freq) // ���� �ڽ��� �� ũ�� ����
               break;
            targetPos = leftPos;
         }
         else // ���� ������ ���� �ִ� ���
         {
            if(heap.get(leftPos).freq >= heap.get(parentPos).freq &&
                  heap.get(rightPos).freq >= heap.get(parentPos).freq)
                  break; // �� �ڽ� ��尡 �� ũ�ų� ������ ����
            
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
   String result=new String();//������ ��ȣȭ �ڵ� ����
   int textsize=0;
 
   // ���ĺ� �󵵼� count�ϴ� �Լ�
   public  void countfreq(JTextField filename, JTextArea infor)
   {
      try {
        //���� �о����
    	 FileReader file = new FileReader(filename.getText());
         BufferedReader in = new BufferedReader(file,2000);
         
         int c;
         while((c=in.read())!=-1)//���� �ϳ��� �н��ϴ�.
         {
        	 if( huffmantable.containsKey((char)c))  
                 huffmantable.put((char)c,  huffmantable.get((char)c)+1);
              else  
                 huffmantable.put((char)c, 1);
         }
      }
      catch(IOException e) {
          System.err.println(e); // ������ �ִٸ� �޽��� ���
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

      if(huffmantable.isEmpty()) // �� �� �� ���� ������  return
         return;

      // �ּ� ���� ������ �� �� �� ���ĺ��� �����մϴ�.
      for(char key : huffmantable.keySet())
         mh.insert(new Node(huffmantable.get(key), key));

      while(true)
      {
         // �ּ� ��� 2�� ����
         Node leftChild = mh.getMinNode();
         Node rightChild = mh.getMinNode();

         // ���ο� �θ� ��带 ����ϴ�. �θ� ���� ���� �ڽ��� freq�� �� �Դϴ�.
         rootnode = new Node(leftChild.freq+rightChild.freq, '.');

         rootnode.leftNode = leftChild;
         rootnode.rightNode = rightChild;

         if(mh.isEmpty()) return; // ���� ��������� huffman Ʈ���� �ϼ�.

         mh.insert(rootnode);
      }
   }
    
   public void makehuffmancode(Node Root)
   {
      // left�� Ž���� ��� 0�� ����ϰ�, right�� Ž���� ��� 1�� ����� �մϴ�.
      // �ܸ� ��带 ������ ���, ��, left right ��� null�� ��� �ܸ� ����� character�� ����մϴ�.
	 
      if(Root.leftNode != null)
      {   	 
    	  Root.leftNode.setHuffmanCode(Root.getHuffmanCode()+"0");//�����̹Ƿ� ������ �ڵ忡 0�� �߰�    	      
    	  makehuffmancode(Root.leftNode);//���� Ž��
      }
      if(Root.rightNode != null)
      {   	 
    	 Root.rightNode.setHuffmanCode(Root.getHuffmanCode()+"1");//�������̹Ƿ� ������ �ڵ忡 1�� �߰�       
    	 makehuffmancode(Root.rightNode);//������ Ž��
      }

      if(Root.leftNode == null && Root.rightNode == null) // �ܸ� ��带 ������ ���
      {
       hufcode.put(Root.alphabet,Root.getHuffmanCode().toString());// hufcode�� ����� ���ڿ� ����� ������ �ڵ带 ����
      }
   }
  
   public void makecode(JTextField filename, JTextArea infor)
   {
      try
      {
         FileReader file = new FileReader(filename.getText());
           int c;         
            infor.append("--------���� �󵵼�------------\n");
            countfreq(filename,infor);   
            makeHuffmanTree();//������ Ʈ�� ����
            makehuffmancode(rootnode);//������ Ʈ���� �̿��� ������ ��ȣȭ �ڵ� ����
            infor.append("--------������ ��ȣȭ �ڵ�------\n");
           while ((c = file.read()) != -1)
            {
               textsize++;
               result+=hufcode.get((char)c);//������ ��ȣȭ �ڵ� ����
               infor.append(hufcode.get((char)c));//������ ��ȣȭ �ڵ� ���
               if(c==10)//�����̸� ����ϰ� ����
               { 
                  infor.append("\n");
               }
               
            }
           incoding incode=new incoding(result,filename,infor,huffmantable);
           incode.makebit(filename, infor);
           int zipsize=incode.textsize(filename, infor);
           int rate=(1-(zipsize/textsize))*100;
           infor.append("\n�����"+rate+"%");
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }             
   } 

}
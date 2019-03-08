package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class decoding {
	private JTextField filename;
	private JTextArea infor;
	
	decoding(JTextField filename,JTextArea infor)
	{
		this.filename=filename;
		this.infor=infor;
	}
	
	public void filedecode(JTextField filename,JTextArea infor)
	{
		   String name=filename.getText();
		   HuffmanTree tree=new HuffmanTree();
		   int buf;//��Ʈ�� ������ ����
		   int shift=0;//��Ʈ ��ġ�� ��� ����
		   String recode = new String();//���ڸ� ������ ����
	       tree.countfreq(filename,infor);
	       tree.makeHuffmanTree();//������ Ʈ������
	       infor.setText(" ");
	       Node tempNode=tree.rootnode;//��Ʈ��带 ����
	       
		   try 
		   {
			   FileReader file = new FileReader(name+"code(zip).txt");
			   FileWriter unzip = new FileWriter (name+"(unzip).txt");
			   BufferedReader in=new BufferedReader(file,2000);
			   BufferedWriter out = new BufferedWriter(unzip, 2000);
	       		
				while((buf=in.read())!=-1)//������ ���ڸ� �ϳ� �о�´�
	       		{	
	       			while(shift!=7)//�ƽ�Ű �ڵ�� �� 8��Ʈ�̱������� ���� �Ǿ� ��Ʈ�� �Ⱦ��Ƿ� 7�̸� ����
	       			{       				
	       				
	       				if((buf &(64>>>shift))!=0)//�Ѻ�Ʈ�� �о 1�϶�
	       				{	       					
	       						tree.rootnode=tree.rootnode.rightNode;//������ �ڽ����� �̵�
	    						if(tree.rootnode.rightNode==null)//������ �ڽ��� ���ٸ�
	    						{
	    							recode+=tree.rootnode.alphabet; // �ܸ� ����� ���� ����							
	    							tree.rootnode=tempNode;//�ٽ� ��Ʈ ���� ����							
	    						}
	       				}				
	       				else if((buf &(64>>>shift))==0)//�Ѻ�Ʈ�� �о 0�϶�
	       				{	     					
	       						tree.rootnode=tree.rootnode.leftNode;//���� ���� �̵�
	    						if(tree.rootnode.leftNode==null)//���� ��尡 ���ٸ�
	    						{
	    							recode+=tree.rootnode.alphabet;// �ܸ� ����� ���� ����
	    							tree.rootnode=tempNode; //�ٽ� ��Ʈ���� �ٲ�     						
	    						}       				
	       				}       				
	       				shift++;//��Ʈ�� ��ġ ���� �̵�	      				  	
	       			} 	
	       			if(shift==7)//���� ��Ʈ�� ���ٸ�
	       			{
	       					shift=0;//��Ʈ��ġ �ٽ� 0���� �ʱ�ȭ
	       			}
	       		
	       		}
				out.write(recode);
		   }
		   catch(IOException e) 
		   {
			   System.out.print("������ ���������ʽ��ϴ�.");
			   infor.append("������ ���������ʽ��ϴ�.");
		   }
		   infor.append(recode);//���� ������ ���� ��� 
		   
	}
}

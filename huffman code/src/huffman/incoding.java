package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

public class incoding {
	private String code;
	private JTextField filename;
	private JTextArea infor;
	private HashMap <Character,Integer> huffmantable;
	
	incoding(String code,JTextField filename, JTextArea infor,HashMap <Character,Integer> huffmantable)
	{
		this.code=code;
		this.filename=filename;
		this.infor=infor;
		this.huffmantable=huffmantable;
	}
	
	public void makebit(JTextField filename, JTextArea infor)
	{
		 int size=code.length();//������ �ڵ��� ������
	     int buffer=0;//��Ʈ�� ������ ����
	     int bitshift=6;
	     String name=filename.getText();
	     
	     try{  
	    	   
	         FileWriter zip = new FileWriter (name+"code(zip).txt");//������ �ڵ带 ����
	         FileWriter table = new FileWriter (name+"table(zip).txt");//������ ���̺� ����
	         BufferedWriter out = new BufferedWriter(zip, 2000);
	         BufferedWriter tableout = new BufferedWriter(table, 2000);
	         
	         for(char key:huffmantable.keySet())
	         {
	        	 tableout.write(key);//���� ����
	        	 tableout.write(huffmantable.get(key));//�󵵼� ����
	        	 tableout.newLine();
	         }
	         
	         for(int i=0;i<size;i++)
	         {
	            if(code.charAt(i) == '1')//������ �ڵ� �� 1�϶��� ����Ʈ 
	            {
	               buffer=(buffer |(1<<bitshift));// 00000001�� �������� ����Ʈ   
	            }
	            bitshift--;
	            if(bitshift==-1)//8��Ʈ�� ������
	            {
	               out.write(buffer);//8��Ʈ�� �Ǹ� ����
	               buffer=0;//�ʱ�ȭ
	               bitshift=6;
	            }
	            if(i==size-1)
	            {
	            	out.write(buffer);//������ ���ڴ� �׳� ����
	            }
	         }       
	        out.close();
	     	}
	     	catch (IOException e)
	     	{
	              System.out.println("������ �����ϴ�.");
	     	}
	}
	
	public int textsize(JTextField filename,JTextArea infor)
	{
		int size=0;
		try {
			FileReader zip=new FileReader(filename.getText()+"code(zip).txt");
			BufferedReader in=new BufferedReader(zip,2000);
			int c;
			while((c=in.read())!=-1)
			{
				size++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
}

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
		   int buf;//비트를 저장할 변수
		   int shift=0;//비트 위치를 잦는 변수
		   String recode = new String();//문자를 저장할 변수
	       tree.countfreq(filename,infor);
	       tree.makeHuffmanTree();//허프만 트리생성
	       infor.setText(" ");
	       Node tempNode=tree.rootnode;//루트노드를 저장
	       
		   try 
		   {
			   FileReader file = new FileReader(name+"code(zip).txt");
			   FileWriter unzip = new FileWriter (name+"(unzip).txt");
			   BufferedReader in=new BufferedReader(file,2000);
			   BufferedWriter out = new BufferedWriter(unzip, 2000);
	       		
				while((buf=in.read())!=-1)//파일의 문자를 하나 읽어온다
	       		{	
	       			while(shift!=7)//아스키 코드는 총 8비트이긴하지만 왼쪽 맨앞 비트는 안쓰므로 7이면 종료
	       			{       				
	       				
	       				if((buf &(64>>>shift))!=0)//한비트씩 읽어서 1일때
	       				{	       					
	       						tree.rootnode=tree.rootnode.rightNode;//오른쪽 자식으로 이동
	    						if(tree.rootnode.rightNode==null)//오른쪽 자식이 없다면
	    						{
	    							recode+=tree.rootnode.alphabet; // 단말 노드의 문자 저장							
	    							tree.rootnode=tempNode;//다시 루트 노드로 스왑							
	    						}
	       				}				
	       				else if((buf &(64>>>shift))==0)//한비트씩 읽어서 0일때
	       				{	     					
	       						tree.rootnode=tree.rootnode.leftNode;//왼쪽 노드로 이동
	    						if(tree.rootnode.leftNode==null)//왼쪽 노드가 없다면
	    						{
	    							recode+=tree.rootnode.alphabet;// 단말 노드의 문자 저장
	    							tree.rootnode=tempNode; //다시 루트노드로 바꿈     						
	    						}       				
	       				}       				
	       				shift++;//비트의 위치 변수 이동	      				  	
	       			} 	
	       			if(shift==7)//남은 비트가 없다면
	       			{
	       					shift=0;//비트위치 다시 0으로 초기화
	       			}
	       		
	       		}
				out.write(recode);
		   }
		   catch(IOException e) 
		   {
			   System.out.print("파일이 존재하지않습니다.");
			   infor.append("파일이 존재하지않습니다.");
		   }
		   infor.append(recode);//압축 해제된 문자 출력 
		   
	}
}

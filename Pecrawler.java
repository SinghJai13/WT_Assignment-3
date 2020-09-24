import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Pecrawler {

	public class Q_Node{
		String str;
		int ht;
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 XSSFWorkbook workbook = new XSSFWorkbook();
		 XSSFSheet spreadsheet_text = workbook.createSheet( " Text ");
		 XSSFSheet spreadsheet_links = workbook.createSheet( " Links ");
		 XSSFSheet spreadsheet = workbook.createSheet(" Fac data ");
		 XSSFRow r1;
		 XSSFRow r2;
		 XSSFRow r3;
		
		 Queue<Q_Node> q=new LinkedList<>(); 
		 String source = "https://www.pec.ac.in/";

		 Pecrawler outer = new Pecrawler();
		 Pecrawler.Q_Node inner = outer.new Q_Node();
		 inner.str=source;
		 inner.ht=1;
		 int counter_1=0;
		 int counter_2=0;
		 q.add(inner);
		 HashSet<String> hs = new HashSet<String>();
		 hs.add(source);

		
		 while(!q.isEmpty())
		 {
			 
			 Q_Node popped=q.poll();
			 //url poped out
			 String url=popped.str;
			 
			
			 
			 
			 
	      Document document = Jsoup.connect(url).get();
	
	      //exploring the links in the url
	      Elements links=document.select("a[href]");
	      for(Element link: links)
	      {
	           String relHref = link.attr("href"); 
	           String absHref = link.attr("abs:href");
	           
	           int len=absHref.length();
	           
	           if(!hs.contains(absHref))
	           {
	           
	        	   counter_2++;
	               r2=spreadsheet_links.createRow(counter_2);
	    		   int cellid = 0;
		           
	    		   Cell cell_1 = r2.createCell(cellid);
	    		   cellid++;
		           cell_1.setCellValue(link.text());
		           Cell cell_2 = r2.createCell(cellid);
		           cellid++;
		           cell_2.setCellValue(absHref);
	           }
		        if(popped.ht<3&&relHref.length()>1)    
		        {		
		        	if(!hs.contains(absHref))
		        	{
		        		if(relHref.charAt(0)!='#')
		        		{
		        			if(absHref.startsWith(source))
		        			{
		        				if(absHref.charAt(len-4)!='.')
		        				{
		        					if(!absHref.endsWith(".docx")&&!absHref.endsWith(".jpeg")&&!absHref.endsWith(".pdf")&&!absHref.endsWith(".png"))
									{
											 Pecrawler out_temp = new Pecrawler();
										 Pecrawler.Q_Node inner_temp = out_temp.new Q_Node();
										 inner_temp.str=absHref;
										 inner_temp.ht=popped.ht+1;
										 q.add(inner_temp);
										 hs.add(absHref);
									}
		        				}
		        			}
		        				
		        		}
		        	}		        	
		        }	           	           
	      }
	      
	      Elements paragraphs = document.getAllElements();
	      for(Element paragraph:paragraphs)
	      {	
	    	  
	    	  String value=paragraph.ownText();
	    	  if(value.length()!=0)
	    	  {	  
	    		  counter_1=counter_1+1;
	    		  r1 = spreadsheet_text.createRow(counter_1);
	    		  int cellid = 0;
		            Cell cell_1 = r1.createCell(cellid++);
		            cell_1.setCellValue(value);
		            Cell cell_2 = r1.createCell(cellid++);
		            cell_2.setCellValue(paragraph.tagName());		            	    		  	    		  	    
	    	  }
	      }
		 }

/*
	      String home = System.getProperty("user.home");
	
	      FileOutputStream out = new FileOutputStream(
	         new File(home+"\\Desktop\\Writesheet.xlsx"));
	      
	      workbook.write(out);
	      
	   
	*/      
	     
			 Queue<Q_Node> q1=new LinkedList<>(); 
			 
			 Pecrawler outer_f = new Pecrawler();
			 Pecrawler.Q_Node inner_f = outer.new Q_Node();
			 
			 inner_f.str=source;
			 inner_f.ht=1;
			 q1.add(inner_f);
		
				
			
			
			 String[] keywords= {"/departments","/department","/centres","centre","/faculty","/aero","/cse","/civil","/ee","/ece","/me","/metta","/pie","/applied-sciences"};
			int count=0;
			HashSet<String> hs1 = new HashSet<String>();
			hs1.add(source);
			while(!q1.isEmpty())
			{	
				
				 Q_Node popped=q1.poll();
				String url=popped.str;
				Document document = Jsoup.connect(url).get();
					

			
		
			
			 if(url.contains("fac"))
			 {
				 
			 
			 Elements text=document.select("div:has(>strong)");
			 for(Element t:text)
			 {
				 if(t.select("strong").size()>2)
			 {
				 count=count+1;

				 r3=spreadsheet.createRow(count);
				 
	   		  		int cellid = 0;
		            Cell cell_1 = r3.createCell(cellid++);
		            cell_1.setCellValue(t.select("strong").get(0).text());
		            
		            Cell cell_2 = r3.createCell(cellid++);
		            cell_2.setCellValue(t.select("strong").get(1).text());
		            
		            Cell cell_3 = r3.createCell(cellid++);
		            cell_3.setCellValue(t.select("strong").get(2).text());
		            
			 }
				 
			 }
			 }
			 else
			 {
				 Elements links=document.select("a[href]");
				 for(Element link:links)
				 {
					 String relHref = link.attr("href"); 
			         String absHref = link.attr("abs:href");
			         int len=absHref.length();
			         if(popped.ht<10)
			         {
			        	 if(relHref.length()>1)
			        		 if((!hs1.contains(absHref))&&(relHref.charAt(0)!='#')&&(absHref.startsWith(source))&&(absHref.charAt(len-4)!='.')&&(!absHref.endsWith(".docx"))&&(!absHref.endsWith(".jpeg")))
			        	{
			        	 for(int i=0;i<14;i++)
			        	 {
			        		if(absHref.endsWith(keywords[i])||absHref.contains("/fac/"))
			        		{
					       		 Pecrawler out_temp = new Pecrawler();
					    		 Pecrawler.Q_Node inner_f_temp = out_temp.new Q_Node();
					    		 inner_f_temp.str=absHref;
					    		 inner_f_temp.ht=popped.ht+1;
//					    		 System.out.println(absHref);
					    		 q1.add(inner_f_temp);
					    		 break;
			        		}
			        	 }
			    		 hs1.add(absHref);
			        	}
			         }
			         
			         
			         
			         
				 }
				 
			 }
			}
			
			

		      String home1 = System.getProperty("user.home");
		
		     FileOutputStream out = new FileOutputStream(
		      new File(home1+"\\Desktop\\Pecfac.xlsx"));
		      
		      workbook.write(out);
		 
		      System.out.println("Pecfac.xlsx written successfully");
		      out.close();
		}
	      

	}




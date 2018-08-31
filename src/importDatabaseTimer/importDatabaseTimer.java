package importDatabaseTimer;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class importDatabaseTimer {

@SuppressWarnings({ "deprecation", "unused" })
public static void main(String args[]){
		
		while(true){
		
		
		//Reads the data from temp.txt file
		List<String> insertThis = new ArrayList<String>();
		String path = "C:\\Users\\moual\\Desktop\\temp.txt";
		path = path.replace("\\", "/");
		// or
		path = path.replaceAll("\\\\", "/");
		
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				insertThis.add(line);
			}
			fileReader.close();
			System.out.println(stringBuffer.toString());
		} catch (IOException e) {e.printStackTrace();}
		
		
		//Connects to MySQL Database and inputs data read from test file into table
		try{
	
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/mydb?useSSL=false","dbadmin","password4");  
			//here mydb is database name, dbadmin is username and password is password4 
			Statement stmt=con.createStatement();
			
			//Starts from the first line of the text file
		    for(int i=0; i<insertThis.size(); i++){
		    	stmt.executeUpdate("INSERT INTO raw_table (tagID) VALUES (" + insertThis.get(i) + ")");
	         }
		    //Inserts into main_table of MySQL
		    int rs=stmt.executeUpdate("insert into main_table(tagID,date,overall_punches,current_punches) (select substring(tagID,5,5),max(date),sum(substring(tagID,2,3)),sum(substring(tagID,2,3)) from raw_table group by substring(tagID,5,5))");
			//remove old info from raw_table
		    int rs1=stmt.executeUpdate("delete from raw_table");
		    //remove old info from main_table
		    int rs2=stmt.executeUpdate("delete from store_table");
		    //Update main_table with new data
		    int rs3=stmt.executeUpdate("insert into store_table select tool_number,tagID,max(date),sum(overall_punches),date_sharpened,sum(current_punches) from main_table group by tagID");
		    //clears old main_table
		    int rs4=stmt.executeUpdate("delete from main_table");
		    //main_table takes on new data values from main_table for combining next combination of data values
		    int rs5=stmt.executeUpdate("insert into main_table select tool_number,tagID,max(date),sum(overall_punches),date_sharpened,sum(current_punches) from store_table group by tagID");
		    
					con.close();  
			}catch(Exception e){ System.out.println(e);}  
		
		
		//Deletes contents of text file
		try {
			FileUtils.write(new File("C:\\Users\\moual\\Desktop\\temp.txt"), "");
		} catch (IOException e) {e.printStackTrace();}
		
		
		//Pauses program for an amount of time
		try {
			Thread.sleep(60 * 1000); //pauses for the specified amount of time(seconds)
		} catch (InterruptedException e) {e.printStackTrace();} 
	}  
		
}
	
}

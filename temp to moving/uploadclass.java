package datatest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class mainclass {
	
	static Connection conn = null;

    public static void main(String[] args) {
    	
        String url = "jdbc:mysql://194.81.104.22:3306/db13432608";
        String user = "s13432608";
        String password = "ajam1994";
        String current = System.getProperty("user.dir");
        String filePath = current;
        String user_id = args[0];
        
        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            int x = 1;
            int checker = 1;
            while(x <=10)
            {
            filePath = current+"/"+ x + ".jpg";
            String sql = "INSERT INTO Live (image_content, user_id) values (?, ?)";
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
            InputStream inputStream = new FileInputStream(new File(filePath));
 
            statement.setBlob(1, inputStream);
            statement.setString(2, user_id);
            int row = statement.executeUpdate();
            if (row == 0) {
                checker = 0;
            }
            x++;
            }
            conn.close();
            System.out.print(""+checker);
        } catch (SQLException ex) {
        	System.out.print(0);
        } catch (IOException ex) {
        	System.out.print(0);
        }
        
    }
}


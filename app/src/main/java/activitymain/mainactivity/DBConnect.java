package activitymain.mainactivity; /**
 * Created by Simon on 15-12-2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect(){
        try{
            Class.forName("com.mysql.jbdc.Driver");
            con = DriverManager.getConnection("jbdc:mysql://localhost:3306/testdatabase",  "root", "" );
            st = con.createStatement();


        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
    }

    public ResultSet getData(String query) {
        try {
            return rs = st.executeQuery(query);

            /*System.out.println("Records from database");
            while(rs.next() == true) {
                int id= rs.getInt(1);
                String correctAntwoord = rs.getString(2);
                System.out.println(id + " " + correctAntwoord);
            }*/
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

}

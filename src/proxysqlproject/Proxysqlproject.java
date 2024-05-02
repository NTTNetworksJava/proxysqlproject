/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proxysqlproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.cj.jdbc.Driver;
//import com.mysql.jdbc.Driver;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author hai.lt
 */
public class Proxysqlproject {
    
    static Utility uti = new Utility();

    /**
     * @param args the command line arguments
     */
//    static String JDBC_URL = "jdbc:mysql://address=(protocol=tcp)(host=db-hcm-cluster-mysql.cluster-c5so2imeal7m.ap-southeast-1.rds.amazonaws.com)(port=3306)";
    static String reader_url = "database-1.cluster-c5so2imeal7m.ap-southeast-1.rds.amazonaws.com";
    static String JDBC_URL = "jdbc:mysql://"+reader_url+":3306/session";
    public static void main(String args[]) throws Exception{
        System.out.println("hello proxysqltesting");
        Driver driver = new Driver();
        Properties props = new Properties();
        ResultSet rs;
        String variable_value;
        Connection conn = null;
        

        props.put("useSSL", "false");
        props.put("autoReconnect", "true");
        props.put("user", "admin");
        props.put("password", "Hai.2024");
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("\n------------ MYSQL Connector/J and ProxySQL Testing ------------\n");
        Proxysqlproject project = new Proxysqlproject();
        project.insertSession(props);
//        for(int i = 0; i< 200; i++){
//            select(i, props);
//        }
//        return;
        
//        System.out.println("Trying connection...");
//        try {
////                    conn.get
//            conn = driver.connect(JDBC_URL, props);
//        }catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Connection Failed!");
//            System.out.println("Error cause: "+e.getCause());
//            System.out.println("Error message: "+e.getMessage());
//            return;
//        }

//        System.out.println("Connection established..."+conn);
 
//            for(int i=1; i <= 50; i++) {
//                System.out.println("\nQuery "+i+": ");
                // Read write query that can be performed ONLY on master server
//                System.out.println("Read Write query...");
//                try {
////                    String sql  = "\"select (select variable_value from information_schema.global_variables where variable_name=\\'hostname\\') || \\' on port \\' || (select variable_value from information_schema.global_variables where variable_name=\\'port\\') variable_value for update\"";
//                    String sql  = "SELECT @@server_id as server_id";
//                    rs = conn.createStatement().executeQuery(sql);
//                    while (rs.next()) {
//                        variable_value = rs.getString("server_id");
////                        variable_value = rs.getString("variable_value");
//                        System.out.println("variable_value["+i+"]["+conn+"] : " + variable_value);
//                    }
//                }catch (SQLException e) {
//                    System.out.println("Read/write query has failed...");
//                    e.printStackTrace();
//                }
//                try {
////                    String sql  = "\"select (select variable_value from information_schema.global_variables where variable_name=\\'hostname\\') || \\' on port \\' || (select variable_value from information_schema.global_variables where variable_name=\\'port\\') variable_value for update\"";
//                    String sql  = "insert into testreplica.user values(@@server_id,NOW(),"+i+")";
//                    conn.createStatement().executeUpdate(sql);
////                    rs = conn.createStatement().executeQuery(sql);
////                    while (rs.next()) {
////                        variable_value = rs.getString("server_id");
//////                        variable_value = rs.getString("variable_value");
////                        System.out.println("variable_value["+i+"]["+conn+"] : " + variable_value);
////                    }
//                }catch (SQLException e) {
//                    System.out.println("Read/write query has failed... Retry one time");
//                    e.printStackTrace();
//                    Thread.sleep(1000);
//                    String sql  = "insert into testreplica.user values(@@server_id,NOW(),"+i+")";
//                    conn.createStatement().executeUpdate(sql);                    
//                }
                // Read Only statement (that can also be done on master server if all slaves are down)
//                System.out.println("Read Only query...");
//                try {
//                    rs = conn.createStatement().executeQuery("select (select variable_value from information_schema.global_variables where variable_name=\'hostname\') || \' on port \' || (select variable_value from information_schema.global_variables where variable_name=\'port\') variable_value");
//                    while (rs.next()) {
//                        variable_value = rs.getString("variable_value");
//                        System.out.println("variable_value : " + variable_value);
//                    }
//                }catch (SQLException e) {
//                    System.out.println("Read only query has failed...");
//                    e.printStackTrace();
//                }

//                Thread.sleep(1000);
//            }
//        conn.close();        
    }
    
    private void insertSession(Properties props){
        
        try {
            Driver driver = new Driver();
//            Properties props = new Properties();
            ResultSet rs;
            String variable_value;
            Connection conn = null;
//            String JDBC_URL = "jdbc:mysql://address=(protocol=tcp)(host=172.168.12.129)(port=6033)/testreplica";

//            props.put("useSSL", "false");
//            props.put("autoReconnect", "true");
//            props.put("user", "stnduser");
//            props.put("password", "stnduser");
            System.out.println("\n------------ MYSQL Connector/J and ProxySQL Testing ------------\n");

            System.out.println("Trying connection...");
            Class.forName("com.mysql.jdbc.Driver");  
            conn = driver.connect(JDBC_URL, props);
                      
            
            long now = new Date().getTime();
            System.out.println("Start run generate session check duplicate ["+uti.getDatetimeMsec()+"]");                     
            int max = 19;
            int max_loop = 500000;
            TreeMap <BigInteger, Integer> list = new TreeMap();
//            int index = 0;
//            Client2 client  = new Client2();
            int count =0 ;
            for(int i = 0; i < max_loop; i++){
                BigInteger session = genBigInt(max);
                if(!list.containsKey(session)){
                    list.put(session, i);
                    String sql = "INSERT INTO `info` VALUE('"+i+"','"+session+"')";
                    conn.createStatement().executeUpdate(sql); 
                }else{
                    count ++;
                    int index = list.get(session);
                    System.out.println("Duplicate case ["+session+"]index->["+index+"] i->["+i+"]["+session.toString().length()+"]");                     
                }
                System.out.println("Gen random session ["+i+"]["+session+"]["+count+"]["+session.toString().length()+"]");                     
                Thread.sleep(50000);
            }
            long finish = new Date().getTime();
            System.out.println("Finish, list size is ["+list.size()+"]");
            System.out.println("Duplicate case count ["+count+"]["+uti.getDatetimeMsec()+"]["+(finish - now )/1000+"]");                     
            
                    
        } catch (Exception ex) {
//            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
            
        }        
        
    }
    public BigInteger genBigInt(int max){
        BigInteger gen = new BigInteger(80, new Random());
        String str_gen = gen.toString();
        str_gen = (str_gen.length() > max )? str_gen.substring(0, max): str_gen;
//        System.out.println("Very large random number after = "+str_gen+" ["+str_gen.length()+"]"); 
        BigInteger result = new BigInteger(str_gen);
        return result;
    }    
    private static void select(int i, Properties props){
        try{
            Thread.sleep(1000);
//            System.out.println("hello proxysqltesting ["+i+"]");
            Driver driver = new Driver();
//            Properties props = new Properties();
            ResultSet rs;
            String variable_value;
            Connection conn = null;
//            String JDBC_URL = "jdbc:mysql://address=(protocol=tcp)(host=172.168.12.129)(port=6033)/testreplica";

//            props.put("useSSL", "false");
//            props.put("autoReconnect", "true");
//            props.put("user", "stnduser");
//            props.put("password", "stnduser");
            System.out.println("\n------------ MYSQL Connector/J and ProxySQL Testing ------------\n");

            System.out.println("Trying connection...");
            Class.forName("com.mysql.jdbc.Driver");
            conn = driver.connect(JDBC_URL, props);
            
            System.out.println("Connection established...["+i+"]"+conn);
            try {
//                    String sql  = "\"select (select variable_value from information_schema.global_variables where variable_name=\\'hostname\\') || \\' on port \\' || (select variable_value from information_schema.global_variables where variable_name=\\'port\\') variable_value for update\"";
//                String sql  = "SELECT * from user order by info desc limit 5";
                String sql  = "SELECT @@server_id as server_id";
                rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
//                    variable_value = rs.getString("info");
                        variable_value = rs.getString("server_id");
                    System.out.println("variable_value : " + variable_value);
//                        sql = "insert into db1.test (`server_id`, `time`, `count`)values("+variable_value+", now(), "+i+");";
//                        conn.createStatement().executeUpdate(sql);                    
                }
                Thread.sleep(2000000);
//                    String sql  = "insert into testreplica.user values(@@server_id,NOW(),"+i+")";
//                    conn.createStatement().executeUpdate(sql);
            }catch (SQLException e) {
                System.out.println("Read/write query has failed...");
                e.printStackTrace();
            }
            conn.close();            
//            try {
//    //                    conn.get
//            }catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("Connection Failed!");
//                System.out.println("Error cause: "+e.getCause());
//                System.out.println("Error message: "+e.getMessage());
//                return;
//            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}

package proxysqlproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utility {

    private Properties prop = new Properties();
    private String logFolder = "log";        

    public String getDatetime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String datetime = dateFormat.format(cal.getTime()).toString();
        return datetime;
    }
    public String getYearMonth() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        String datetime = dateFormat.format(cal.getTime()).toString();
        return datetime;
    }
    public String getDatetimeMsec() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Calendar cal = Calendar.getInstance();
        String datetime = dateFormat.format(cal.getTime()).toString();
        return datetime;
    }
    public String getDatetimeCall() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar cal = Calendar.getInstance();
        String datetime = dateFormat.format(cal.getTime()).toString();
        datetime = datetime.replaceAll("-", "");
        datetime = datetime.replaceAll(" ", "-");
        return datetime;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String datetime = dateFormat.format(cal.getTime()).toString();
        return datetime;
    }
    public String getDate(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        String datetime = dateFormat.format(time);
        return datetime;
    }
    public String getDateSlash(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");        
        String datetime = dateFormat.format(time);
        return datetime;
    }
        
    public String getDateTime(long time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        String datetime = dateFormat.format(time);
        return datetime;
    }
    public Date getStrToDate(String date_time){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // Convert from String to Date
            Date date = dateFormat.parse(date_time);            
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public String getDateTimeFileName(long time){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");        
        String datetime = dateFormat.format(time);
        return datetime;
    }
        
    public String getDateTimeRecording(String date_time){
        String datetime = date_time.replaceAll("-", "");
        datetime = datetime.replaceAll(":", "");
        datetime = datetime.replaceAll(" ", "-");        
        return datetime;
    }
    public String secs_to_time(int time){
        DecimalFormat dFormat = new DecimalFormat("00");
        int hr = time/3600;
        time = time%3600;
        int min = time/60;
        int sec = time%60;
        String datetime = dFormat.format(hr)+":"+dFormat.format(min)+":"+dFormat.format(sec);
        return datetime;
    }

    public void log(String log){ 
        try{
            File folder = new File(logFolder+"/"+getYearMonth());
            folder.mkdirs();
            File child = new File(folder, getDate()+".log");
            child.createNewFile();
            Writer output;
            output = new BufferedWriter(new FileWriter(child, true));
            output.append(getDatetimeMsec() + "\t" + log + "\r\n");
            output.close();                                  
        }catch(Exception ex){}
    }
    public void debug(String log){ 
        try{
            File folder = new File(logFolder+"/"+"debug");
            folder.mkdirs();
            File child = new File(folder, getDate()+".log");
            child.createNewFile();
            Writer output;
            output = new BufferedWriter(new FileWriter(child, true));
            output.append(getDatetimeMsec() + "\t" + log + "\r\n");
            output.close();                                  
        }catch(Exception ex){}
    }
    
    public String getStackTrace(Exception ex){
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));   
        String str_error = errors.toString();            
        System.out.println("stack: "+str_error);   
        return str_error;
    }

    public String generateSession() {
        String temps = "QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz1234567890";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            sb.append(temps.charAt(rnd.nextInt(temps.length())));
        }
        return sb.toString();
    }
    
    public synchronized String generate_pause_session() {
        String temps = "abcdefghijklmn1234567890opqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            sb.append(temps.charAt(rnd.nextInt(temps.length())));
        }
        return sb.toString();
    }    
    
    public String generatePhone() {
        String temps = "1234567890";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 8; i++) {
            sb.append(temps.charAt(rnd.nextInt(temps.length())));
        }
        return sb.toString();
    }
    public String md5Generate(String input_text){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input_text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2*hash.length); 
            for(byte b : hash){ sb.append(String.format("%02x", b&0xff)); }
            return sb.toString();
        }catch(Exception ex){return null;}
    }    

    public void writeInfor(String fname, String key, String value){
        try{
            prop.setProperty(key, value);
            prop.store(new FileOutputStream(fname), null);            
        }catch(Exception e){}
    }

    public String readInfor(String fname, String key) throws Exception {
        String value = "";
        try{
            prop.load(new FileInputStream(fname));
            value = prop.getProperty(key);            
        }catch(Exception ex){}

        return value;
    }   
    
    
    
    public boolean checkExistInfor(String fname, String key) {
        boolean isExist = false;
        try{
            prop.load(new FileInputStream(fname));
            isExist = prop.containsKey(key);            
        }catch(Exception ex){
            log("- SYSTEM - Check configuration not exist key ["+key+"]"); 
        }
        return isExist;
    }    
    
    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable  
    { 
        try {
            // will print name of object
            System.out.println("Utility  object successfully garbage collected");
        } finally {
            super.finalize();
        }
    }    
}

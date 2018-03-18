package spark.Util;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    public void getData(String url){
        String result="";//访问返回结果
        BufferedReader read=null;//读取访问结果
        try {
            URL realurl=new URL(url);
            URLConnection connection=realurl.openConnection();
            connection.setRequestProperty("accept", "*//*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            read = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            read.lines();
            String line;
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(read!=null){//关闭流
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

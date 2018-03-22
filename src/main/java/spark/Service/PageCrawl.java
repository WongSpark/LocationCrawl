package spark.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import spark.Pojo.PoiData;
import spark.Util.FileUtils;

import java.io.*;
import java.util.UUID;
import java.util.function.Consumer;

public class PageCrawl {
    private int startPage,pageCount;
    private String url,distinctCode,savePath;
    private Consumer<String> updateWebSite;
    private Consumer<String> updateConsole;
    private Consumer<Integer> updateDetailProgress;
    private Consumer<Integer> updateTotalProgress;

    public PageCrawl(String url,String ditinctCode,String savePath,int startPage,int pageCount){
        this.url = url;
        this.pageCount = pageCount;
        this.startPage = startPage;
        this.distinctCode = ditinctCode;
        this.savePath = savePath+ditinctCode+".csv";
    }

    public void setWebSiteUpdator(Consumer<String> updateWebSite){
        this.updateWebSite = updateWebSite;
    }
    public void setConsoleUpdator(Consumer<String> updateConsole){
        this.updateConsole = updateConsole;
    }
    public void setDetailProgressUpdator(Consumer<Integer> updateDetailProgress){
        this.updateDetailProgress = updateDetailProgress;
    }
    public void setTotalProgressUpdator(Consumer<Integer> updateTotalProgress){
        this.updateTotalProgress = updateTotalProgress;
    }

    public void startTask(){
        String requestUrl,displayMessage;
        for(int i = startPage;i<startPage+pageCount;i++){
            requestUrl = url+"/"+distinctCode+"/"+i+".html";
            displayMessage = getOnePageData(requestUrl);

            updateWebSite.accept(requestUrl);
            updateConsole.accept(displayMessage);
            updateTotalProgress.accept(((i-startPage+1)*100)/pageCount);
        }
    }

    public String getOnePageData(String requestUrl) {
        Document docPage = null;
        BufferedWriter  bufferCsv = null;
        try {
            File fileCsv = FileUtils.CreateIfNotExist(savePath);
            FileOutputStream fileOutputStream = new FileOutputStream(fileCsv,true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
            bufferCsv = new BufferedWriter(outputStreamWriter);
            //写入csv文件头，防止出现乱码
            bufferCsv.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));

            docPage = Jsoup.connect(requestUrl).timeout(5000)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)").get();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "FileNotFoundException";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "UnsupportedEncodingException";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException";
        }

        if(docPage!=null) {
            Elements tablePage = docPage.select("table");
            Elements trPage = tablePage.select("tr");
            Elements tdPage;String line="";
            PoiData poiData = new PoiData();
            for(int i=0;i<trPage.size();i++){
                tdPage = trPage.get(i).select("td");
                if(tdPage.size()==4) {
                    poiData.setId(UUID.randomUUID().toString());
                    poiData.setName(tdPage.get(0).select("a").text());
                    poiData.setUrl(tdPage.get(0).select("a").attr("href"));
                    poiData.setAddress(tdPage.get(1).text());
                    poiData.setPhone(tdPage.get(2).text());
                    poiData.setCatogray(tdPage.get(3).text());
                    line = poiData.getId()+","+poiData.getName()+","+poiData.getUrl()+","+poiData.getAddress()+","+poiData.getPhone()+","+poiData.getCatogray();
                    try {
                        bufferCsv.write(line);
                        bufferCsv.newLine();
                        bufferCsv.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(updateDetailProgress!=null) {
                    updateDetailProgress.accept(((i+1)*100)/ trPage.size());
                }
                if(updateConsole!=null){
                    updateConsole.accept(line);
                }
            }
            try {
                bufferCsv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

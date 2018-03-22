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

    private String getOnePageData(String requestUrl) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(FileUtils.CreateIfNotExist(savePath),true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
            BufferedWriter bufferCsv = new BufferedWriter(outputStreamWriter)) {

            bufferCsv.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            Document docPage = Jsoup.connect(requestUrl).timeout(5000)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)").get();

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
                        bufferCsv.write(line);
                        bufferCsv.newLine();
                        bufferCsv.flush();
                    }

                    if(updateDetailProgress!=null) {
                        updateDetailProgress.accept(((i+1)*100)/ trPage.size());
                    }
                    if(updateConsole!=null){
                        updateConsole.accept(line);
                    }
                }
            }
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
        return "";
    }
}

package spark.Pojo;

public class PoiTask {
    private String webSite;
    private int startPage;
    private int pageCount;
    private String distinctCode;
    private String savePath;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDistinctCode() {
        return distinctCode;
    }

    public void setDistinctCode(String distinctCode) {
        this.distinctCode = distinctCode;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}

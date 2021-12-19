package Networks;

import database.DataBaseHelper;
import helper.Tools;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FetchUrlsData {
    private Document doc;
    private final String url;
    private Elements links;
    private Elements media;
    private CheckSiteStatus siteStatus;
    private DataBaseHelper dataBaseHelper = null;
    private int uid;

    public FetchUrlsData(String url) throws IOException {
        siteStatus = new CheckSiteStatus();
        Validate.isTrue(true, "usage: supply url to fetch");
        this.url = url;
        doc = Jsoup.connect(this.url).get();
        uid = Tools.INSTANCE.getRandomNumber(100000, 900000);
        setupDatabase();
    }

    public void setupDatabase() {
        dataBaseHelper = new DataBaseHelper();
        dataBaseHelper.initConnection();
    }

    private void getMainLinkData() {
        Tools.INSTANCE.print("Fetching %s...", url);
        int totalLinks;
        String siteSize;
        String responseTime;
        try {
            doc = Jsoup.connect(url).get();
            String title = doc.title();
            links = doc.select("a[href]");
            totalLinks = links.size();
            siteSize = Tools.INSTANCE.getSizeInKb(siteStatus, url);
            responseTime = Tools.INSTANCE.getResponseTime(siteStatus, url);
            if (dataBaseHelper != null) {
                Tools.INSTANCE.insertDataInMainTable(
                        dataBaseHelper,
                        uid,
                        url,
                        title,
                        siteSize != null ? siteSize : "0kb",
                        responseTime != null ? responseTime : "0ms",
                        "OK",
                        totalLinks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getLinks() {
        String web_link;
        String web_name;
        String siteSize;
        String responseTime;
        getMainLinkData();
        links = doc.select("a[href]");
        Tools.INSTANCE.print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            web_link = link.attr("abs:href");
            web_name = link.text();
            siteSize = Tools.INSTANCE.getSizeInKb(siteStatus,web_link);
            responseTime = Tools.INSTANCE.getResponseTime(siteStatus, web_link);
            if (dataBaseHelper != null) {
                Tools.INSTANCE.insertDataInSubTable(
                        dataBaseHelper,
                        uid,
                        Tools.INSTANCE.getRandomNumber(100000, 900000),
                        web_link,
                        web_name,
                        responseTime != null ? responseTime : "0ms",
                        "OK",
                        siteSize != null ? siteSize : "0kb"
                );
            }
            System.out.println(link.attr("abs:href"));
            System.out.println(web_name);
        }
    }

    public void getMediaData() {
        media = doc.select("[src]");
        Tools.INSTANCE.print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.normalName().equals("img")) {
                Tools.INSTANCE.print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"),
                        src.attr("width"), src.attr("height"),
                        Tools.INSTANCE.trimIt((src.attr("alt")), 20));
            }
        }
    }

}

package Networks;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class CheckSiteStatus {
    private HttpURLConnection connection = null;
    private URL mUrl = null;
    private long totalTime = 0L;
    private long finish;

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public Long urlResponseTime(String url) {
        if (isUrlValid(url)){
            try {
                mUrl = new URL(url);
                connection = (HttpURLConnection) mUrl.openConnection();
                long start = System.currentTimeMillis();
                String jsonResponse = myInputStreamReader(connection.getInputStream());
                if (jsonResponse != null) {
                    finish = System.currentTimeMillis();
                }
                totalTime = finish - start;
                System.out.println(totalTime + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        }else {
            System.err.println("This site is broken!");
        }
        return totalTime;
    }

    public String myInputStreamReader(InputStream in) {
        StringBuilder sb = null;
        try {
            InputStreamReader reader = new InputStreamReader(in);
            sb = new StringBuilder();
            int c = reader.read();
            while (c != -1) {
                sb.append((char) c);
                c = reader.read();
            }
            reader.close();
            return sb.toString();

        } catch (Exception e) {
            System.err.println("This site is broken!");
        }
        return sb.toString();
    }

    public long getFileSize(String url) {
        URLConnection conn;
        long size = 0;
        if (isUrlValid(url)){
            try {
                conn = new URL(url).openConnection();
                if (conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).setRequestMethod("HEAD");
                }
                conn.getInputStream();
                size = conn.getContentLength();
                long fileSizeInKB = size / 1024;
                System.out.println("The Size of file is:" +
                        fileSizeInKB + " bytes");
                conn.getInputStream().close();
            } catch (Exception e) {
                System.out.println("Connection failed");
            }
        }else {
            System.out.println("Connection failed");
        }
        return size;
    }
}

package helper;

public class Constant {
    public static final String DB_NAME = "webcrawler";
    public static final String TBL_MAIN_URL = "tbl_main_url";
    public static final String TBL_MAIN_URL_VALUES = "(uid, website, website_name, website_size, response_time, status, total_links_found)";
    public static final String TBL_SUB_URL = "tbl_sub_url";
    public static final String TBL_SUB_URL_VALUES = "(uid, site_uid, website, website_name, response_time, status, website_size)";
}

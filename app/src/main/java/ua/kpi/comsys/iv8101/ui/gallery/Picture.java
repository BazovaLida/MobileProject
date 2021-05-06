package ua.kpi.comsys.iv8101.ui.gallery;

import android.net.Uri;

public class Picture {
    private final Uri uri;
    private final String link;

    private Integer id;
    private static int counter = 0;

    public Picture(Uri uri){
        this.uri = uri;
        link = null;
    }

    public Picture(String url){
        this.link = url;
        uri = null;
        id = counter;
        counter ++;
    }

    public Picture(String url, int given_id){
        this.link = url;
        uri = null;
        id = given_id;
    }

    public String getLink() {
        return link;
    }

    public Uri getUri() {
        return uri;
    }

    public Integer getId() {
        return id;
    }
}

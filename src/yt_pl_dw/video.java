/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yt_pl_dw;

import java.io.File;

/**
 *
 * @author Daniel
 */
public class video {
    public String token;
    public String title;
    public String thumbnail_url;
    public String download_url;
    File outputfile;
    public video(String token, String title, String thumbnail_url, String download_url, File outputfile) {
        this.token = token;
        this.title = title;
        this.thumbnail_url = thumbnail_url;
        this.download_url = download_url;
        this.outputfile = outputfile;
    }
    public String getDownload_url() {
        return download_url;
    }

    public File getOutputfile() {
        return outputfile;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    

    public String getTitle() {
        return title;
    }

    public String getToken() {
        return token;
    }

    
    
    
}

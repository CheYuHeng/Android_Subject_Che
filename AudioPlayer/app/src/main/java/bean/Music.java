package bean;

import android.graphics.Bitmap;

public class Music {
    public String title;
    public String artist;
    public int duration;
    public String album;
    public Bitmap albumbtm;
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Bitmap getAlbumbtm() {
        return albumbtm;
    }

    public void setAlbumbtm(Bitmap albumbtm) {
        this.albumbtm = albumbtm;
    }
}

package library;

import javafx.scene.control.Button;

import java.sql.Timestamp;

public class Song {

	private int id;
    private String name;
    private String date;
    private int singerId;
    private int albumId;
    private String type;
    private String time;
    private int count;
    private Button sarkiBtn = new Button();

    public Song(int id, String name, String date, int singerId, int albumId, String type, String time, int count, String buttonId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.singerId = singerId;
        this.albumId = albumId;
        this.type = type;
        this.time = time;
        this.count = count;
        this.sarkiBtn = sarkiBtn;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getSingerId() {
        return singerId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public int getCoumt() {
        return count;
    }

    public Button getSarkiBtn() {
        return sarkiBtn;
    }

    public void setSarkiBtn(Button sarkiBtn) {
        this.sarkiBtn = sarkiBtn;
    }
}

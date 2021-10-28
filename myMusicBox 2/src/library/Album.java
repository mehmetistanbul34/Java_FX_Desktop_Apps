package library;

import javafx.scene.control.Button;

public class Album {

	private int id;
    private String name;
    private int singerId;
    private int songId;
    private String date;
    private String type;
    private Button sarkiBtn = new Button();
    private Button gosterBtn = new Button();

    public Album(int id, String name, int singerId, int songId, String date, String type, String buttonId, String buttonGosterId) {
        this.id = id;
        this.name = name;
        this.singerId = singerId;
        this.songId = songId;
        this.date = date;
        this.type = type;
        this.sarkiBtn = sarkiBtn;
        this.gosterBtn = gosterBtn;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSingerId() {
        return singerId;
    }

    public int getSongId() {
        return songId;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public Button getSarkiBtn() {
        return sarkiBtn;
    }

    public void setSarkiBtn(Button sarkiBtn) {
        this.sarkiBtn = sarkiBtn;
    }

    public Button getGosterBtn() {
        return sarkiBtn;
    }

    public void setGosterBtn(Button sarkiBtn) {
        this.sarkiBtn = sarkiBtn;
    }
}

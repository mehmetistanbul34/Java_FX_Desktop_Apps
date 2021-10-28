package library;

import javafx.scene.control.Button;

public class Singer {

	private int id;
    private String name;
    private String country;
    private Integer userId;
    private Button sarkiBtn = new Button();


    public Singer(int id, String name, String country, Integer userId,String buttonId) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.userId = userId;
        this.sarkiBtn = new Button(buttonId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Integer getUserId() {
        return userId;
    }

    public Button getSarkiBtn() {
        return sarkiBtn;
    }

    public void setSarkiBtn(Button sarkiBtn) {
        this.sarkiBtn = sarkiBtn;
    }
}

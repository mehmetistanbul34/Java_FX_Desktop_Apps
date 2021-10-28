package library;

public class Musics {

	private int id;
    private String sarki;
    private String sarkici;
    private int userId;

    public Musics(int id, String sarki, String sarkici, int userId) {
        this.id = id;
        this.userId = userId;
        this.sarki = sarki;
        this.sarkici = sarkici;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getSarki() {
        return sarki;
    }

    public String getSarkici() {
        return sarkici;
    }
}

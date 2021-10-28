package library;

public class Cargo {

	private Integer id;
    private String customerName;
    private String customerAddress;
    private String usersId;
    private Double latitude;
    private Double longitude;
    private Boolean cargoStatus;

    public Cargo(Integer id, String customerName, String customerAddress, String usersId, Double latitude, Double longitude, Boolean cargoStatus) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.usersId = usersId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cargoStatus = cargoStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(Boolean cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
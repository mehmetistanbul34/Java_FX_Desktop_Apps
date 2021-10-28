package library;

public class Cargo {

	private Integer id;
    private String customerName;
    private String customerAddress;
    private String usersId;
    private Boolean cargoStatus;

    public Cargo(int id, String customerName, String customerAddress, String usersId, Boolean cargoStatus) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.usersId = usersId;
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

    public Boolean getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(Boolean cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
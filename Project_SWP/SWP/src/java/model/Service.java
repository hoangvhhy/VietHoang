package model;


public class Service {

    private int packageID;
    private String packageName;
    private String description;
    private byte[] serviceImage;
    private String type;
    private double price;
    private int duration;
    private String status;
    private String createdAt;
    private String introduce; // Thuộc tính mới được thêm

    public Service() {
    }

    public Service(int packageID, String packageName, String description, byte[] serviceImage, String type, double price, int duration, String status, String createdAt) {
        this.packageID = packageID;
        this.packageName = packageName;
        this.description = description;
        this.serviceImage = serviceImage;
        this.type = type;
        this.price = price;
        this.duration = duration;
        this.status = status;
        this.createdAt = createdAt;
        this.introduce = null; // Gán giá trị mặc định là null để không ảnh hưởng constructor hiện tại
    }

    // Getters và Setters cho các thuộc tính hiện có
    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(byte[] serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Getter và Setter cho thuộc tính introduce
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Service{" + "packageID=" + packageID + ", packageName=" + packageName + ", description=" + description + ", serviceImage=" + serviceImage + ", type=" + type + ", price=" + price + ", duration=" + duration + ", status=" + status + ", createdAt=" + createdAt + ", introduce=" + introduce + '}';
    }
    
}
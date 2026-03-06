package bd.edu.seu.ecommerce.classes;

import javafx.scene.image.Image;

public class Product {
    private int productId;
    private String productName;
    private String productType;
    private String productSize;
    private String productColor;
    private String productDetails;
    private double productPrice;
    private String AddDate;
    private int productQuantity;
    private double totalPrice;
    private boolean checkBox;
    private String discountCode;
    private String customerGender;
    private String  imagePath;
    private String  nothing;

    public Product(int productId,String productName,String productType,String productSize,String productColor,String addDate,String productDetails,double productPrice,int productQuantity,double totalPrice,String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productSize=productSize;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.AddDate = addDate;
        this.productDetails=productDetails;
        this.productQuantity = productQuantity;
        this.totalPrice = totalPrice;
        this.imagePath=imagePath;

    }
    public Product(int productId, String productName, String productType, String productSize, String productColor, String addDate, String productDetails, double productPrice, int productQuantity, double totalPrice, Image image) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productSize=productSize;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.AddDate = addDate;
        this.productDetails=productDetails;
        this.productQuantity = productQuantity;
        this.totalPrice = totalPrice;

    }
    public Product(int productId, String productName, String productType, String productSize, String productColor, String addDate, String productDetails, double productPrice, int productQuantity, double totalPrice, Image image,String nothing) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productSize=productSize;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.AddDate = addDate;
        this.productDetails=productDetails;
        this.productQuantity = productQuantity;
        this.totalPrice = totalPrice;

    }
    public Product(String productName, String productType, String productSize, String productColor, String productDetails,int productQuantity, double productPrice,String imagePath) {
        this.productName = productName;
        this.productType = productType;
        this.productSize = productSize;
        this.productColor = productColor;
        this.productDetails = productDetails;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.imagePath=imagePath;
    }
    public Product(String productName, String productType, String productSize, String productColor, String productDetails, double productPrice) {
        this.productName = productName;
        this.productType = productType;
        this.productSize = productSize;
        this.productColor = productColor;
        this.productDetails = productDetails;
        this.productPrice = productPrice;
    }
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
    public String getProductType() {
        return productType;
    }

    public String getProductSize() {
        return productSize;
    }

    public String getProductColor() {
        return productColor;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public String getAddDate() {
        return AddDate;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getCustomerGender() {
        return customerGender;
    }
    public String getImagePath() {
        return imagePath;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

}

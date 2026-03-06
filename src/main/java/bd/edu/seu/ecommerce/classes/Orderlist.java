package bd.edu.seu.ecommerce.classes;
public class Orderlist{
    private String name;
    private String email;
    private String mobile;
    private String city;
    private String address;
    private String productName;
    private String paymentMethod;
    private int quantity;
    private double price;
    private double total;

    // Constructor
    public Orderlist(String name, String email, String mobile, String city, String address,
                     String productName, String paymentMethod, int quantity, double price, double total) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.address = address;
        this.productName = productName;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    // Getters (used in your SQL query)
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getProductName() { return productName; }
    public String getPayMethod() { return paymentMethod; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotal() { return total; }
}

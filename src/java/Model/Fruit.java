package Model;

import java.io.Serializable;

public class Fruit implements Serializable {
   private int productId;
    private String productName;
    private String description;
    private double price;

    public Fruit() {
    }

    
    public Fruit(int productId, String productName, String description, double price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
  

    // Getter methods for product properties
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
  
     
    
    public Fruit(Fruit s){
        this(s.productId,s.productName,s.description,s.price);
    }
    
    @Override
    public String toString() {
        return "Fruit{" + "productId=" + productId + ", productName=" + productName + ", description=" + description + ", price=" + price + '}';
    }
    
    
}

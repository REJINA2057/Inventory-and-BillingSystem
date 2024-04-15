package com.example.billsandinvento;

public class ProductDetails {

    String purchaseDate;
    String category;
    String itemQty;
    String supplierName;
    String supplierPhone;
    String costPrice;
    String productName;

   long productId;

    public long getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public String getPurchaseDate() {
        return purchaseDate;
    }
    public String getItemQty() {
        return itemQty;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public String getCostPrice() {
        return costPrice;
    }


    public String getCategory() {
        //System.out.println(category);
        return category;
    }


    public String getSupplierName() {
        //System.out.println(supplierName);
        return supplierName;
    }

}

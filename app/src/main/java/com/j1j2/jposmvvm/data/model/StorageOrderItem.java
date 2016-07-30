package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-7-15.
 */
public class StorageOrderItem {

    /**
     * ItemId : 1
     * StockId : 2
     * OrderId : 3
     * Quantity : 4.0
     * Price : 5.0
     * Spec : sample string 6
     * Img : sample string 7
     */

    private int ItemId;
    private int StockId;
    private int OrderId;
    private double Quantity;
    private double Price;
    private String Spec;
    private String Img;

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int ItemId) {
        this.ItemId = ItemId;
    }

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }
}

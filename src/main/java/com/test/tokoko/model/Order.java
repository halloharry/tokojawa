package com.test.tokoko.model;

import com.test.tokoko.common.auditable.ModelBase;

import javax.persistence.*;

@Entity
@Table(name = "user_order")
public class Order extends ModelBase {

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    private Product productId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "is_cancel")
    private int isCancel;

    public Order() {
    }

    public Order(User userId, Product productId, int amount, double totalPrice, int isCancel) {
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.isCancel = isCancel;
    }

    public User getUserId() {
        return userId;
    }

    public Product getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }
}

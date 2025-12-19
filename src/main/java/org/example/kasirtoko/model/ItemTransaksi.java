package org.example.kasirtoko.model;

import java.io.Serializable;

public class ItemTransaksi implements Serializable {
    private final Produk produk;
    private final int qty;

    public ItemTransaksi(Produk produk, int qty) {
        this.produk = produk;
        this.qty = qty;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getQty() {
        return qty;
    }

    public double getSubtotal() {
        return produk.getHarga() * qty;
    }
}

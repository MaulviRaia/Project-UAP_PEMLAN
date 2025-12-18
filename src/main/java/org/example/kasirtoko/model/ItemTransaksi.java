package org.example.kasirtoko.model;

import java.io.Serializable;

public class ItemTransaksi implements Serializable {
    public Produk produk;
    public int qty;

    public ItemTransaksi(Produk produk) {
        this.produk = produk;
        this.qty = 1;
    }

    public double subtotal() {
        return produk.harga * qty;
    }
}

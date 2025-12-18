package org.kasirtoko.model;

class ItemTransaksi {
    Produk produk;
    int qty = 1;

    ItemTransaksi(Produk p) {
        produk = p;
    }

    double getSubtotal() {
        return produk.harga * qty;
    }
}

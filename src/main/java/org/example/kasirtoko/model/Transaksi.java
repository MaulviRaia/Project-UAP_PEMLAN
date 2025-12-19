package org.example.kasirtoko.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaksi implements Serializable {
    private LocalDateTime tanggal;
    private List<ItemTransaksi> items = new ArrayList<>();
    private double total;

    public Transaksi() {
        tanggal = LocalDateTime.now();
    }

    public void tambahItem(ItemTransaksi item) {
        items.add(item);
        total += item.getSubtotal();
    }

    public List<ItemTransaksi> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }
    public String toCSV() {
        return tanggal + "," + total;
    }

    public static Transaksi fromCSV(String line) {
        String[] d = line.split(",");
        Transaksi t = new Transaksi();
        t.tanggal = java.time.LocalDateTime.parse(d[0]);
        t.total = Double.parseDouble(d[1]);
        return t;
    }
    public void clear() {
        items.clear();
    }

}

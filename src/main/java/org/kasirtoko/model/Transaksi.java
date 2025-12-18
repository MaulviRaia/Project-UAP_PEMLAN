package org.kasirtoko.model;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Transaksi {
    ArrayList<ItemTransaksi> items = new ArrayList<>();

    void tambahItem(Produk p) {
        for (ItemTransaksi it : items) {
            if (it.produk.nama.equals(p.nama)) {
                it.qty++;
                return;
            }
        }
        items.add(new ItemTransaksi(p));
    }

    String cetakStruk() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        sb.append("=== STRUK BELANJA ===\n");
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n\n");
        for (ItemTransaksi it : items) {
            sb.append(it.produk.nama + " x" + it.qty + " = " + it.getSubtotal() + "\n");
            total += it.getSubtotal();
        }
        sb.append("\nTOTAL : " + total);
        return sb.toString();
    }

    void simpanStruk() {
        try {
            FileWriter fw = new FileWriter("struk.txt", true);
            fw.write(cetakStruk() + "\n-------------------\n");
            fw.close();
        } catch (Exception e) {
        }
    }

    void reset() {
        items.clear();
    }
}

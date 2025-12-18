package org.kasirtoko.service;

import org.kasirtoko.model.Produk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

class ProdukManager {
    ArrayList<Produk> list = new ArrayList<>();
    File file = new File("produk.csv");

    ProdukManager() {
        load();
    }

    void tambahProduk(String n, double h) {
        list.add(new Produk(n, h));
        save();
    }

    void hapusProduk(int i) {
        list.remove(i);
        save();
    }

    Produk getProduk(int i) {
        return list.get(i);
    }

    ArrayList<Produk> getAll() {
        return list;
    }

    void load() {
        try {
            if (!file.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(",");
                list.add(new Produk(d[0], Double.parseDouble(d[1])));
            }
            br.close();
        } catch (Exception e) {
        }
    }

    void save() {
        try {
            PrintWriter pw = new PrintWriter(file);
            for (Produk p : list) pw.println(p.nama + "," + p.harga);
            pw.close();
        } catch (Exception e) {
        }
    }
}

package org.example.kasirtoko.service;

import java.util.*;
import java.io.*;

import org.example.kasirtoko.model.Produk;

public class ProdukManager {

    private ArrayList<Produk> list = new ArrayList<>();
    private File file = new File("produk.csv");

    public ProdukManager() {
        load();
    }

    public void tambah(String nama, double harga) {
        list.add(new Produk(nama, harga));
        save();
    }

    public void hapus(int index) {
        list.remove(index);
        save();
    }

    public ArrayList<Produk> getAll() {
        return list;
    }

    public Produk get(int index) {
        return list.get(index);
    }

    private void load() {
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(",");
                list.add(new Produk(d[0], Double.parseDouble(d[1])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Produk p : list) {
                pw.println(p.nama + "," + p.harga);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

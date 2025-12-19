package org.example.kasirtoko.service;

import org.example.kasirtoko.model.*;
import java.io.*;
import java.util.*;

public class ProdukManager {

    private final List<Produk> list = new ArrayList<>();
    private final File file =
            new File("src/main/java/org/example/kasirtoko/data/produk.csv");

    public ProdukManager() {
        load();
    }

    public void tambah(Produk p) {
        list.add(p);
        save();
    }

    public void hapus(int index) {
        list.remove(index);
        save();
    }

    public List<Produk> getAll() {
        return list;
    }

    public Produk get(int index) {
        return list.get(index);
    }

    public void kurangiStok(Produk p, int qty) {
        p.kurangiStok(qty);
        save();
    }

    private void load() {
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                list.add(Produk.fromCSV(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Produk p : list) {
                pw.println(p.toCSV());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

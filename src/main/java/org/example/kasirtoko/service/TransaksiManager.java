package org.example.kasirtoko.service;

import org.example.kasirtoko.model.Transaksi;
import org.example.kasirtoko.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiManager {

    private final File file = FileUtil.getFile("transaksi.csv");

    // ===== SIMPAN TRANSAKSI =====
    public void simpan(Transaksi t) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            pw.println(t.toCSV());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan transaksi");
        }
    }

    // ===== AMBIL SEMUA TRANSAKSI =====
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Transaksi.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca transaksi");
        }
        return list;
    }

    // ===== TOTAL PENDAPATAN =====
    public double getTotalPendapatan() {
        double total = 0;
        for (Transaksi t : getAll()) {
            total += t.getTotal();
        }
        return total;
    }
}

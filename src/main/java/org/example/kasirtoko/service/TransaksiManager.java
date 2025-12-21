package org.example.kasirtoko.service;

import org.example.kasirtoko.model.Transaksi;
import org.example.kasirtoko.util.FileUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TransaksiManager {

    private final File file = FileUtil.getFile("transaksi.csv");

    // ================= SIMPAN =================
    public void simpan(Transaksi t) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            pw.println(t.toCSV());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan transaksi");
        }
    }

    // ================= AMBIL SEMUA =================
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

    // ================= TOTAL PENDAPATAN =================
    public double getTotalPendapatan() {
        double total = 0;
        for (Transaksi t : getAll()) {
            total += t.getTotal();
        }
        return total;
    }

    // ================= JUMLAH TRANSAKSI =================
    public int getJumlahTransaksi() {
        return getAll().size();
    }

    // ================= DATA GRAFIK (7 HARI TERAKHIR) =================
    public int[] getPendapatanPerHari() {
        int[] result = new int[7];
        List<Transaksi> list = getAll();

        LocalDate today = LocalDate.now();

        for (Transaksi t : list) {
            LocalDate tanggal = t.getTanggal().toLocalDate();

            long selisih = java.time.temporal.ChronoUnit.DAYS
                    .between(tanggal, today);

            if (selisih >= 0 && selisih < 7) {
                result[(int) (6 - selisih)] += t.getTotal();
            }
        }
        return result;
    }
}
package org.example.kasirtoko.service;

import org.example.kasirtoko.util.FileUtil;

import java.io.*;

public class LaporanManager {

    public double totalPendapatan() {
        double total = 0;
        File file = FileUtil.getFile("transaksi.csv");
        if (!file.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(",");
                total += Double.parseDouble(d[1]);
            }
        } catch (Exception e) {
            System.out.println("Gagal baca laporan");
        }
        return total;
    }
}

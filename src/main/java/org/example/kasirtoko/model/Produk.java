package org.example.kasirtoko.model;

public class Produk {
    private String nama;
    private double harga;
    private int stok;
    private Kategori kategori;

    public Produk(String nama, double harga, int stok, Kategori kategori) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }

    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    public Kategori getKategori() { return kategori; }

    public void kurangiStok(int qty) {
        this.stok -= qty;
    }

    public String toCSV() {
        return nama + "," + harga + "," + stok + "," + kategori;
    }

    public static Produk fromCSV(String line) {
        String[] d = line.split(",");
        return new Produk(
                d[0],
                Double.parseDouble(d[1]),
                Integer.parseInt(d[2]),
                Kategori.valueOf(d[3])
        );
    }
}

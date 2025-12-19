package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import org.example.kasirtoko.model.*;
import org.example.kasirtoko.service.*;

public class KasirView extends JPanel {

    private final DefaultTableModel modelProduk;
    private final DefaultTableModel modelKeranjang;

    public KasirView(CardLayout card, JPanel container,
                     ProdukManager pm, TransaksiManager tm) {

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // ===== TABLE PRODUK =====
        modelProduk = new DefaultTableModel(
                new String[]{"Nama","Harga","Stok","Kategori"},0);
        JTable tblProduk = new JTable(modelProduk);

        for (Produk p : pm.getAll()) {
            modelProduk.addRow(new Object[]{
                    p.getNama(), p.getHarga(), p.getStok(), p.getKategori()
            });
        }

        // ===== TABLE KERANJANG =====
        modelKeranjang = new DefaultTableModel(
                new String[]{"Nama","Qty","Subtotal"},0);
        JTable tblKeranjang = new JTable(modelKeranjang);

        // ===== TOTAL =====
        JLabel lblTotal = new JLabel("Total : Rp 0");
        lblTotal.setFont(new Font("Segoe UI",Font.BOLD,18));

        JTextField bayar = new JTextField();
        JLabel lblKembali = new JLabel("Kembalian : Rp 0");

        Transaksi transaksi = new Transaksi();

        JButton tambah = new JButton("Tambah");
        JButton bayarBtn = new JButton("Bayar");
        JButton back = new JButton("Dashboard");

        UIStyle.styleButton(tambah);
        UIStyle.styleButton(bayarBtn);
        UIStyle.styleButton(back);

        tambah.addActionListener(e -> {
            int row = tblProduk.getSelectedRow();
            if (row < 0) return;

            Produk p = pm.getAll().get(row);
            if (p.getStok() <= 0) {
                JOptionPane.showMessageDialog(this,"Stok habis!");
                return;
            }

            ItemTransaksi it = new ItemTransaksi(p,1);
            transaksi.tambahItem(it);
            pm.kurangiStok(p,1);

            modelKeranjang.addRow(new Object[]{
                    p.getNama(),1,it.getSubtotal()
            });

            lblTotal.setText("Total : Rp " + transaksi.getTotal());
            modelProduk.setValueAt(p.getStok(), row, 2);
        });

        bayarBtn.addActionListener(e -> {
            try {
                double total = transaksi.getTotal();
                double uang = Double.parseDouble(bayar.getText());

                if (uang < total) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Pembayaran ditolak!\nUang anda kurang.",
                            "Gagal",
                            JOptionPane.ERROR_MESSAGE
                    );
                    bayar.setText("");
                    bayar.requestFocus();
                    return;
                }

                double kembali = uang - total;
                lblKembali.setText("Kembalian : Rp " + kembali);

                tm.simpan(transaksi);

                JOptionPane.showMessageDialog(
                        this,
                        "Pembayaran berhasil!\nKembalian : Rp " + kembali,
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                modelKeranjang.setRowCount(0);
                transaksi.clear();
                lblTotal.setText("Total : Rp 0");
                bayar.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Masukkan jumlah uang yang valid!",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                bayar.setText("");
            }
        });


        back.addActionListener(e -> card.show(container,"dashboard"));

        // ===== LAYOUT =====
        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(new JScrollPane(tblProduk),BorderLayout.CENTER);
        kiri.add(tambah,BorderLayout.SOUTH);

        JPanel kanan = new JPanel(new BorderLayout(5,5));
        kanan.add(new JScrollPane(tblKeranjang),BorderLayout.CENTER);

        JPanel bayarPanel = new JPanel(new GridLayout(4,1,5,5));
        bayarPanel.add(lblTotal);
        bayarPanel.add(bayar);
        bayarPanel.add(lblKembali);
        bayarPanel.add(bayarBtn);

        kanan.add(bayarPanel,BorderLayout.SOUTH);

        add(kiri,BorderLayout.CENTER);
        add(kanan,BorderLayout.EAST);
        add(back,BorderLayout.SOUTH);
    }
}

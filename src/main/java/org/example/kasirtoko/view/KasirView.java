package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import org.example.kasirtoko.model.*;
import org.example.kasirtoko.service.*;
import org.example.kasirtoko.util.FormatterUtil;

public class KasirView extends JPanel {

    private final DefaultTableModel modelProduk;
    private final DefaultTableModel modelKeranjang;

    public KasirView(CardLayout card, JPanel container,
                     ProdukManager pm, TransaksiManager tm) {

        setLayout(new BorderLayout(12,12));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        setBackground(UIStyle.BG_MAIN);

        // ================= TABLE PRODUK =================
        modelProduk = new DefaultTableModel(
                new String[]{"Nama","Harga","Stok","Kategori"},0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tblProduk = new JTable(modelProduk);
        tblProduk.setRowHeight(26);
        tblProduk.setSelectionBackground(UIStyle.PRIMARY);
        tblProduk.setSelectionForeground(Color.WHITE);

        for (Produk p : pm.getAll()) {
            modelProduk.addRow(new Object[]{
                    p.getNama(),
                    FormatterUtil.rupiah(p.getHarga()),
                    p.getStok(),
                    p.getKategori()
            });
        }

        // ================= TABLE KERANJANG =================
        modelKeranjang = new DefaultTableModel(
                new String[]{"Nama","Qty","Subtotal"},0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tblKeranjang = new JTable(modelKeranjang);
        tblKeranjang.setRowHeight(26);
        tblKeranjang.setSelectionBackground(UIStyle.PRIMARY);
        tblKeranjang.setSelectionForeground(Color.WHITE);

        // ================= TOTAL =================
        JLabel lblTotal = new JLabel("Total : Rp 0");
        lblTotal.setFont(UIStyle.TITLE_FONT);
        lblTotal.setForeground(UIStyle.PRIMARY);

        JTextField tfBayar = new JTextField();
        JLabel lblKembali = new JLabel("Kembalian : Rp 0");
        lblKembali.setFont(UIStyle.NORMAL_FONT);

        Transaksi transaksi = new Transaksi();

        // ================= BUTTON =================
        JButton btnTambah = UIStyle.primaryButton("Tambah");
        JButton btnBayar = UIStyle.primaryButton("Bayar");
        JButton btnBack = UIStyle.secondaryButton("Dashboard");

        btnBayar.setEnabled(false); // UX AMAN

        // ================= ACTION: TAMBAH =================
        btnTambah.addActionListener(e -> {
            int row = tblProduk.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,"Pilih produk terlebih dahulu");
                return;
            }

            Produk p = pm.getAll().get(row);
            if (p.getStok() <= 0) {
                JOptionPane.showMessageDialog(this,"Stok habis!");
                return;
            }

            ItemTransaksi it = new ItemTransaksi(p,1);
            transaksi.tambahItem(it);
            pm.kurangiStok(p,1);

            modelKeranjang.addRow(new Object[]{
                    p.getNama(),
                    1,
                    FormatterUtil.rupiah(it.getSubtotal())
            });

            lblTotal.setText("Total : " + FormatterUtil.rupiah(transaksi.getTotal()));
            modelProduk.setValueAt(p.getStok(), row, 2);
            btnBayar.setEnabled(true);
        });

        // ================= ACTION: BAYAR =================
        btnBayar.addActionListener(e -> {
            try {
                double total = transaksi.getTotal();
                double bayar = Double.parseDouble(tfBayar.getText());

                if (bayar < total) {
                    JOptionPane.showMessageDialog(this,"Uang tidak cukup!");
                    return;
                }

                double kembali = bayar - total;
                lblKembali.setText("Kembalian : " + FormatterUtil.rupiah(kembali));

                tm.simpan(transaksi);

                JOptionPane.showMessageDialog(
                        this,
                        "Pembayaran Berhasil\nKembalian : " + FormatterUtil.rupiah(kembali),
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // RESET
                modelKeranjang.setRowCount(0);
                transaksi.clear();
                lblTotal.setText("Total : Rp 0");
                lblKembali.setText("Kembalian : Rp 0");
                tfBayar.setText("");
                btnBayar.setEnabled(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Masukkan nominal yang valid");
            }
        });

        btnBack.addActionListener(e -> card.show(container,"dashboard"));

        // ================= LAYOUT =================
        JPanel kiri = new JPanel(new BorderLayout(8,8));
        kiri.setBackground(UIStyle.BG_MAIN);
        kiri.add(new JLabel("Daftar Produk"),BorderLayout.NORTH);
        kiri.add(new JScrollPane(tblProduk),BorderLayout.CENTER);
        kiri.add(btnTambah,BorderLayout.SOUTH);

        JPanel kanan = new JPanel(new BorderLayout(8,8));
        kanan.setBackground(UIStyle.BG_MAIN);
        kanan.add(new JLabel("Keranjang"),BorderLayout.NORTH);
        kanan.add(new JScrollPane(tblKeranjang),BorderLayout.CENTER);

        JPanel bayarPanel = new JPanel(new GridLayout(4,1,8,8));
        bayarPanel.setBackground(UIStyle.BG_MAIN);
        bayarPanel.add(lblTotal);
        bayarPanel.add(tfBayar);
        bayarPanel.add(lblKembali);
        bayarPanel.add(btnBayar);

        kanan.add(bayarPanel,BorderLayout.SOUTH);

        add(kiri,BorderLayout.CENTER);
        add(kanan,BorderLayout.EAST);
        add(btnBack,BorderLayout.SOUTH);
    }
}

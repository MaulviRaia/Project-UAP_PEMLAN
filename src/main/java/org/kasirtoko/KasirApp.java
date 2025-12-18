package org.kasirtoko;

import org.kasirtoko.model.ItemTransaksi;
import org.kasirtoko.model.Produk;
import org.kasirtoko.model.Transaksi;
import org.kasirtoko.service.ProdukManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class KasirApp extends JFrame {
    CardLayout card = new CardLayout();
    JPanel container = new JPanel(card);

    ProdukManager produkManager = new ProdukManager();
    Transaksi transaksi = new Transaksi();

    JTable tableProduk, tableKeranjang;
    DefaultTableModel modelProduk, modelKeranjang;

    JTextField tfNama, tfHarga, tfCari;
    JTextArea areaStruk;

    public KasirApp() {
        setTitle("Kasir Toko - OOP");
        setSize(1000,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.add(dashboard(), "dashboard");
        container.add(listProduk(), "produk");
        container.add(formProduk(), "form");
        container.add(kasirPage(), "kasir");

        add(container);
        setVisible(true);
    }

    JPanel dashboard() {
        JPanel p = new JPanel(new GridLayout(2,2,20,20));
        p.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        JButton b1 = new JButton("Data Produk");
        JButton b2 = new JButton("Tambah Produk");
        JButton b3 = new JButton("Kasir");
        JButton b4 = new JButton("Keluar");

        b1.addActionListener(e->card.show(container,"produk"));
        b2.addActionListener(e->card.show(container,"form"));
        b3.addActionListener(e->card.show(container,"kasir"));
        b4.addActionListener(e->System.exit(0));

        p.add(b1);p.add(b2);p.add(b3);p.add(b4);
        return p;
    }

    JPanel listProduk() {
        JPanel p = new JPanel(new BorderLayout());
        modelProduk = new DefaultTableModel(new String[]{"Nama","Harga"},0);
        tableProduk = new JTable(modelProduk);
        refreshProduk();

        tfCari = new JTextField();
        tfCari.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelProduk);
                tableProduk.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(tfCari.getText()));
            }
        });

        JButton hapus = new JButton("Hapus");
        hapus.addActionListener(e->{
            int i = tableProduk.getSelectedRow();
            if(i>=0){
                produkManager.hapusProduk(tableProduk.convertRowIndexToModel(i));
                refreshProduk();
            }
        });

        JButton back = new JButton("Dashboard");
        back.addActionListener(e->card.show(container,"dashboard"));

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Cari: "),BorderLayout.WEST);
        top.add(tfCari);

        JPanel bottom = new JPanel();
        bottom.add(hapus);bottom.add(back);

        p.add(top,BorderLayout.NORTH);
        p.add(new JScrollPane(tableProduk));
        p.add(bottom,BorderLayout.SOUTH);
        return p;
    }

    JPanel formProduk() {
        JPanel p = new JPanel(new GridLayout(4,2,10,10));
        p.setBorder(BorderFactory.createEmptyBorder(50,250,50,250));

        tfNama = new JTextField();
        tfHarga = new JTextField();

        JButton simpan = new JButton("Simpan");
        simpan.addActionListener(e->{
            try{
                produkManager.tambahProduk(tfNama.getText(), Double.parseDouble(tfHarga.getText()));
                tfNama.setText(""); tfHarga.setText("");
                JOptionPane.showMessageDialog(this,"Produk disimpan");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Input tidak valid");
            }
        });

        JButton back = new JButton("Dashboard");
        back.addActionListener(e->card.show(container,"dashboard"));

        p.add(new JLabel("Nama Produk"));p.add(tfNama);
        p.add(new JLabel("Harga"));p.add(tfHarga);
        p.add(simpan);p.add(back);
        return p;
    }

    JPanel kasirPage() {
        JPanel p = new JPanel(new GridLayout(1,2));

        modelProduk = new DefaultTableModel(new String[]{"Nama","Harga"},0);
        tableProduk = new JTable(modelProduk);
        refreshProduk();

        modelKeranjang = new DefaultTableModel(new String[]{"Nama","Harga","Qty","Subtotal"},0);
        tableKeranjang = new JTable(modelKeranjang);

        JButton tambah = new JButton("Tambah >>");
        tambah.addActionListener(e->{
            int i = tableProduk.getSelectedRow();
            if(i>=0){
                Produk pr = produkManager.getProduk(tableProduk.convertRowIndexToModel(i));
                transaksi.tambahItem(pr);
                refreshKeranjang();
            }
        });

        JButton bayar = new JButton("Bayar & Cetak Struk");
        bayar.addActionListener(e->{
            areaStruk.setText(transaksi.cetakStruk());
            transaksi.simpanStruk();
            transaksi.reset();
            refreshKeranjang();
        });

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(new JScrollPane(tableProduk));
        kiri.add(tambah,BorderLayout.SOUTH);

        JPanel kanan = new JPanel(new BorderLayout());
        kanan.add(new JScrollPane(tableKeranjang));
        kanan.add(bayar,BorderLayout.SOUTH);

        p.add(kiri); p.add(kanan);
        return p;
    }

    void refreshProduk(){
        modelProduk.setRowCount(0);
        for(Produk p:produkManager.getAll()){
            modelProduk.addRow(new Object[]{p.nama,p.harga});
        }
    }

    void refreshKeranjang(){
        modelKeranjang.setRowCount(0);
        for(ItemTransaksi it:transaksi.items){
            modelKeranjang.addRow(new Object[]{it.produk.nama,it.produk.harga,it.qty,it.getSubtotal()});
        }
    }

    public static void main(String[] args) {
        new KasirApp();
    }
}


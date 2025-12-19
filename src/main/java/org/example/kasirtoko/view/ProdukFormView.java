package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;
import org.example.kasirtoko.model.*;
import org.example.kasirtoko.service.*;

public class ProdukFormView extends JPanel {

    public ProdukFormView(CardLayout card, JPanel container, ProdukManager pm) {

        setLayout(new BorderLayout(10,10));
        setBackground(UIStyle.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(30,200,30,200));

        JLabel title = new JLabel("Tambah Produk", SwingConstants.CENTER);
        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.TEXT_DARK);

        JTextField tfNama = new JTextField();
        JTextField tfHarga = new JTextField();
        JTextField tfStok = new JTextField();
        JComboBox<Kategori> cbKategori =
                new JComboBox<>(Kategori.values());

        JPanel form = new JPanel(new GridLayout(4,2,12,12));
        form.setBackground(UIStyle.BG_MAIN);

        form.add(new JLabel("Nama Produk"));
        form.add(tfNama);
        form.add(new JLabel("Harga"));
        form.add(tfHarga);
        form.add(new JLabel("Stok"));
        form.add(tfStok);
        form.add(new JLabel("Kategori"));
        form.add(cbKategori);

        JButton simpan = UIStyle.primaryButton("Simpan");
        JButton back = UIStyle.secondaryButton("Kembali");

        simpan.addActionListener(e -> {
            try {
                Produk p = new Produk(
                        tfNama.getText(),
                        Double.parseDouble(tfHarga.getText()),
                        Integer.parseInt(tfStok.getText()),
                        (Kategori) cbKategori.getSelectedItem()
                );
                pm.tambah(p);
                JOptionPane.showMessageDialog(this,"Produk berhasil ditambahkan");
                tfNama.setText("");
                tfHarga.setText("");
                tfStok.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Input tidak valid");
            }
        });

        back.addActionListener(e -> card.show(container,"produk"));

        JPanel bottom = new JPanel();
        bottom.setBackground(UIStyle.BG_MAIN);
        bottom.add(back);
        bottom.add(simpan);

        add(title,BorderLayout.NORTH);
        add(form,BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);
    }
}

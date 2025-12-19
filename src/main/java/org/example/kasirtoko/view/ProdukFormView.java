package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;
import org.example.kasirtoko.model.*;
import org.example.kasirtoko.service.*;

public class ProdukFormView extends JPanel {

    public ProdukFormView(CardLayout card, JPanel container, ProdukManager pm) {

        setLayout(new GridBagLayout());
        setBackground(UIStyle.BG_MAIN);

        // ===== CARD FORM =====
        JPanel cardForm = new JPanel();
        cardForm.setPreferredSize(new Dimension(420, 380));
        cardForm.setBackground(Color.WHITE);
        cardForm.setLayout(new BorderLayout(16,16));
        cardForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(20,24,20,24)
        ));

        // ===== TITLE =====
        JLabel title = new JLabel("Tambah Produk");
        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.PRIMARY);

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(8,1,8,8));
        form.setBackground(Color.WHITE);

        JTextField tfNama = new JTextField();
        JTextField tfHarga = new JTextField();
        JTextField tfStok = new JTextField();
        JComboBox<Kategori> cbKategori = new JComboBox<>(Kategori.values());

        form.add(label("Nama Produk"));
        form.add(tfNama);

        form.add(label("Harga"));
        form.add(tfHarga);

        form.add(label("Stok"));
        form.add(tfStok);

        form.add(label("Kategori"));
        form.add(cbKategori);

        // ===== BUTTON =====
        JButton btnSimpan = UIStyle.primaryButton("Simpan");
        JButton btnBack   = UIStyle.secondaryButton("Batal");

        JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        action.setBackground(Color.WHITE);
        action.add(btnBack);
        action.add(btnSimpan);

        // ===== ACTION =====
        btnSimpan.addActionListener(e -> {
            try {
                Produk p = new Produk(
                        tfNama.getText(),
                        Double.parseDouble(tfHarga.getText()),
                        Integer.parseInt(tfStok.getText()),
                        (Kategori) cbKategori.getSelectedItem()
                );
                pm.tambah(p);
                JOptionPane.showMessageDialog(this,"Produk berhasil ditambahkan");
                card.show(container,"produk");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Input tidak valid");
            }
        });

        btnBack.addActionListener(e -> card.show(container,"produk"));

        // ===== ASSEMBLE =====
        cardForm.add(title, BorderLayout.NORTH);
        cardForm.add(form, BorderLayout.CENTER);
        cardForm.add(action, BorderLayout.SOUTH);

        add(cardForm);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UIStyle.BODY_FONT);
        return l;
    }
}

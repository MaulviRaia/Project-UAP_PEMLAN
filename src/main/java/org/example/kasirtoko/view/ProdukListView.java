package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.example.kasirtoko.service.*;

public class ProdukListView extends JPanel {

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Nama","Harga","Stok","Kategori"},0);

    public ProdukListView(CardLayout card, JPanel container, ProdukManager pm) {

        setLayout(new BorderLayout(10,10));
        setBackground(UIStyle.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTable table = new JTable(model);
        table.setRowHeight(24);
        refresh(pm);

        JButton tambah = UIStyle.primaryButton("Tambah Produk");
        JButton hapus = UIStyle.dangerButton("Hapus");
        JButton back = UIStyle.secondaryButton("Dashboard");

        tambah.addActionListener(e -> card.show(container,"produk_form"));

        hapus.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                pm.hapus(i);
                refresh(pm);
            } else {
                JOptionPane.showMessageDialog(this,"Pilih produk terlebih dahulu");
            }
        });

        back.addActionListener(e -> card.show(container,"dashboard"));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        bottom.setBackground(UIStyle.BG_MAIN);
        bottom.add(back);
        bottom.add(hapus);
        bottom.add(tambah);

        add(new JScrollPane(table),BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);
    }

    private void refresh(ProdukManager pm) {
        model.setRowCount(0);
        pm.getAll().forEach(p ->
                model.addRow(new Object[]{
                        p.getNama(), p.getHarga(),
                        p.getStok(), p.getKategori()
                })
        );
    }
}

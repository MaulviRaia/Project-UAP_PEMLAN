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
        JTable table = new JTable(model);
        refresh(pm);

        JButton tambah = new JButton("Tambah");
        JButton hapus = new JButton("Hapus");
        JButton back = new JButton("Dashboard");

        tambah.addActionListener(e -> card.show(container,"produk_form"));
        hapus.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                pm.hapus(i);
                refresh(pm);
            }
        });
        back.addActionListener(e -> card.show(container,"dashboard"));

        JPanel bottom = new JPanel();
        bottom.add(back);
        bottom.add(tambah);
        bottom.add(hapus);

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

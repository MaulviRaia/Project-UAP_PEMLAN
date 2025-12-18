package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import org.example.kasirtoko.model.Produk;
import org.example.kasirtoko.service.ProdukManager;

public class ProdukListView extends JPanel {

    DefaultTableModel model =
            new DefaultTableModel(new String[]{"Nama","Harga"},0);
    JTable table = new JTable(model);

    public ProdukListView(CardLayout card, JPanel container, ProdukManager m) {
        setLayout(new BorderLayout());

        refresh(m);

        JButton hapus = new JButton("Hapus");
        JButton back = new JButton("← Dashboard");

        hapus.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                m.hapus(i);
                refresh(m);
            }
        });

        back.addActionListener(e -> card.show(container,"dashboard"));

        JPanel bottom = new JPanel();
        bottom.add(back);
        bottom.add(hapus);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refresh(ProdukManager m) {
        model.setRowCount(0);
        for (Produk p : m.getAll()) {
            model.addRow(new Object[]{p.nama, p.harga});
        }
    }
}

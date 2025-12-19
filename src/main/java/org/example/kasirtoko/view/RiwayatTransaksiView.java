package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.example.kasirtoko.service.*;
import org.example.kasirtoko.model.*;
import org.example.kasirtoko.util.*;

public class RiwayatTransaksiView extends JPanel {
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Tanggal", "Total"}, 0
    );

    public RiwayatTransaksiView(CardLayout card, JPanel c, TransaksiManager tm) {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTable table = new JTable(model);
        refresh(tm);

        JLabel totalLabel = new JLabel(
                "Total Pendapatan: " + FormatterUtil.rupiah(tm.getTotalPendapatan())
        );
        add(totalLabel, BorderLayout.NORTH);

        JButton back = new JButton("Dashboard");
        back.addActionListener(e -> card.show(c, "dashboard"));

        add(new JLabel("Riwayat Transaksi", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
    }

    void refresh(TransaksiManager tm) {
        model.setRowCount(0);
        for (Transaksi t : tm.getAll()) {
            model.addRow(new Object[]{
                    t.getTanggal().toString(),
                    FormatterUtil.rupiah(t.getTotal())
            });
        }
    }
}

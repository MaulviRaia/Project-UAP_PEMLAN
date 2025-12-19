package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

import org.example.kasirtoko.service.*;
import org.example.kasirtoko.model.*;
import org.example.kasirtoko.util.*;

public class RiwayatTransaksiView extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;

    // ===== THEME =====
    private final Color bgWhite = Color.WHITE;
    private final Color blue = new Color(33, 150, 243);
    private final Color blueSoft = new Color(232, 242, 252);

    public RiwayatTransaksiView(CardLayout card, JPanel container, TransaksiManager tm) {

        setLayout(new BorderLayout(10,10));
        setBackground(bgWhite);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ===== TITLE =====
        JLabel title = new JLabel("Riwayat Transaksi", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(blue);

        // ===== TOTAL PENDAPATAN =====
        JLabel totalLabel = new JLabel(
                "Total Pendapatan : " + FormatterUtil.rupiah(tm.getTotalPendapatan()),
                SwingConstants.CENTER
        );
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel top = new JPanel(new GridLayout(2,1,5,5));
        top.setBackground(bgWhite);
        top.add(title);
        top.add(totalLabel);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Tanggal", "Total Transaksi"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(blueSoft);
        table.setGridColor(new Color(220,220,220));

        // Header Style
        JTableHeader header = table.getTableHeader();
        header.setBackground(blue);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Center align total
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(right);

        // Zebra row
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : blueSoft);
                }
                return c;
            }
        });

        refresh(tm);

        // ===== BUTTON =====
        JButton back = new JButton("Kembali ke Dashboard");
        back.setFont(new Font("Segoe UI", Font.BOLD, 13));
        back.setBackground(blue);
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addActionListener(e -> card.show(container, "dashboard"));

        JPanel bottom = new JPanel();
        bottom.setBackground(bgWhite);
        bottom.add(back);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refresh(TransaksiManager tm) {
        model.setRowCount(0);

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

        for (Transaksi t : tm.getAll()) {
            model.addRow(new Object[]{
                    t.getTanggal().format(fmt),
                    FormatterUtil.rupiah(t.getTotal())
            });
        }
    }
}

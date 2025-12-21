package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.example.kasirtoko.service.*;

public class ProdukListView extends JPanel {

    private final ProdukManager pm;
    private final DefaultTableModel model;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;

    // ===== THEME =====
    private final Color primary = new Color(33, 150, 243);
    private final Color bgMain  = Color.WHITE;
    private final Color bgSoft  = new Color(245, 247, 250);
    private final Color textDark = new Color(50, 50, 50);

    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
    private final Font bodyFont  = new Font("Segoe UI", Font.PLAIN, 13);

    public ProdukListView(CardLayout card, JPanel container, ProdukManager pm) {
        this.pm = pm;

        setLayout(new BorderLayout(16,16));
        setBackground(bgSoft);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ================= TITLE =================
        JLabel title = new JLabel("Daftar Produk");
        title.setFont(titleFont);
        title.setForeground(textDark);

        // ================= SEARCH =================
        JTextField txtSearch = new JTextField(20);
        txtSearch.setFont(bodyFont);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(6,10,6,10)
        ));

        JLabel lblSearch = new JLabel("Cari:");
        lblSearch.setFont(bodyFont);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(bgSoft);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        searchPanel.setBackground(bgSoft);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        top.add(title, BorderLayout.WEST);
        top.add(searchPanel, BorderLayout.EAST);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"Nama Produk","Harga","Stok","Kategori"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(bodyFont);
        table.setRowHeight(26);
        table.setSelectionBackground(primary);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(bodyFont);
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }

            private void search() {
                String text = txtSearch.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        // ================= BUTTON =================
        JButton btnBack = createButton("Dashboard", false);
        JButton btnRefresh = createButton("Refresh", false);
        JButton btnTambah = createButton("Tambah Produk", true);

        btnBack.addActionListener(e ->
                card.show(container,"dashboard")
        );

        btnRefresh.addActionListener(e -> refreshData());

        btnTambah.addActionListener(e ->
                card.show(container,"produk_form")
        );

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        bottom.setBackground(bgSoft);
        bottom.add(btnBack);
        bottom.add(btnRefresh);
        bottom.add(btnTambah);

        // ================= ADD =================
        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshData();
    }

    // ================= BUTTON STYLE =================
    private JButton createButton(String text, boolean primaryBtn) {
        JButton btn = new JButton(text);
        btn.setFont(bodyFont);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (primaryBtn) {
            btn.setBackground(primary);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(primary);
            btn.setBorder(BorderFactory.createLineBorder(primary));
        }

        btn.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        return btn;
    }

    // ================= REFRESH DATA =================
    public void refreshData() {
        model.setRowCount(0);
        pm.getAll().forEach(p ->
                model.addRow(new Object[]{
                        p.getNama(),
                        p.getHarga(),
                        p.getStok(),
                        p.getKategori()
                })
        );
    }
}

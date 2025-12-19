package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.example.kasirtoko.service.*;

public class ProdukListView extends JPanel {

    private final ProdukManager pm;
    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;

    public ProdukListView(CardLayout card, JPanel container, ProdukManager pm) {
        this.pm = pm;

        setLayout(new BorderLayout(12,12));
        setBackground(UIStyle.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"Nama","Harga","Stok","Kategori"},0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(UIStyle.BODY_FONT);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        refreshData();

        // ================= SEARCH =================
        JTextField tfSearch = new JTextField();
        tfSearch.setFont(UIStyle.BODY_FONT);
        tfSearch.putClientProperty(
                "JTextField.placeholderText",
                "Cari nama produk..."
        );

        JPanel searchBar = createSearchBar(tfSearch);

        tfSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = tfSearch.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(
                            RowFilter.regexFilter("(?i)" + text, 0)
                    );
                }
            }
        });

        // ================= TOP =================
        JLabel title = new JLabel("Daftar Produk");
        title.setFont(UIStyle.TITLE_FONT);

        JPanel top = new JPanel(new BorderLayout(10,10));
        top.setBackground(UIStyle.BG_MAIN);
        top.add(title, BorderLayout.WEST);
        top.add(searchBar, BorderLayout.EAST);

        // ================= BUTTON =================
        JButton tambah = UIStyle.primaryButton("Tambah Produk");
        JButton hapus  = UIStyle.dangerButton("Hapus");
        JButton back   = UIStyle.secondaryButton("Dashboard");

        tambah.addActionListener(e ->
                card.show(container,"produk_form")
        );

        hapus.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                pm.hapus(modelRow);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Pilih produk terlebih dahulu",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        back.addActionListener(e ->
                card.show(container,"dashboard")
        );

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        bottom.setBackground(UIStyle.BG_MAIN);
        bottom.add(back);
        bottom.add(hapus);
        bottom.add(tambah);

        // ================= ADD =================
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);
    }

    // ================= SEARCH BAR UI =================
    private JPanel createSearchBar(JTextField tfSearch) {
        JPanel wrapper = new JPanel(new BorderLayout(6,0));
        wrapper.setBackground(Color.WHITE);
        wrapper.setPreferredSize(new Dimension(280,36));
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyle.PRIMARY,1,true),
                BorderFactory.createEmptyBorder(6,10,6,10)
        ));

        JLabel icon = new JLabel("🔍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        tfSearch.setBorder(null);
        tfSearch.setBackground(Color.WHITE);

        wrapper.add(icon, BorderLayout.WEST);
        wrapper.add(tfSearch, BorderLayout.CENTER);

        return wrapper;
    }

    // ================= REFRESH =================
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

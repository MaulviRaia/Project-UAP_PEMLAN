package org.example.kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.example.kasirtoko.service.*;
import org.example.kasirtoko.model.*;

public class ProdukListView extends JPanel {

    private final ProdukManager pm;
    private final DefaultTableModel model;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;

    private final Color primary = new Color(33, 150, 243);
    private final Color danger  = new Color(220, 53, 69);
    private final Color bgSoft  = new Color(245, 247, 250);
    private final Color textDark = new Color(50, 50, 50);

    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
    private final Font bodyFont  = new Font("Segoe UI", Font.PLAIN, 13);

    public ProdukListView(CardLayout card, JPanel container, ProdukManager pm) {
        this.pm = pm;

        setLayout(new BorderLayout(16,16));
        setBackground(bgSoft);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Daftar Produk");
        title.setFont(titleFont);
        title.setForeground(textDark);

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

        model = new DefaultTableModel(
                new String[]{"Nama Produk","Harga","Stok","Kategori"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };

        table = new JTable(model);
        table.setFont(bodyFont);
        table.setRowHeight(26);
        table.setSelectionBackground(primary);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(bodyFont);
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }

            private void search() {
                String text = txtSearch.getText();
                sorter.setRowFilter(
                        text.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + text)
                );
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        JButton btnBack    = createButton("Dashboard", false, primary);
        JButton btnRefresh = createButton("Refresh", false, primary);
        JButton btnTambah  = createButton("Tambah Produk", true, primary);
        JButton btnHapus   = createButton("Hapus Produk", true, danger);
        JButton btnEdit    = createButton("Edit Produk", true, primary);

        btnBack.addActionListener(e -> card.show(container,"dashboard"));
        btnRefresh.addActionListener(e -> refreshData());
        btnTambah.addActionListener(e -> card.show(container,"produk_form"));

        // ===== HAPUS =====
        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,"Pilih produk yang ingin dihapus!","Peringatan",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int modelRow = table.convertRowIndexToModel(row);
            String nama = model.getValueAt(modelRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this,"Yakin hapus produk \"" + nama + "\" ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pm.hapus(modelRow);
                refreshData();
            }
        });

        // ===== EDIT =====
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,"Pilih produk yang ingin diedit!","Peringatan",JOptionPane.WARNING_MESSAGE);
                return;
            }
            int modelRow = table.convertRowIndexToModel(row);
            String nama     = model.getValueAt(modelRow, 0).toString();
            String harga    = model.getValueAt(modelRow, 1).toString();
            String stok     = model.getValueAt(modelRow, 2).toString();
            Kategori kategori = (Kategori) model.getValueAt(modelRow, 3);

            JTextField namaField = new JTextField(nama);
            JTextField hargaField = new JTextField(harga);
            JTextField stokField = new JTextField(stok);
            JComboBox<Kategori> kategoriBox = new JComboBox<>(Kategori.values());
            kategoriBox.setSelectedItem(kategori);

            JPanel panel = new JPanel(new GridLayout(0,1,6,6));
            panel.add(new JLabel("Nama Produk:")); panel.add(namaField);
            panel.add(new JLabel("Harga:")); panel.add(hargaField);
            panel.add(new JLabel("Stok:")); panel.add(stokField);
            panel.add(new JLabel("Kategori:")); panel.add(kategoriBox);

            // ===== WARNA TOMBOL =====
            UIManager.put("OptionPane.okButtonText", "Simpan");
            UIManager.put("OptionPane.cancelButtonText", "Batal");
            UIManager.put("Button.background", primary);
            UIManager.put("Button.foreground", Color.WHITE);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Edit Produk",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            // reset supaya tombol lain tidak terpengaruh
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);

            if (result == JOptionPane.OK_OPTION) {
                pm.edit(modelRow,
                        namaField.getText(),
                        Double.parseDouble(hargaField.getText()),
                        Integer.parseInt(stokField.getText()),
                        (Kategori) kategoriBox.getSelectedItem()
                );
                refreshData();
            }
        });

        JPanel bottom = new JPanel(new GridLayout(1,5,12,0));
        bottom.setBackground(bgSoft);
        bottom.add(btnBack);
        bottom.add(btnRefresh);
        bottom.add(btnHapus);
        bottom.add(btnEdit);
        bottom.add(btnTambah);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshData();
    }

    private JButton createButton(String text, boolean filled, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(bodyFont);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8,14,8,14));
        if (filled) { btn.setBackground(color); btn.setForeground(Color.WHITE); }
        else { btn.setBackground(Color.WHITE); btn.setForeground(color); btn.setBorder(BorderFactory.createLineBorder(color)); }
        return btn;
    }

    public void refreshData() {
        model.setRowCount(0);
        pm.getAll().forEach(p -> model.addRow(new Object[]{
                p.getNama(), p.getHarga(), p.getStok(), p.getKategori()
        }));
    }
}

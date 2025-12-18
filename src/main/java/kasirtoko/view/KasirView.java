package kasirtoko.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import kasirtoko.model.*;
import kasirtoko.service.*;

public class KasirView extends JPanel {

    Transaksi trx = new Transaksi();

    DefaultTableModel mProduk =
            new DefaultTableModel(new String[]{"Nama","Harga"},0);
    DefaultTableModel mKeranjang =
            new DefaultTableModel(new String[]{"Nama","Harga","Qty","Subtotal"},0);

    public KasirView(CardLayout card, JPanel container, ProdukManager m) {
        setLayout(new GridLayout(1,2));

        JTable tProduk = new JTable(mProduk);
        JTable tKeranjang = new JTable(mKeranjang);

        for (var p : m.getAll()) {
            mProduk.addRow(new Object[]{p.nama, p.harga});
        }

        JButton tambah = new JButton("Tambah");
        JButton bayar = new JButton("Bayar");
        JButton back = new JButton("← Dashboard");

        tambah.addActionListener(e -> {
            int i = tProduk.getSelectedRow();
            if (i >= 0) {
                trx.tambah(m.get(i));
                refresh();
            }
        });

        bayar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, trx.struk());
            trx.simpanStruk();
            trx.reset();
            refresh();
        });

        back.addActionListener(e -> card.show(container,"dashboard"));

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(new JScrollPane(tProduk));
        kiri.add(tambah, BorderLayout.SOUTH);

        JPanel kanan = new JPanel(new BorderLayout());
        kanan.add(new JScrollPane(tKeranjang));

        JPanel bawah = new JPanel();
        bawah.add(back);
        bawah.add(bayar);

        kanan.add(bawah, BorderLayout.SOUTH);

        add(kiri);
        add(kanan);
    }

    private void refresh() {
        mKeranjang.setRowCount(0);
        for (ItemTransaksi it : trx.getItems()) {
            mKeranjang.addRow(new Object[]{
                    it.produk.nama, it.produk.harga, it.qty, it.subtotal()
            });
        }
    }
}

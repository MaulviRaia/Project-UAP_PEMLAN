package kasirtoko.view;

import javax.swing.*;
import java.awt.*;
import kasirtoko.service.*;

public class ProdukFormView extends JPanel {

    public ProdukFormView(CardLayout card, JPanel container, ProdukManager m) {
        setLayout(new GridLayout(4,2,10,10));
        setBorder(BorderFactory.createEmptyBorder(60,250,60,250));

        JTextField nama = new JTextField();
        JTextField harga = new JTextField();

        JButton simpan = new JButton("Simpan");
        JButton back = new JButton("← Dashboard");

        simpan.addActionListener(e -> {
            try {
                m.tambah(nama.getText(), Double.parseDouble(harga.getText()));
                nama.setText("");
                harga.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input salah");
            }
        });

        back.addActionListener(e -> card.show(container,"dashboard"));

        add(new JLabel("Nama Produk"));
        add(nama);
        add(new JLabel("Harga"));
        add(harga);
        add(back);
        add(simpan);
    }
}

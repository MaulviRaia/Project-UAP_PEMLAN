package kasirtoko.view;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {

    public DashboardView(CardLayout card, JPanel container) {
        setLayout(new GridLayout(2,2,20,20));
        setBorder(BorderFactory.createEmptyBorder(60,80,60,80));

        JButton b1 = new JButton("Data Produk");
        JButton b2 = new JButton("Tambah Produk");
        JButton b3 = new JButton("Kasir");
        JButton b4 = new JButton("Keluar");

        b1.addActionListener(e -> card.show(container, "produk"));
        b2.addActionListener(e -> card.show(container, "form"));
        b3.addActionListener(e -> card.show(container, "kasir"));
        b4.addActionListener(e -> System.exit(0));

        add(b1); add(b2); add(b3); add(b4);
    }
}

package kasirtoko;

import javax.swing.*;
import java.awt.*;
import kasirtoko.view.*;
import kasirtoko.service.*;

public class MainApp extends JFrame {

    CardLayout card = new CardLayout();
    JPanel container = new JPanel(card);
    ProdukManager manager = new ProdukManager();

    public MainApp() {
        setTitle("Aplikasi Kasir Toko");
        setSize(1000,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.add(new DashboardView(card, container), "dashboard");
        container.add(new ProdukListView(card, container, manager), "produk");
        container.add(new ProdukFormView(card, container, manager), "form");
        container.add(new KasirView(card, container, manager), "kasir");

        add(container);
        card.show(container, "dashboard");
        setVisible(true);
    }
//
    public static void main(String[] args) {
        new MainApp();
    }
}

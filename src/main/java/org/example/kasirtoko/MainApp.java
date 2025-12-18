package org.example.kasirtoko;

import javax.swing.*;
import java.awt.*;

import org.example.kasirtoko.service.ProdukManager;
import org.example.kasirtoko.view.DashboardView;
import org.example.kasirtoko.view.KasirView;
import org.example.kasirtoko.view.ProdukFormView;
import org.example.kasirtoko.view.ProdukListView;

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

package org.example.kasirtoko;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import org.example.kasirtoko.view.*;
import org.example.kasirtoko.service.*;

public class MainApp extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    ProdukManager produkManager = new ProdukManager();
    TransaksiManager transaksiManager = new TransaksiManager();

    // ===== VIEW INSTANCE =====
    DashboardView dashboardView;
    ProdukListView produkListView;
    KasirView kasirView;
    RiwayatTransaksiView riwayatView;

    // ===== THEME =====
    Color sidebarBg   = new Color(24, 28, 36);
    Color sidebarLine = new Color(45, 50, 65);
    Color accent      = new Color(33, 150, 243);
    Color textWhite   = Color.WHITE;
    Color textSoft    = new Color(180, 180, 180);

    Font fontTitle = new Font("Segoe UI", Font.BOLD, 18);
    Font fontMenu  = new Font("Segoe UI", Font.PLAIN, 14);
    Font fontSmall = new Font("Segoe UI", Font.PLAIN, 11);

    // ===== ACTIVE MENU =====
    JButton activeButton;
    Map<JButton, JPanel> indicatorMap = new HashMap<>();

    public MainApp() {
        setTitle("Kasir Toko");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(sidebarBg);
        sidebar.setBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, sidebarLine)
        );

        // ===== TITLE =====
        JLabel title = new JLabel("KASIR TOKO", SwingConstants.CENTER);
        title.setForeground(textWhite);
        title.setFont(fontTitle);
        title.setBorder(BorderFactory.createEmptyBorder(25, 10, 20, 10));

        // ===== MENU =====
        JPanel menu = new JPanel(new GridLayout(6,1,0,12));
        menu.setBackground(sidebarBg);
        menu.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JButton btnDashboard = createMenuItem("Dashboard");
        JButton btnProduk    = createMenuItem("Manajemen Produk");
        JButton btnKasir     = createMenuItem("Kasir");
        JButton btnRiwayat   = createMenuItem("Riwayat Transaksi");
        JButton btnExit      = createMenuItem("Keluar");

        menu.add(wrapMenu(btnDashboard));
        menu.add(wrapMenu(btnProduk));
        menu.add(wrapMenu(btnKasir));
        menu.add(wrapMenu(btnRiwayat));
        menu.add(wrapMenu(btnExit));

        // ===== COPYRIGHT =====
        JLabel copyright = new JLabel(
                "© 2025 KasirToko App",
                SwingConstants.CENTER
        );
        copyright.setForeground(textSoft);
        copyright.setFont(fontSmall);
        copyright.setBorder(BorderFactory.createEmptyBorder(10, 5, 15, 5));

        sidebar.add(title, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);
        sidebar.add(copyright, BorderLayout.SOUTH);

        // ================= CONTENT =================
        dashboardView = new DashboardView(
                cardLayout, container,
                produkManager, transaksiManager
        );

        produkListView = new ProdukListView(
                cardLayout, container,
                produkManager
        );

        kasirView = new KasirView(
                cardLayout, container,
                produkManager, transaksiManager
        );

        riwayatView = new RiwayatTransaksiView(
                cardLayout, container,
                transaksiManager
        );

        container.add(dashboardView, "dashboard");
        container.add(produkListView, "produk");
        container.add(new ProdukFormView(cardLayout, container, produkManager), "produk_form");
        container.add(kasirView, "kasir");
        container.add(riwayatView, "riwayat");

        // ================= ACTION =================
        btnDashboard.addActionListener(e -> {
            setActive(btnDashboard);
            dashboardView.refresh();
            cardLayout.show(container, "dashboard");
        });

        btnProduk.addActionListener(e -> {
            setActive(btnProduk);
            produkListView.refreshData();
            cardLayout.show(container, "produk");
        });

        btnKasir.addActionListener(e -> {
            setActive(btnKasir);
            kasirView.refreshProduk();
            cardLayout.show(container, "kasir");
        });

        btnRiwayat.addActionListener(e -> {
            setActive(btnRiwayat);
            riwayatView.refreshData();
            cardLayout.show(container, "riwayat");
        });

        btnExit.addActionListener(e -> System.exit(0));

        add(sidebar, BorderLayout.WEST);
        add(container, BorderLayout.CENTER);

        // DEFAULT ACTIVE
        setActive(btnDashboard);
        cardLayout.show(container, "dashboard");

        setVisible(true);
    }

    // ================= MENU ITEM =================
    private JButton createMenuItem(String text) {
        JButton btn = new JButton(text);
        btn.setFont(fontMenu);
        btn.setForeground(textWhite);
        btn.setBackground(sidebarBg);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btn.setOpaque(true);

        // HOVER ANIMATION (SMOOTH)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                animateBg(btn, sidebarBg, accent.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeButton) {
                    animateBg(btn, btn.getBackground(), sidebarBg);
                }
            }
        });

        return btn;
    }

    // ================= MENU WRAPPER + INDICATOR =================
    private JPanel wrapMenu(JButton btn) {
        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(4, 0));
        indicator.setBackground(accent);
        indicator.setVisible(false);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(sidebarBg);
        wrapper.add(indicator, BorderLayout.WEST);
        wrapper.add(btn, BorderLayout.CENTER);

        indicatorMap.put(btn, indicator);
        return wrapper;
    }

    // ================= ACTIVE STATE =================
    private void setActive(JButton btn) {
        if (activeButton != null) {
            activeButton.setBackground(sidebarBg);
            indicatorMap.get(activeButton).setVisible(false);
        }

        activeButton = btn;
        btn.setBackground(accent);
        indicatorMap.get(btn).setVisible(true);
    }

    // ================= ANIMATION =================
    private void animateBg(JButton btn, Color from, Color to) {
        Timer timer = new Timer(10, null);
        final float[] progress = {0};

        timer.addActionListener(e -> {
            progress[0] += 0.08f;
            if (progress[0] >= 1) {
                btn.setBackground(to);
                timer.stop();
                return;
            }

            int r = (int)(from.getRed()   + (to.getRed()   - from.getRed())   * progress[0]);
            int g = (int)(from.getGreen() + (to.getGreen() - from.getGreen()) * progress[0]);
            int b = (int)(from.getBlue()  + (to.getBlue()  - from.getBlue())  * progress[0]);

            btn.setBackground(new Color(r, g, b));
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}

package org.example.kasirtoko;

import javax.swing.*;
import java.awt.*;
import org.example.kasirtoko.view.*;
import org.example.kasirtoko.service.*;

public class MainApp extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    ProdukManager produkManager = new ProdukManager();
    TransaksiManager transaksiManager = new TransaksiManager();

    // ===== COLOR THEME =====
    Color sidebarBg = new Color(22, 24, 28);
    Color sidebarLine = new Color(45, 48, 55);
    Color mainBg = new Color(245, 246, 250);
    Color accent = new Color(0, 184, 148);
    Color btnBg = new Color(32, 34, 40);
    Color btnHover = accent;

    Font fontTitle = new Font("Segoe UI", Font.BOLD, 20);
    Font fontMenu = new Font("Segoe UI", Font.PLAIN, 15);
    Font fontFooter = new Font("Segoe UI", Font.PLAIN, 12);

    public MainApp() {
        setTitle("Sistem Manajemen | Kasir Toko");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(230, 0));
        sidePanel.setBackground(sidebarBg);

        JLabel title = new JLabel("Page Menu", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(fontTitle);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));

        JSeparator separator = new JSeparator();
        separator.setForeground(sidebarLine);

        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 0, 12));
        menuPanel.setBackground(sidebarBg);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JButton btnDashboard = createMenuButton("Dashboard");
        JButton btnProduk = createMenuButton("Produk");
        JButton btnKasir = createMenuButton("Kasir");
        JButton btnRiwayat = createMenuButton("Riwayat");
        JButton btnExit = createMenuButton("Keluar");

        menuPanel.add(btnDashboard);
        menuPanel.add(btnProduk);
        menuPanel.add(btnKasir);
        menuPanel.add(btnRiwayat);
        menuPanel.add(btnExit);

        JLabel footer = new JLabel("© 2025 TokoSumberTerang", SwingConstants.CENTER);
        footer.setForeground(new Color(160, 160, 160));
        footer.setFont(fontFooter);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 5, 15, 5));

        // ==== sidebar layout fix ====
        JPanel topSide = new JPanel(new BorderLayout());
        topSide.setBackground(sidebarBg);
        topSide.add(title, BorderLayout.NORTH);
        topSide.add(separator, BorderLayout.SOUTH);

        sidePanel.add(topSide, BorderLayout.NORTH);
        sidePanel.add(menuPanel, BorderLayout.CENTER);
        sidePanel.add(footer, BorderLayout.SOUTH);

        // ================= CONTENT =================
        container.setBackground(mainBg);

        container.add(
                new DashboardView(cardLayout, container, produkManager, transaksiManager),
                "dashboard"
        );
        container.add(
                new ProdukListView(cardLayout, container, produkManager),
                "produk"
        );
        container.add(
                new ProdukFormView(cardLayout, container, produkManager),
                "produk_form"
        );
        container.add(
                new KasirView(cardLayout, container, produkManager, transaksiManager),
                "kasir"
        );
        container.add(
                new RiwayatTransaksiView(cardLayout, container, transaksiManager),
                "riwayat"
        );

        // ================= ACTION =================
        btnDashboard.addActionListener(e -> cardLayout.show(container, "dashboard"));
        btnProduk.addActionListener(e -> cardLayout.show(container, "produk"));
        btnKasir.addActionListener(e -> cardLayout.show(container, "kasir"));
        btnRiwayat.addActionListener(e -> cardLayout.show(container, "riwayat"));
        btnExit.addActionListener(e -> System.exit(0));

        add(sidePanel, BorderLayout.WEST);
        add(container, BorderLayout.CENTER);

        cardLayout.show(container, "dashboard");
        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(btnBg);
        btn.setForeground(Color.WHITE);
        btn.setFont(fontMenu);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(btnHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(btnBg);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}

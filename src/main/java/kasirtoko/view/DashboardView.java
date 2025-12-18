package kasirtoko.view;
import javax.swing.*;
import java.awt.*;


public class DashboardView extends JPanel{
    public DashboardView(CardLayout card,JPanel c){
        setLayout(new GridLayout(2,2,20,20));
        setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        JButton b1=new JButton("Data Produk");
        JButton b2=new JButton("Tambah Produk");
        JButton b3=new JButton("Kasir");
        JButton b4=new JButton("Keluar");
        b1.addActionListener(e->card.show(c,"produk"));
        b2.addActionListener(e->card.show(c,"form"));
        b3.addActionListener(e->card.show(c,"kasir"));
        b4.addActionListener(e->System.exit(0));
        add(b1);add(b2);add(b3);add(b4);
    }
}
package kasirtoko.view;
import javax.swing.*;
import java.awt.*;
import kasirtoko.service.*;


public class ProdukFormView extends JPanel{
    public ProdukFormView(CardLayout card,JPanel c,ProdukManager m){
        setLayout(new GridLayout(4,2,10,10));
        setBorder(BorderFactory.createEmptyBorder(50,250,50,250));
        JTextField n=new JTextField();
        JTextField h=new JTextField();
        JButton s=new JButton("Simpan");
        s.addActionListener(e->{try{m.tambah(n.getText(),Double.parseDouble(h.getText()));n.setText("");h.setText("");}catch(Exception ex){}});
        JButton b=new JButton("Dashboard");
        b.addActionListener(e->card.show(c,"dashboard"));
        add(new JLabel("Nama"));add(n);
        add(new JLabel("Harga"));add(h);
        add(s);add(b);
    }
}
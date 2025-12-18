package kasirtoko.view;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import kasirtoko.service.*;


public class ProdukListView extends JPanel{
    DefaultTableModel model=new DefaultTableModel(new String[]{"Nama","Harga"},0);
    JTable table=new JTable(model);


    public ProdukListView(CardLayout card,JPanel c,ProdukManager m){
        setLayout(new BorderLayout());
        refresh(m);
        JButton h=new JButton("Hapus");
        h.addActionListener(e->{int i=table.getSelectedRow();if(i>=0){m.hapus(i);refresh(m);}});
        JButton b=new JButton("Dashboard");
        b.addActionListener(e->card.show(c,"dashboard"));
        JPanel bot=new JPanel();bot.add(h);bot.add(b);
        add(new JScrollPane(table));add(bot,BorderLayout.SOUTH);
    }


    void refresh(ProdukManager m){
        model.setRowCount(0);
        for(var p:m.getAll())model.addRow(new Object[]{p.nama,p.harga});
    }
}
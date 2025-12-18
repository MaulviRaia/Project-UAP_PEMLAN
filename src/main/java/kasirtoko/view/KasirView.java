package kasirtoko.view;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import kasirtoko.model.*;
import kasirtoko.service.*;


public class KasirView extends JPanel{
    Transaksi trx=new Transaksi();
    DefaultTableModel mp=new DefaultTableModel(new String[]{"Nama","Harga"},0);
    DefaultTableModel mk=new DefaultTableModel(new String[]{"Nama","Harga","Qty","Subtotal"},0);


    public KasirView(CardLayout card,JPanel c,ProdukManager m){
        setLayout(new GridLayout(1,2));
        JTable tp=new JTable(mp);
        JTable tk=new JTable(mk);
        for(var p:m.getAll())mp.addRow(new Object[]{p.nama,p.harga});
        JButton t=new JButton("Tambah");
        t.addActionListener(e->{int i=tp.getSelectedRow();if(i>=0){trx.tambah(m.get(i));refresh();}});
        JButton b=new JButton("Bayar");
        b.addActionListener(e->{JOptionPane.showMessageDialog(this,trx.struk());trx.simpan();trx.reset();refresh();});
        JPanel l=new JPanel(new BorderLayout());l.add(new JScrollPane(tp));l.add(t,BorderLayout.SOUTH);
        JPanel r=new JPanel(new BorderLayout());r.add(new JScrollPane(tk));r.add(b,BorderLayout.SOUTH);
        add(l);add(r);
        
    }


    void refresh(){
        mk.setRowCount(0);
        for(ItemTransaksi it:trx.items)mk.addRow(new Object[]{it.produk.nama,it.produk.harga,it.qty,it.subtotal()});
    }
}
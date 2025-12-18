package kasirtoko.service;
import java.util.*;
import java.io.*;
import kasirtoko.model.*;


public class ProdukManager {
    ArrayList<Produk> list=new ArrayList<>();
    File file=new File("produk.csv");


    public ProdukManager(){load();}


    public void tambah(String n,double h){list.add(new Produk(n,h));save();}
    public void hapus(int i){list.remove(i);save();}
    public ArrayList<Produk> getAll(){return list;}
    public Produk get(int i){return list.get(i);}


    void load(){
        try{
            if(!file.exists())return;
            BufferedReader br=new BufferedReader(new FileReader(file));
            String s;
            while((s=br.readLine())!=null){
                String[] d=s.split(",");
                list.add(new Produk(d[0],Double.parseDouble(d[1])));
            }
            br.close();
        }catch(Exception e){}
    }


    void save(){
        try{
            PrintWriter pw=new PrintWriter(file);
            for(Produk p:list)pw.println(p.nama+","+p.harga);
            pw.close();
        }catch(Exception e){}
    }
}
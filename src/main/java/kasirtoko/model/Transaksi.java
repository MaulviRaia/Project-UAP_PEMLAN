package kasirtoko.model;

import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Transaksi implements Serializable {

    private ArrayList<ItemTransaksi> items = new ArrayList<>();
    private final File file = new File("keranjang.dat");

    public Transaksi() {
        load();
    }

    public void tambah(Produk p) {
        for (ItemTransaksi it : items) {
            if (it.produk.nama.equals(p.nama)) {
                it.qty++;
                save();
                return;
            }
        }
        items.add(new ItemTransaksi(p));
        save();
    }

    public ArrayList<ItemTransaksi> getItems() {
        return items;
    }

    public double total() {
        double total = 0;
        for (ItemTransaksi it : items) {
            total += it.subtotal();
        }
        return total;
    }

    public String struk() {
        StringBuilder sb = new StringBuilder();
        sb.append("STRUK BELANJA\n");
        sb.append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        sb.append("\n\n");

        for (ItemTransaksi it : items) {
            sb.append(it.produk.nama)
                    .append(" x")
                    .append(it.qty)
                    .append(" = ")
                    .append(it.subtotal())
                    .append("\n");
        }

        sb.append("\nTOTAL : ").append(total());
        return sb.toString();
    }

    public void simpanStruk() {
        try (FileWriter fw = new FileWriter("struk.txt", true)) {
            fw.write(struk());
            fw.write("\n----------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        items.clear();
        save();
    }

    private void save() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        if (!file.exists()) return;
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {
            items = (ArrayList<ItemTransaksi>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

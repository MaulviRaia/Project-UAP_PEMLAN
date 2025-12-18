package kasirtoko.model;
import java.io.Serializable;

public class Produk implements Serializable {
    public String nama;
    public double harga;

    public Produk(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }
}

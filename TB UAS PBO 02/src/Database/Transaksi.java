package Database;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.cj.jdbc.PreparedStatementWrapper;

//Inheritance 
//Interface 
public class Transaksi extends List implements Pemesanan {
    // Class, Atribut, Method
    public String NoFaktur;
    public String namapemesan;
    public int hargaproduk;
    public String produk;
    public int jumlah;
    public int Total;
    Connection conn;
    Statement statement;
    PreparedStatement stmt;

    Scanner input = new Scanner(System.in);

    public Transaksi() {
        try {
            // Java Database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tbuas_pbo02?serverTimezone=Asia/Jakarta", "root",
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Overriding
    public void NomorFaktur() {
        System.out.print("Masukkan Nomor Faktur : ");
        NoFaktur = input.nextLine();

    }

    @Override
    public void NamaPemesan() {
        System.out.print("Masukkan Nama Pemesan : ");
        this.namapemesan = input.nextLine();
    }

    @Override
    public void Produk() {
        System.out.print("Masukkan Nama Produk : ");
        this.produk = input.nextLine();
    }

    @Override
    public void HargaProduk() {
        System.out.print("Masukkan Harga Produk : ");
        this.hargaproduk = input.nextInt();
    }

    @Override
    public void Jumlah() {
        System.out.print("Masukkan Jumlah : ");
        this.jumlah = input.nextInt();
    }

    @Override
    public void Total() {
        Total = hargaproduk * jumlah;
        System.out.println("Total Harga = " + Total);
    }

    public void getAll() {
        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM transaksi";
            ResultSet rs = statement.executeQuery(sql);
            String format1 = " %s\t %s\t %s\t %s\t %s\t %s\t\t %s\t\t";
            System.out.printf(format1, "No. ", "no Faktur", "Nama Pemesan", "Nama Produk", "Harga Produk", "Jumlah",
                    "Total harga");
            System.out.print(
                    "\n|--------------------------------------------------------------------------------------------------------------------|\n");
            int i = 1;
            while (rs.next()) {
                String shownofaktur = rs.getString("noFaktur");
                String shownamapemesan = rs.getString("nama_pemesan");
                String shownamaproduk = rs.getString("nama_produk");
                int showharga = rs.getInt("harga_produk");
                int showjumlah = rs.getInt("jumlah_barang");
                int showtotal = rs.getInt("total_harga");

                String format2 = " [%s]\t %s\t\t %s\t\t %s\t\t %s\t\t %s\t\t %s\t\t \n";
                System.out.printf(format2, i, shownofaktur, shownamapemesan, shownamaproduk, showharga, showjumlah,
                        showtotal);
                i++;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData() {
        NomorFaktur();
        NamaPemesan();
        Produk();
        HargaProduk();
        Jumlah();
        Total();
        try {
            Statement stmt = conn.createStatement();
            String Query = "INSERT INTO transaksi(noFaktur,nama_pemesan,nama_produk,harga_produk,jumlah_barang,total_harga) VALUES ('"
                    + NoFaktur + "', '" + namapemesan + "', '" + produk + "', '" + hargaproduk + "' , '" + jumlah
                    + "' , '" + Total + "')";

            extracted(stmt, Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void extracted(Statement stmt, String Query) throws SQLException {
        stmt.executeUpdate(Query);
    }

    public void edit() {
        getAll();
        System.out.print("\nMasukan Nomor Faktur yang akan diedit ? :  ");
        String NoFaktur = input.nextLine();

        String sql = "SELECT * FROM transaksi WHERE noFaktur = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, NoFaktur);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.print("No Faktur [" + rs.getString("noFaktur") + "]: ");
                String faktur = input.nextLine();

                System.out.print("Nama Pemesan [" + rs.getString("nama_pemesan") + "]: ");
                String nama1 = input.nextLine();

                System.out.print("Nama Produk [" + rs.getString("nama_produk") + "]: ");
                String nama2 = input.nextLine();

                HargaProduk();
                Jumlah();
                Total();

                String update = "UPDATE transaksi SET noFaktur = ? , nama_pemesan = ? , nama_produk = ? , harga_produk = ?, jumlah_barang = ?, total_harga = ? WHERE noFaktur = ? ";

                statement = conn.prepareStatement(update);
                statement.setString(1, faktur);
                statement.setString(2, nama1);
                statement.setString(3, nama2);
                statement.setInt(4, this.hargaproduk);
                statement.setInt(5, this.jumlah);
                statement.setInt(6, this.Total);
                statement.setString(7, NoFaktur);
                statement.executeUpdate();

                System.out.println("Berhasil memperbaharui data koleksi");

                statement.close();

            } else {
                System.out.println("Data tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void hapus() {
        try {
            getAll();
            System.out.print("Nomor Faktur Data yang akan dihapus ? ");
            String NoFaktur = input.nextLine();
            if (NoFaktur.equals("")) {
                System.out.println("Nomor tidak valid");
            } else {
                String sql = "DELETE FROM transaksi WHERE noFaktur = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, NoFaktur);
                statement.executeUpdate();
                System.out.println("Berhasil menghapus data faktur");
            }

        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan dalam menghapus data");
        }
    }

    public void print() {
        try {
            getAll();
            statement = conn.createStatement();
            String sql = "SELECT * FROM Transaksi";
            ResultSet rs = statement.executeQuery(sql);

            FileWriter file = new FileWriter("Struk.txt");
            PrintWriter printer = new PrintWriter(file);

            // String and date
            LocalDateTime tanggal = LocalDateTime.now();
            DateTimeFormatter formattanggal = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
            String tanggaltransaksi = tanggal.format(formattanggal);

            printer.print(
                    "\n#====================================================================================================================#\n");
            printer.print(
                    "|----------------------------------------- SELAMAT DATANG DI HengSpeed Shop -------------------------------------------|\n");
            printer.print(
                    "#====================================================================================================================#\n\n");
            printer.print(
                    "\n|----------------------------------------------- Struk Belanja ------------------------------------------------------|\n");
            printer.print(" Waktu Transaksi : " + tanggaltransaksi);
            printer.print(
                    "\n|--------------------------------------------------------------------------------------------------------------------|\n");
            printer.printf(" %s\t %s\t %s\t %s\t %s\t %s\t\t %s\t\t ", "No. ", "Nomor Faktur", "Nama Pemesan",
                    "Nama Produk", "Harga Produk", "Jumlah", "Total harga");
            printer.print(
                    "\n|--------------------------------------------------------------------------------------------------------------------|\n");

            // Manipulasi file & objek -
            int i = 1;
            while (rs.next()) {
                String shownofaktur = rs.getString("noFaktur");
                String shownamapemesan = rs.getString("nama_pemesan");
                String shownamaproduk = rs.getString("nama_produk");
                int showharga = rs.getInt("harga_produk");
                int showjumlah = rs.getInt("jumlah_barang");
                int showtotal = rs.getInt("total_harga");

                printer.printf(" [%s]\t %s\t\t %s\t\t %s\t\t %s\t\t %s\t\t %s\t\t \n", i, shownofaktur, shownamapemesan,
                        shownamaproduk, showharga, showjumlah, showtotal);
                i++;
            }
            printer.print(
                    "|--------------------------------------------------------------------------------------------------------------------|\n");
            printer.close();
            System.out.println("Berhasil mencetak struk!");
        } catch (Exception e) {
            System.out.print("Gagal mencetak struk!");
            e.printStackTrace();
        }

    }

}

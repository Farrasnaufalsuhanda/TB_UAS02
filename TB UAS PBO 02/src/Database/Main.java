package Database;

import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

public class Main {
    // Constructor
    static Scanner scanner = new Scanner(System.in);
    static Transaksi barang = new Transaksi();

    // Class, Atribut, Method
    public static void main(String[] args) {
        Integer option = 0;

        do {
            System.out.println(
                    "\n#====================================================================================================================#");
            System.out.println(
                    "|----------------------------------------- SELAMAT DATANG DI HengSpeed Shop -----------------------------------------|");
            System.out.println(
                    "#====================================================================================================================#");
            System.out.println(">>> Opsi Pilihan");
            System.out.println(" 1. Tambah Data");
            System.out.println(" 2. Edit Data");
            System.out.println(" 3. Hapus Data");
            System.out.println(" 4. Tampilkan Data");
            System.out.println(" 5. Print Struk");
            System.out.println(" 0. Keluar");
            System.out.print("\nPilihan Anda (1/2/3/4/5/0)? ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    tambah();
                    tunggu();
                    break;
                case 2:
                    edit();
                    tunggu();
                    break;
                case 3:
                    hapus();
                    tunggu();
                    break;
                case 4:
                    tampil();
                    tunggu();
                    break;
                case 5:
                    print();
                    tunggu();
                case 0:
                    break;
                default:
                    System.out.println("Input tidak valid");
            }

        } while (option != 0);

    }

    private static void tunggu() {
        System.out.print("\n\nTekan Enter untuk melanjutkan");
        scanner.nextLine();
    }

    // Java Database Lanjutan (Edit, Insert, Delete)
    public static void edit() {
        System.out.println(" >> Edit Data Faktur");
        barang.edit();
    }

    public static void tambah() {
        System.out.println(" >> Tambah Data Faktur");
        barang.insertData();
    }

    public static void hapus() {
        System.out.println(" >> Hapus Data Faktur");

        barang.hapus();

    }

    public static void tampil() {
        System.out.println(" >> Tampil Data Faktur");

        barang.getAll();

    }

    public static void print() {
        System.out.println(" >> Print Struk");

        barang.print();

    }

}

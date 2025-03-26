/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 5
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class Menu extends JFrame{

    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();
        // atur ukuran window
        window.setSize(800, 600);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.MainPanel);
        // Ubah warna background
        window.MainPanel.setBackground(new Color(102, 205, 170)); // Warna Tosca)
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;

    // Array of Object List Mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    // Database Mahasiswa
    private Database database;

    //Atribut GUI Java Swing
    private JPanel MainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JRadioButton Islam;
    private JRadioButton Kristen;
    private JRadioButton Katolik;
    private JRadioButton Hindu;
    private JRadioButton Budha;
    private JRadioButton Khonghucu;
    private ButtonGroup AgamaGroup;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel Agama;

    // constructor
    public Menu() {

        //Buat Object database untuk mahasiswa
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.BLUE);

        // atur isi combo box
        jenisKelaminComboBox.addItem("Laki-laki");
        jenisKelaminComboBox.addItem("Perempuan");

        // Atur Isi Radio Button untuk Agama
        //Kelompokkan Kedalam Group Agama
        AgamaGroup = new ButtonGroup();
        AgamaGroup.add(Islam);
        AgamaGroup.add(Kristen);
        AgamaGroup.add(Katolik);
        AgamaGroup.add(Hindu);
        AgamaGroup.add(Budha);
        AgamaGroup.add(Khonghucu);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ambil index baris yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                try{
                    // Pastikan selectedIndex valid
                    if (selectedIndex >= 0) {
                        // Ambil data dari database berdasarkan NIM
                        String nim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();
                        ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
                        while (resultSet.next()){
                            if (resultSet.getString("nim").equals(nim)){
                                break;
                            }
                        }

                        // Isi form dengan data dari database
                        nimField.setText(resultSet.getString("nim"));
                        namaField.setText(resultSet.getString("nama"));
                        jenisKelaminComboBox.setSelectedItem(resultSet.getString("jenis_kelamin"));

                        // Setel radio button berdasarkan agama yang tersimpan
                        String agama = resultSet.getString("agama");

                        // Setel radio button berdasarkan agama yang tersimpan
                        switch (agama) {
                            case "Islam": Islam.setSelected(true); break;
                            case "Kristen": Kristen.setSelected(true); break;
                            case "Katolik": Katolik.setSelected(true); break;
                            case "Hindu": Hindu.setSelected(true); break;
                            case "Budha": Budha.setSelected(true); break;
                            case "Khonghucu": Khonghucu.setSelected(true); break;
                        }

                        // ubah button "Add" menjadi "Update"
                        addUpdateButton.setText("Update");
                        // tampilkan button delete
                        deleteButton.setVisible(true);
                    }
                }
                catch (SQLException exeption){
                    throw  new RuntimeException(exeption);
                }
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No.", "NIM", "Nama", "Jenis Kelamin", "Agama"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("agama");
                temp.addRow(row);
                i++;
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return temp;
    }

    public void insertData() {
        // ambil value dari textfield dan combo-box
        String nim = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
        String agama = getSelectedAgama().trim();

        try{
            //Check Data Ke Database dulu
            ResultSet checkNIM = database.selectQuery("SELECT nim FROM mahasiswa WHERE nim = '" + nim + "'");
            String resultnim = null;
            while (checkNIM.next()){
                if (checkNIM.getString("nim").equals(nim)){
                    resultnim = checkNIM.getString("nim");
                    break;
                }
            }

            // Validasi input tidak boleh kosong
            if (nim.isEmpty() || nama.isEmpty() || jenisKelamin == null || agama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua kolom harus terisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                // update tabel
                mahasiswaTable.setModel(setTable());
                // bersihkan form
                clearForm();
            }
            // Validasi Jika NIM ada yang sama dengan Database
            else if (resultnim != null){
                JOptionPane.showMessageDialog(this, "NIM sudah terdaftar di Database!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                // update tabel
                mahasiswaTable.setModel(setTable());
                // bersihkan form
                clearForm();
            }
            else{
                // tambahkan data ke dalam list
                String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + agama + "')";
                database.InsertUpdateDeleteQuery(sql);
                // update tabel
                mahasiswaTable.setModel(setTable());
                // bersihkan form
                clearForm();
                // feedback
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }

    }

    public void updateData() {
        if (selectedIndex >= 0) {
            // ambil data dari form
            String nimLama = mahasiswaTable.getValueAt(selectedIndex, 1).toString();
            String nim = nimField.getText().trim();
            String nama = namaField.getText().trim();
            String jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
            String agama = getSelectedAgama().trim();

            try{

                // Validasi input tidak boleh kosong
                if (nim.isEmpty() || nama.isEmpty() || jenisKelamin == null || agama.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua kolom harus terisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    // update tabel
                    mahasiswaTable.setModel(setTable());
                    // bersihkan form
                    clearForm();
                }
                // Validasi Jika NIM yang berubah sama dengan nim yang ada di Database
                else if (!nimLama.equals(nim)){
                    //Check Data Ke Database dulu
                    ResultSet checkNIM = database.selectQuery("SELECT nim FROM mahasiswa WHERE nim = '" + nim + "'");
                    while (checkNIM.next()){
                        if (checkNIM.getString("nim").equals(nim)){
                            JOptionPane.showMessageDialog(this, "NIM sudah terdaftar di Database!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                            // update tabel
                            mahasiswaTable.setModel(setTable());
                            // bersihkan form
                            clearForm();
                            break;
                        }
                    }
                }
                else{
                    // tambahkan data ke dalam list
                    String sql = "UPDATE mahasiswa SET nama = '" + nama + "', " +
                            "jenis_kelamin = '" + jenisKelamin + "', " +
                            "agama = '" + agama + "' " +
                            "WHERE nim = '" + nim + "'";
                    database.InsertUpdateDeleteQuery(sql);

                    // update tabel
                    mahasiswaTable.setModel(setTable());
                    // bersihkan form
                    clearForm();
                    // feedback
                    JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");

                }
            }
            catch (SQLException e){
                throw  new RuntimeException(e);
            }

        }
    }

    public void deleteData() {
        if (selectedIndex >= 0) {

            String nim = nimField.getText().trim();
            // Tampilkan konfirmasi sebelum menghapus data
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin menghapus data ini?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            // Jika user memilih "Yes", hapus data
            if (confirm == JOptionPane.YES_OPTION) {
                // Query untuk menghapus data berdasarkan NIM
                String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
                database.InsertUpdateDeleteQuery(sql);
                // update tabel
                mahasiswaTable.setModel(setTable());
                // bersihkan form
                clearForm();
                // feedback
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        AgamaGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private String getSelectedAgama() {
        if (Islam.isSelected()) {
            return "Islam";
        }
        else if (Kristen.isSelected()) {
            return "Kristen";
        }
        else if (Katolik.isSelected()){
            return "Katolik";
        }
        else if (Hindu.isSelected()) {
            return "Hindu";
        }
        else if (Budha.isSelected()) {
            return "Budha";
        }
        else if (Khonghucu.isSelected()) {
            return "Khonghucu";
        }
        else{
            return "";
        }
    }
}
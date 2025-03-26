/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 5
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import java.sql.*;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mahasiswa", "root", "");
            statement = connection.createStatement();
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    public ResultSet selectQuery(String sql){
        try{
            statement.executeQuery(sql);
            return statement.getResultSet();
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    public int InsertUpdateDeleteQuery(String sql){
        try{
            return statement.executeUpdate(sql);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    public Statement getStatement(){
        return statement;
    }
}
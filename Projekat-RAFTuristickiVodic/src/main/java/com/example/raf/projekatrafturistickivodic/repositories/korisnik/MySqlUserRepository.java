package com.example.raf.projekatrafturistickivodic.repositories.korisnik;



import com.example.raf.projekatrafturistickivodic.entities.Korisnik;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository{

    @Override
    public Korisnik findKorisnik(String email) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnik where email = ?");
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("korisnik_id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String tip = resultSet.getString("tip");
                String lozinka = resultSet.getString("lozinka");
                String status = resultSet.getString("status");
                korisnik = new Korisnik(id,ime,prezime,lozinka,email,tip, status);
                System.out.println("id: " + id);
                System.out.println("ime: " + ime);
                System.out.println("prezime: " + prezime);
                System.out.println("tip: " + tip);
                System.out.println("lozinka: " + lozinka);  // Proverite vrednost lozinke
                System.out.println("status: " + status);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public Korisnik addKorisnik(Korisnik korisnik) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"korisnik_id"};
            String hashedPassword = DigestUtils.sha256Hex(korisnik.getLozinka());
            preparedStatement = connection.prepareStatement("INSERT INTO korisnik (email, ime, prezime, tip, lozinka, status) VALUES(?,?,?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, korisnik.getEmail());
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, korisnik.getTip());
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, korisnik.getStatus());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                korisnik.setKorisnik_id(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    //dal ne treba da vraca nista ili treba da vrati tog korisnika.
    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        String status = null;
        try {
            connection = this.newConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = connection.prepareStatement("SELECT status FROM korisnik WHERE korisnik_id = ?");
            preparedStatement1.setInt(1, id);
            resultSet = preparedStatement1.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getString("status").equalsIgnoreCase("aktivan"))
                    status = "neaktivan";
                else
                    status = "aktivan";
            }



            preparedStatement = connection.prepareStatement("UPDATE korisnik SET status = ? WHERE korisnik_id = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement1);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

    }

    @Override
    public Korisnik updateKorisnik(Korisnik korisnik) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = this.newConnection();
            String hashedPassword = DigestUtils.sha256Hex(korisnik.getLozinka());
            preparedStatement = connection.prepareStatement("UPDATE korisnik SET email = ?, ime = ?, prezime = ?, tip = ?, lozinka = ?, status = ? WHERE korisnik_id = ?");
            preparedStatement.setString(1, korisnik.getEmail()); ;
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, korisnik.getTip());
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, korisnik.getStatus());
            preparedStatement.setInt(7, korisnik.getKorisnik_id());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public List<Korisnik> findAllKorisnik(int limit, int offset) {
        List<Korisnik> korisnici = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("select * from korisnik limit ? offset ?");
            statement.setInt(1,limit);
            statement.setInt(2,offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                korisnici.add(new Korisnik(resultSet.getInt("korisnik_id"), resultSet.getString("ime"), resultSet.getString("prezime"),
                        resultSet.getString("lozinka"), resultSet.getString("email"),
                        resultSet.getString("tip"), resultSet.getString("status")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnici;
    }

    @Override
    public int countKorisnika() {
        int br=0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) from korisnik");
            if (resultSet.next()) {
                br = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return br;
    }

}

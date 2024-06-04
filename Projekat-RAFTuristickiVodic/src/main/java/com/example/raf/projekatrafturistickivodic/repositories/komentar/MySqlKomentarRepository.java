package com.example.raf.projekatrafturistickivodic.repositories.komentar;

import com.example.raf.projekatrafturistickivodic.entities.Komentar;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlKomentarRepository extends MySqlAbstractRepository implements KomentarRepository {
    @Override
    public List<Komentar> findAllKomentar(Integer id) {
        List<Komentar> komentars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM komentar where clanak = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();;
            while(resultSet.next()) {
                String ime = resultSet.getString("autor");
                String kom = resultSet.getString("tekst");
                String datum = resultSet.getDate("datum").toString();
                int komentarid = resultSet.getInt("komentar_id");
                komentars.add(new Komentar(ime, kom, komentarid, datum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        if(komentars.isEmpty())
            return null;
        return komentars;
    }

    @Override
    public Komentar addKomentar(Komentar komentar, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"komentar_id"};
            preparedStatement = connection.prepareStatement("Insert into komentar(autor, tekst, datum,clanak) VALUES(?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, komentar.getAutor());
            preparedStatement.setString(2, komentar.getTekst());
            preparedStatement.setString(3,komentar.getDatum());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                komentar.setKomentar_id(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return  komentar;
    }
}

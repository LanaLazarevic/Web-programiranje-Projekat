package com.example.raf.projekatrafturistickivodic.repositories.aktivnost;

import com.example.raf.projekatrafturistickivodic.entities.Aktivnost;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAktivnosrtRepository extends MySqlAbstractRepository implements AktivnostRepository {

    @Override
    public List<Aktivnost> findAllAktivnost() {
        List<Aktivnost> aktivnosts = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from aktivnost");
            while (resultSet.next()) {
                aktivnosts.add(new Aktivnost(resultSet.getInt("aktivnost_id"),
                        resultSet.getString("naziv")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnosts;
    }

    @Override
    public Aktivnost addAktivnost(Aktivnost aktivnost) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"aktivnost_id"};

            preparedStatement = connection.prepareStatement("INSERT INTO aktivnost (naziv) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, aktivnost.getNaziv());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                aktivnost.setAktivnost_id(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnost;
    }

    @Override
    public Integer findAktivnostByName(String aktivnostName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer indeks = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("select aktivnost_id from aktivnost where naziv = ?");
            statement.setString(1, aktivnostName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                indeks = resultSet.getInt("aktivnost_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return indeks;
    }
}

package com.example.raf.projekatrafturistickivodic.repositories.destinacija;




import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDestinacijeRepository extends MySqlAbstractRepository implements DestinacijeRepository {


    @Override
    public List<Destinacija> findAllDestinacije(int limit, int offset) {
        List<Destinacija> destinacijas = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from destinacija limit " + limit + " offset " + offset);
            while (resultSet.next()) {
                destinacijas.add(new Destinacija(resultSet.getInt("destinacija_id"),
                        resultSet.getString("ime"), resultSet.getString("opis")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinacijas;
    }

    @Override
    public Destinacija updateDestinacija(Destinacija destinacija) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //dal radi upit i dal cu imtati prosledjen id ili moram prvo da ga nadjem pa da vrsim update

            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE destinacija SET ime = ?, opis = ? WHERE destinacija_id = ?");
            preparedStatement.setString(1, destinacija.getIme());
            preparedStatement.setString(2, destinacija.getOpis());
            preparedStatement.setInt(3, destinacija.getDestinacija_id());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return destinacija;
    }

    @Override
    public String deleteDestinacija(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {

            connection = this.newConnection();
            preparedStatement2 = connection.prepareStatement("select clanak_id from clanak WHERE destinacija = ?");
            preparedStatement2.setInt(1, id);
            resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                return "Ne moze da se izbrise ova destinacija posto postoje clanci o ovoj destinaciji";
            }

            preparedStatement = connection.prepareStatement("DELETE FROM destinacija WHERE destinacija_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement2);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return "Uspesno obrisano";
    }

    @Override
    public Destinacija addDestinacija(Destinacija destinacija) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"destinacija_id"};
            preparedStatement = connection.prepareStatement("Insert into destinacija (ime, opis) VALUES(?,?)", generatedColumns);
            preparedStatement.setString(1, destinacija.getIme());
            preparedStatement.setString(2, destinacija.getOpis());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                destinacija.setDestinacija_id(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return  destinacija;
    }

    @Override
    public Integer findDestinacijaIdByName(String destinacijaName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer indeks = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("select destinacija_id from destinacija where ime = ?");
            statement.setString(1, destinacijaName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                indeks = resultSet.getInt("destinacija_id");
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

    @Override
    public int countdestinacije() {
        int br=0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) from destinacija");
            while (resultSet.next()) {
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

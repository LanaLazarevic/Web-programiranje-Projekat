package com.example.raf.projekatrafturistickivodic.repositories.clanak;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlClanakRepository extends MySqlAbstractRepository implements ClanakRepository {
    @Override
    public List<Clanak> findAllClanak(String filter) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        Statement preparedStatement = null;
        ResultSet rs = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            if(filter.equalsIgnoreCase("najnoviji"))
                resultSet = statement.executeQuery("SELECT * FROM clanak ORDER BY vreme DESC limit 10");
            else if(filter.equalsIgnoreCase("najcitaniji"))
                resultSet = statement.executeQuery("SELECT * FROM clanak WHERE datum >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ORDER BY br_poseta DESC");
            else
                resultSet = statement.executeQuery("SELECT * FROM clanak ORDER BY vreme DESC");

            while (resultSet.next()) {
                preparedStatement = connection.createStatement();
                rs = preparedStatement.executeQuery("select aktivnost from clanak_aktivnost where clanak = " +  resultSet.getInt("clanak_id"));
                List<Integer> aktivnost = new ArrayList<>();
                while (rs.next()) {
                    aktivnost.add(rs.getInt("aktivnost"));
                }
                clanci.add(new Clanak(resultSet.getString("autor"),
                        resultSet.getInt("clanak_id"),
                        resultSet.getString("naslov"),
                        resultSet.getDate("vreme").toString(), resultSet.getString("tekst"),
                        resultSet.getInt("br_poseta"),
                        resultSet.getInt("destinacija"),
                        aktivnost));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeResultSet(rs);
            this.closeConnection(connection);
        }

        return clanci;
    }

    @Override
    public Clanak updateClanak(Clanak clanak) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement deletemedju = null;
        PreparedStatement umetni = null;
        ResultSet resultSet = null;
        try {

            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE clanak SET naslov = ?, tekst = ?, destinacija = ? WHERE clanak_id = ?");
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setInt(3, clanak.getDestinacija());
            preparedStatement.setInt(4, clanak.getClanak_id());
            preparedStatement.executeUpdate();


            //brisanje medju tabele

            deletemedju = connection.prepareStatement("DELETE FROM clanak_aktivnost WHERE clanak_id = ?");
            deletemedju.setInt(1, clanak.getClanak_id());
            deletemedju.executeUpdate();

            // dodavanje novih i dal rafi ovako dal moze isti statement da se menja
            for (Integer aktivnost : clanak.getAktivnosti()){
                umetni = connection.prepareStatement("INSERT INTO clanak_aktivnost(clanak,aktivnost) VALUES(?,?)");
                umetni.setInt(1, clanak.getClanak_id());
                umetni.setInt(2, aktivnost);
                umetni.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(deletemedju);
            this.closeStatement(umetni);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanak;
    }

    @Override
    public void deleteClanak(Integer id) {
        Connection connection = null;
        //PreparedStatement deleteCommentsStatement = null;
        PreparedStatement deleteClanakStatement = null;
        try {

            connection = this.newConnection();
            // brisanje komentara
//            String deleteCommentsSql = "DELETE FROM komentar WHERE clanak = ?";
//            deleteCommentsStatement = connection.prepareStatement(deleteCommentsSql);
//            deleteCommentsStatement.setInt(1, id);
//            deleteCommentsStatement.executeUpdate();

            // brisanje clanka
            String deleteClanakSql = "DELETE FROM clanak WHERE clanak_id = ?";
            deleteClanakStatement = connection.prepareStatement(deleteClanakSql);
            deleteClanakStatement.setInt(1, id);
            deleteClanakStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //this.closeStatement(deleteCommentsStatement);
            this.closeStatement(deleteClanakStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void incrementbrposetioca(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {


            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE clanak SET br_poseta=br_poseta + 1 WHERE clanak_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }

    @Override
    public Clanak addClanak(Clanak clanak) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"clanak_id"};
            preparedStatement = connection.prepareStatement("INSERT INTO clanak (naslov, tekst, vreme, br_poseta, autor, destinacija) VALUES(?,?,?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setString(3, clanak.getVreme());
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, clanak.getDestinacija());
            preparedStatement.setString(6, clanak.getAutor());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                clanak.setClanak_id(resultSet.getInt("clanak_id"));
            }

            for (Integer aktivnost : clanak.getAktivnosti()){
                preparedStatement1 = connection.prepareStatement("INSERT INTO clanak_aktivnost(clanak,aktivnost) VALUES(?,?)");
                preparedStatement1.setInt(1, clanak.getClanak_id());
                preparedStatement1.setInt(2, aktivnost);
                preparedStatement1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatement1);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanak;
    }

    @Override
    public List<Clanak> findAllClanakByAktivnost(Integer id) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("SELECT c.* FROM clanak c JOIN clanak_aktivnost ca ON c.clanak_id = ca.clanak WHERE ca.aktivnost = ? ORDER BY c.vreme DESC");
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clanci.add(new Clanak(resultSet.getString("autor"),
                        resultSet.getInt("clanak_id"),
                        resultSet.getString("naslov"),
                        resultSet.getDate("vreme").toString(), resultSet.getString("tekst"),
                        resultSet.getInt("br_poseta"),
                        resultSet.getInt("destinacija")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanci;
    }


}

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
            connection.setAutoCommit(false);

            preparedStatement2 = connection.prepareStatement("select clanak_id from clanak WHERE destinacija = ?");
            preparedStatement2.setInt(1, id);
            resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                connection.rollback();
                return "Ne moze da se izbrise ova destinacija posto postoje clanci o ovoj destinaciji";
            }

            preparedStatement = connection.prepareStatement("DELETE FROM destinacija WHERE destinacija_id = ?");
            preparedStatement.setInt(1, id);
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
            return "Došlo je do greške prilikom brisanja";
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
    public String findDestinacijaById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String ime = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("select ime from destinacija where destinacija_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ime = resultSet.getString("ime");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return ime;
    }

    @Override
    public List<Destinacija> findAllDestinacijeByIds(int limit, int offset, List<Integer> ids) {
        List<Destinacija> destinacijas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            StringBuilder query = new StringBuilder("SELECT * FROM destinacija WHERE destinacija_id IN (");
            for (int i = 0; i < ids.size(); i++) {
                query.append("?");
                if (i < ids.size() - 1) {
                    query.append(",");
                }
            }
            query.append(") LIMIT ? OFFSET ?");

            statement = connection.prepareStatement(query.toString());

            for (int i = 0; i < ids.size(); i++) {
                statement.setInt(i + 1, ids.get(i));
            }
            statement.setInt(ids.size() + 1, limit);
            statement.setInt(ids.size() + 2, offset);

            resultSet = statement.executeQuery();
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
    public int countdestinacije() {
        int br=0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) from destinacija");
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

package com.example.raf.projekatrafturistickivodic.repositories.clanak;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;
import com.example.raf.projekatrafturistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlClanakRepository extends MySqlAbstractRepository implements ClanakRepository {
    @Override
    public List<Clanak> findAllClanak(String filter, int limit, int offset) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        Statement preparedStatement = null;
        ResultSet rs = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            if(filter.equalsIgnoreCase("naj")){
                statement = connection.prepareStatement("SELECT * FROM clanak ORDER BY vreme DESC limit 10");
                resultSet = statement.executeQuery();

            }
               else if(filter.equalsIgnoreCase("najc")) {
                statement = connection.prepareStatement("SELECT * FROM clanak WHERE vreme >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ORDER BY br_poseta DESC LIMIT ? OFFSET ?");
                statement.setInt(1, limit);
                statement.setInt(2, offset);
                resultSet = statement.executeQuery();
            }
            else if (isNumber(filter)) {
                Integer destinacija = Integer.parseInt(filter);
                statement = connection.prepareStatement("SELECT * FROM clanak where destinacija = ? ORDER BY vreme DESC Limit ? OFFSET ?");
                statement.setInt(1, destinacija);
                statement.setInt(2, limit);
                statement.setInt(3, offset);
                resultSet = statement.executeQuery();
            } else {
                statement = connection.prepareStatement("SELECT * FROM clanak ORDER BY vreme DESC Limit ? OFFSET ?");
                statement.setInt(1, limit);
                statement.setInt(2, offset);
                resultSet = statement.executeQuery();
            }

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
        PreparedStatement umetni = null;

        try {
            connection = this.newConnection();
            connection.setAutoCommit(false); // Početak transakcije

            // Ažuriranje članka
            preparedStatement = connection.prepareStatement("UPDATE clanak SET naslov = ?, tekst = ?, destinacija = ? WHERE clanak_id = ?");
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setInt(3, clanak.getDestinacija());
            preparedStatement.setInt(4, clanak.getClanak_id());
            preparedStatement.executeUpdate();

            //brisanje medju tabele

//            deletemedju = connection.prepareStatement("DELETE FROM clanak_aktivnost WHERE clanak = ?");
//            deletemedju.setInt(1, clanak.getClanak_id());
//            deletemedju.executeUpdate();


            // dodavanje novih aktivnosti
            umetni = connection.prepareStatement("INSERT INTO clanak_aktivnost(clanak, aktivnost) VALUES(?,?)");
            for (Integer aktivnost : clanak.getAktivnosti()) {
                umetni.setInt(1, clanak.getClanak_id());
                umetni.setInt(2, aktivnost);
                umetni.addBatch();
            }
            umetni.executeBatch();

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
            this.closeStatement(umetni);
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
            connection.setAutoCommit(false);

            String[] generatedColumns = {"clanak_id"};
            preparedStatement = connection.prepareStatement("INSERT INTO clanak (naslov, tekst, vreme, br_poseta, autor, destinacija) VALUES(?,?,?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setString(3, clanak.getVreme());
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, clanak.getAutor());
            preparedStatement.setInt(6, clanak.getDestinacija());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                clanak.setClanak_id(resultSet.getInt(1));
            }

            preparedStatement1 = connection.prepareStatement("INSERT INTO clanak_aktivnost(clanak, aktivnost) VALUES(?,?)");
            for (Integer aktivnost : clanak.getAktivnosti()) {
                preparedStatement1.setInt(1, clanak.getClanak_id());
                preparedStatement1.setInt(2, aktivnost);
                preparedStatement1.addBatch();
            }
            preparedStatement1.executeBatch();

            connection.commit();
            clanak.setBr_poseta(0);

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Vraćanje transakcije u slučaju greške
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

        return clanak;
    }

    @Override
    public List<Clanak> findAllClanakByAktivnost(Integer id, int limit, int offset) {
        List<Clanak> clanci = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("SELECT c.* FROM clanak c JOIN clanak_aktivnost ca ON c.clanak_id = ca.clanak WHERE ca.aktivnost = ? ORDER BY c.vreme DESC limit ? OFFSET ?");
            statement.setInt(1,id);
            statement.setInt(2,limit);
            statement.setInt(3,offset);
            resultSet = statement.executeQuery();
            List<Integer> aktivnosti = new ArrayList<>();
            aktivnosti.add(id);
            while (resultSet.next()) {
                clanci.add(new Clanak(resultSet.getString("autor"),
                        resultSet.getInt("clanak_id"),
                        resultSet.getString("naslov"),
                        resultSet.getDate("vreme").toString(), resultSet.getString("tekst"),
                        resultSet.getInt("br_poseta"),
                        resultSet.getInt("destinacija"),
                        aktivnosti));
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

    @Override
    public Clanak findClanakById(Integer id) {
        Clanak clanak = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet2 = null;
        try {


            connection = this.newConnection();

            List<Integer> aktivnosti = new ArrayList<>();
            preparedStatement1 = connection.prepareStatement("select * from clanak_aktivnost WHERE clanak = ?");
            preparedStatement1.setInt(1, id);
            resultSet2 = preparedStatement1.executeQuery();
            while (resultSet2.next()) {
                aktivnosti.add(resultSet2.getInt("aktivnost"));
            }
            System.out.println(aktivnosti);
            preparedStatement = connection.prepareStatement("select * from clanak WHERE clanak_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String autor = resultSet.getString("autor");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                int brPoseta = resultSet.getInt("br_poseta");
                int destinacija = resultSet.getInt("destinacija");
                String vreme = resultSet.getDate("vreme").toString();

                System.out.println("Autor: " + autor);
                System.out.println("Naslov: " + naslov);
                System.out.println("Tekst: " + tekst);
                System.out.println("Broj poseta: " + brPoseta);
                System.out.println("Destinacija: " + destinacija);
                System.out.println("Vreme: " + vreme);
                clanak = new Clanak(autor, id, naslov, vreme, tekst, brPoseta, destinacija, aktivnosti);
                System.out.println("Kreiran clanak: " + clanak);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
            this.closeStatement(preparedStatement1);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSet2);
        }

        return  clanak;
    }

    @Override
    public int countClanakByFilter(String filter) {
        int br=0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            if(filter.equalsIgnoreCase("naj")){
                statement = connection.prepareStatement("SELECT count(*) FROM clanak ORDER BY vreme DESC");
                resultSet = statement.executeQuery();

            }
            else if(filter.equalsIgnoreCase("najc")) {
                statement = connection.prepareStatement("SELECT count(*) FROM clanak WHERE vreme >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ORDER BY br_poseta DESC");
                resultSet = statement.executeQuery();
            }
            else if (isNumber(filter)) {
                Integer destinacija = Integer.parseInt(filter);
                statement = connection.prepareStatement("SELECT count(*) FROM clanak where destinacija = ?");
                statement.setInt(1, destinacija);
                resultSet = statement.executeQuery();
            } else {
                statement = connection.prepareStatement("SELECT count(*) FROM clanak");
                resultSet = statement.executeQuery();
            }

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

    @Override
    public int countClanakByAktivnsot(Integer id) {
        int br=0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("SELECT count(*) FROM clanak_aktivnost where aktivnost = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();


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

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

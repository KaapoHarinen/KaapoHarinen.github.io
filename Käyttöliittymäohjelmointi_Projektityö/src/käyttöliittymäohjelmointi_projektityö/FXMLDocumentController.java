package käyttöliittymäohjelmointi_projektityö;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Kaapo Harinen
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtfldOpiskelijaId;
    @FXML
    private TextField txtfldEtunimi;
    @FXML
    private TextField txtfldSukunimi;
    @FXML
    private TextField txtfldLisääOpiskelijaId;
    @FXML
    private TextField txtfldLisääOpiskelijaEtunimi;
    @FXML
    private TextField txtfldLisääOpiskelijaSukunimi;
    @FXML
    private TextField txtfldKurssiId;
    @FXML
    private TextField txtfldKurssiNimi;
    @FXML
    private TextField txtfldKurssiLaajuus;
    @FXML
    private TextField txtfldLisääKurssiId;
    @FXML
    private TextField txtfldLisääKurssiNimi;
    @FXML
    private TextField txtfldLisääKurssiLaajuus;
    @FXML
    private TextField txtfldLisääSuoritusOpiskelijaId;
    @FXML
    private TextField txtfldLisääSuoritusKurssiId;
    @FXML
    private TextField txtfldLisääSuoritusPvm;
    @FXML
    private TextField txtfldLisääSuoritusArvosana;
    @FXML
    private TextField txtfldLisääSuoritusId;
    @FXML
    private TextArea txtareaOpiskelijanSuoritukset;
    @FXML
    private TextArea txtareaKurssinSuoritukset;

    /**
     *
     * @param connString
     * @return Connection con
     * @throws SQLException
     */
    private static Connection openConnection(String connString) throws SQLException {
        Connection con = DriverManager.getConnection(connString);

        return con;
    }

    /**
     *
     * @param c Connection
     * @throws SQLException
     */
    private static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokanta suljettu");
    }

    /**
     *
     * @param c Connection
     * @param db Database
     * @throws SQLException
     */
    private static void createDatabase(Connection c, String db) throws SQLException {

        Statement stmt = c.createStatement();

        stmt.executeQuery("DROP DATABASE IF EXISTS " + db);

        stmt.executeQuery("CREATE DATABASE " + db);

        stmt.executeQuery("USE " + db);

    }

    /**
     *
     * @param c Connection
     * @param sql query
     * @throws SQLException
     */
    private static void createTable(Connection c, String sql) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery(sql);
        System.out.println("\t>> Taulu luotu");

    }

    /**
     *
     * @param c Connection
     * @param id opiskelija.id
     * @param etunimi opiskelija.etunimi
     * @param sukunimi opiskelija.sukunimi
     * @throws SQLException
     */
    private void addName(Connection c, int id, String etunimi, String sukunimi) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO opiskelija (id, etunimi, sukunimi) "
                + "VALUES(?, ?, ?)"
        );

        ps.setObject(1, id);
        ps.setObject(2, etunimi);
        ps.setObject(3, sukunimi);
        ps.execute();

        System.out.println("Opiskelija lisätty");

        selectOpiskelijat(c);
    }

    /**
     *
     * @param c Connection
     * @param id kurssi.id
     * @param nimi kurssi.nimi
     * @param laajuus kurssi.laajuus
     * @throws SQLException
     */
    private void addKurssi(Connection c, int id, String nimi, int laajuus) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO kurssi (id, nimi, laajuus) "
                + "VALUES(?, ?, ?)"
        );

        ps.setObject(1, id);
        ps.setObject(2, nimi);
        ps.setObject(3, laajuus);
        ps.execute();

        System.out.println("Kurssi lisätty");

        selectKurssit(c);
    }

    /**
     *
     * @param c Connection
     * @param suoritusId suoritus.suoritus_id
     * @param opiskelijaId suoritus.opiskelija_id
     * @param kurssiId suoritus.kurssi_id
     * @param suoritusPvm suoritus.suoritus_pvm
     * @param arvosana suoritus.arvosana
     * @throws SQLException
     */
    private void addSuoritus(Connection c, int suoritusId, int opiskelijaId, int kurssiId, Date suoritusPvm, int arvosana) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO suoritus (suoritus_id, opiskelija_id, kurssi_id, suoritus_pvm, arvosana) "
                + "VALUES(?, ?, ?, ?, ?)"
        );

        ps.setObject(1, suoritusId);
        ps.setObject(2, opiskelijaId);
        ps.setObject(3, kurssiId);
        ps.setObject(4, suoritusPvm);
        ps.setObject(5, arvosana);
        ps.execute();

        System.out.println("Suoritus lisätty");

        selectSuoritukset(c);
    }

    /**
     *
     * @param c Connection
     * @param hakuId opiskelija.id
     * @param etunimi opiskelija.etunimi
     * @param sukunimi opiskelija.sukunimi
     * @throws SQLException
     */
    private void MuokkaaOpiskelijanTietoja(Connection c, int hakuId, String etunimi, String sukunimi) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "UPDATE opiskelija "
                + "SET etunimi = ?, sukunimi = ?"
                + " WHERE id = ?"
        );

        ps.setObject(1, etunimi);
        ps.setObject(2, sukunimi);
        ps.setObject(3, hakuId);
        ps.execute();

        System.out.println("Opiskelijan tiedot muutettu");

    }

    /**
     *
     * @param c Connection
     * @param hakuId kurssi.id
     * @param nimi kurssi.nimi
     * @param laajuus kurssi.laajuus
     * @throws SQLException
     */
    private void MuokkaaKurssinTietoja(Connection c, int hakuId, String nimi, int laajuus) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "UPDATE kurssi "
                + "SET nimi = ?, laajuus = ?"
                + " WHERE id = ?"
        );

        ps.setObject(1, nimi);
        ps.setObject(2, laajuus);
        ps.setObject(3, hakuId);
        ps.execute();

        System.out.println("Kurssin tiedot muutettu");

    }

    /**
     *
     * @param c Connection
     * @param hakuId opiskelija.id
     * @throws SQLException
     */
    private void PoistaOpiskelija(Connection c, int hakuId) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM opiskelija "
                + " WHERE id = ?"
        );

        ps.setObject(1, hakuId);
        ps.execute();

        this.txtfldOpiskelijaId.clear();
        this.txtfldEtunimi.clear();
        this.txtfldSukunimi.clear();

        System.out.println("Opiskelijan tiedot poitettu");
    }

    /**
     *
     * @param c Connection
     * @param hakuId kurssi.id
     * @throws SQLException
     */
    private void PoistaKurssi(Connection c, int hakuId) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM kurssi "
                + " WHERE id = ?"
        );

        ps.setObject(1, hakuId);
        ps.execute();

        this.txtfldKurssiId.clear();
        this.txtfldKurssiNimi.clear();
        this.txtfldKurssiLaajuus.clear();

        System.out.println("Kurssin tiedot poitettu");
    }

    /**
     *
     * @param c Connection
     * @throws SQLException
     */
    public static void selectOpiskelijat(Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery(
                "SELECT id, etunimi, sukunimi FROM opiskelija"
        );

        while (rs.next()) {
            System.out.println(
                    "[" + rs.getInt("id") + "] "
                    + rs.getString("etunimi") + " | "
                    + rs.getString("sukunimi")
            );

        }
    }

    /**
     *
     * @param c Connection
     * @throws SQLException
     */
    public static void selectKurssit(Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery(
                "SELECT id, nimi, laajuus FROM kurssi"
        );

        while (rs.next()) {
            System.out.println(
                    "[" + rs.getInt("id") + "] "
                    + rs.getString("nimi") + " | "
                    + rs.getInt("laajuus")
            );

        }
    }

    /**
     *
     * @param c Connection
     * @throws SQLException
     */
    public static void selectSuoritukset(Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery(
                "SELECT suoritus_id, opiskelija_id, kurssi_id, suoritus_pvm, arvosana FROM suoritus"
        );

        while (rs.next()) {
            System.out.println(
                    "[" + rs.getInt("suoritus_id") + "] "
                    + rs.getInt("opiskelija_id") + " "
                    + rs.getInt("kurssi_id") + " "
                    + rs.getString("suoritus_pvm") + " "
                    + rs.getInt("arvosana")
            );

        }
    }

    /**
     *
     * @param c Connection
     * @param db Database
     * @throws SQLException
     */
    private void useDatabase(Connection c, String db) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeQuery("USE " + db);
        System.out.println("Käytetään tietokantaa " + db);
    }

    /**
     *
     * @param c Connection
     * @param hakuId opiskelija.id
     * @throws SQLException
     */
    private void haeOpiskelija(Connection c, int hakuId) throws SQLException {
        this.txtfldEtunimi.clear();
        this.txtfldSukunimi.clear();

        String query = "SELECT id, etunimi, sukunimi FROM opiskelija "
                + "WHERE id = " + hakuId;

        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");

                this.txtfldEtunimi.setText(etunimi);
                this.txtfldSukunimi.setText(sukunimi);
            }
        } catch (SQLException e) {
            System.out.println("Errori");;
        }
    }

    /**
     *
     * @param c Connection
     * @param hakuId kurssi.id
     * @throws SQLException
     */
    private void haeKurssi(Connection c, int hakuId) throws SQLException {
        this.txtfldKurssiNimi.clear();
        this.txtfldKurssiLaajuus.clear();

        String query = "SELECT id, nimi, laajuus FROM kurssi "
                + "WHERE id = " + hakuId;

        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String nimi = rs.getString("nimi");
                int laajuus = rs.getInt("laajuus");

                this.txtfldKurssiNimi.setText(nimi);

                String laajuusToString = Integer.toString(laajuus);

                this.txtfldKurssiLaajuus.setText(laajuusToString);
            }
        } catch (SQLException e) {
            System.out.println("Errori");;
        }
    }

    /**
     *
     * @param c Connection
     * @param hakuId suoritus.suoritus_id
     * @throws SQLException
     */
    private void haeOpiskelijanSuoritukset(Connection c, int hakuId) throws SQLException {
        this.txtareaOpiskelijanSuoritukset.clear();

        String query = "SELECT suoritus_id, opiskelija_id, kurssi_id, suoritus_pvm, arvosana FROM suoritus "
                + "WHERE opiskelija_id = " + hakuId;

        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                String suoritukset = "\t" + rs.getString(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3) + "\t|\t" + rs.getString(4) + "\t|\t" + rs.getString(5) + "\n";

                this.txtareaOpiskelijanSuoritukset.appendText(suoritukset);

            }
        } catch (SQLException e) {
            System.out.println("Errori");;
        }
    }

    /**
     *
     * @param c Connection
     * @param hakuId suoritus.suoritus_id
     * @throws SQLException
     */
    private void haeKurssinSuoritukset(Connection c, int hakuId) throws SQLException {
        this.txtareaKurssinSuoritukset.clear();

        String query = "SELECT suoritus_id, opiskelija_id, kurssi_id, suoritus_pvm, arvosana FROM suoritus "
                + "WHERE kurssi_id = " + hakuId;

        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                String suoritukset = "\t" + rs.getString(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3) + "\t|\t" + rs.getString(4) + "\t|\t" + rs.getString(5) + "\n";

                this.txtareaKurssinSuoritukset.appendText(suoritukset);

            }
        } catch (SQLException e) {
            System.out.println("Errori");;
        }
    }

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = openConnection(
                    "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

            createDatabase(conn, "karelia_2007206");

            createTable(conn,
                    "CREATE TABLE opiskelija ("
                    + "id INT NOT NULL PRIMARY KEY,"
                    + "etunimi VARCHAR(50),"
                    + "sukunimi VARCHAR(50))"
            );

            createTable(conn,
                    "CREATE TABLE kurssi ("
                    + "id INT NOT NULL PRIMARY KEY,"
                    + "nimi VARCHAR(50),"
                    + "laajuus INT)"
            );

            createTable(conn,
                    "CREATE TABLE suoritus ("
                    + "suoritus_id INT NOT NULL PRIMARY KEY,"
                    + "opiskelija_id INT NOT NULL,"
                    + "kurssi_id INT NOT NULL,"
                    + "suoritus_pvm DATE,"
                    + "arvosana INT)"
            );
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnLisääOpiskelijaOnAction(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        Integer id = Integer.parseInt(txtfldLisääOpiskelijaId.getText());
        String etunimi = this.txtfldLisääOpiskelijaEtunimi.getText();
        String sukunimi = this.txtfldLisääOpiskelijaSukunimi.getText();

        useDatabase(conn, "karelia_2007206");
        addName(conn, id, etunimi, sukunimi);

        this.txtfldLisääOpiskelijaId.clear();
        this.txtfldLisääOpiskelijaEtunimi.clear();
        this.txtfldLisääOpiskelijaSukunimi.clear();
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void mItmExit(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");
        closeConnection(conn);
        Platform.exit();
        System.exit(0);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnHaeOpiskelijaOnAction(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(this.txtfldOpiskelijaId.getText());

        haeOpiskelija(conn, hakuId);
        haeOpiskelijanSuoritukset(conn, hakuId);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnTallennaOpiskelijanMuutokset(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(this.txtfldOpiskelijaId.getText());
        String etunimi = this.txtfldEtunimi.getText();
        String sukunimi = this.txtfldSukunimi.getText();

        MuokkaaOpiskelijanTietoja(conn, hakuId, etunimi, sukunimi);

        selectOpiskelijat(conn);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnPoistaOpiskelija(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(this.txtfldOpiskelijaId.getText());

        PoistaOpiskelija(conn, hakuId);

        selectOpiskelijat(conn);

    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnHaeKurssiOnAction(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(this.txtfldKurssiId.getText());

        haeKurssi(conn, hakuId);
        haeKurssinSuoritukset(conn, hakuId);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnTallennaKurssinMuutokset(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(txtfldKurssiId.getText());
        String nimi = this.txtfldKurssiNimi.getText();
        Integer laajuus = Integer.parseInt(txtfldKurssiLaajuus.getText());

        MuokkaaKurssinTietoja(conn, hakuId, nimi, laajuus);

        selectKurssit(conn);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnPoistaKurssi(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        useDatabase(conn, "karelia_2007206");

        Integer hakuId = Integer.parseInt(this.txtfldKurssiId.getText());

        PoistaKurssi(conn, hakuId);

        selectKurssit(conn);
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnLisääKurssiOnAction(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        Integer id = Integer.parseInt(txtfldLisääKurssiId.getText());
        String nimi = this.txtfldLisääKurssiNimi.getText();
        Integer laajuus = Integer.parseInt(txtfldLisääKurssiLaajuus.getText());

        useDatabase(conn, "karelia_2007206");
        addKurssi(conn, id, nimi, laajuus);

        this.txtfldLisääKurssiId.clear();
        this.txtfldLisääKurssiNimi.clear();
        this.txtfldLisääKurssiLaajuus.clear();
    }

    /**
     *
     * @param event ActionEvent
     * @throws SQLException
     */
    @FXML
    private void btnLisääSuoritusOnAction(ActionEvent event) throws SQLException {
        Connection conn = openConnection(
                "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1");

        Integer suoritusId = Integer.parseInt(txtfldLisääSuoritusId.getText());
        Integer opiskelijaId = Integer.parseInt(txtfldLisääSuoritusOpiskelijaId.getText());
        Integer kurssiId = Integer.parseInt(txtfldLisääSuoritusKurssiId.getText());
        Date suoritusPvm = Date.valueOf(txtfldLisääSuoritusPvm.getText());
        Integer arvosana = Integer.parseInt(txtfldLisääSuoritusArvosana.getText());

        useDatabase(conn, "karelia_2007206");
        addSuoritus(conn, suoritusId, opiskelijaId, kurssiId, suoritusPvm, arvosana);

        this.txtfldLisääSuoritusId.clear();
        this.txtfldLisääSuoritusOpiskelijaId.clear();
        this.txtfldLisääSuoritusKurssiId.clear();
        this.txtfldLisääSuoritusPvm.clear();
        this.txtfldLisääSuoritusArvosana.clear();
    }

}

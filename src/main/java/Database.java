import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    Connection con;

    public Database() throws SQLException {
        emptyTable();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/scrapping", "root", "root");
    }

    public Database(String Username, String Password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/scrapping", Username, Password);
    }

    public boolean addRecordToDatabase(WebRecords records) throws SQLException {
        Statement stmt = con.createStatement();
        int rs = stmt.executeUpdate("INSERT INTO `records`(`symbol`, `ISIN`, `company`, `description`, `date`, `doc_type`, `link_1`, `link_2`) VALUES (\"" + records.getSymbol() + "\",\"" + records.getISIN() + "\",\"" + records.getCompany() + "\",\"" + records.getDescription() + "\",\"" + records.getDate() + "\",\"" + records.getDocType() + "\",\"" + records.getLinkToFile() + "\",\"" + records.getLinkToFile2() + "\")");
        return rs > 0 ? true : false;

    }

    public boolean emptyTable() throws SQLException {
        Statement stmt = con.createStatement();
        int rs = stmt.executeUpdate("TRUNCATE `scrapping`.`records`");
        return rs > 0 ? true : false;
    }


}

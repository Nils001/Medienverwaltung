import java.sql.*;

public class DBV
{
    private Connection conn;
    private final String treiber = "com.mysql.jdbc.Driver";
    private String dBase = "jdbc:mysql://localhost/spielerdatenbank";
    private String benutzer;
    private String passwort;
    private Statement stmt;

    public DBV(String user, String pass)throws Exception
    {
        benutzer = user;
        passwort = pass;
        try {
            connect();
            close();
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public void connect() throws Exception
    {
        try 
        {
            //Laedt den Datenbanktreiber
            Class.forName(treiber);
            //Stellt die Verbindung her
            conn = DriverManager.getConnection("jdbc:mysql://84.200.76.8:3306/mv", "nils", "nils");
            //conn = DriverManager.getConnection("jdbc:mysql://84.200.76.8:3306/mv", benutzer, passwort);
            //Erzeugt ein Objekt fuer Abfragen und Aenderungen der Datenbank
            stmt = conn.createStatement();
        }
        catch (ClassNotFoundException cnfe) 
        {
            throw cnfe;
        }
        catch (SQLException sqle) 
        {
            throw sqle;
        }
    }

    public void close() throws Exception
    {
        try 
        {
            stmt.close();
            conn.close();
        }
        catch (SQLException sqle) 
        {
            throw sqle;
        }
    }

    public ResultSet verbindung(String select) throws Exception
    {
        try 
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(select);
            return rs;
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }
    }

    public void ausgabe()
    {
        try
        {
            ResultSet rs = alleNutzer();
            while ( rs.next() )
            {
                System.out.println(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public ResultSet alleNutzer()
    { 
        try
        {
            this.connect();
            ResultSet rs = verbindung("SELECT Name FROM user;");
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}

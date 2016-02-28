import java.sql.*;
public class DBV{
    private Connection conn;
    private final String treiber = "com.mysql.jdbc.Driver";
    private String dBase = "jdbc:mysql://localhost/spielerdatenbank";
    private String benutzer;
    private String passwort;
    private Statement stmt;

    /*public static void main(String[]args)throws Exception{
    conn = null;
    try{
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost/spielerdatenbank","root","");

    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT * FROM spielertabelle");

    System.out.println("id");

    while(rs.next()){
    System.out.println(rs.getString("id"));
    }

    }

    catch(Exception e ){
    e.printStackTrace();
    }
    finally {
    if(conn!= null){
    conn.close();
    }*/

    public DBV(){
    }

    public void connect(){
        try {
            //Laedt den Datenbanktreiber
            Class.forName(treiber);
            //Stellt die Verbindung her
            conn = DriverManager.getConnection("jdbc:mysql://localhost/spielerdatenbank","root","");
            //Erzeugt ein Objekt fuer Abfragen und Aenderungen der Datenbank
            stmt = conn.createStatement();
        }
        catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
        }
        catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }
    }

    public void close(){
        try {
            stmt.close();
            conn.close();
        }
        catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }
    }

    public boolean sucheSpieler(String spielerName) throws Exception{
        boolean istDa = false;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT name FROM spielertabelle");
        while(rs.next()){
            String pName = rs.getString("name");
            if(spielerName.equals(pName)){
                istDa = true;
            }
            //System.out.println(rs.getString("name"));
        }
        return istDa;
    }
    
    public Spieler gibSpieler(String spielerName) throws Exception{
        Spieler pSpieler = new Spieler(null,null);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM spielertabelle");
        while(rs.next()){
            String pName = rs.getString("name");
            if(spielerName.equals(pName)){
                String pPasswort = rs.getString("passwort");
                Spieler abcSpieler = new Spieler(pName,pPasswort);
                int pScore = rs.getInt("score");
                abcSpieler.setzeScore(pScore);
                int pZustand = rs.getInt("zustand");
                abcSpieler.setzeZustand(pZustand);
                pSpieler = abcSpieler;
            }
            //System.out.println(rs.getString("name"));
        }
        return pSpieler;
    }

    public void hinzufügen(Spieler pSpieler)throws Exception{
        Statement st = conn.createStatement();
        String pName = pSpieler.gibName();
        String pPasswort = pSpieler.gibPasswort();
        int pZustand = pSpieler.gibZustand();
        int pScore = pSpieler.gibScore();
        String upD = "INSERT INTO spielertabelle (name ,passwort, zustand,score)" +
                                 "VALUES ('"+pName+"', '"+pPasswort+"', "+pZustand+", "+pScore+") ";
        st.executeUpdate(upD);
    }

    public void schreiben() throws Exception{
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM spielertabelle");
        while(rs.next()){
            System.out.println(rs.getString("id"));
            System.out.println(rs.getString("name"));
        }

    }
}

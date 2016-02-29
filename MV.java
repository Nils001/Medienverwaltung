import java.sql.ResultSet;

public class MV
{
    private String passwort;
    private String name;
    private DBV dbv;

    public MV(String benutzer, String pass)
    {
        name = benutzer;
        passwort = pass;
        dbv = new DBV(name, passwort);
    }

    public ResultSet getFreiMedien(String zeit)
    {
        try
        {
            dbv.connect();
            ResultSet rs = verbindung("SELECT Name FROM user;");
            dbv.close();
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public ResultSet getFreieRäume(String zeit)
    {    
        try
        {
            dbv.connect();
            ResultSet rs = verbindung("SELECT Name FROM user;");
            dbv.close();
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public ResultSet getRaum()
    {
        try
        {
            dbv.connect();
            ResultSet rs = verbindung("SELECT * FROM medien WHERE Typ LIKE %Raum%;"); //falsch
            dbv.close();
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public ResultSet getMedien()
    {
        try
        {
            dbv.connect();
            ResultSet rs = verbindung("SELECT Name FROM user;");
            dbv.close();
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void setRaum(String zeit, int RaumID)
    {
    }

    public void removeRaum(String zeit, int RaumID)
    {
    }

    public void setMedium(String zeit, int mediumID)
    {
    }

    public void removeMedium(String zeit, int mediumID)
    {
    }

    public void login()
    {
    }

    public void logout()
    {
    }

    public void createRaum()
    {
    }

    public void createMedium()
    {
    }

    public void removeRaum()
    {
    }

    public void removeMedium()
    {
    }

    /*public String abfrage(String querry)
    {
    return "";
    }

    public void setData(String wayne)
    {
    }*/
}

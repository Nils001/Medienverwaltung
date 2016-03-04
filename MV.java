import java.sql.ResultSet;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MV
{
    private String passwort;
    private String name;
    private DBV dbv;

    public MV(String benutzer, String pass)
    {
        name = "root";//name = benutzer;
        passwort = "nils1000";//passwort = pass;
        dbv = new DBV(name, passwort);
    }

    public ResultSet getBelegung(String medienID, String datum) throws ParseException
    {
        String date1 = datum1(datum); //Wochenstart
        String date2 = datum2(datum); //Wochenende
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT * FROM verwaltung WHERE MedienID = "+medienID+" AND Datum BETWEEN '"+date2+"' AND '"+date1+"';");  //funktioniert
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
            ResultSet rs = dbv.verbindung("SELECT * FROM medien WHERE Typ LIKE '%Raum';"); //falsch
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
            ResultSet rs = dbv.verbindung("SELECT * FROM medien WHERE Typ NOT LIKE '%Raum';"); //funktioniert
            dbv.close();
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void setRaum(String datum, int stunde, int RaumID)
    {
    }

    public void unsetRaum(String datum, int stunde, int RaumID)
    {
    }

    public void setMedium(String datum, int stunde, int mediumID)
    {
    }

    public void unsetMedium(String datum, int stunde, int mediumID)
    {
    }

    public void login()
    {
    }

    public void logout()
    {
    }

    public void createRaum(String name, String typ)
    {
    }

    public void createMedium(String name, String typ)
    {
    }

    public void removeRaum(String name)
    {
    }

    public void removeMedium(String name)
    {
    }

    private String datum1(String date) throws ParseException
    {
        int tag = wochentag(date);
        if (tag == 0)
        {
            return date;
        }
        else
        {
            int tagh = tag * -1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, tagh);
            date = sdf.format(c.getTime()); 
            return date;
        }
    }

    private String datum2(String date) throws ParseException
    {
        int tag = wochentag(date);
        if (tag == 4)
        {
            return date;
        }
        else if (tag < 5)
        {
            int tagh = 4 - tag;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, tagh);
            date = sdf.format(c.getTime()); 
            return date;
        }
        else if (tag == 5)
        {
            int tagh = -1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, tagh);
            date = sdf.format(c.getTime()); 
            return date;
        }
        else
        {
            int tagh = -2;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, tagh);
            date = sdf.format(c.getTime()); 
            return date;
        }
    }

    private int wochentag(String date_s) throws ParseException
    {        
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dt.parse(date_s); //macht aus String ein Datum

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //gibt den Wochentag an

        return dayOfWeek-2; //fängt mit Montag = 2 an, soll aber bei Montag = 0 anfangen
    }
}

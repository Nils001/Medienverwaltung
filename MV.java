import java.sql.ResultSet;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MV
{
    private String passwort;
    private String name;
    private DBV dbv;

    public MV(/*String benutzer, String pass*/)
    {
        name = "";//name = benutzer;
        passwort = "";//passwort = pass;
        dbv = new DBV(name, passwort);
    }

    private String[][] rsToArray (ResultSet rs) throws ParseException
    {
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            List rows = new ArrayList();
            while(rs.next())
            {
                String[] row = new String[columnCount];
                for(int i = 1;i<=columnCount;i++)
                {
                    row[i-1]=rs.getString(i);
                }
                rows.add(row);
            }

            String[][] rowData = (String[][])rows.toArray(new String[rows.size()][columnCount]);
            return rowData;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private String[][] belegungArray (ResultSet rs, String date) throws ParseException
    {
        String[][] zeitplan = new String[5][11];
        zeitplan[1][1] = date;
        return null;
    }

    public String[][] getBelegung(String medienID, String datum) throws ParseException
    {
        String date1 = datum1(datum); //Wochenstart
        System.out.println(date1);
        String date2 = datum2(datum); //Wochenende
        System.out.println(date2);
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT * FROM verwaltung WHERE MedienID = "+medienID+" AND Datum BETWEEN '"+date1+"' AND '"+date2+"'");  //funktioniert
            String[][] a = rsToArray(rs);
            a = belegungArray(rs, date1);
            dbv.close();
            return a;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public String[][] getRaum()
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT * FROM medien WHERE Typ LIKE '%Raum'"); //falsch
            String[][] a = rsToArray(rs);
            dbv.close();
            return a;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public String[][] getMedien()
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT * FROM medien WHERE Typ NOT LIKE '%Raum'"); //funktioniert
            String[][] a = rsToArray(rs);
            dbv.close();
            return a;
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

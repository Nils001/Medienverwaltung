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
    private int status;

    public MV(/*String benutzer, String pass*/) throws Exception
    {
        name = "";//name = benutzer;
        passwort = "";//passwort = pass;
        try{
            dbv = new DBV(name, passwort);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Object[][] getBelegung(String name, String datum) throws ParseException
    {
        if (status == 10 || status == 11)
        {
            String medienID = mediumToID(name);
            String date1 = datum1(datum); //Wochenstart
            String date2 = datum2(datum); //Wochenende
            try
            {
                dbv.connect();
                ResultSet rs = dbv.verbindung("SELECT * FROM verwaltung WHERE MedienID = "+medienID+" AND Datum BETWEEN '"+date1+"' AND '"+date2+"'");  //funktioniert
                String[][] a = rsToArray(rs);
                Object[][] b = belegungArray(a, date1);
                dbv.close();
                return b;
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        return null;
    }

    public String[][] getRaum() throws Exception
    {
        if (status == 10 || status == 11)
        {
            try
            {
                dbv.connect();
                ResultSet rs = dbv.verbindung("SELECT Name FROM medien WHERE Typ LIKE '%Raum'"); //funktioniert
                String[][] a = rsToArray(rs);
                dbv.close();
                return a;
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return null;
    }

    public String[][] getMedien() throws Exception
    {
        if (status == 10 || status == 11)
        {
            try
            {
                dbv.connect();
                ResultSet rs = dbv.verbindung("SELECT Name FROM medien WHERE Typ NOT LIKE '%Raum'"); //funktioniert
                String[][] a = rsToArray(rs);
                dbv.close();
                return a;
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return null;
    }

    public void set(String nameU, String nameM, String datum, String stunde)
    {
        if (status == 10 || status == 11)
        {
            try
            {
                String medienID = mediumToID(nameM);
                String userID = nameToID(nameU);
                dbv.connect();
                dbv.verbindung2("INSERT INTO verwaltung VALUES (NULL, '"+userID+"', '"+medienID+"', '"+datum+"', '"+stunde+"')");
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void unset(String userID, String name, String datum, String stunde)
    {
        if (status == 10 || status == 11)
        {
            try
            {
                String medienID = mediumToID(name);
                dbv.connect();
                dbv.verbindung2("DELETE FROM verwaltung WHERE UserID = '"+userID+"' AND MedienID = '"+medienID+"' AND Datum = '"+datum+"' Stunde = '"+stunde+"'");
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public int login(String name, String passwort) throws Exception
    {
        //name = "fritsch";
        //passwort = "hallo";
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT * FROM user WHERE Name = '"+name+"'");
            String[][] a = rsToArray(rs);
            dbv.close();
            if (a.length == 0)
            {
                status = 0;
                return 0;
            }
            else
            {
                if (a[0][2].equals(passwort))
                {
                    if (a[0][3].equals("1"))
                    {
                        status = 11;
                        return 11;
                    }
                    else
                    {
                        status = 10;
                        return 10;
                    }
                }
                else
                {
                    status = 0;
                    return 0;
                }
            }
        }
        catch (Exception e)
        {

        }
        return 0;
    }

    public void logout()
    {
        status = 00;
    }

    public void createMedium(String name, String typ)
    {
        if (status == 11)
        {
            try
            {
                dbv.connect();
                dbv.verbindung2("INSERT INTO medien VALUES (NULL, '"+name+"', '"+typ+"')");
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void removeMedium(String name)
    {
        if (status == 11)
        {
            try
            {
                dbv.connect();
                dbv.verbindung2("DELETE FROM medien WHERE Name = '"+name+"'");
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void createUser(String name, String passwort, String admin)
    {
        if (status == 11)
        {
            try
            {
                dbv.connect();
                dbv.verbindung2("INSERT INTO user VALUES (NULL, '"+name+"', '"+passwort+"', '"+admin+"')"); //('ID', 'Name', 'Passwort', 'Admin')
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void removeUser(String name)
    {
        if (status == 11)
        {
            try
            {
                dbv.connect();
                dbv.verbindung2("DELETE FROM user WHERE Name = '"+name+"'");
                dbv.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
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

    private String[][] rsToArray (ResultSet rs) throws ParseException //macht aus ResultSet ein zweidimesionales String Array
    {
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            List<String[]> rows = new ArrayList<String[]>();
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

    private Object[][] belegungArray (String[][] a, String date) throws ParseException
    {
        Object[][] zeitplan = new Object[5][11];

        /*for (int e = 0; e < 5; e++)
        {
        for (int f = 1; f < 11; f++)
        {
        zeitplan[e][f] = false;
        }
        }*/

        zeitplan[0][0] = date;
        for (int i = 1; i < 5; i++)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, 1);
            date = sdf.format(c.getTime()); 
            zeitplan[i][0] = date;
        }

        for (int b = 0; b < a.length; b++)
        {
            int d = 0;
            while (!zeitplan[d][0].equals(a[b][3]) && d < 5)
            {
                d++;
            }
            int stun = Integer.parseInt(a[b][4]);
            String name = idToName(a[b][1]);
            zeitplan[d][stun] = name;
        }

        return zeitplan;
    }

    private String idToName(String id) throws ParseException
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT Name FROM user WHERE ID = '"+id+"'");  //funktioniert
            String[][] a = rsToArray(rs);
            dbv.close();
            return a[0][0];
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private String nameToID(String name) throws ParseException
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT ID FROM user WHERE Name = '"+name+"'");  //funktioniert
            String[][] a = rsToArray(rs);
            dbv.close();
            return a[0][0];
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private String idToMedium(String id) throws ParseException
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT Name FROM medien WHERE ID = '"+id+"'");  //funktioniert
            String[][] a = rsToArray(rs);
            dbv.close();
            return a[0][0];
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    private String mediumToID(String name) throws ParseException
    {
        try
        {
            dbv.connect();
            ResultSet rs = dbv.verbindung("SELECT ID FROM medien WHERE Name = '"+name+"'");  //funktioniert
            String[][] a = rsToArray(rs);
            dbv.close();
            return a[0][0];
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}

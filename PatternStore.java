import java.io.*;
import java.net.*;
import java.util.*;


public class PatternStore {

    private ArrayList<Pattern> patterns = new ArrayList<>();

    // public static final int MAX_NUMBER_PATTERNS = 1000;
    // private Pattern[] patterns = new Pattern[MAX_NUMBER_PATTERNS];
    // private int numberUsed = 0;

    public PatternStore(String source) throws Exception {
    if (source.startsWith("http://") || source.startsWith("https://")) {
        loadFromURL(source);
    }
    else {
        loadFromDisk(source);
    }

    }
    public PatternStore(Reader source) throws Exception {
        load(source);
    }

    private void load(Reader r) throws Exception {
        BufferedReader b = new BufferedReader(r);
        String line;
        while ( (line = b.readLine()) != null) {
            try{
                Pattern pat = new Pattern(line);
                // patterns[numberUsed] = pat;
                // numberUsed++;
                patterns.add(pat);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        b.close();
    }

    private void loadFromURL(String url) throws Exception {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }

    private void loadFromDisk(String filename) throws Exception {
        Reader r = new FileReader(filename);
        load(r);
    }
    
    public ArrayList<Pattern> getPatternsNameSorted() {
        Collections.sort(patterns);
        ArrayList<Pattern> copy = new ArrayList<Pattern>(patterns);
        return copy;
    }

}
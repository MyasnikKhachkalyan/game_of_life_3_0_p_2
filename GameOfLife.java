import java.io.*;
import java.net.*;
import java.util.*;


public class GameOfLife{
    private World world;
    private PatternStore store;
    private ArrayList<World> cachedWorlds = new ArrayList<>();

    public GameOfLife(PatternStore s){
        store = s;
    } 
    public void print(){
        System.out.println("- " + world.getGenerationCount());
        for(int i=0; i<world.getPattern().getHeight(); i++){
            for(int j=0; j<world.getPattern().getWidth(); j++){
                if(world.getCell(j, i)){
                    System.out.print("#");
                }
                else{
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    private World copyWorld(boolean useCloning) throws Exception {
        if (useCloning) {
            return (World) this.world.clone();
        }
        else{
            return null;
        }
    }

    public void play() throws Exception {
        String response = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please select a pattern to play (l to list):");
        while (!response.equals("q")) {
            response = in.readLine();
            System.out.println(response);
            if (response.equals("f")) {
                if (world == null) {
                        System.out.println("Please select a pattern to play (l to list p index to play):");
                }
                else {
                    World tmpWorld=null;
                    for (World w:cachedWorlds) {
                        if(w.getGenerationCount()==world.getGenerationCount()+1){
                            tmpWorld=w;
                            break;
                        }
                    }
                    if(tmpWorld==null){
                        World copiedWorld = copyWorld(true);

                        cachedWorlds.add(copiedWorld);
                        world.nextGeneration();
                    }
                    else{
                        world = tmpWorld;
                    }
                    print();
                }
            }
            else if (response.equals("b")) {
                if(world.getGenerationCount()>0){
                    for (World w:cachedWorlds) {
                        if(w.getGenerationCount()==world.getGenerationCount()-1){
                            world=w;
                            break;
                        }
                    }
                }
                print();
            }    
            else if (response.equals("l")) {
                ArrayList<Pattern> names = store.getPatternsNameSorted();
                int i = 0;
                for (Pattern p : names) {
                    System.out.println(i + " " + p.getName() + " (" + p.getAuthor() + ")");
                    i++;
                }
            }
            else if (response.startsWith("p")) {
                ArrayList<Pattern> names = store.getPatternsNameSorted();
                int index = Integer.parseInt(response.split(" ")[1]);
                Pattern pat = names.get(index);
                if(pat.getHeight()<= 8 && pat.getWidth()<=8){
                    world = new PackedWorld(pat);
                }
                else{
                    world = new ArrayWorld(pat);
                }
                cachedWorlds.clear();
                print();
            }
        }
    }
    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java GameOfLife <path/url to store>");
            return;
        }
        try {
            PatternStore ps = new PatternStore(args[0]);
            GameOfLife gol = new GameOfLife(ps);
            gol.play();
        }
        catch (IOException ioe) {
            System.out.println("Failed to load pattern store");
        }
    }
}

public class Pattern implements Comparable<Pattern>{

    private String name;
    private String author;
    private int width;
    private int height;
    private int startUpperCol;
    private int startUpperRow;
    private String cells;

    public Pattern(String initialiser) throws Exception{
        String[] arr = initialiser.split(":");
        if(arr.length!=7){
            throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern(found " + arr.length +").");
        }
        
        name = arr[0];
        author = arr[1];
        try{
            width = Integer.parseInt(arr[2]);
        }
        catch(Exception e){
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number (’" + arr[2] +"’ given).");
        }
        try{
            height = Integer.parseInt(arr[3]);
        }
        catch(Exception e){
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number (’" + arr[3] +"’ given).");
        }
        try{
            startUpperCol = Integer.parseInt(arr[4]);
        }
        catch(Exception e){
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number (’" + arr[4]+"’ given).");
        }
        try{
            startUpperRow = Integer.parseInt(arr[5]);
        }
        catch(Exception e){
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as a number (’" + arr[5]+"’ given).");
        }
        
        for(int i = 0; i<arr[6].length(); i++){
            if(arr[6].charAt(i)!= '0' && arr[6].charAt(i)!= '1' && arr[6].charAt(i)!= ' '){
                throw new PatternFormatException("Invalid pattern format: Malformed pattern ’" + arr[6] + "’.");
            }
        }
        cells = arr[6];
    }

    public void initialise(World world){
        String[] helparr = cells.split(" ");


        for(int i=0; i<helparr.length; i++){
            for(int j=0; j<helparr[i].length(); j++){
                if(Character.getNumericValue(helparr[i].charAt(j)) == 1){
                            if(j<0 || j>=width){
                                return;
                            }
                            if(i<0 || i>=height){
                                return;
                            }
                            world.setCell(j+startUpperCol, i+startUpperRow, true);
                }
            }
        } 
    }

    public int compareTo(Pattern o) {
        if(name.compareTo(o.getName())<0){
            return -1;
        }
        else if(name.compareTo(o.getName())>0){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getStartCol(){
        return startUpperCol;
    }

    public int getStartRow(){
        return startUpperRow;
    }

    public String getCells(){
        return cells;
    }
    public String toString(){
        return getName() + " (" + getAuthor() + ")";
    }
}

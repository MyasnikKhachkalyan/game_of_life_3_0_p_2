import java.io.*;

public abstract class World {
    protected int generation;
    private Pattern pattern;
    
    public World(Pattern format) throws Exception{
        pattern = format;
    }///doing shit
    public World(World world){
        pattern = world.getPattern();
    }
    public int getWidth(){
        return pattern.getWidth();
    }
    public int getHeight(){
        return pattern.getHeight();
    }
    public int getGenerationCount(){
        return generation;
    }
    protected void incrementGenerationCount(){
        generation ++;
    }
    protected Pattern getPattern(){
        return pattern;
    }
    protected int countNeighbours(int col, int row){
        int counter = 0;
        for(int i=row-1;i<row-1+3;i++){
            for(int j=col-1;j<col-1+3;j++){
                if(i==row && j==col){
                    continue;
                }
                if(getCell(j,i)){
                    counter++;
                } 
            }
        }
        return counter;
    }
    protected boolean computeCell(int col, int row){
        int numOfNeighbours = countNeighbours(col, row); 
        if(getCell(col, row)){
            if(numOfNeighbours>=2 && numOfNeighbours<=3){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(numOfNeighbours==3){
                return true;
            }
            else{
                return false;
            }

        }   
    }
    public void nextGeneration(){
        nextGenerationImpl();
        incrementGenerationCount();
    }
    public abstract boolean getCell(int col, int row);
    public abstract void setCell(int col, int row, boolean value);
    protected abstract void nextGenerationImpl();
    public abstract Object clone();


    }

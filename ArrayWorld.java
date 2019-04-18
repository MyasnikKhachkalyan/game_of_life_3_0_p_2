
public class ArrayWorld extends World implements Cloneable {
    private boolean[][] world;
    private boolean [] deadRow;
    
    public ArrayWorld(Pattern serial) throws Exception {
    super(serial);
    world = new boolean[getPattern().getHeight()][getPattern().getWidth()];
    getPattern().initialise(this);
    makingDead();
    

    }//doing shit
    public ArrayWorld(World w) throws Exception{
        super(w);
        world = new boolean[getPattern().getHeight()][getPattern().getWidth()];
        getPattern().initialise(this);
        makingDead();
    }

    @Override
    public Object clone(){
        try{ArrayWorld world=new ArrayWorld(getPattern());
        for (int i = 0; i < this.world.length; i++) {
            boolean dead=true;
            for (int j = 0; j < this.world[0].length; j++) {
                world.setCell(j,i,this.world[i][j]);
                if(this.world[i][j]) {
                    dead = false;
                }
            }
            if(dead){
                world.world[i]=this.deadRow;
            }
        }
        world.generation=this.generation;
        world.deadRow=this.deadRow;
        return  world;
    } catch(Exception e){
        return null;
    }

}

    public void makingDead(){
        deadRow = new boolean[getWidth()];
        for(int i = 0; i<getHeight();i++){
            boolean dead=true;
            for(int j = 0; j<getWidth(); j++){
                if(world[i][j])
                    dead=false;
            }
            if(dead){
                world[i]=deadRow;
            }
        }
    }

    public boolean getCell(int col, int row){
        if(col<0 || col>=getPattern().getWidth()){
            return false;
        }
        if(row<0 || row>=getPattern().getHeight()){
            return false;
        }
        return world[row][col];
        
    }
    public void setCell(int col, int row, boolean value){
        if(col<0 || col>=getPattern().getWidth()){
            return;
        }
        if(row<0 || row>=getPattern().getHeight()){
            return;
        }
        world[row][col] = value;
    }
    protected void nextGenerationImpl(){
        boolean[][] worldCopy = new boolean[getPattern().getHeight()][getPattern().getWidth()];
        for(int i=0; i<getPattern().getHeight(); i++){
            for(int j=0; j<getPattern().getWidth(); j++){
                if(computeCell(j,i)){
                    worldCopy[i][j] = true;
                }
                else{
                    worldCopy[i][j] = false;
                }
            }
        }   
        for(int i=0; i<getPattern().getHeight(); i++){
            for(int j=0; j<getPattern().getWidth(); j++){
                 world[i][j] = worldCopy[i][j] ;
            }
        }
    }

}
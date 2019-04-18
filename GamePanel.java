import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private World world = null;
    public GamePanel(World w){
        super();
        world = w;
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        
        if(world==null){    
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }    
        else{                                                                                      //// shifted by 6 and 12 so that it will be inside the borders.
            if((this.getWidth()-2*6)/world.getWidth()<=(this.getHeight()-2*12)/world.getHeight()){
                g.setColor(java.awt.Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                int cellWidth = (this.getWidth()-12)/world.getWidth();   
                int cellHeight = cellWidth; 
                for(int i=0;i<world.getHeight(); i++){
                    for(int j=0;j<world.getWidth(); j++){
                        if(world.getCell(j, i)){
                            g.setColor(Color.BLACK);
                            g.fillRect(6+j*cellWidth, 12+i*cellHeight, cellWidth, cellHeight);
                        }
                        else{
                            g.setColor(Color.LIGHT_GRAY);
                            g.drawRect(6+j*cellWidth, 12+i*cellHeight, cellWidth, cellHeight);
                        }
                    }
                }
                g.setColor(Color.BLACK);
                g.drawString("Generation: " + world.getGenerationCount(),6,this.getHeight()-12);
            }
            else{
                g.setColor(java.awt.Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                int cellHeight = (this.getHeight()-24)/world.getHeight();   
                int cellWidth = cellHeight; 
                for(int i=0;i<world.getHeight(); i++){
                    for(int j=0;j<world.getWidth(); j++){
                        if(world.getCell(j, i)){
                            g.setColor(Color.BLACK);
                            g.fillRect(6+j*cellWidth, 12+i*cellHeight, cellWidth, cellHeight);
                        }
                        else{
                            g.setColor(Color.LIGHT_GRAY);
                            g.drawRect(6+j*cellWidth, 12+i*cellHeight, cellWidth, cellHeight);
                        }
                    }
                }
                g.setColor(Color.BLACK);
                g.drawString("Generation: " + world.getGenerationCount(),6,this.getHeight()-12);
            }
        }
    }
    public void display(World w) {
        world = w;
        repaint();
    }
}
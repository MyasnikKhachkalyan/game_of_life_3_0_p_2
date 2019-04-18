import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class GUILife extends JFrame {
    private World world;
    private PatternStore store;
    private GamePanel gamePanel;
    private ArrayList<World> cachedWorlds = new ArrayList<>();

    public GUILife(PatternStore ps) {
        super("Game of Life");
        store=ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024,768);
        add(createPatternsPanel(),BorderLayout.WEST);
        add(createControlPanel(),BorderLayout.SOUTH);
        add(createGamePanel(),BorderLayout.CENTER);
        }


        private void addBorder(JComponent component, String title) {
            Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            Border tb = BorderFactory.createTitledBorder(etch,title);
            component.setBorder(tb);
            }

            private JPanel createGamePanel() {
                gamePanel = new GamePanel(world);
                addBorder(gamePanel,"Game Panel");
                return gamePanel;
            }

            

            private JPanel createPatternsPanel() {
                JPanel patt = new JPanel();
                addBorder(patt,"Patterns");
                ArrayList<Pattern> names = store.getPatternsNameSorted();
                String[] l = new String[names.size()];
                int i = 0;
                for (Pattern p : names) {
                    l[i] = p.toString();
                    i++;
                }
                JList list = new JList(names.toArray());
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setPreferredSize(new Dimension(330, 655));
                patt.add(scrollPane);
                list.addListSelectionListener(new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent arg0) {
                        
                            Pattern pat = names.get(list.getSelectedIndex());
                            if(pat.getHeight()<= 8 && pat.getWidth()<=8){
                                try{
                                    world = new PackedWorld(pat);
                                    gamePanel.display(world);
                                }catch(Exception e){}
                            }
                            else{
                                try{
                                    world = new ArrayWorld(pat);
                                    gamePanel.display(world);
                                }catch(Exception e){}
                            }
                            cachedWorlds.clear();
                          
                        }
                    
                });
                return patt;
            }

            private JPanel createControlPanel() {
                JPanel ctrl = new JPanel();
                addBorder(ctrl,"Controls");
                ctrl.setLayout(new GridLayout(1, 3));
                JButton backButton = new JButton("<Back");
                ctrl.add(backButton);
                JButton playButton = new JButton("Play");
                ctrl.add(playButton);
                JButton forwardButton = new JButton("Forward>");
                ctrl.add(forwardButton);

                return ctrl;
            }





    public static void main(String[] args) throws Exception {
        PatternStore ps = new PatternStore(args[0]);
        GUILife gui = new GUILife(ps);
        gui.setVisible(true);
    }
}
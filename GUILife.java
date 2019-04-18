import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUILife extends JFrame implements ActionListener {
    private World world;
    private PatternStore store;
    private GamePanel gamePanel;
    private boolean playing;
    private Timer timer;
    private ArrayList<World> cachedWorlds = new ArrayList<>();

    private World copyWorld(boolean useCloning) throws Exception {
        if (useCloning) {
            return (World) this.world.clone();
        } else {
            return null;
        }
    }

    public GUILife(PatternStore ps) {
        super("Game of Life");
        store = ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {
        gamePanel = new GamePanel(world);
        addBorder(gamePanel, "Game Panel");
        return gamePanel;
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        addBorder(patt, "Patterns");
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

                if (playing) {
                    runOrPause();
                }
                else{  
                    Pattern pat = names.get(list.getSelectedIndex());
                    if (pat.getHeight() <= 8 && pat.getWidth() <= 8) {
                        try {
                            world = new PackedWorld(pat);
                            gamePanel.display(world);
                        } catch (Exception e) {
                        }
                    } else {
                        try {
                            world = new ArrayWorld(pat);
                            gamePanel.display(world);
                        } catch (Exception e) {
                        }
                    }
                    cachedWorlds.clear();
                }
            }

        });
        return patt;
    }

    private JPanel createControlPanel() {
        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
        ctrl.setLayout(new GridLayout(1, 3));
        JButton backButton = new JButton("<Back");
        backButton.addActionListener(this);
        ctrl.add(backButton);
        JButton playButton = new JButton("Play");
        playButton.addActionListener(this);
        ctrl.add(playButton);
        JButton forwardButton = new JButton("Forward>");
        forwardButton.addActionListener(this);
        ctrl.add(forwardButton);

        return ctrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonString = e.getActionCommand();

        if (buttonString.equals("<Back")) {
            if (playing) {
                runOrPause();
            }
            else{
                if (world.getGenerationCount() > 0) {
                    for (World w : cachedWorlds) {
                        if (w.getGenerationCount() == world.getGenerationCount() - 1) {
                            world = w;
                            break;
                        }
                    }
                }
                gamePanel.display(world);
            }
        } else if (buttonString.equals("Forward>")) {
            if (playing) {
                runOrPause();
            }
            else{
                moveForward();
            }
        } else if (buttonString.equals("Play")) {
            runOrPause();
        } else {
            System.out.println("Unexpected error.");
        }

    }

    private void moveForward() {
        if (world == null) {
            System.out.println("Please select a pattern to play (l to list p index to play):");
        } else {
            World tmpWorld = null;
            for (World w : cachedWorlds) {
                if (w.getGenerationCount() == world.getGenerationCount() + 1) {
                    tmpWorld = w;
                    break;
                }
            }
            if (tmpWorld == null) {
                try {
                    World copiedWorld = copyWorld(true);
                    cachedWorlds.add(copiedWorld);
                    world.nextGeneration();
                } catch (Exception ex) {
                }
            } else {
                world = tmpWorld;
            }
            gamePanel.display(world);
        }
    }

    private void runOrPause() {
        if (playing) {
            timer.cancel();
            playing = false;
        } else {
            playing = true;
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 500);
        }
    }

    public static void main(String[] args) throws Exception {
        PatternStore ps = new PatternStore(args[0]);
        GUILife gui = new GUILife(ps);
        gui.setVisible(true);
    }
}
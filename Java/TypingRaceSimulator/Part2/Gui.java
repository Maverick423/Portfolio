import javax.swing.*;
import java.awt.*;

public class Gui{    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Typing Race");
        JPanel panel = new JPanel();
        RaceData raceData = new RaceData();

        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        home(frame,panel,gbc,raceData);
        
    }
    public static void home(JFrame frame, JPanel panel, GridBagConstraints gbc, RaceData raceData) {
        panel.removeAll();

        gbc.insets = new Insets(5, 5, 5, 5); // space around block

        JLabel simName = new JLabel("Typing Race Simulator");
        JButton startB = new JButton("Start");
        
        gbc.gridy = 0;
        panel.add(simName,gbc);
        
        gbc.gridy = 1;
        panel.add(startB,gbc);       
        startB.addActionListener(e -> difiiculty(frame,panel,gbc, raceData));

        frame.setContentPane(panel);
    }
    public static void difiiculty(JFrame frame, JPanel panel, GridBagConstraints gbc, RaceData raceData) {
        panel.removeAll();

        gbc.insets = new Insets(5, 5, 5, 5); // space around block

        JLabel difficultyL = new JLabel("Select Difficulty", JLabel.CENTER);
        JButton shortB = new JButton("Short");
        JButton mediumB = new JButton("Medium");
        JButton longB = new JButton("Long");
        JButton customB = new JButton("Custom");
        JButton backB =  new JButton("Back");

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(difficultyL,gbc);
        
        gbc.gridwidth = 1;


        gbc.gridy = 1;
        panel.add(shortB,gbc);
        shortB.addActionListener(e -> {
            raceData.difficulty="Short";
            typist(frame,panel,gbc,raceData);
        });

        gbc.gridy = 2;
        panel.add(mediumB,gbc);
        mediumB.addActionListener(e -> {
            raceData.difficulty="Medium";
            typist(frame,panel,gbc,raceData);
        });
        
        gbc.gridy = 3;
        panel.add(longB,gbc);
        longB.addActionListener(e -> {
            raceData.difficulty="Long";
            typist(frame,panel,gbc,raceData);
        });

        gbc.gridy = 4;
        panel.add(customB,gbc);
        customB.addActionListener(e -> {
            raceData.difficulty=custom(frame);
            typist(frame,panel,gbc,raceData);
        });

        gbc.gridy = 5;
        panel.add(backB,gbc);
        backB.addActionListener(e -> home(frame,panel,gbc,raceData));

        frame.setContentPane(panel);
    }
    public static String custom(JFrame frame) {
        String input = JOptionPane.showInputDialog(frame, "Enter difficulty:");
        if (input == null) {
            return custom(frame);
        }
        try {
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid number!");
            return custom(frame);
        }
    }
    public static void typist(JFrame frame, JPanel panel, GridBagConstraints gbc, RaceData raceData) {
        panel.removeAll();

        gbc.insets = new Insets(5, 5, 5, 5); // space around block

        JLabel typistNumL = new JLabel("Select Number Of Typists", JLabel.CENTER);
        JButton two = new JButton("2");
        JButton three = new JButton("3");
        JButton four = new JButton("4");
        JButton five = new JButton("5");
        JButton six = new JButton("6");
        JButton backB =  new JButton("Back");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(typistNumL,gbc);

        gbc.gridwidth = 1;
        
        gbc.gridy = 1;

        gbc.gridx = 0;
        panel.add(two,gbc);
        two.addActionListener(e -> {
            raceData.numTypists = 2;
            difficultyMod(frame,panel,gbc,raceData);
        });

        gbc.gridx = 1;
        panel.add(three,gbc);
        three.addActionListener(e -> {
            raceData.numTypists = 3;
            difficultyMod(frame,panel,gbc,raceData);
        });

        gbc.gridy = 2;
        
        gbc.gridx = 0;
        panel.add(four,gbc);
        four.addActionListener(e -> {
            raceData.numTypists = 4;
            difficultyMod(frame,panel,gbc,raceData);
        });

        gbc.gridx = 1;
        panel.add(five,gbc);
        five.addActionListener(e -> {
            raceData.numTypists = 5;
            difficultyMod(frame,panel,gbc,raceData);
        });

        gbc.gridy = 3;

        gbc.gridx = 0;
        panel.add(six,gbc);
        six.addActionListener(e -> {
            raceData.numTypists = 6;
            difficultyMod(frame,panel,gbc,raceData);
        });

        gbc.gridx = 1;
        panel.add(backB,gbc);
        backB.addActionListener(e -> difiiculty(frame,panel,gbc,raceData));

        frame.setContentPane(panel);
    }
    public static void difficultyMod(JFrame frame, JPanel panel, GridBagConstraints gbc, RaceData raceData) {
        panel.removeAll();

        gbc.insets = new Insets(5, 5, 5, 5); // space around block

        JLabel difficultyModL = new JLabel("Select Difficulty Modifiers", JLabel.CENTER);
        
        JLabel autocorrectL = new JLabel("Autocorrect", JLabel.CENTER);
        JButton AOnB = new JButton("On");
        JButton AOffB = new JButton("Off");

        JLabel caffeineModeL = new JLabel("Caffeine Mode", JLabel.CENTER);
        JButton COnB = new JButton("On");
        JButton COffB = new JButton("Off");

        JLabel nightShiftL = new JLabel("Night Shift", JLabel.CENTER);
        JButton NOnB = new JButton("On");
        JButton NOffB = new JButton("Off");
        
        JButton backB =  new JButton("Back");
        JButton nextB =  new JButton("Next");

        gbc.gridx = 0;
        gbc.gridwidth = 2;

        gbc.gridy = 0;
        panel.add(difficultyModL, gbc);
        
        gbc.gridy = 1;
        panel.add(autocorrectL, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;

        gbc.gridx = 0;
        panel.add(AOnB, gbc);
        AOnB.addActionListener(e ->  {
            raceData.autocorrect = true;
            AOnB.setBackground(Color.GREEN);
            AOffB.setBackground(null);
        });

        gbc.gridx = 1;
        panel.add(AOffB, gbc);
        AOffB.addActionListener(e ->  {
            raceData.autocorrect = false;
            AOffB.setBackground(Color.GREEN);
            AOnB.setBackground(null);
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(caffeineModeL, gbc);


        gbc.gridwidth = 1;
        gbc.gridy = 4;

        gbc.gridx = 0;
        panel.add(COnB, gbc);
        COnB.addActionListener(e ->  {
            raceData.caffeineMode = true;
            COnB.setBackground(Color.GREEN);
            COffB.setBackground(null);
        });

        gbc.gridx = 1;
        panel.add(COffB, gbc);
        COffB.addActionListener(e ->  {
            raceData.caffeineMode = false;
            COffB.setBackground(Color.GREEN);
            COnB.setBackground(null);
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(nightShiftL, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 6;

        gbc.gridx = 0;
        panel.add(NOnB, gbc);
        NOnB.addActionListener(e ->  {
            raceData.nightShift = true;
            NOnB.setBackground(Color.GREEN);
            NOffB.setBackground(null);
        });

        gbc.gridx = 1;
        panel.add(NOffB, gbc);
        NOffB.addActionListener(e ->  {
            raceData.nightShift = false;
            NOffB.setBackground(Color.GREEN);
            NOnB.setBackground(null);
        });

        gbc.gridy = 7;

        gbc.gridx = 0;
        panel.add(backB, gbc);
        backB.addActionListener(e -> typist(frame,panel,gbc,raceData));

        gbc.gridx = 1;
        panel.add(nextB, gbc);
        nextB.addActionListener(e -> gameScreen(frame,panel,gbc,raceData));
        
        frame.setContentPane(panel);
    }    
    public static void gameScreen(JFrame frame, JPanel panel, GridBagConstraints gbc, RaceData raceData) {
        panel.removeAll();
        int passage;
        if (raceData.difficulty.equals("Short")){
            passage = 5;
        } else if (raceData.difficulty.equals("Medium")){
            passage = 10;
        }else if (raceData.difficulty.equals("Long")){
            passage = 20;
        }else{
            passage = Integer.parseInt(raceData.difficulty);
        }
        
        TypingRace race = new TypingRace(passage, raceData.numTypists); 
        race.startRace(raceData);
        JLabel winner = new JLabel("Winner: " +raceData.winner);
        panel.add(winner);
        frame.setContentPane(panel);
    

    }
    
}
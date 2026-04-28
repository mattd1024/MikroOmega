package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JFrame {
    public TitleScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Top label
        JLabel title = new JLabel("Minesweeper");
        title.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        add(title, BorderLayout.PAGE_START);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 0));

        // Start button
        JButton startButton = new JButton("Start");
        centerPanel.add(startButton);

        // Difficulty panel
        JPanel difficultyPanel = new JPanel();
        centerPanel.add(difficultyPanel);

        // Choose difficulty label
        JLabel difficultyLabel = new JLabel("Choose difficulty");
        difficultyLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        difficultyPanel.add(difficultyLabel);

        // Difficulty combo box
        String[] difficulties = {"Easy", "Medium", "Hard"};
        JComboBox<String> difficultyBox = new JComboBox<>(difficulties);
        difficultyPanel.add(difficultyBox);

        centerPanel.add(difficultyPanel);

        // End button
        JButton endButton = new JButton("End");
        centerPanel.add(endButton);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String difficulty = (String) difficultyBox.getSelectedItem();
                System.out.println(difficulty);
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

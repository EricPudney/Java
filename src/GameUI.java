package src;

import javax.swing.*;

import java.awt.*;

import static src.Decision.Commands.*;


public class GameUI {
        private JFrame frame;
        private JTextArea outputArea;
        private JTextField inputField;
        private JButton submitButton;
        private JButton nButton;
        private JButton eButton;
        private JButton sButton;
        private JButton wButton;


    public GameUI() {
            frame = new JFrame("Dungeon Bash");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            outputArea = new JTextArea();
            outputArea.setEditable(false);
            frame.add(new JScrollPane(outputArea), "Center");

            inputField = new JTextField(20);
            submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> handleUserInput());

            nButton = new JButton("N");
            nButton.addActionListener(click -> handleUserInput(n));

            eButton = new JButton("E");
            eButton.addActionListener(click -> handleUserInput(e));

            sButton = new JButton("S");
            sButton.addActionListener(click -> handleUserInput(s));

            wButton = new JButton("W");
            wButton.addActionListener(click -> handleUserInput(w));

            JPanel bottomPanel = new JPanel();
            bottomPanel.add(inputField);
            bottomPanel.add(submitButton);
            frame.add(bottomPanel, BorderLayout.PAGE_END);

            JPanel lowerPanel = new JPanel();
            lowerPanel.add(nButton);
            lowerPanel.add(eButton);
            lowerPanel.add(wButton);
            lowerPanel.add(sButton);
            bottomPanel.add(lowerPanel, "South");

        frame.setVisible(true);
        }

        private void handleUserInput() {
            String input = inputField.getText();
            inputField.setText("");
            outputArea.append("You entered: " + input + "\n");
        }

        private Decision.Actions handleUserInput(Decision.Commands command) {
            return switch (command) {
                case n -> Decision.Actions.north;
                case e -> Decision.Actions.east;
                case s -> Decision.Actions.south;
                case w -> Decision.Actions.west;
                case c -> null;
                case i -> null;
                case h -> null;
                case m -> null;
                case t -> null;
                case a -> null;
                case x -> null;
                case b -> null;
                case q -> null;
                case u -> null;
                case d -> null;
            };
        }

        public static void main(String[] args) {
            new GameUI();
        }
}

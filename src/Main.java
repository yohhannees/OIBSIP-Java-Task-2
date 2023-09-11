import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Show a dialog to set the number limits
            String minInput = JOptionPane.showInputDialog("Enter the minimum number:");
            String maxInput = JOptionPane.showInputDialog("Enter the maximum number:");

            int min, max;

            try {
                min = Integer.parseInt(minInput);
                max = Integer.parseInt(maxInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Using default range (1-100).");
                min = 1;
                max = 100;
            }

            // Create an instance of NumberGuessingGame
            NumberGuessingGame game = new NumberGuessingGame(min, max);
            game.setVisible(true);
        });
    }

    static class NumberGuessingGame extends JFrame implements ActionListener {
        private final int min;
        private final int max;
        private int targetNumber;
        private int attemptsLeft;
        private int score;

        private JLabel titleLabel;
        private JLabel attemptsLabel;
        private JLabel resultLabel;
        private JTextField guessTextField;
        private JButton guessButton;
        private JButton newGameButton;

        public NumberGuessingGame(int min, int max) {
            this.min = min;
            this.max = max;
            this.attemptsLeft = 3; // You can customize the number of attempts here.
            this.score = 0;

            setTitle("Number Guessing Game");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 1));

            titleLabel = new JLabel("Guess the number between " + min + " and " + max);
            attemptsLabel = new JLabel("Attempts left: " + attemptsLeft);
            resultLabel = new JLabel("");
            guessTextField = new JTextField();
            guessButton = new JButton("Guess");
            newGameButton = new JButton("New Game");

            guessButton.addActionListener(this);
            newGameButton.addActionListener(this);

            add(titleLabel);
            add(attemptsLabel);
            add(guessTextField);
            add(guessButton);
            add(newGameButton);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == guessButton) {
                handleGuess();
            } else if (e.getSource() == newGameButton) {
                startNewGame();
            }
        }

        private void handleGuess() {
            try {
                int userGuess = Integer.parseInt(guessTextField.getText());

                if (userGuess < min || userGuess > max) {
                    resultLabel.setText("Please enter a number between " + min + " and " + max);
                } else {
                    attemptsLeft--;
                    attemptsLabel.setText("Attempts left: " + attemptsLeft);

                    if (userGuess == targetNumber) {
                        resultLabel.setText("Congratulations! You guessed the correct number.");
                        score += attemptsLeft + 1;
                        guessButton.setEnabled(false);
                        showScore();
                    } else if (attemptsLeft == 0) {
                        resultLabel.setText("Out of attempts. The correct number was " + targetNumber);
                        guessButton.setEnabled(false);
                        showScore();
                    } else {
                        resultLabel.setText("Incorrect guess. Try again.");
                    }
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input. Please enter a number.");
            }
        }

        private void startNewGame() {
            targetNumber = new Random().nextInt((max - min) + 1) + min;
            attemptsLeft = 3; // Reset the number of attempts.
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            resultLabel.setText("");
            guessTextField.setText("");
            guessButton.setEnabled(true);
        }

        private void showScore() {
            JOptionPane.showMessageDialog(null, "Your score: " + score);
        }
    }
}

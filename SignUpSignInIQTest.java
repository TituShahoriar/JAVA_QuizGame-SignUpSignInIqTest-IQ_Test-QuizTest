import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SignUpSignInIQTest extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JPasswordField signInPasswordField;
    private JLabel messageLabel;
    private Map<String, String> users = new HashMap<>();
    private int currentLevel = 1;
    private int currentQuestion = 1;
    private int score = 0;
    private JLabel questionLabel;
    private JTextField answerField;

    public SignUpSignInIQTest() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        JPanel signUpPanel = new JPanel(new GridLayout(7, 2));
        signUpPanel.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        signUpPanel.add(nameField);
        signUpPanel.add(new JLabel("Email:"));
        emailField = new JTextField(10);
        signUpPanel.add(emailField);
        signUpPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        signUpPanel.add(passwordField);
        signUpPanel.add(new JLabel(""));
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        signUpPanel.add(signUpButton);
        signUpPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        signUpPanel.add(usernameField);
        signUpPanel.add(new JLabel("Password:"));
        signInPasswordField = new JPasswordField(10);
        signUpPanel.add(signInPasswordField);
        signUpPanel.add(new JLabel(""));
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(this);
        signUpPanel.add(signInButton);
        messageLabel = new JLabel("");
        signUpPanel.add(messageLabel);

        JPanel iqTestPanel = new JPanel(new GridLayout(3, 1));
        questionLabel = new JLabel("Level 1, Question 1: What is 2 + 2?");
        iqTestPanel.add(questionLabel);
        answerField = new JTextField(10);
        iqTestPanel.add(answerField);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        iqTestPanel.add(submitButton);

        add(signUpPanel, "Sign Up/Sign In");
        add(iqTestPanel, "IQ Test");

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Sign Up")) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            users.put(email, password);
            messageLabel.setText("Signed up successfully!");
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            usernameField.setText("");
            signInPasswordField.setText("");
            cardLayout.show(getContentPane(), "Sign Up/Sign In");
            pack();
            return;
        } else if (command.equals("Sign In")) {
            String username = usernameField.getText();
            String password = new String(signInPasswordField.getPassword());
            if (users.containsKey(username) && users.get(username).equals(password)) {
                messageLabel.setText("");
                usernameField.setText("");
                signInPasswordField.setText("");
                cardLayout.show(getContentPane(), "IQ Test");
                pack();
                return;
            } else {
                messageLabel.setText("Invalid username or password.");
                return;
            }
        } else if (command.equals("Submit")) {
            String answer = answerField.getText();
            boolean correct = checkAnswer(currentLevel, currentQuestion, answer);
            if (correct) {
                score += getPointsForLevel(currentLevel);
                JOptionPane.showMessageDialog(this, "Correct!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect.");
            }
            currentQuestion++;
            if (currentQuestion > 5) {
                currentQuestion = 1;
                currentLevel++;
                if (currentLevel > 3) {
                    showResults();
                    return;
                }
            }
            updateQuestion();
            return;
        }
    }

    private boolean checkAnswer(int level, int question, String answer) {
        // TODO: Add code to check the user's answer
        return true;
    }

    private int getPointsForLevel(int level) {
        if (level == 1) {
            return 20;
        } else if (level == 2) {
            return 30;
        } else if (level == 3) {
            return 50;
        } else {
            return 0;
        }
    }

    private void updateQuestion() {
        // TODO: Add code to update the question label
        questionLabel.setText("Level " + currentLevel + ", Question " + currentQuestion + ": ...");
    }

    private void showResults() {
        String result;
        if (score < 50) {
            result = "Bad";
        } else if (score < 100) {
            result = "Need to improve";
        } else if (score < 150) {
            result = "Good";
        } else {
            result = "Excellent";
        }
        JOptionPane.showMessageDialog(this, "Your score: " + score + "\nResult: " + result);

        currentLevel = 1;
        currentQuestion = 1;
        score = 0;

        cardLayout.show(getContentPane(), "Sign Up/Sign In");
        pack();
    }

    public static void main(String[] args) {
        new SignUpSignInIQTest();
    }
}

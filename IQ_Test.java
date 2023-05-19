import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class IQ_Test extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField usernameSignUpField;
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

    private String[][] questions = {
            {
                    "What is the next number in the sequence: 2, 4, 6, 8, ?",
                    "If a cat has four legs and a bird has two legs, how many legs would three cats and two birds have in total?",
                    "If you have 10 apples and you give away 3, how many apples do you have left?",
                    "A shop sells a shirt for $20, which is a 25% discount from the original price. What was the original price?",
                    "If 3 people can complete a task in 6 hours, how many hours would it take for 6 people to complete the same task?"
            },
            {
                    "What is the term for the fear of heights?",
                    "Which emotion is associated with the color yellow?",
                    "What is the opposite of love?",
                    "What is the scientific term for a fear of spiders?",
                    "What is the primary emotion associated with the color red?"
            },
            {
                    "What is the capital city of Bangladesh?",
                    "Which river is the longest in Bangladesh?",
                    "What is the national flower of Bangladesh?",
                    "Which year did Bangladesh gain independence from Pakistan?",
                    "Who is the current Prime Minister of Bangladesh?"
            }
    };

    private String[][] answers = {
            {"10", "14", "7", "$26.67", "3"},
            {"Acrophobia", "Happiness", "Hate", "Arachnophobia", "Anger"},
            {"Dhaka", "Padma River", "Water Lily (Shapla)", "1971", "Sheikh Hasina"}
    };

    public IQ_Test() {
        cardLayout=new CardLayout();
        setLayout(cardLayout);

        JPanel signUpPanel=new JPanel(new GridLayout(0,2));
        signUpPanel.add(new JLabel("Name:"));
        nameField=new JTextField(10);
        signUpPanel.add(nameField);
        signUpPanel.add(new JLabel("Email:"));
        emailField=new JTextField(10);
        signUpPanel.add(emailField);
        signUpPanel.add(new JLabel("Username:"));
        usernameSignUpField=new JTextField(10);
        signUpPanel.add(usernameSignUpField);
        signUpPanel.add(new JLabel("Password:"));
        passwordField=new JPasswordField(10);
        signUpPanel.add(passwordField);
        signUpPanel.add(new JLabel(""));
        JButton signUpButton=new JButton("Sign Up");
        signUpButton.addActionListener(this);
        signUpPanel.add(signUpButton);
        signUpPanel.add(new JLabel("Username:"));
        usernameField=new JTextField(10);
        signUpPanel.add(usernameField);
        signUpPanel.add(new JLabel("Password:"));
        signInPasswordField=new JPasswordField(10);
        signUpPanel.add(signInPasswordField);
        signUpPanel.add(new JLabel(""));
        JButton signInButton=new JButton("Sign In");
        signInButton.addActionListener(this);
        signUpPanel.add(signInButton);
        messageLabel=new JLabel("");
        signUpPanel.add(messageLabel);

        JPanel iqTestPanel = new JPanel(new GridLayout(3,1));
        questionLabel=new JLabel(questions[currentLevel-1][currentQuestion-1]);
        iqTestPanel.add(questionLabel);
        answerField=new JTextField(10);
        iqTestPanel.add(answerField);
        JButton submitButton=new JButton("Submit");
        submitButton.addActionListener(this);
        iqTestPanel.add(submitButton);

        add(signUpPanel,"Sign Up/Sign In");
        add(iqTestPanel,"IQ Test");

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command.equals("Sign Up")){
            String name=nameField.getText();
            String email=emailField.getText();
            String usernameSignUp=usernameSignUpField.getText();
            String password=new String(passwordField.getPassword());
            users.put(usernameSignUp,password);
            messageLabel.setText("Signed up successfully!");
            nameField.setText("");
            emailField.setText("");
            usernameSignUpField.setText("");
            passwordField.setText("");
            usernameField.setText("");
            signInPasswordField.setText("");
            cardLayout.show(getContentPane(),"Sign Up/Sign In");
            pack();
            return;
        }else if(command.equals("Sign In")){
            String usernameSignIn=usernameField.getText();
            String passwordSignIn=new String(signInPasswordField.getPassword());
            if(users.containsKey(usernameSignIn)&&users.get(usernameSignIn).equals(passwordSignIn)){
                messageLabel.setText("");
                usernameField.setText("");
                signInPasswordField.setText("");
                cardLayout.show(getContentPane(),"IQ Test");
                pack();
                return;
            }else{
                messageLabel.setText("Invalid username or password.");
                return;
            }
        }else if(command.equals("Submit")){
            String answer=answerField.getText();
            boolean correct=checkAnswer(currentLevel,currentQuestion,answer);
            if(correct){
                score+=getPointsForLevel(currentLevel);
                JOptionPane.showMessageDialog(this,"Correct!");
            }else{
                JOptionPane.showMessageDialog(this,"Incorrect.");
            }
            currentQuestion++;
            if(currentQuestion>5){
                currentQuestion=1;
                currentLevel++;
                if(currentLevel>3){
                    showResults();
                    return;
                }
            }
            updateQuestion();
            return;
        }
    }

    private boolean checkAnswer(int level,int question,String answer){
        return answer.equalsIgnoreCase(answers[level-1][question-1]);
    }

    private int getPointsForLevel(int level){
        if(level==1){
            return 20;
        }else if(level==2){
            return 30;
        }else if(level==3){
            return 50;
        }else{
            return 0;
        }
    }

    private void updateQuestion(){
        questionLabel.setText(questions[currentLevel-1][currentQuestion-1]);
    }

    private void showResults(){
        double percentageScore=((double)score/(20*5+30*5+50*5))*100 ;
        String result;

        if(percentageScore<50){
            result="Bad";
        }else if(percentageScore<70){
            result="Need to improve";
        }else if(percentageScore<90){
            result="Good";
        }else{
            result="Excellent";
        }

        JOptionPane.showMessageDialog(this,"Your score: "+String.format("%.2f",percentageScore)+"%\nResult: "+result);

        currentLevel=1;
        currentQuestion=1;
        score=0;

        cardLayout.show(getContentPane(),"Sign Up/Sign In");
        pack();
    }

    public static void main(String[] args) {
        new IQ_Test();
    }
}
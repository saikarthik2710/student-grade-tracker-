import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField studentCountField;
    private JTextArea gradeInputArea;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    private JButton calculateButton;
    private ArrayList<Double> grades;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Initialize components
        grades = new ArrayList<>();
        studentCountField = new JTextField(10);
        gradeInputArea = new JTextArea(5, 20);
        averageLabel = new JLabel("Average Grade: ");
        highestLabel = new JLabel("Highest Grade: ");
        lowestLabel = new JLabel("Lowest Grade: ");
        calculateButton = new JButton("Calculate");

        // Layout setup
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Details"));
        inputPanel.add(new JLabel("Number of Students: "));
        inputPanel.add(studentCountField);
        inputPanel.add(new JLabel("Enter Grades (one per line): "));
        inputPanel.add(new JScrollPane(gradeInputArea));

        JPanel resultPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        resultPanel.add(averageLabel);
        resultPanel.add(highestLabel);
        resultPanel.add(lowestLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.EAST);

        // Action Listener for the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGrades();
            }
        });
    }

    private void processGrades() {
        try {
            int numberOfStudents = Integer.parseInt(studentCountField.getText());
            grades.clear();
            String[] gradeLines = gradeInputArea.getText().split("\\n");

            if (gradeLines.length != numberOfStudents) {
                JOptionPane.showMessageDialog(this, "Number of grades entered does not match the number of students!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (String gradeLine : gradeLines) {
                grades.add(Double.parseDouble(gradeLine.trim()));
            }

            // Calculate average, highest, and lowest grades
            double average = calculateAverage(grades);
            double highest = findHighest(grades);
            double lowest = findLowest(grades);

            // Display results beside the Calculate button
            averageLabel.setText("Average Grade: " + average);
            highestLabel.setText("Highest Grade: " + highest);
            lowestLabel.setText("Lowest Grade: " + lowest);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for student count and grades.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static double calculateAverage(ArrayList<Double> grades) {
        double sum = 0.0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public static double findHighest(ArrayList<Double> grades) {
        double highest = grades.get(0);
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }

    public static double findLowest(ArrayList<Double> grades) {
        double lowest = grades.get(0);
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeTrackerGUI().setVisible(true);
            }
        });
    }
}

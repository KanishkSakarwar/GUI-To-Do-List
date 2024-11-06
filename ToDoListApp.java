import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JTextField filterInput;

    public ToDoListApp() {
        setTitle("To-Do List Application");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Input Panel for Task Entry and Filtering
        JPanel inputPanel = new JPanel(new BorderLayout());
        taskInput = new JTextField();
        filterInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton editButton = new JButton("Edit Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton filterButton = new JButton("Filter");

        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter:"), BorderLayout.WEST);
        filterPanel.add(filterInput, BorderLayout.CENTER);
        filterPanel.add(filterButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add Panels to Frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addButton.addActionListener(e -> addTask());
        editButton.addActionListener(e -> editTask());
        deleteButton.addActionListener(e -> deleteTask());
        filterButton.addActionListener(e -> filterTasks());

        setVisible(true);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskModel.addElement(task);
            taskInput.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Task cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String newTask = JOptionPane.showInputDialog(this, "Edit Task", taskModel.get(selectedIndex));
            if (newTask != null && !newTask.trim().isEmpty()) {
                taskModel.set(selectedIndex, newTask.trim());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to edit", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to delete", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void filterTasks() {
        String keyword = filterInput.getText().trim();
        if (!keyword.isEmpty()) {
            ArrayList<String> filteredTasks = new ArrayList<>();
            for (int i = 0; i < taskModel.getSize(); i++) {
                String task = taskModel.getElementAt(i);
                if (task.contains(keyword)) {
                    filteredTasks.add(task);
                }
            }
            // Display filtered tasks
            taskList.setListData(filteredTasks.toArray(new String[0]));
        } else {
            // Reset list if filter input is empty
            taskList.setModel(taskModel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}

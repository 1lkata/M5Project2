import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class MovieCollectionManager extends JFrame {
    private ArrayList<Movie> movies;
    private DefaultTableModel tableModel;
    private JTable movieTable;
    private JTextField titleField;
    private JComboBox<String> genreComboBox;
    private JLabel movieCountLabel;

    public MovieCollectionManager() {
        movies = new ArrayList<>();

        setTitle("Movie Collection Manager");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 255));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(200, 200, 255));
        JLabel titleLabel = new JLabel("MOVIE COLLECTION MANAGER");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 70, 150));
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        inputPanel.setBackground(new Color(240, 240, 255));
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        titleField = new JTextField(15);
        titleField.setFont(inputFont);

        String[] genres = {"Action", "Comedy", "Horror", "Drama", "Sci-Fi", "Romance"};
        genreComboBox = new JComboBox<>(genres);
        genreComboBox.setFont(inputFont);

        JButton addButton = new JButton("Add Movie 🎥");
        addButton.setFont(inputFont);
        addButton.setBackground(new Color(100, 200, 100));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addMovie());

        JTextField searchField = new JTextField(12);
        searchField.setFont(inputFont);

        JButton searchButton = new JButton("Search 🔍");
        searchButton.setFont(inputFont);
        searchButton.setBackground(new Color(100, 150, 250));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchMovie(searchField.getText()));

        inputPanel.add(titleField);
        inputPanel.add(genreComboBox);
        inputPanel.add(addButton);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);

        topPanel.add(inputPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 255));

        String[] columns = {"Title", "Genre"};
        tableModel = new DefaultTableModel(columns, 0);
        movieTable = new JTable(tableModel);
        movieTable.setRowHeight(25);
        movieTable.setFont(new Font("Arial", Font.PLAIN, 13));
        movieTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        movieTable.getTableHeader().setBackground(new Color(200, 200, 255));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        movieTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setPreferredSize(new Dimension(660, 300));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 255));

        movieCountLabel = new JLabel("Total Movies: 0");
        movieCountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton removeButton = new JButton("Remove Selected Movie 🗑️");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        removeButton.setBackground(new Color(250, 100, 100));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(e -> removeSelectedMovie());

        bottomPanel.add(movieCountLabel);
        bottomPanel.add(removeButton);

        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void addMovie() {
        String title = titleField.getText().trim();
        String genre = (String) genreComboBox.getSelectedItem();

        if (!title.isEmpty()) {
            movies.add(new Movie(title, genre));
            tableModel.addRow(new Object[]{title, genre});
            updateMovieCount();
            titleField.setText("");
            JOptionPane.showMessageDialog(this, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a movie title!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = movieTable.convertRowIndexToModel(selectedRow);
            movies.remove(modelRow);
            tableModel.removeRow(modelRow);
            updateMovieCount();
            JOptionPane.showMessageDialog(this, "Movie removed successfully!", "Removed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a movie to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchMovie(String query) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String title = tableModel.getValueAt(i, 0).toString().toLowerCase();
            if (title.contains(query.toLowerCase())) {
                movieTable.setRowSelectionInterval(i, i);
                movieTable.scrollRectToVisible(movieTable.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Movie not found.", "Search", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateMovieCount() {
        movieCountLabel.setText("Total Movies: " + movies.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MovieCollectionManager().setVisible(true);
        });
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MainWindow extends JFrame implements ClipboardOwner{

    private static final long serialVersionUID = 1L;
    MainListener buttonListener;
    JTextArea groupsTextArea, masksTextArea, namesTextArea;
    JCheckBox checkBoxCapitalize;
    JPanel mainPanel, groupsPanel, masksPanel, resultPanel;
    int frameWidth, frameHeight;
    NameGenerator nameGenerator;
    Font fontSegoe, fontMono;


    private MainWindow() {
        super("Name gen by yiotro");

        init();
        createInterface();
        load();

        setVisible(true);
        centerWindow();
    }


    private void createInterface() {
        createPanels();
        createSaveButton();
        createTabbedPane();
        createGroupsTextArea();
        createMasksTextArea();
        createResultTab();
    }


    private void createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(buttonListener);
        saveButton.setFont(fontSegoe);
        saveButton.setBounds(frameWidth - 98, 1, 90, 25);
        mainPanel.add(saveButton);
    }


    private void createResultTab() {
        createGenerateButton();
        createNamesArea();
        createCapitalizeCheckBox();
    }


    private void createCapitalizeCheckBox() {
        checkBoxCapitalize = new JCheckBox("Capitalize name");
        checkBoxCapitalize.setSelected(true);
        checkBoxCapitalize.setFont(fontSegoe);
        resultPanel.add(checkBoxCapitalize, BorderLayout.NORTH);
    }


    private void createNamesArea() {
        namesTextArea = new JTextArea();
        namesTextArea.setFont(fontSegoe);
        JScrollPane namesScrollPane = new JScrollPane(namesTextArea);
        namesScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultPanel.add(namesScrollPane, BorderLayout.CENTER);
    }


    void generate() {
        GroupsAnalyzer groupsAnalyzer = new GroupsAnalyzer();
        groupsAnalyzer.setSource(groupsTextArea.getText());
        HashMap<String, String> groups = groupsAnalyzer.getGroups();

        MasksAnalyzer masksAnalyzer = new MasksAnalyzer();
        masksAnalyzer.setSource(masksTextArea.getText());
        ArrayList<String> masks = masksAnalyzer.getMasks();

        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.setGroups(groups);
        nameGenerator.setMasks(masks);
        nameGenerator.setCapitalize(checkBoxCapitalize.isSelected());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String name = nameGenerator.generateName();
            result.append(name).append("\n");
        }
        namesTextArea.setText(result.toString());
    }


    private void createGenerateButton() {
        JButton generateButton = new JButton("Generate");
        generateButton.setActionCommand("generate");
        generateButton.addActionListener(buttonListener);
        generateButton.setFont(fontSegoe);
        resultPanel.add(generateButton, BorderLayout.SOUTH);
    }


    private void init() {
        initFrameSize();
        initLookAndFeel();
        initButtonListener();
        initFont();
        initNameGenerator();
    }


    private void initNameGenerator() {
        nameGenerator = new NameGenerator();
    }


    private void createMasksTextArea() {
        masksTextArea = new JTextArea();
        masksTextArea.setFont(fontMono);
        masksTextArea.setText("kakao\n" +
                "kakakao\n" +
                "kakkao\n" +
                "kakar\n" +
                "kakkar\n" +
                "kakakar");
        JScrollPane masksScrollPane = new JScrollPane(masksTextArea);
        masksScrollPane.setBorder(BorderFactory.createEmptyBorder());
        masksPanel.add(masksScrollPane, BorderLayout.CENTER);
    }


    private void createGroupsTextArea() {
        groupsTextArea = new JTextArea();
        groupsTextArea.setFont(fontMono);
        groupsTextArea.setText("a: o i a e\n" +
                "k: r t p s d f g k l c v b n m\n" +
                "o: o\n" +
                "r: r");
        JScrollPane groupsScrollPane = new JScrollPane(groupsTextArea);
        groupsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        groupsPanel.add(groupsScrollPane, BorderLayout.CENTER);
    }


    private void createPanels() {
        mainPanel = new JPanel(null);
        add(mainPanel, BorderLayout.CENTER);
        groupsPanel = new JPanel(new BorderLayout());
        masksPanel = new JPanel(new BorderLayout());
        resultPanel = new JPanel(new BorderLayout());
    }


    private void createTabbedPane() {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setFont(fontSegoe);
        jTabbedPane.setBounds(0, 0, frameWidth - 4, frameHeight - 25);
        mainPanel.add(jTabbedPane);
        jTabbedPane.addTab("Groups", groupsPanel);
        jTabbedPane.addTab("Masks", masksPanel);
        jTabbedPane.addTab("Result", resultPanel);
    }


    private void initButtonListener() {
        buttonListener = new MainListener(this);
    }


    private void putIntoClipboard(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), this);
    }


    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }


    void save() {
        saveGroups();
        saveMasks();
    }


    private void saveMasks() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("masks.txt"), "Cp1251"));
            String text = masksTextArea.getText();
            StringTokenizer tokenizer = new StringTokenizer(text, "\n");
            ArrayList<String> lines = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                lines.add(tokenizer.nextToken());
            }
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();
        } catch (Exception ignored) {
        }
    }


    private void saveGroups() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("groups.txt"), "Cp1251"));
            String text = groupsTextArea.getText();
            StringTokenizer tokenizer = new StringTokenizer(text, "\n");
            ArrayList<String> lines = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                lines.add(tokenizer.nextToken());
            }
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();
        } catch (Exception ignored) {
        }
    }


    void load() {
        loadGroups();
        loadMasks();
    }


    private void loadMasks() {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("masks.txt"), "Cp1251"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 1) continue;
                buffer.append(line + "\n");
            }
            reader.close();
        } catch (IOException ignored) {
        }
        masksTextArea.setText(buffer.toString());
    }


    private void loadGroups() {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("groups.txt"), "Cp1251"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 1) continue;
                buffer.append(line + "\n");
            }
            reader.close();
        } catch (IOException ignored) {
        }
        groupsTextArea.setText(buffer.toString());
    }


    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }


    private void initFont() {
        fontSegoe = new Font("Segoe UI", Font.PLAIN, 14);
        fontMono = new Font("Monospaced", Font.PLAIN, 15);
        setFont(fontSegoe);
    }


    private void initFrameSize() {
        frameWidth = 300;
        frameHeight = 305;
        setSize(frameWidth, frameHeight);
        setResizable(false);
    }


    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }


    public static void say(String message) {
        System.out.println(message);
    }


    public static void main(String args[]) {
        JFrame f = new MainWindow();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }
}


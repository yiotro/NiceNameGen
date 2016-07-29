import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainListener implements ActionListener {

    private final MainWindow mainWindow;

    MainListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("generate")) {
            mainWindow.generate();
        } else if (e.getActionCommand().equals("save")) {
            mainWindow.save();
        }
    }
}
package spark.UI;

import spark.UI.Button.MyIconButton;
import spark.UI.Panel.CrawlCityPanel;
import spark.UI.Panel.TaskInfoPanel;
import spark.UI.Panel.ToolBarPanel;
import spark.Util.PropertyUtil;

import javax.swing.*;
import java.awt.*;

public class MainAppUI {
    private static JFrame frame;
    private static JPanel mainPanel;

    public static JPanel mainPanelCenter;
    public static CrawlCityPanel cityPanel;
    public static TaskInfoPanel taskPanel;

    public static void main(String[] args) {
        // 设置系统默认样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        frame = new JFrame();
        frame.setBounds(ConstantsUI.MAIN_WINDOW_X, ConstantsUI.MAIN_WINDOW_Y, ConstantsUI.MAIN_WINDOW_WIDTH,
                ConstantsUI.MAIN_WINDOW_HEIGHT);
        frame.setTitle(ConstantsUI.APP_NAME);
        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        ToolBarPanel panelToolBar = new ToolBarPanel();
        mainPanel.add(panelToolBar,BorderLayout.WEST);

        cityPanel = new CrawlCityPanel();
        taskPanel = new TaskInfoPanel();

        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanelCenter.add(cityPanel,BorderLayout.CENTER);

        mainPanel.add(mainPanelCenter,BorderLayout.CENTER);

        frame.add(mainPanel);

        frame.setVisible(true);
    }


}

package spark.UI.Panel;

import spark.UI.Button.MyIconButton;
import spark.UI.ConstantsUI;
import spark.UI.MainAppUI;
import spark.Util.PropertyUtil;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;

public class ToolBarPanel extends JPanel {
    private static MyIconButton buttonStatus;
    private static MyIconButton buttonDatabase;
    private static MyIconButton buttonSetting;

    public ToolBarPanel(){
        initialize();
        addButton();
        addListener();
    }

    private void initialize(){
        Dimension preferredSize = new Dimension(48, ConstantsUI.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    private void addButton(){
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        buttonStatus = new MyIconButton(ConstantsUI.ICON_STATUS_ENABLE, ConstantsUI.ICON_STATUS_ENABLE,
                ConstantsUI.ICON_STATUS, PropertyUtil.getProperty("sp.ui.status.title"));
        buttonDatabase = new MyIconButton(ConstantsUI.ICON_DATABASE,ConstantsUI.ICON_DATABASE_ENABLE,
                ConstantsUI.ICON_STATUS,PropertyUtil.getProperty("sp.ui.database.title"));
        buttonSetting = new MyIconButton(ConstantsUI.ICON_SETTING, ConstantsUI.ICON_SETTING_ENABLE,
                ConstantsUI.ICON_SETTING, PropertyUtil.getProperty("sp.ui.setting.title"));

        panelUp.add(buttonStatus);
        panelUp.add(buttonDatabase);

        panelDown.add(buttonSetting, BorderLayout.SOUTH);
        this.add(panelUp);
        this.add(panelDown);
    }

    private void addListener(){
        buttonStatus.addActionListener((event)->{
            buttonStatus.setIcon(ConstantsUI.ICON_STATUS_ENABLE);
            buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
            buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

            MainAppUI.mainPanelCenter.removeAll();
            MainAppUI.mainPanelCenter.add(MainAppUI.cityPanel);
            MainAppUI.mainPanelCenter.updateUI();
        });
        buttonDatabase.addActionListener((event)->{
            buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE_ENABLE);
            buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
            buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

            MainAppUI.mainPanelCenter.removeAll();
            MainAppUI.mainPanelCenter.add(MainAppUI.taskPanel);
            MainAppUI.mainPanelCenter.updateUI();
            MainAppUI.taskPanel.setContent();
        });

        buttonSetting.addActionListener((event)->{
            buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
            buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
            buttonSetting.setIcon(ConstantsUI.ICON_SETTING_ENABLE);
        });
    }
}

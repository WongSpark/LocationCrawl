package spark.UI.Panel;

import spark.Pojo.PoiTask;
import spark.UI.Button.MyIconButton;
import spark.UI.ConstantsUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TaskInfoPanel extends JPanel {
    private JTextField textWebSite;
    private JTextField textStartPage;
    private JTextField textPageCount;
    private JTextField textDistinctCode;
    private JTextField textSavePath;
    private MyIconButton buttonSave;

    private static PoiTask poiTask;

    public TaskInfoPanel(){
        initialize();
        addComponent();
        addListener();
    }

    private void initialize(){
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
    }

    private void addComponent(){
        JPanel panelInfoGrid = new JPanel();
        panelInfoGrid.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelInfoGrid.setLayout(new FlowLayout());

        JPanel panelWebSite = new JPanel();
        panelWebSite.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelWebSite.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelWebSite = new JLabel("爬取目标地址:");
        labelWebSite.setFont(ConstantsUI.FONT_NORMAL);
        labelWebSite.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        textWebSite = new JTextField();
        textWebSite.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        panelWebSite.add(labelWebSite);
        panelWebSite.add(textWebSite);

        JPanel panelStartPage = new JPanel();
        panelStartPage.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelStartPage.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelStartPage = new JLabel("爬取起始页:");
        labelStartPage.setFont(ConstantsUI.FONT_NORMAL);
        labelStartPage.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        textStartPage = new JTextField();
        textStartPage.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        panelStartPage.add(labelStartPage);
        panelStartPage.add(textStartPage);

        JPanel panelPageCount = new JPanel();
        panelPageCount.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelPageCount.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelPageCount = new JLabel("爬取数量:");
        labelPageCount.setFont(ConstantsUI.FONT_NORMAL);
        labelPageCount.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        textPageCount = new JTextField();
        textPageCount.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        panelPageCount.add(labelPageCount);
        panelPageCount.add(textPageCount);

        JPanel panelDistinctCode = new JPanel();
        panelDistinctCode.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDistinctCode.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelDistinctCode = new JLabel("行政区编码:");
        labelDistinctCode.setFont(ConstantsUI.FONT_NORMAL);
        labelDistinctCode.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        textDistinctCode = new JTextField();
        textDistinctCode.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        panelDistinctCode.add(labelDistinctCode);
        panelDistinctCode.add(textDistinctCode);

        JPanel panelSavePath = new JPanel();
        panelSavePath.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelSavePath.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelSavePath = new JLabel("保存路径:");
        labelSavePath.setFont(ConstantsUI.FONT_NORMAL);
        labelSavePath.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        textSavePath = new JTextField();
        textSavePath.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        panelSavePath.add(labelSavePath);
        panelSavePath.add(textSavePath);

        panelInfoGrid.add(panelWebSite);
        panelInfoGrid.add(panelStartPage);
        panelInfoGrid.add(panelPageCount);
        panelInfoGrid.add(panelDistinctCode);
        panelInfoGrid.add(panelSavePath);

        //按钮面板
        JPanel panelButton = new JPanel();
        panelButton.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT,ConstantsUI.MAIN_H_GAP,15));

        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE,ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE,"");
        panelButton.add(buttonSave);

        this.add(panelInfoGrid, BorderLayout.CENTER);
        this.add(panelButton,BorderLayout.SOUTH);
    }

    private void addListener(){
        buttonSave.addActionListener((event)->{
            if(poiTask==null){
                poiTask = new PoiTask();
            }

            poiTask.setWebSite(textWebSite.getText());
            poiTask.setStartPage(Integer.valueOf(textStartPage.getText()));
            poiTask.setPageCount(Integer.valueOf(textPageCount.getText()));
            poiTask.setDistinctCode(textDistinctCode.getText());
            poiTask.setSavePath(textSavePath.getText());

            JOptionPane.showMessageDialog(this, "保存成功", "提示", JOptionPane.PLAIN_MESSAGE);
        });
    }

    public static PoiTask getTask(){
        return poiTask;
    }

    public void setContent(){
        textWebSite.setText("http://www.poi86.com/poi/amap/district");
        textSavePath.setText("F:\\");
        textDistinctCode.setText("370102");
        textStartPage.setText("1");
        textPageCount.setText("1");
    }
}

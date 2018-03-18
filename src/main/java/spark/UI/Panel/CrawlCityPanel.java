package spark.UI.Panel;

import com.sun.beans.editors.ColorEditor;
import spark.Pojo.PoiTask;
import spark.Service.PageCrawl;
import spark.UI.Button.MyIconButton;
import spark.UI.ConstantsUI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrawlCityPanel extends JPanel {
    private JLabel labelWebSiteValue;
    private JProgressBar progressTotal;
    private JProgressBar progressDetail;
    private MyIconButton buttonStartSchedule;
    private MyIconButton buttonStop;
    private MyIconButton buttonStartNow;
    private JTextPane textAreaDisplay;

    private ExecutorService executorService= Executors.newFixedThreadPool(10);

    public CrawlCityPanel(){
        initialize();
        addComponent();
        addListener();
    }

    private void initialize(){
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    private void addComponent(){
        this.add(getTopPanel(),BorderLayout.NORTH);
        this.add(getMiddlePanel(),BorderLayout.CENTER);
        this.add(getBottomPanel(),BorderLayout.SOUTH);
    }

    private JPanel getTopPanel(){
        JPanel panelTop = new JPanel();
        panelTop.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT,ConstantsUI.MAIN_H_GAP,5));

        JLabel labelTitle = new JLabel("状态");
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelTop.add(labelTitle);

        return panelTop;
    }

    private JPanel getMiddlePanel(){
        JPanel panelMiddle = new JPanel();
        panelMiddle.setLayout(new BorderLayout());
        panelMiddle.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        //爬取信息mianban
        JPanel panelCrawlInfo = new JPanel();
        panelCrawlInfo.setLayout(new GridLayout(3,1));
        panelCrawlInfo.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        JPanel panelWebSize = new JPanel();
        panelWebSize.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelWebSize.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 20));
        JLabel labelWebSite = new JLabel("爬取地址:");
        labelWebSite.setFont(ConstantsUI.FONT_RADIO);
        labelWebSiteValue = new JLabel("");
        labelWebSiteValue.setFont(ConstantsUI.FONT_NORMAL);
        panelWebSize.add(labelWebSite);
        panelWebSize.add(labelWebSiteValue);

        JPanel panelTotalProgress = new JPanel();
        panelTotalProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTotalProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 20));
        JLabel labelTotal = new JLabel("总体进度:");
        labelTotal.setFont(ConstantsUI.FONT_RADIO);
        Dimension preferredSizeProgressbar = new Dimension(640, 20);
        progressTotal = new JProgressBar();
        progressTotal.setPreferredSize(preferredSizeProgressbar);
        panelTotalProgress.add(labelTotal);
        panelTotalProgress.add(progressTotal);

        JPanel panelDetailProgress = new JPanel();
        panelDetailProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDetailProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 20));
        JLabel labelDetail = new JLabel("当前进度:");
        labelDetail.setFont(ConstantsUI.FONT_RADIO);
        progressDetail = new JProgressBar();
        progressDetail.setPreferredSize(preferredSizeProgressbar);
        panelDetailProgress.add(labelDetail);
        panelDetailProgress.add(progressDetail);

        panelCrawlInfo.add(panelWebSize);
        panelCrawlInfo.add(panelTotalProgress);
        panelCrawlInfo.add(panelDetailProgress);

        //控制台面板
        JScrollPane panelConsole = new JScrollPane();
        panelConsole.setBackground(Color.BLACK);
        panelConsole.setViewportBorder(null);
        textAreaDisplay = new JTextPane();
        textAreaDisplay.setForeground(Color.WHITE);
        textAreaDisplay.setFont(ConstantsUI.FONT_NORMAL);
        textAreaDisplay.setBackground(Color.BLACK);
        panelConsole.setViewportView(textAreaDisplay);

        panelMiddle.add(panelCrawlInfo,BorderLayout.NORTH);
        panelMiddle.add(panelConsole,BorderLayout.CENTER);

        return panelMiddle;
    }

    private JPanel getBottomPanel(){
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new GridLayout(1, 2));
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid1.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 15));
        JPanel panelGrid2 = new JPanel();
        panelGrid2.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid2.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonStartSchedule = new MyIconButton(ConstantsUI.ICON_START_SCHEDULE, ConstantsUI.ICON_START_SCHEDULE_ENABLE,
                ConstantsUI.ICON_START_SCHEDULE_DISABLE, "");
        buttonStop = new MyIconButton(ConstantsUI.ICON_STOP, ConstantsUI.ICON_STOP_ENABLE,
                ConstantsUI.ICON_STOP_DISABLE, "");
        buttonStop.setEnabled(false);
        buttonStartNow = new MyIconButton(ConstantsUI.ICON_SYNC_NOW, ConstantsUI.ICON_SYNC_NOW_ENABLE,
                ConstantsUI.ICON_SYNC_NOW_DISABLE, "");
        panelGrid1.add(buttonStartSchedule);
        panelGrid1.add(buttonStop);
        panelGrid2.add(buttonStartNow);

        panelDown.add(panelGrid1);
        panelDown.add(panelGrid2);
        return panelDown;
    }

    private void addListener(){
        buttonStartSchedule.addActionListener((event)->{
            buttonStartNow.setEnabled(false);
            buttonStartSchedule.setEnabled(false);
            buttonStop.setEnabled(true);
        });
        buttonStartNow.addActionListener((event)->{
            buttonStartNow.setEnabled(false);
            buttonStartSchedule.setEnabled(false);
            buttonStop.setEnabled(true);
            progressTotal.setValue(0);

            PoiTask poiTask = TaskInfoPanel.getTask();
            PageCrawl pageCrawl = new PageCrawl(poiTask.getWebSite(),
                    poiTask.getDistinctCode(),poiTask.getSavePath(),poiTask.getStartPage(),poiTask.getPageCount());
            pageCrawl.setWebSiteUpdator((webSite)->{
                labelWebSiteValue.setText(webSite);
            });

            pageCrawl.setConsoleUpdator((message)->{
                try {
                    StyledDocument styledDocument = textAreaDisplay.getStyledDocument();
                    SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
                    if(styledDocument.getLength()>10000) {
                        styledDocument.remove(0,5000);
                    }
                    styledDocument.insertString(styledDocument.getLength(),message, simpleAttributeSet);
                    styledDocument.insertString(styledDocument.getLength(),"\n",simpleAttributeSet);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            });
            pageCrawl.setDetailProgressUpdator((detailProgress)->{
                System.out.println("detailProgress"+detailProgress);
                progressDetail.setValue(detailProgress);
            });
            pageCrawl.setTotalProgressUpdator((totalProgress)->{
                System.out.println("totalProgress"+totalProgress);
                progressTotal.setValue(totalProgress);
            });

            executorService.execute(()->{
                pageCrawl.startTask();
                buttonStop.setEnabled(false);
                buttonStartNow.setEnabled(true);
                buttonStartSchedule.setEnabled(true);
                buttonStop.setEnabled(false);
                JOptionPane.showMessageDialog(this, "任务完成", "提示", JOptionPane.PLAIN_MESSAGE);
            });
        });
        buttonStop.addActionListener((event)->{
            buttonStartNow.setEnabled(true);
            buttonStartSchedule.setEnabled(true);
            buttonStop.setEnabled(false);
        });
    }
}

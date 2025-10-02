package GUI.Panel;

import java.awt.*;
import javax.swing.*;
import GUI.Component.PanelShadow;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class TrangChu extends JPanel {

    JPanel top, center, bar1, bar2;
    PanelShadow content[];
    JPanel info[];
    JLabel title, subTit, infoDetail[], objDetail[], objDetail1[], infoIcon[];

    String[][] getSt = {
        {"Tính chính xác", "tinhchinhxac_128px.svg", "<html>Mã IMEI là một số duy nhất được <br>gán cho từng thiết bị điện thoại,<br> do đó hệ thống quản lý điện thoại<br> theo mã IMEI sẽ đảm bảo tính <br>chính xác và độ tin cậy cao.</html>"},
        {"Tính bảo mật", "tinhbaomat_128px.svg", "<html>Ngăn chặn việc sử dụng các thiết bị<br> điện thoại giả mạo hoặc bị đánh cắp.<br> Điều này giúp tăng tính bảo mật cho <br>các hoạt động quản lý điện thoại.</html>"},
        {"Tính hiệu quả", "tinhhieuqua_128px.svg", "<html>Dễ dàng xác định được thông tin <br>về từng thiết bị điện thoại một cách <br>nhanh chóng và chính xác, giúp <br>cho việc quản lý điện thoại được <br>thực hiện một cách hiệu quả hơn.</html>"},
    };

    Color MainColor = new Color(255, 255, 255);
    Color FontColor = new Color(96, 125, 139);
    Color BackgroundColor = new Color(240, 247, 250);
    Color HowerFontColor = new Color(225, 230, 232);

    private void initComponent() {
        this.setBackground(new Color(24, 24, 24));
        this.setBounds(0, 200, 300, 1200);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Tạo panel trên cùng
        top = new JPanel();
        top.setBackground(MainColor);
        top.setPreferredSize(new Dimension(1100, 200));
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        // Tạo panel con chứa logo và slogan
        JPanel logoSloganPanel = new JPanel();
        logoSloganPanel.setBackground(MainColor);
        logoSloganPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.jpg")));
        logoSloganPanel.add(logo);

        // Slogan
        JLabel slogan = new JLabel();
        slogan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/slogan2.png")));
        logoSloganPanel.add(slogan);

        // Thêm logo-slogan vào top
        top.add(logoSloganPanel);
        this.add(top, BorderLayout.NORTH);

        // Phần trung tâm
        center = new JPanel();
        center.setBackground(BackgroundColor);
        center.setPreferredSize(new Dimension(1100, 800));
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        content = new PanelShadow[getSt.length];
        info = new JPanel[3];
        infoDetail = new JLabel[3];
        objDetail = new JLabel[3];
        objDetail1 = new JLabel[3];
        infoIcon = new JLabel[3];

        for (int i = 0; i < getSt.length; i++) {
            content[i] = new PanelShadow(getSt[i][1], getSt[i][0], getSt[i][2]);
            center.add(content[i]);
        }

        this.add(center, BorderLayout.CENTER);
    }

    public TrangChu() {
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
    }
}

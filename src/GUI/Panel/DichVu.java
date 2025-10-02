package GUI.Panel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class DichVu extends JPanel {

    private JTable serviceTable;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch;
    private JTextField searchField;

    public DichVu() {
        // Cấu trúc cơ bản của Panel
        setLayout(new BorderLayout());

        // Tạo bảng để hiển thị dịch vụ
        String[] columnNames = {"Mã dịch vụ", "Tên dịch vụ", "Trạng thái", "Ngày tạo"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        serviceTable = new JTable(model);
        
        JScrollPane tableScroll = new JScrollPane(serviceTable);
        add(tableScroll, BorderLayout.CENTER);

        // Panel phía trên chứa các nút và thanh tìm kiếm
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // Tìm kiếm
        searchField = new JTextField(20);
        topPanel.add(searchField);
        btnSearch = new JButton("Tìm kiếm");
        topPanel.add(btnSearch);

        // Các nút thêm, sửa, xóa
        btnAdd = new JButton("Thêm dịch vụ");
        btnEdit = new JButton("Sửa dịch vụ");
        btnDelete = new JButton("Xóa dịch vụ");
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);

        add(topPanel, BorderLayout.NORTH);
    }

    // Các phương thức getter cho các nút và ô tìm kiếm
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTable getServiceTable() {
        return serviceTable;
    }
}

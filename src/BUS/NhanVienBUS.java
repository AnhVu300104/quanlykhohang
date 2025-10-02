/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import GUI.Panel.NhanVien;
import GUI.Dialog.NhanVienDialog;
import helper.Validation;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class NhanVienBUS implements ActionListener, DocumentListener {

    public NhanVien nv;
    private JTextField textField;
    public ArrayList<NhanVienDTO> listNv = NhanVienDAO.getInstance().selectAll();
    public NhanVienDAO nhanVienDAO = NhanVienDAO.getInstance();

    public NhanVienBUS() {}

    public NhanVienBUS(NhanVien nv) {
        this.nv = nv;
    }

    public NhanVienBUS(JTextField textField, NhanVien nv) {
        this.textField = textField;
        this.nv = nv;
    }

    public ArrayList<NhanVienDTO> getAll() {
        return listNv;
    }

    public NhanVienDTO getByIndex(int index) {
        return listNv.get(index);
    }

    public int getIndexById(int manv) {
        for (int i = 0; i < listNv.size(); i++) {
            if (listNv.get(i).getManv() == manv) {
                return i;
            }
        }
        return -1;
    }

    public String getNameById(int manv) {
        NhanVienDTO nvDTO = nhanVienDAO.selectById(String.valueOf(manv));
        return nvDTO != null ? nvDTO.getHoten() : "";
    }

    public String[] getArrTenNhanVien() {
        String[] result = new String[listNv.size()];
        for (int i = 0; i < listNv.size(); i++) {
            result[i] = listNv.get(i).getHoten();
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        switch (btn) {
            case "THÊM" -> new NhanVienDialog(this, nv.owner, true, "Thêm nhân viên", "create");
            case "SỬA" -> {
                int index = nv.getRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa");
                } else {
                    new NhanVienDialog(this, nv.owner, true, "Sửa nhân viên", "update", nv.getNhanVien());
                }
            }
            case "XÓA" -> {
                if (nv.getRow() < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa");
                } else {
                    deleteNv(nv.getNhanVien());
                }
            }
            case "CHI TIẾT" -> {
                if (nv.getRow() < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên");
                } else {
                    new NhanVienDialog(this, nv.owner, true, "Xem nhân viên", "detail", nv.getNhanVien());
                }
            }
            case "NHẬP EXCEL" -> importExcel();
            case "XUẤT EXCEL" -> {
                String[] header = {"MãNV", "Tên nhân viên", "Email nhân viên", "Số điên thoại", "Giới tính", "Ngày sinh"};
                exportExcel(listNv, header);
            }
        }
        nv.loadDataTalbe(listNv);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        searchAndLoad();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        searchAndLoad();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}

    private void searchAndLoad() {
        String text = textField.getText();
        if (text.isEmpty()) {
            nv.loadDataTalbe(listNv);
        } else {
            searchLoadTable(search(text));
        }
    }

    public void insertNv(NhanVienDTO nvDTO) {
        listNv.add(nvDTO);
    }

    public void updateNv(int index, NhanVienDTO nvDTO) {
        listNv.set(index, nvDTO);
    }

    public int getIndex() {
        return nv.getRow();
    }

    public void deleteNv(NhanVienDTO nvDTO) {
        NhanVienDAO.getInstance().delete(String.valueOf(nvDTO.getManv()));
        TaiKhoanDAO.getInstance().delete(String.valueOf(nvDTO.getManv()));
        listNv.removeIf(n -> n.getManv() == nvDTO.getManv());
        loadTable();
    }

    public void loadTable() {
        nv.loadDataTalbe(listNv);
    }

    public void searchLoadTable(ArrayList<NhanVienDTO> list) {
        nv.loadDataTalbe(list);
    }

    public void openFile(String file) {
        try {
            Desktop.getDesktop().open(new File(file));
        } catch (IOException e) {
            System.out.println("Lỗi mở file: " + e.getMessage());
        }
    }

    public void exportExcel(ArrayList<NhanVienDTO> list, String[] header) {
        if (list.isEmpty()) return;

        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(nv.owner) != JFileChooser.APPROVE_OPTION) return;

        File file = new File(chooser.getSelectedFile() + ".xlsx");

        try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(file)) {
            Sheet sheet = wb.createSheet("Nhân viên");
            writeHeader(header, sheet, 0);
            int rowIndex = 1;
            for (NhanVienDTO nvDTO : list) {
                Row row = sheet.createRow(rowIndex++);
                writeNhanVien(nvDTO, row);
            }
            wb.write(out);
            openFile(file.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NhanVienDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        String luachon = (String) nv.search.cbxChoose.getSelectedItem();

        for (NhanVienDTO i : listNv) {
            switch (luachon) {
                case "Tất cả" -> {
                    if (i.getHoten().toLowerCase().contains(text)
                            || i.getEmail().toLowerCase().contains(text)
                            || i.getSdt().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                case "Họ tên" -> {
                    if (i.getHoten().toLowerCase().contains(text)) result.add(i);
                }
                case "Email" -> {
                    if (i.getEmail().toLowerCase().contains(text)) result.add(i);
                }
            }
        }
        return result;
    }

    private static void writeHeader(String[] list, Sheet sheet, int rowIndex) {
        CellStyle style = createStyleForHeader(sheet);
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < list.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(list[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);

        return style;
    }

    private static void writeNhanVien(NhanVienDTO nv, Row row) {
        row.createCell(0).setCellValue(nv.getManv());
        row.createCell(1).setCellValue(nv.getHoten());
        row.createCell(2).setCellValue(nv.getEmail());
        row.createCell(3).setCellValue(nv.getSdt());
        row.createCell(4).setCellValue(nv.getGioitinh() == 1 ? "Nam" : "Nữ");
        row.createCell(5).setCellValue(String.valueOf(nv.getNgaysinh()));
    }

    public void importExcel() {
        JFileChooser jf = new JFileChooser();
        if (jf.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File file = jf.getSelectedFile();
        int k = 0;

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             XSSFWorkbook workbook = new XSSFWorkbook(bis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    XSSFRow row = sheet.getRow(i);
                    int id = NhanVienDAO.getInstance().getAutoIncrement();
                    String tennv = row.getCell(0).getStringCellValue();
                    String gioitinh = row.getCell(1).getStringCellValue();
                    int gt = (gioitinh.equalsIgnoreCase("Nam")) ? 1 : 0;
                    Date ngaysinh = row.getCell(2).getDateCellValue();
                    String sdt = row.getCell(3).getStringCellValue();
                    String email = row.getCell(4).getStringCellValue();

                    if (Validation.isEmpty(tennv) || Validation.isEmpty(email)
                            || !Validation.isEmail(email) || Validation.isEmpty(sdt)
                            || !isPhoneNumber(sdt) || sdt.length() != 10) {
                        k++;
                        continue;
                    }

                    java.sql.Date sqlDate = new java.sql.Date(ngaysinh.getTime());
                    NhanVienDTO nvdto = new NhanVienDTO(id, tennv, gt, sqlDate, sdt, 1, email);
                    NhanVienDAO.getInstance().insert(nvdto);
                } catch (Exception ex) {
                    k++;
                }
            }
            if (k == 0) {
                JOptionPane.showMessageDialog(null, "Nhập thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Một số dòng lỗi và không được nhập: " + k);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file: " + e.getMessage());
        }
    }

    public static boolean isPhoneNumber(String str) {
        str = str.replaceAll("[\\s\\-()]", "");
        return str.matches("\\d{10}");
    }
}

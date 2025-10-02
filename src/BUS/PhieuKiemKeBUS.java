package BUS;

import DAO.ChiTietKiemKeDAO;
import DAO.ChiTietKiemKeSanPhamDAO;
import DAO.PhieuKiemKeDAO;
import DTO.ChiTietKiemKeDTO;
import DTO.ChiTietKiemKeSanPhamDTO;
import DTO.PhieuKiemKeDTO;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhieuKiemKeBUS {

    private final PhieuKiemKeDAO phieuKiemKeDAO = PhieuKiemKeDAO.getInstance();
    private final ChiTietKiemKeDAO chiTietKiemKeDAO = ChiTietKiemKeDAO.getInstance();
    private final ChiTietKiemKeSanPhamDAO chiTietKiemKeSanPhamDAO = ChiTietKiemKeSanPhamDAO.getInstance();
    private final NhanVienBUS nvBUS = new NhanVienBUS();
    private ArrayList<PhieuKiemKeDTO> danhSachPhieu;

    public PhieuKiemKeBUS() {
        danhSachPhieu = phieuKiemKeDAO.selectAll();
    }

    public void insert(PhieuKiemKeDTO phieuKiemKeDTO, ArrayList<ChiTietKiemKeDTO> dsPhieu, ArrayList<ChiTietKiemKeSanPhamDTO> ctPhieu) {
        phieuKiemKeDAO.insert(phieuKiemKeDTO);
        chiTietKiemKeDAO.insert(dsPhieu);
        chiTietKiemKeSanPhamDAO.insert(ctPhieu);
        danhSachPhieu.add(phieuKiemKeDTO);
    }

    public ArrayList<PhieuKiemKeDTO> getDanhSachPhieu() {
        return danhSachPhieu;
    }

    public void setDanhSachPhieu(ArrayList<PhieuKiemKeDTO> danhSachPhieu) {
        this.danhSachPhieu = danhSachPhieu;
    }

    public int getAutoIncrement() {
        return phieuKiemKeDAO.getAutoIncrement();
    }

    public ArrayList<PhieuKiemKeDTO> selectAll() {
        return phieuKiemKeDAO.selectAll();
    }

    public void cancel(int index) {
        PhieuKiemKeDTO phieuKiemKeDTO = danhSachPhieu.get(index);
        String id = String.valueOf(phieuKiemKeDTO.getMaphieukiemke());
        chiTietKiemKeSanPhamDAO.delete(id);
        chiTietKiemKeDAO.delete(id);
        phieuKiemKeDAO.delete(id);
        danhSachPhieu.remove(index);
    }

    public ArrayList<ChiTietKiemKeDTO> getChitietTiemKe(int maphieu) {
        return chiTietKiemKeDAO.selectAll(String.valueOf(maphieu));
    }

    public ArrayList<PhieuKiemKeDTO> fillerPhieuKiemKe(int type, String input, int manv, Date time_s, Date time_e) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time_e);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Timestamp time_end = new Timestamp(cal.getTimeInMillis());

        Timestamp time_start = new Timestamp(time_s.getTime());
        ArrayList<PhieuKiemKeDTO> result = new ArrayList<>();

        for (PhieuKiemKeDTO phieuKiemKe : getDanhSachPhieu()) {
            boolean match = false;
            switch (type) {
                case 0 -> {
                    match = String.valueOf(phieuKiemKe.getMaphieukiemke()).contains(input)
                            || nvBUS.getNameById(phieuKiemKe.getNguoitao()).toLowerCase().contains(input.toLowerCase());
                }
                case 1 -> {
                    match = String.valueOf(phieuKiemKe.getMaphieukiemke()).contains(input);
                }
                case 2 -> {
                    match = nvBUS.getNameById(phieuKiemKe.getNguoitao()).toLowerCase().contains(input.toLowerCase());
                }
            }

            if (match &&
                (manv == 0 || phieuKiemKe.getNguoitao() == manv) &&
                !phieuKiemKe.getThoigiantao().before(time_start) &&
                !phieuKiemKe.getThoigiantao().after(time_end)) {
                result.add(phieuKiemKe);
            }
        }

        return result;
    }

    public void xuatFileExcel(String filePath, ArrayList<PhieuKiemKeDTO> list) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PhieuKiemKe");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã Phiếu");
            header.createCell(1).setCellValue("Người Tạo");
            header.createCell(2).setCellValue("Thời Gian Tạo");

            int rowIndex = 1;
            for (PhieuKiemKeDTO pkk : list) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(pkk.getMaphieukiemke());
                row.createCell(1).setCellValue(nvBUS.getNameById(pkk.getNguoitao()));
                row.createCell(2).setCellValue(pkk.getThoigiantao().toString());
            }

            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

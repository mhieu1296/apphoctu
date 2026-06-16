package ui.panel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

public class DanhSachTKDaLamChuDePanel extends javax.swing.JPanel {

    private dao.ChuDeDAO chuDeDAO = new dao.ChuDeDAO();
    private dao.KetQuaKiemTraDAO ketQuaKiemTraDAO = new dao.KetQuaKiemTraDAO();
    private List<Object[]> allResults = new ArrayList<>();

    /**
     * Creates new form DanhSachTKDaLamChuDePanel
     */
    public DanhSachTKDaLamChuDePanel() {
        initComponents();
        tintIcons();
        
        // Add action listener to combobox programmatically
        comboboxChuDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxChuDeActionPerformed(evt);
            }
        });

        // Add mouse listener to Search label programmatically
        lblTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblTimKiemMousePressed(evt);
            }
        });

        // Add mouse listener to Sort Alphabet programmatically
        lblSapXepTheoAlphabet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSapXepTheoAlphabetMousePressed(evt);
            }
        });

        // Add mouse listener to Sort Score programmatically
        lblSapXepTheoDiemSo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblSapXepTheoDiemSoMousePressed(evt);
            }
        });

        loadChuDeComboBox();
        loadData();
    }

    private void tintIcons() {
        java.awt.Color tealColor = new java.awt.Color(15, 118, 110);
        lblTimKiem.setIcon(utils.ImageUtils.tintIcon(lblTimKiem.getIcon(), tealColor));
        lblSapXepTheoAlphabet.setIcon(utils.ImageUtils.tintIcon(lblSapXepTheoAlphabet.getIcon(), tealColor));
        lblSapXepTheoDiemSo.setIcon(utils.ImageUtils.tintIcon(lblSapXepTheoDiemSo.getIcon(), tealColor));
    }

    private void loadChuDeComboBox() {
        comboboxChuDe.removeAllItems();
        for (models.ChuDe cd : chuDeDAO.selectAll()) {
            comboboxChuDe.addItem(cd.getTenChuDe());
        }
    }

    public void loadData() {
        if (comboboxChuDe.getSelectedItem() == null) {
            return;
        }
        String tenCD = comboboxChuDe.getSelectedItem().toString();
        jLabel6.setText("Danh sách tài khoản đã làm bài kiểm tra của chủ đề: " + tenCD);
        models.ChuDe cd = chuDeDAO.selectByName(tenCD);
        if (cd != null) {
            allResults = ketQuaKiemTraDAO.selectDetailsByChuDe(cd.getMaChuDe());
            displayResults(allResults);
        }
    }

    private void displayResults(List<Object[]> list) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"STT", "Tên tài khoản", "Điểm số", "Tổng số câu", "Thời điểm làm bài"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        int stt = 1;
        for (Object[] row : list) {
            model.addRow(new Object[] {
                String.format("%02d", stt++),
                row[0], // username
                row[1], // score
                row[2], // total questions
                row[3]  // timestamp
            });
        }
        tableDanhSachTKDaLamChuDe.setModel(model);
    }

    private void comboboxChuDeActionPerformed(java.awt.event.ActionEvent evt) {
        loadData();
    }

    private void lblTimKiemMousePressed(java.awt.event.MouseEvent evt) {
        String keyword = txtTimMaTaiKhoan.getText().trim().toLowerCase();
        List<Object[]> filtered = new ArrayList<>();
        for (Object[] row : allResults) {
            String username = row[0].toString().toLowerCase();
            if (username.contains(keyword)) {
                filtered.add(row);
            }
        }
        displayResults(filtered);
    }

    private void lblSapXepTheoAlphabetMousePressed(java.awt.event.MouseEvent evt) {
        List<Object[]> sorted = new ArrayList<>(allResults);
        sorted.sort((o1, o2) -> o1[0].toString().compareToIgnoreCase(o2[0].toString()));
        displayResults(sorted);
    }

    private void lblSapXepTheoDiemSoMousePressed(java.awt.event.MouseEvent evt) {
        List<Object[]> sorted = new ArrayList<>(allResults);
        sorted.sort((o1, o2) -> {
            int score1 = (int) o1[1];
            int score2 = (int) o2[1];
            return Integer.compare(score2, score1); // Descending (highest score first)
        });
        displayResults(sorted);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboboxChuDe = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDanhSachTKDaLamChuDe = new javax.swing.JTable();
        lblTimKiem = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTimMaTaiKhoan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblSapXepTheoAlphabet = new javax.swing.JLabel();
        lblSapXepTheoDiemSo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Chọn chủ đề:");

        jLabel6.setText("Danh sách tài khoản đã làm bài kiểm tra của chủ đề");

        tableDanhSachTKDaLamChuDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã kết quả", "Mã tài khoản", "Mã chủ đề", "Điểm số", "Tổng số câu", "Thời điểm làm bài"
            }
        ));
        jScrollPane1.setViewportView(tableDanhSachTKDaLamChuDe);

        lblTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/magnifying-glass-solid.png"))); // NOI18N
        lblTimKiem.setText("Tìm kiếm");
        lblTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setText("Tìm mã tài khoản:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Sắp xếp theo tên tài khoản");

        lblSapXepTheoAlphabet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-down-a-z-solid.png"))); // NOI18N
        lblSapXepTheoAlphabet.setText("Sắp xếp");
        lblSapXepTheoAlphabet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblSapXepTheoDiemSo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-down-a-z-solid.png"))); // NOI18N
        lblSapXepTheoDiemSo.setText("Sắp xếp");
        lblSapXepTheoDiemSo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sắp xếp theo điểm số");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(comboboxChuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTimMaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSapXepTheoAlphabet, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSapXepTheoDiemSo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 34, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(122, 122, 122))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(comboboxChuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(80, 80, 80))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(lblSapXepTheoDiemSo)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtTimMaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(55, 55, 55)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTimKiem)
                                        .addComponent(lblSapXepTheoAlphabet)))))
                        .addGap(51, 51, 51)))
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboboxChuDe;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSapXepTheoAlphabet;
    private javax.swing.JLabel lblSapXepTheoDiemSo;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JTable tableDanhSachTKDaLamChuDe;
    private javax.swing.JTextField txtTimMaTaiKhoan;
    // End of variables declaration//GEN-END:variables
}

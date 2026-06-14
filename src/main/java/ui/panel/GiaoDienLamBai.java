/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.panel;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ui.frame.LoginFrame;

/**
 *
 * @author Admin
 */
public class GiaoDienLamBai extends javax.swing.JPanel {

    private int maTaiKhoan;
    private int maChuDe;
    private java.util.List<models.TuVung> testWords = new java.util.ArrayList<>();
    private java.util.List<String[]> questionOptions = new java.util.ArrayList<>(); // 4 options per question
    private String[] userAnswers;
    private int currentQuestionIndex = 0;
    
    private dao.TuVungDAO tuVungDAO = new dao.TuVungDAO();
    private dao.KetQuaKiemTraDAO ketQuaKiemTraDAO = new dao.KetQuaKiemTraDAO();
    private dao.ChuDeDAO chuDeDAO = new dao.ChuDeDAO();

    /**
     * Creates new form GiaoDienLamBai
     */
    public GiaoDienLamBai() {
        initComponents();
        
        java.awt.event.ActionListener radioListener = e -> {
            saveCurrentAnswer();
            int completed = 0;
            for (String ans : userAnswers) {
                if (ans != null) completed++;
            }
            lblSoCauDaHoanThanh.setText(String.valueOf(completed));
        };
        radio1.addActionListener(radioListener);
        radio2.addActionListener(radioListener);
        radio3.addActionListener(radioListener);
        radio4.addActionListener(radioListener);
    }

    public void setupTest(int maTaiKhoan, int maChuDe) {
        this.maTaiKhoan = maTaiKhoan;
        this.maChuDe = maChuDe;
        
        // Show topic name
        models.ChuDe cd = chuDeDAO.selectById(maChuDe);
        if (cd != null) {
            lblChuDe.setText(cd.getTenChuDe());
        }
        
        // Get all words under this topic
        java.util.List<models.TuVung> allWordsInTopic = tuVungDAO.selectByChuDe(maChuDe);
        if (allWordsInTopic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chủ đề này chưa có từ vựng để làm bài kiểm tra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(() -> {
                JDialog frame = (JDialog) SwingUtilities.getWindowAncestor(this);
                if (frame != null) frame.dispose();
            });
            return;
        }
        
        // Pick up to 10 random words as questions
        java.util.Collections.shuffle(allWordsInTopic);
        int totalQuestions = Math.min(10, allWordsInTopic.size());
        testWords = allWordsInTopic.subList(0, totalQuestions);
        
        userAnswers = new String[totalQuestions];
        
        // Fetch all vocabulary in system to get random incorrect options
        java.util.List<models.TuVung> allSystemWords = tuVungDAO.selectAll();
        
        // Generate options for each question
        questionOptions.clear();
        for (models.TuVung correctTv : testWords) {
            java.util.Set<String> optionsSet = new java.util.HashSet<>();
            optionsSet.add(correctTv.getNghiaTiengViet());
            
            // Add incorrect options from entire system
            java.util.List<models.TuVung> pool = new java.util.ArrayList<>(allSystemWords);
            java.util.Collections.shuffle(pool);
            for (models.TuVung tv : pool) {
                if (optionsSet.size() >= 4) break;
                if (!tv.getNghiaTiengViet().equalsIgnoreCase(correctTv.getNghiaTiengViet())) {
                    optionsSet.add(tv.getNghiaTiengViet());
                }
            }
            
            // If still less than 4 (e.g. system has very few words), add placeholders
            while (optionsSet.size() < 4) {
                optionsSet.add("Đáp án " + (optionsSet.size() + 1));
            }
            
            // Convert to list, shuffle, and add to questionOptions
            java.util.List<String> optionsList = new java.util.ArrayList<>(optionsSet);
            java.util.Collections.shuffle(optionsList);
            questionOptions.add(optionsList.toArray(new String[0]));
        }
        
        currentQuestionIndex = 0;
        showQuestion();
    }

    private void showQuestion() {
        if (testWords == null || testWords.isEmpty()) return;
        
        lblSoThuTuCau.setText("Câu hỏi " + (currentQuestionIndex + 1) + "/" + testWords.size());
        
        models.TuVung tv = testWords.get(currentQuestionIndex);
        lblCauHoi.setText("Từ tiếng Anh '" + tv.getTuTiengAnh() + "' có nghĩa là gì?");
        
        String[] options = questionOptions.get(currentQuestionIndex);
        radio1.setText(options[0]);
        radio2.setText(options[1]);
        radio3.setText(options[2]);
        radio4.setText(options[3]);
        
        // Load user's previous answer if any
        DapAn.clearSelection();
        String prevAnswer = userAnswers[currentQuestionIndex];
        if (prevAnswer != null) {
            if (prevAnswer.equals(options[0])) radio1.setSelected(true);
            else if (prevAnswer.equals(options[1])) radio2.setSelected(true);
            else if (prevAnswer.equals(options[2])) radio3.setSelected(true);
            else if (prevAnswer.equals(options[3])) radio4.setSelected(true);
        }
        
        // Update completed count
        int completed = 0;
        for (String ans : userAnswers) {
            if (ans != null) completed++;
        }
        lblSoCauDaHoanThanh.setText(String.valueOf(completed));
    }

    private void saveCurrentAnswer() {
        if (testWords == null || testWords.isEmpty()) return;
        String ans = null;
        if (radio1.isSelected()) ans = radio1.getText();
        else if (radio2.isSelected()) ans = radio2.getText();
        else if (radio3.isSelected()) ans = radio3.getText();
        else if (radio4.isSelected()) ans = radio4.getText();
        
        userAnswers[currentQuestionIndex] = ans;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DapAn = new javax.swing.ButtonGroup();
        lblNopBai = new javax.swing.JLabel();
        lblThoat = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblChuDe = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSoCauDaHoanThanh = new javax.swing.JLabel();
        lblCauHoi = new javax.swing.JLabel();
        radio1 = new javax.swing.JRadioButton();
        radio2 = new javax.swing.JRadioButton();
        radio3 = new javax.swing.JRadioButton();
        radio4 = new javax.swing.JRadioButton();
        lblPrev = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();
        lblSoThuTuCau = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lblNopBai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circle-xmark-solid.png"))); // NOI18N
        lblNopBai.setText("Thoát");
        lblNopBai.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 3, true));
        lblNopBai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNopBai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNopBaiMousePressed(evt);
            }
        });

        lblThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check-solid.png"))); // NOI18N
        lblThoat.setText("Nộp bài");
        lblThoat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 102), 3, true));
        lblThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblThoatMousePressed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 204, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LÀM BÀI KIỂM TRA CỦA CHỦ ĐỀ");
        jLabel1.setOpaque(true);

        jLabel2.setText("Chủ đề: ");

        lblChuDe.setForeground(new java.awt.Color(255, 0, 0));
        lblChuDe.setText("Tên chủ đề");

        jLabel4.setText("Số câu đã hoàn thành");

        lblSoCauDaHoanThanh.setFont(new java.awt.Font("UTM HelvetIns", 0, 36)); // NOI18N
        lblSoCauDaHoanThanh.setForeground(new java.awt.Color(0, 204, 102));
        lblSoCauDaHoanThanh.setText("1");

        lblCauHoi.setText("Câu hỏi");

        DapAn.add(radio1);
        radio1.setText("A / T");
        radio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio1ActionPerformed(evt);
            }
        });

        DapAn.add(radio2);
        radio2.setText("B / F");

        DapAn.add(radio3);
        radio3.setText("C");

        DapAn.add(radio4);
        radio4.setText("D");

        lblPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chevron-left-solid.png"))); // NOI18N
        lblPrev.setText("Câu trước");
        lblPrev.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 102), 3, true));
        lblPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPrevMousePressed(evt);
            }
        });

        lblNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/angle-right-solid.png"))); // NOI18N
        lblNext.setText("Câu kế tiếp");
        lblNext.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 102), 3, true));
        lblNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNextMousePressed(evt);
            }
        });

        lblSoThuTuCau.setFont(new java.awt.Font("UTM HelvetIns", 0, 14)); // NOI18N
        lblSoThuTuCau.setForeground(new java.awt.Color(0, 204, 102));
        lblSoThuTuCau.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblChuDe))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(lblSoCauDaHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblCauHoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radio2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radio1, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                        .addComponent(radio3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radio4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNopBai, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSoThuTuCau, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblChuDe))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblSoCauDaHoanThanh))
                .addGap(36, 36, 36)
                .addComponent(lblSoThuTuCau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCauHoi)
                .addGap(18, 18, 18)
                .addComponent(radio1)
                .addGap(18, 18, 18)
                .addComponent(radio2)
                .addGap(18, 18, 18)
                .addComponent(radio3)
                .addGap(18, 18, 18)
                .addComponent(radio4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNopBai)
                        .addComponent(lblThoat))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPrev)
                        .addComponent(lblNext)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblNopBaiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNopBaiMousePressed
        int chon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có muốn thoát làm bài? Lần làm bài này sẽ không được ghi nhận vào hệ thống.",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (chon == JOptionPane.YES_OPTION) {
            JDialog frame = (JDialog) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();
        }
    }//GEN-LAST:event_lblNopBaiMousePressed

    private void lblThoatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMousePressed
        saveCurrentAnswer();
        
        // Check if all questions are completed
        int completed = 0;
        for (String ans : userAnswers) {
            if (ans != null) completed++;
        }
        
        if (completed < testWords.size()) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn chưa hoàn thành hết tất cả câu hỏi. Bạn vẫn muốn nộp bài chứ?",
                "Xác nhận nộp bài",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn nộp bài kiểm tra?",
                "Nộp bài",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        // Calculate score
        int score = 0;
        for (int i = 0; i < testWords.size(); i++) {
            String correctAns = testWords.get(i).getNghiaTiengViet();
            String userAns = userAnswers[i];
            if (correctAns.equalsIgnoreCase(userAns)) {
                score++;
            }
        }
        
        // Insert test result to DB
        models.KetQuaKiemTra kq = new models.KetQuaKiemTra(
            0,
            maTaiKhoan,
            maChuDe,
            score,
            testWords.size(),
            new java.sql.Timestamp(System.currentTimeMillis())
        );
        
        boolean success = ketQuaKiemTraDAO.insert(kq);
        if (success) {
            double convertedScore = score * 10.0 / testWords.size();
            JOptionPane.showMessageDialog(
                this,
                "Nộp bài thành công!\nSố câu đúng: " + score + "/" + testWords.size() + "\nĐiểm số: " + String.format("%.2f", convertedScore),
                "Kết quả",
                JOptionPane.INFORMATION_MESSAGE
            );
            JDialog frame = (JDialog) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu kết quả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_lblThoatMousePressed

    private void lblPrevMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPrevMousePressed
        saveCurrentAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion();
        }
    }//GEN-LAST:event_lblPrevMousePressed

    private void lblNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNextMousePressed
        saveCurrentAnswer();
        if (currentQuestionIndex < testWords.size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        }
    }//GEN-LAST:event_lblNextMousePressed

    private void radio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio1ActionPerformed
        // Handled via programmatically added ActionListener
    }//GEN-LAST:event_radio1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup DapAn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblCauHoi;
    private javax.swing.JLabel lblChuDe;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblNopBai;
    private javax.swing.JLabel lblPrev;
    private javax.swing.JLabel lblSoCauDaHoanThanh;
    private javax.swing.JLabel lblSoThuTuCau;
    private javax.swing.JLabel lblThoat;
    private javax.swing.JRadioButton radio1;
    private javax.swing.JRadioButton radio2;
    private javax.swing.JRadioButton radio3;
    private javax.swing.JRadioButton radio4;
    // End of variables declaration//GEN-END:variables
}

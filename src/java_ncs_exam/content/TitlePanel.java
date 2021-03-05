package java_ncs_exam.content;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java_ncs_exam.dto.Title;
import java_ncs_exam.exception.EmptyTfException;
import java_ncs_exam.exception.InValidationException;

@SuppressWarnings("serial")
public class TitlePanel extends JPanel {
    private JTextField tfNo;
    private JTextField tfName;

    public TitlePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(0, 2, 20, 10));
        setPreferredSize(new Dimension(450, 200));
        JLabel lblNo = new JLabel("번호");
        lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblNo);

        tfNo = new JTextField();
        tfNo.setColumns(10);
        add(tfNo);

        JLabel lblName = new JLabel("직책");
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblName);
        
        tfName = new JTextField();
        add(tfName);
        tfName.setColumns(10);
    }

    public Title getItem() {
        if (isEmpty()) {
            throw new EmptyTfException("공란 존재");
        }
        if (!isTfValid()) {
            throw new InValidationException("형식이 맞지 않습니다.");
        }
        int no = Integer.parseInt(tfNo.getText().trim());
        String name = tfName.getText().trim();

        return new Title(no, name);
    }

    private boolean isTfValid() {
        String deptNo = tfNo.getText().trim();
        String deptName = tfName.getText().trim();

        boolean noCheck = Pattern.matches("\\d{1,3}", deptNo); // 3자리 숫자만 허용
        boolean nameCheck = Pattern.matches("^[가-힣]*$", deptName);// 한글및 공백허용

        return noCheck && nameCheck;
    }

    public void setItem(Title title) {
        tfNo.setText(String.valueOf(title.getNo()));
        tfName.setText(title.getName());
    }

    public JTextField getTf() {
        return tfNo;
    }

    public void setEditableTf(boolean isEditable) {
        for (Component c : getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setEditable(isEditable);
            }
        }
    }

    boolean isEmpty() {
        for (Component c : getComponents()) {
            if (c instanceof JTextField) {
                return ((JTextField) c).getText().isEmpty();
            }
        }
        return false;
    }
    
    public void clearTf() {
        for (Component c : getComponents()) { 
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            }
        }
    }

}// end of class

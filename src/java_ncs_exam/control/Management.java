package java_ncs_exam.control;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java_ncs_exam.content.TitlePanel;
import java_ncs_exam.content.TitleTable;
import java_ncs_exam.dto.Title;
import java_ncs_exam.exception.EmptyTfException;
import java_ncs_exam.exception.InValidationException;

@SuppressWarnings("serial")
public class Management extends JFrame implements ActionListener {

    private JPanel contentPane;
    private TitlePanel pItemContent;
    private JPanel pBtns;
    private JPanel pList;
    private JButton btnAdd;
    private JButton btnCancel;
    private TitleTable pItemTable;

    private ArrayList<Title> itemList = new ArrayList<>();
    
    public Management() {
        setTitle("직책 관리");
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        pItemContent = new TitlePanel();
        contentPane.add(pItemContent);

        pBtns = new JPanel();
        contentPane.add(pBtns);

        btnAdd = new JButton("추가");
        btnAdd.addActionListener(this);
        pBtns.add(btnAdd);

        btnCancel = new JButton("취소");
        btnCancel.addActionListener(this);
        pBtns.add(btnCancel);

        pList = new JPanel();
        contentPane.add(pList);
        pList.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        pList.add(scrollPane, BorderLayout.CENTER);

        pItemTable = new TitleTable();

        scrollPane.setViewportView(pItemTable);

        JPopupMenu popUpMenu = new PopUpMenu(this);
        pItemTable.setComponentPopupMenu(popUpMenu);
    }

    public void setItemList(ArrayList<Title> itemList) {
        this.itemList = itemList;
        pItemTable.setItems(itemList);
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
                if (e.getActionCommand().equals("취소")) {
                    btnCancelActionPerformed(e);
                }
                if (e.getActionCommand().equals("추가")) {
                    btnAddActionPerformed(e);
                } 
                if (e.getActionCommand().equals("수정")) {
                    btnUpateActionPerformed();
                }
            
                if (e.getActionCommand().equals("부서 수정")) {
                    updateStudent();
                }
                if (e.getActionCommand().equals("부서 삭제")) {
                    deleteStudent();
                }
            
        } catch (EmptyTfException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        } catch (InValidationException ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        }
    }

    private void btnUpateActionPerformed() {
        Title afterTitle = pItemContent.getItem();
        int idx = itemList.indexOf(afterTitle);
        Title beforeTitle = itemList.get(idx);
        itemList.set(idx, afterTitle);
        pItemTable.updateRow(idx, afterTitle);
        btnAdd.setText("추가");
        pItemContent.clearTf();
        pItemContent.getTf().setEditable(true);
        
        JOptionPane.showMessageDialog(null, String.format("%s(%d)이(가) %s(%d)로 변경되었습니다.", beforeTitle.getName(), beforeTitle.getNo(), afterTitle.getName(), afterTitle.getNo()));
    }

    protected void btnAddActionPerformed(ActionEvent e) {
        Title newTitle = pItemContent.getItem();
        itemList.add(newTitle);
        pItemTable.addRow(newTitle);
        pItemContent.clearTf();
        JOptionPane.showMessageDialog(null, String.format("%s(%d)이(가) 추가되었습니다.", newTitle.getName(), newTitle.getNo()));
    }

    protected void btnCancelActionPerformed(ActionEvent e) {
        pItemContent.clearTf();
        if (btnAdd.getText().equals("수정")) {
            btnAdd.setText("추가");
        }
        pItemContent.getTf().setEditable(true);
    }

    private void updateStudent() {
        int idx = pItemTable.getSelectedRow();
        if (idx == -1) {
            JOptionPane.showMessageDialog(null, "해당 직책을 선택하세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Title title = itemList.get(idx);
        pItemContent.setItem(title);
        pItemContent.getTf().setEditable(false);
        btnAdd.setText("수정");
    }

    private void deleteStudent() {
        int idx = pItemTable.getSelectedRow();
        if (idx == -1) {
            JOptionPane.showMessageDialog(null, "해당 직책을 선택하세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Title delTitle = itemList.get(idx);
        itemList.remove(idx);
        pItemTable.removeRow(idx);
        if (btnAdd.getText().equals("수정")) {
            btnAdd.setText("추가");
        }
        JOptionPane.showMessageDialog(null, String.format("%s(%d)이(가) 삭제되었습니다.", delTitle.getName(), delTitle.getNo()));
    }

    private class PopUpMenu extends JPopupMenu {
        
        public PopUpMenu(ActionListener listener) {
            JMenuItem updateItem = new JMenuItem("부서 수정");
            updateItem.addActionListener(listener);
            add(updateItem);

            JMenuItem deleteItem = new JMenuItem("부서 삭제");
            deleteItem.addActionListener(listener);
            add(deleteItem);
        }
    }
}

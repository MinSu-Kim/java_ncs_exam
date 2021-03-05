package java_ncs_exam;

import java.awt.EventQueue;
import java.util.ArrayList;

import java_ncs_exam.control.Management;
import java_ncs_exam.dto.Title;

public class TitleMain {

    public static void main(String[] args) {
        ArrayList<Title> titleList = new ArrayList<>();
        titleList.add(new Title(1, "사장"));
        titleList.add(new Title(2, "부장"));
        titleList.add(new Title(3, "과장"));
        titleList.add(new Title(4, "대리"));
        titleList.add(new Title(5, "사원"));
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Management frame = new Management();
                    frame.setItemList(titleList);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

package java_ncs_exam.content;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java_ncs_exam.dto.Title;

@SuppressWarnings("serial")
public class TitleTable extends JTable {
    private CustomModel model;

    public TitleTable() {
        initialize();
    }

    private void initialize() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void setItems(ArrayList<Title> items) {
        loadData(items);
    }

    public void loadData(ArrayList<Title> items) {
        model = new CustomModel(getRows(items), getColNames());
        setModel(model);

        setWidthAlign();
        
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        setRowSorter(sorter);
    }
    
    String[] getColNames() {
        return new String[] {"번호", "직책"};
    }

    Object[] toArray(Title item) {
        return new Object[] {String.format("T%03d",item.getNo()), item.getName()};
    }

    void setWidthAlign() {
        tableSetWidth(150, 250);
        tableCellAlign(SwingConstants.CENTER, 0, 1);        
    }

    protected void tableCellAlign(int align, int...idx) {
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(align);
        
        TableColumnModel cModel = getColumnModel();
        for(int i=0; i<idx.length; i++) {
            cModel.getColumn(idx[i]).setCellRenderer(dtcr);
        }
    }
    
    protected void tableSetWidth(int...width) {
        TableColumnModel cModel = getColumnModel();
        for(int i=0; i<width.length; i++) {
            cModel.getColumn(i).setPreferredWidth(width[i]);
        }
    }
    
    private Object[][] getRows(ArrayList<Title> items) {
        Object[][] rows = new Object[items.size()][];
        for(int i=0; i<rows.length; i++) {
            rows[i] = toArray(items.get(i));
        }
        return rows;
    }
    
    public void addRow(Title item) {
        model.addRow(toArray(item));
    }
    
    public void removeRow(int idx){
        model.removeRow(idx);
    }
    
    public void updateRow(int idx, Title item) {
        model.removeRow(idx);
        model.insertRow(idx, toArray(item));
        clearSelection();
    }
    
    private class CustomModel extends DefaultTableModel {
        public CustomModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
}

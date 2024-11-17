package src.Model;

import src.ViewModel.DataTableModel;

public interface TablePanelObserver {

    void updateData(DataTableModel model);

    void setData(DataTableModel model);
}

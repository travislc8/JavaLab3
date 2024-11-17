package src.Model;

import src.ViewModel.DataTableModel;

public interface FilterPanelObserver {

    void updateData(DataTableModel model);

    void setData(DataTableModel model);
}

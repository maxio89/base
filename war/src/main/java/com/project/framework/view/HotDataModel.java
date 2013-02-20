package com.project.framework.view;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;
import java.util.List;

public abstract class HotDataModel<E> extends DataModel<E> {
// ------------------------------ FIELDS ------------------------------

    // The current row index (zero relative)
    private int index = -1;

// -------------------------- OTHER METHODS --------------------------

    @Override
    public int getRowCount()
    {
        if (getList() == null) {
            return (-1);
        }
        return (getList().size());
    }

    @Override
    public E getRowData()
    {
        if (getList() == null) {
            return (null);
        } else if (!isRowAvailable()) {
            throw new RuntimeException("No row available");
        } else {
            return getList().get(index);
        }
    }

    public int getRowIndex()
    {
        return (index);
    }

    @Override
    public Object getWrappedData()
    {
        return null;
    }

    @Override
    public boolean isRowAvailable()
    {
        if (getList() == null) {
            return (false);
        } else if ((index >= 0) && (index < getList().size())) {
            return (true);
        } else {
            return (false);
        }
    }

    @Override
    public void setRowIndex(int rowIndex)
    {
        if (rowIndex < -1) {
            throw new IllegalArgumentException();
        }
        int old = index;
        index = rowIndex;
        if (getList() == null) {
            return;
        }
        DataModelListener[] listeners = getDataModelListeners();
        if ((old != index) && (listeners != null)) {
            Object rowData = null;
            if (isRowAvailable()) {
                rowData = getRowData();
            }
            DataModelEvent event = new DataModelEvent(this, index, rowData);
            for (DataModelListener listener : listeners) {
                if (null != listener) {
                    listener.rowSelected(event);
                }
            }
        }
    }

    @Override
    public void setWrappedData(Object data)
    {
        throw new IllegalArgumentException("Setting wrapped data is not supported");
    }

    protected abstract List<E> getList();
}

package ru.lanit.steps.types;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;

import java.util.List;
import java.util.stream.IntStream;

import static ru.lanit.utils.FunctionUtils.replaceFunction;

public class DataTableTypes {

    @DataTableType
    public SmartMapContainer mapContainer(DataTable dataTable) {
        SmartMapContainer smartMap = new SmartMapContainer();
        List<String> dataList = dataTable.values();
        IntStream.range(0, dataList.size() - 1).forEach(ind -> smartMap.getSmartMap().put(dataList.get(ind), replaceFunction(dataList.get(ind + 1))));
        return smartMap;
    }
}

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
        IntStream.iterate(0, i -> i < dataList.size() - 1, i -> i + 2)
                .forEach(i -> smartMap.getSmartMap().put(dataList.get(i), replaceFunction(dataList.get(i + 1))));
        return smartMap;
    }
}

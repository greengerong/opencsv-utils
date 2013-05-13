package opencsv.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CsvColumnMapping implements ICsvColumnMapping {

    private Map<String, String> columnMapping;
    private final Class type;

    public CsvColumnMapping(Class type) {
        this.type = type;
    }

    @Override
    public Map<String, String> getColumnMapping() {
        if (columnMapping == null) {
            columnMapping = innerGetColumnMapping(type);
        }
        return columnMapping;
    }

    private Map<String, String> innerGetColumnMapping(Class type) {
        Map<String, String> mapping = new HashMap<String, String>();
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if (field.getAnnotation(Ignore.class) == null) {
                String header = field.getName();
                Csv csv = field.getAnnotation(Csv.class);
                if (csv != null) {
                    header = csv.value();
                }
                mapping.put(header, field.getName());
            }
        }

        return mapping;
    }
}
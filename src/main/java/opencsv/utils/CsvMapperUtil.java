package opencsv.utils;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CsvMapperUtil {

    private static final Map<String, CsvColumnMapping> columnMap = new HashMap<String, CsvColumnMapping>();

    private CsvMapperUtil() {

    }

    public static <T> List<T> fromCsv(Class<T> type, Reader reader) {

        return new CsvMapper<T>(type).withMapping(getColumnMap(type)).fromCsv(reader);
    }

    private static synchronized <T> CsvColumnMapping getColumnMap(Class<T> type) {
        final String key = type.getName();

        if (!columnMap.containsKey(key)) {
            columnMap.put(key, new CsvColumnMapping(type));
        }

        return columnMap.get(key);
    }

    public static <T> void toCsv(Class<T> type, List<T> list, Writer writer) throws Exception {
        new CsvMapper<T>(type).withMapping(getColumnMap(type)).toCsv(writer, list);
    }

    public static void Dispose() {
        columnMap.clear();
    }
}
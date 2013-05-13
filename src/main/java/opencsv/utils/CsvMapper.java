package opencsv.utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvMapper<T> {

    private final Map<String, String> columnMapping = new HashMap<String, String>();
    private final Class<T> type;

    public CsvMapper(Class<T> type) {
        this.type = type;
    }

    public CsvMapper<T> withMapping(Map<String, String> mapping) {
        this.columnMapping.putAll(mapping);
        return this;
    }

    public CsvMapper<T> withMapping(ICsvColumnMapping mapping) {
        return withMapping(mapping.getColumnMapping());
    }

    public CsvMapper<T> withMapping(String header, String property) {
        this.columnMapping.put(header, property);
        return this;
    }

    public List<T> fromCsv(Reader reader) {

        HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        strategy.setType(type);
        strategy.setColumnMapping(columnMapping);

        List<T> list = new ArrayList<T>();
        try {
            list = new CsvToBean<T>().parse(strategy, new CSVReader(reader));
        } catch (Exception ex) {
            throw new CsvMapperRuntimeException(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (Exception ex) {
                    throw new CsvMapperRuntimeException("Close the reader failed.", ex);
                }
            }
        }
        return list;
    }
}
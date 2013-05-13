package opencsv.utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public class CsvMapper<T> implements ICsvMapper<T> {

    private final Map<String, String> columnMapping = new HashMap<String, String>();
    private final HashMap<String, Field> fieldMap = new HashMap<String, Field>();
    private final Class<T> type;

    public CsvMapper(Class<T> type) {
        this.type = type;
    }

    @Override
    public ICsvMapper<T> withMapping(Map<String, String> mapping) {
        this.columnMapping.putAll(mapping);
        return this;
    }

    @Override
    public ICsvMapper<T> withMapping(ICsvColumnMapping mapping) {
        return withMapping(mapping.getColumnMapping());
    }

    @Override
    public ICsvMapper<T> withMapping(String header, String property) {
        this.columnMapping.put(header, property);
        return this;
    }

    @Override
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

    @Override
    public void toCsv(Writer writer, List<T> list) throws Exception {
        CSVWriter csvWriter = new CSVWriter(writer, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER);
        final List<String[]> lines = new ArrayList<String[]>();
        for (T item : list) {
            lines.add(getLine(item));
        }
        csvWriter.writeNext(columnMapping.keySet().toArray(new String[0]));
        csvWriter.writeAll(lines);
        csvWriter.flush();
        csvWriter.close();
    }

    private String[] getLine(T item) throws Exception {
        final List<String> list = new ArrayList<String>();
        for (String key : columnMapping.keySet()) {
            if (!fieldMap.containsKey(key)) {
                final String fieldName = columnMapping.get(key);
                fieldMap.put(key, item.getClass().getDeclaredField(fieldName));
            }
            Field field = fieldMap.get(key);
            field.setAccessible(true);
            final Object value = field.get(item);
            list.add(value == null ? "" : value.toString());
        }
        return list.toArray(new String[0]);
    }

}
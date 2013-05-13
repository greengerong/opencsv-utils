package opencsv.utils;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 13-5-13
 * Time: 下午10:01
 * To change this template use File | Settings | File Templates.
 */
public interface ICsvMapper<T> {
    ICsvMapper<T> withMapping(Map<String, String> mapping);

    ICsvMapper<T> withMapping(ICsvColumnMapping mapping);

    ICsvMapper<T> withMapping(String header, String property);

    List<T> fromCsv(Reader reader);

    void toCsv(Writer writer, List<T> list) throws Exception;
}

package opencsv.utils;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CsvColumnMappingTest {
    @Test
    public void shouldGetColumnMapping() throws Exception {
        final ICsvColumnMapping csvColumnMapping = new CsvColumnMapping(Person.class);
        final Map<String, String> columnMapping = csvColumnMapping.getColumnMapping();

        assertThat(columnMapping.get("id"), is("id"));
        assertThat(columnMapping.get("person name"), is("name"));
        assertThat(columnMapping.containsValue("age"), is(false));
    }
}

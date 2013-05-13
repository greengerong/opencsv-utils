package opencsv.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CsvMapperTest {

    private CsvMapper<Person> personCsvMapper;

    @Before
    public void setUp() throws Exception {
        personCsvMapper = new CsvMapper<Person>(Person.class);
    }

    @Test
    public void shouldGetPersonFromCSV() throws Exception {
        StringReader reader = new StringReader("id,person name\n1,name1\n2,name2\n");
        List<Person> personList = personCsvMapper
                .withMapping("id", "id")
                .withMapping("person name", "name")
                .fromCsv(reader);

        assertThat(personList.size(), is(2));

        final Person first = personList.get(0);
        assertThat(first.getId(), is(1));
        assertThat(first.getName(), is("name1"));

        final Person second = personList.get(1);
        assertThat(second.getId(), is(2));
        assertThat(second.getName(), is("name2"));

    }

    @Test(expected = CsvMapperRuntimeException.class)
    public void shouldThrowRuntimeException() {
        List<Person> personList = personCsvMapper.fromCsv(null);

    }

    @Test
    public void shouldToCsv() throws Exception {
        personCsvMapper.withMapping(new CsvColumnMapping(Person.class));
        final ArrayList<Person> list = new ArrayList<Person>();
        list.add(new Person(1, "name1", 20));
        list.add(new Person(2, "name2", 30));
        final StringWriter writer = new StringWriter();

        personCsvMapper.toCsv(writer, list);

        final String text = writer.toString();
        assertThat(text, is("id,person name\n1,name1\n2,name2\n"));
    }

}

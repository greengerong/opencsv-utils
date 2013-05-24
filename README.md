build status: [![Build Status](https://travis-ci.org/greengerong/opencsv-utils.png?branch=master)](https://travis-ci.org/greengerong/opencsv-utils)

depends:

     <dependency>
      <groupId>com.github.greengerong</groupId>
      <artifactId>opencsv.utils</artifactId>
      <version>1.1</version>
    </dependency>
===============================
public class Person {

    private int id;

    @Csv("person name")
    private String name;

    @Ignore
    private int age;

    ............
 }

 For person object mapping will be :

 id  => id

 name => person name

 It will ignore age field.

 ===============================
Also util for map:

 CsvMapperUtil :

 1:public <T> List<T> fromCsv(Class<T> type, Reader reader)

 2:public <T> void toCsv(Class<T> type, List<T> list, Writer writer) throws Exception

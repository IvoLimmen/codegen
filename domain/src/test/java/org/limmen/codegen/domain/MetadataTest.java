package org.limmen.codegen.domain;

import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.Test;

public class MetadataTest {

   @Test
   public void testSomeMethod() throws JAXBException {

      Metadata m = new Metadata();

      m.setName("test");
      m.addCodeTemplate(ct("sql.vm", "sql", false));
      m.addCodeTemplate(ct("java.vm", "java", true));
      m.getCodeTemplates().get(1).addOption("java.package", "com.example.domain");

      PropertySet set1 = new PropertySet();
      set1.setName("person");
      set1.addProperty(p("name", "String"));
      set1.addProperty(p("length", "Integer"));

      m.setPropertySets(Arrays.asList(set1));

      JAXBContext context = JAXBContext.newInstance(Metadata.class);
      context.createMarshaller().marshal(m, System.out);
   }

   private CodeTemplate ct(String fileName, String ext, boolean perfile) {
      CodeTemplate ct = new CodeTemplate();
      ct.setExtention(ext);
      ct.setFileName(fileName);
      ct.setFilePerSet(perfile);
      return ct;
   }

   private Property p(String name) {
      return p(name, "Object", false);
   }

   private Property p(String name, String type) {
      return p(name, type, false);
   }

   private Property p(String name, String type, boolean req) {
      Property p = new Property();
      p.setName(name);
      p.setType(type);
      p.setRequired(req);
      return p;
   }
}

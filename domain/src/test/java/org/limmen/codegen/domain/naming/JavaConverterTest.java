package org.limmen.codegen.domain.naming;

import org.limmen.codegen.domain.naming.JavaConverter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JavaConverterTest {

   private final JavaConverter fixture = new JavaConverter();
   
   @Test
   public void javaField() {
      assertEquals("myTest", fixture.getProperty("my_test"));
      assertEquals("test", fixture.getProperty("test"));
   }

   @Test
   public void javaClass() {
      assertEquals("MyTest", fixture.getObject("my_test"));
      assertEquals("Test", fixture.getObject("test"));
   }
}

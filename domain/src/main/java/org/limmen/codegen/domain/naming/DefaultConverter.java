package org.limmen.codegen.domain.naming;

public class DefaultConverter implements Converter {

   @Override
   public String getObject(String name) {
      return name;
   }

   @Override
   public String getProperty(String name) {
      return name;
   }

   @Override
   public String getType(String name) {
      return name;
   }
}

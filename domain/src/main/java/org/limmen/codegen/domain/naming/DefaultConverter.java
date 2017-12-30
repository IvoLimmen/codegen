package org.limmen.codegen.domain.naming;

import org.limmen.codegen.domain.naming.Converter;

public class DefaultConverter implements Converter {

   @Override
   public String getObject(String name) {
      return name;
   }

   @Override
   public String getProperty(String name) {
      return name;
   }
}

package org.limmen.codegen.domain.naming;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JavaConverter implements Converter {

   @Override
   public String getObject(String name) {
      if (!name.contains("_")) {
         return name.substring(0, 1).toUpperCase().concat(name.substring(1).toLowerCase());
      }

      String[] parts = name.split("_");
      return Arrays.asList(parts).stream().map((t) -> {
         if (t.length() == 1) {
            return t;
         }
         return t.substring(0, 1).toUpperCase().concat(t.substring(1).toLowerCase());
      }).collect(Collectors.joining(""));
   }

   @Override
   public String getProperty(String name) {
      String newName = getObject(name);
      return newName.substring(0, 1).toLowerCase().concat(newName.substring(1));
   }
}

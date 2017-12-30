package org.limmen.codegen.domain.naming;

public interface Converter {

   String getObject(String name);
   
   String getProperty(String name);
}

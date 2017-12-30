package org.limmen.codegen.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

   private String description;

   private String name;

   private boolean required;

   private String type;

   public String getDescription() {
      return description;
   }

   public String getName() {
      return name;
   }

   public String getType() {
      return type;
   }

   public boolean isRequired() {
      return required;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setRequired(boolean required) {
      this.required = required;
   }

   public void setType(String type) {
      this.type = type;
   }
}

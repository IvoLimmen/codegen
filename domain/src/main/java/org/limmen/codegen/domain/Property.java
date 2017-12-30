package org.limmen.codegen.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import org.limmen.codegen.domain.naming.Converter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

   @XmlTransient
   private Converter converter;

   private String description;

   private String name;

   private boolean required;

   private String type;

   public String getClassName() {
      return this.converter.getObject(getName());
   }

   public Converter getConverter() {
      return converter;
   }

   public String getDescription() {
      return description;
   }

   public String getFieldName() {
      return this.converter.getProperty(getName());
   }

   public String getJavaType() {
      return this.converter.getType(getType());
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

   public void setConverter(Converter converter) {
      this.converter = converter;
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

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

   @XmlTransient
   private PropertySet propertySet;

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
      PropertySet ps = this.getPropertySet().getMetadata().getPropertySetByName(getType());
      if (ps == null) {
         return this.converter.getType(getType());
      }

      return this.converter.getObject(getType());
   }

   public String getName() {
      return name;
   }

   public PropertySet getPropertySet() {
      return propertySet;
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

   public void setPropertySet(PropertySet propertySet) {
      this.propertySet = propertySet;
   }

   public void setRequired(boolean required) {
      this.required = required;
   }

   public void setType(String type) {
      this.type = type;
   }
}

package org.limmen.codegen.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import org.limmen.codegen.domain.naming.Converter;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertySet {

   @XmlTransient
   private Converter converter;

   private String description;

   private String name;

   private List<Property> properties = new ArrayList<>();

   public void addProperty(Property property) {
      this.properties.add(property);
   }

   public Converter getConverter() {
      return converter;
   }

   public String getDescription() {
      return description;
   }

   public String getJavaName() {
      return this.converter.getObject(getName());
   }

   public String getName() {
      return name;
   }

   public List<Property> getProperties() {
      return properties;
   }

   public void setConverter(Converter converter) {
      this.converter = converter;
      this.properties.forEach(p -> p.setConverter(converter));
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setProperties(List<Property> properties) {
      this.properties = properties;
   }
}

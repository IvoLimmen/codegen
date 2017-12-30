package org.limmen.codegen.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertySet {

   private String description;

   private String name;

   private List<Property> properties = new ArrayList<>();
   
   public void addProperty(Property property) {
      this.properties.add(property);
   }

   public String getDescription() {
      return description;
   }

   public String getName() {
      return name;
   }

   public List<Property> getProperties() {
      return properties;
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

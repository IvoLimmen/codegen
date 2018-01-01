package org.limmen.codegen.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import org.limmen.codegen.domain.naming.Converter;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertySet {

   @XmlTransient
   private Converter converter;

   private String description;

   @XmlTransient
   private Metadata metadata;

   private String name;

   @XmlElement(name = "property")
   private List<Property> properties = new ArrayList<>();

   public void addProperty(Property property) {
      property.setPropertySet(this);
      this.properties.add(property);
   }

   public String getClassName() {
      return this.converter.getObject(getName());
   }

   public Converter getConverter() {
      return converter;
   }

   public String getDescription() {
      return description;
   }

   public Metadata getMetadata() {
      return metadata;
   }

   public String getName() {
      return name;
   }

   public List<Property> getProperties() {
      return properties;
   }

   public void init() {
      this.properties.forEach(p -> p.setPropertySet(this));
   }

   public void setConverter(Converter converter) {
      this.converter = converter;
      this.properties.forEach(p -> p.setConverter(converter));
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setMetadata(Metadata metadata) {
      this.metadata = metadata;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setProperties(List<Property> properties) {
      this.properties = properties;
      this.properties.forEach(p -> p.setPropertySet(this));
   }
}

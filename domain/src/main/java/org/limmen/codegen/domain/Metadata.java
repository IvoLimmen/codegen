package org.limmen.codegen.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Metadata {

   private List<CodeTemplate> codeTemplates = new ArrayList<>();

   private String name;

   private List<PropertySet> propertySets = new ArrayList<>();

   public void init() {
      this.propertySets.forEach(p -> p.setMetadata(this));
      this.propertySets.forEach(p -> p.init());
   }
   
   public void addCodeTemplate(CodeTemplate codeTemplate) {
      this.codeTemplates.add(codeTemplate);
   }

   public void addPropertySet(PropertySet propertySet) {
      propertySet.setMetadata(this);
      this.propertySets.add(propertySet);
   }

   public List<CodeTemplate> getCodeTemplates() {
      return codeTemplates;
   }

   public String getName() {
      return name;
   }

   public PropertySet getPropertySetByName(String name) {
      return this.propertySets.stream()
          .filter(f -> f.getName().equalsIgnoreCase(name))
          .findFirst()
          .orElse(null);
   }

   public List<PropertySet> getPropertySets() {
      return propertySets;
   }

   public void setCodeTemplates(List<CodeTemplate> codeTemplates) {
      this.codeTemplates = codeTemplates;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setPropertySets(List<PropertySet> propertySets) {
      this.propertySets = propertySets;
      this.propertySets.forEach(p -> p.setMetadata(this));
   }
}

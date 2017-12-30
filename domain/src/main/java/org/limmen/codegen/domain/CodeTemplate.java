package org.limmen.codegen.domain;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodeTemplate {

   private String extention;

   private String fileName;

   private boolean filePerSet;

   private Map<String, String> options = new HashMap<>();

   public void addOption(String key, String value) {
      this.options.put(key, value);
   }

   public String getExtention() {
      return extention;
   }

   public String getFileName() {
      return fileName;
   }

   public Map<String, String> getOptions() {
      return options;
   }

   public boolean isFilePerSet() {
      return filePerSet;
   }

   public void setExtention(String extention) {
      this.extention = extention;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public void setFilePerSet(boolean filePerSet) {
      this.filePerSet = filePerSet;
   }

   public void setOptions(Map<String, String> options) {
      this.options = options;
   }
}

package org.limmen.codegen.cli;

import java.io.File;

public class Settings {

   private String applicationName;

   private String basedir;

   private String file;

   public String getApplicationName() {
      return applicationName;
   }

   public String getBasedir() {
      return basedir;
   }

   public String getFile() {
      return file;
   }

   public String getOutputDir() {
      File baseDir = new File(basedir);
      baseDir = new File(baseDir, "output");

      return baseDir.getAbsolutePath();
   }

   public String getOutputFile(String name, String ext) {
      File baseDir = new File(getOutputDir());
      baseDir = new File(baseDir, name + "." + ext);

      return baseDir.getAbsolutePath();
   }

   public String getTemplateDir() {
      File baseDir = new File(basedir);
      baseDir = new File(baseDir, "data");

      return baseDir.getAbsolutePath();
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public void setBasedir(String basedir) {
      this.basedir = basedir;
   }

   public void setFile(String file) {
      this.file = file;
   }
}

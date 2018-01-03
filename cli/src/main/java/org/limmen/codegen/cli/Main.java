package org.limmen.codegen.cli;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.limmen.codegen.domain.CodeTemplate;
import org.limmen.codegen.domain.Metadata;
import org.limmen.codegen.domain.naming.Converter;
import org.limmen.codegen.domain.naming.DefaultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

   private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) throws Exception {

      if (args == null || args.length == 0) {
         throw new RuntimeException("Requires file to execute");
      }

      Settings settings = new Settings();
      settings.setBasedir(System.getProperty("app.home"));
      settings.setApplicationName(System.getProperty("app.name"));
      settings.setFile(args[0]);

      new Main(settings);
   }
   private final Settings settings;

   public Main(Settings settings) throws JAXBException, IOException {
      this.settings = settings;
      Metadata metadata = readMetadata(settings.getFile());

      metadata.init();
      
      Properties properties = new Properties();
      properties.put(Velocity.RESOURCE_LOADER, "file");
      properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, settings.getTemplateDir());
      Velocity.init(properties);

      metadata.getCodeTemplates().forEach(ct -> {
         executeTemplate(ct, metadata);
      });
   }

   private void executeTemplate(CodeTemplate codeTemplate, Metadata metadata) {
      LOGGER.info("Executing template '{}'...", codeTemplate.getName());

      LOGGER.debug("Options '{}'...", codeTemplate.getOptions());

      Template template = Velocity.getTemplate(codeTemplate.getFileName());

      Converter converter = getConverter(codeTemplate);

      VelocityContext context = new VelocityContext();
      context.put("metadata", metadata);
      context.put("template", codeTemplate);
      context.put("options", codeTemplate.getOptions());
      context.put("converter", converter);

      if (codeTemplate.isFilePerSet()) {
         metadata.getPropertySets().forEach(ps -> {
            context.put("propertySet", ps);
            ps.setConverter(converter);
            StringWriter sw = new StringWriter();
            template.merge(context, sw);

            writeFile(this.settings.getOutputFile(converter.getObject(ps.getName()), codeTemplate.getExtention()), sw);
         });
      } else {
         StringWriter sw = new StringWriter();
         template.merge(context, sw);

         writeFile(this.settings.getOutputFile(metadata.getName(), codeTemplate.getExtention()), sw);
      }

      // find and execute the support files.
      settings.getSupportFiles(codeTemplate.getName()).forEach(s -> {
         Template tpl = Velocity.getTemplate(settings.getSupportFile(codeTemplate.getName(), s));

         StringWriter sw = new StringWriter();
         tpl.merge(context, sw);

         writeFile(this.settings.getOutputFile(getSupportFileName(s), codeTemplate.getExtention()), sw);
      });
   }

   private Converter getConverter(CodeTemplate codeTemplate) {
      if (codeTemplate.getConverterClassName() != null) {
         try {
            return (Converter) Class.forName(codeTemplate.getConverterClassName()).getConstructor(new Class[0]).newInstance(new Object[0]);
         }
         catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            LOGGER.error("Failed to create converter: {}", codeTemplate.getConverterClassName(), ex);
         }
      }

      return new DefaultConverter();
   }

   private String getSupportFileName(String fileName) {
      if (fileName.endsWith(".vm")) {
         fileName = fileName.substring(0, fileName.length() - 3);
      }
      return fileName;
   }

   private Metadata readMetadata(String file) throws JAXBException {
      try {
         JAXBContext context = JAXBContext.newInstance(Metadata.class);
         Unmarshaller unmarshaller = context.createUnmarshaller();
         JAXBElement<Metadata> element = unmarshaller.unmarshal(new StreamSource(new File(file)), Metadata.class);
         return element.getValue();
      }
      catch (JAXBException je) {
         LOGGER.error("Failed to parse metadata file '{}'", file, je);
         throw je;
      }
   }

   private void writeFile(String fileName, StringWriter data) {
      Path file = Paths.get(fileName);

      try {
         Files.createDirectories(file.getParent());

         Files.write(
             file,
             data.getBuffer().toString().getBytes(),
             StandardOpenOption.CREATE);
      }
      catch (IOException ex) {
         LOGGER.error("Failed to write file! File={}", fileName, ex);
      }
   }
}

package org.limmen.codegen.cli;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

   private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
   
   private final Settings settings;

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

   public Main(Settings settings) throws JAXBException, IOException {
      this.settings = settings;
      Metadata metadata = readMetadata(settings.getFile());

      Properties properties = new Properties();
      properties.put(Velocity.RESOURCE_LOADER, "file");
      properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, settings.getTemplateDir());
      Velocity.init(properties);

      metadata.getCodeTemplates().forEach(ct -> {
         executeTemplate(ct, metadata);
      });
   }

   private void executeTemplate(CodeTemplate codeTemplate, Metadata metadata) {
      LOGGER.info("Executing template '{}'...", codeTemplate.getFileName());

      LOGGER.debug("Options '{}'...", codeTemplate.getOptions());
      
      Template template = Velocity.getTemplate(codeTemplate.getFileName());

      VelocityContext context = new VelocityContext();
      context.put("metadata", metadata);
      context.put("template", codeTemplate);
      context.put("options", codeTemplate.getOptions());

      if (codeTemplate.isFilePerSet()) {
         metadata.getPropertySets().forEach(ps -> {
            context.put("propertySet", ps);
            StringWriter sw = new StringWriter();
            template.merge(context, sw);

            writeFile(this.settings.getOutputFile(ps.getName(), codeTemplate.getExtention()), sw);
         });
      } else {
         StringWriter sw = new StringWriter();
         template.merge(context, sw);

         writeFile(this.settings.getOutputFile(metadata.getName(), codeTemplate.getExtention()), sw);
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
         ex.printStackTrace();
      }
   }

   private Metadata readMetadata(String file) throws JAXBException {
      JAXBContext context = JAXBContext.newInstance(Metadata.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      JAXBElement<Metadata> element = unmarshaller.unmarshal(new StreamSource(new File(file)), Metadata.class);
      return element.getValue();
   }
}

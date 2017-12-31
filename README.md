# README

This is a small and simple code generator written in Java and uses the following technologies:

 * JAXB 2.3.0
 * Apache Velocity 2.0

# Builing from source

Make sure you have:

* JDK 8
* Maven 3.5.0

Check out the source and typ:

    mvn clean install

This will create a directory called `appassembler` in the target directory of the `cli` mopdule. This is the application 
directory. You can also run the application from the root by typing:

    ./cli/target/appassembler/bin/codegen data/example.xml

This will start codegen with the example located in the data directory. If will use the data files from 
`./cli/target/appassembler/data` and the output will be in `./cli/target/appassembler/output`.

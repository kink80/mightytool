## Mighty tool

Simple yet extendable reader/writer multipexer. The goal is to provide framework that can be conveniently configured to 
read a source, transform the input and pass it to a destination. A typical use case is content migration, where files
in one location are read in parallel and moved to another location.

### Building your tool
1. Reader:
Provide your implementation of a reader by extending TupleReader, don't forget to give it a suitable bean name through 
standard Spring annotation (Service, Component), also make sure the component you're creating is a prototype so multiple
different instances can be created. See EndlessReader as an example how to implement it.

2. Writer:
Similarly, a writer extends TupleWriter, must be registered under a certain name and must of prototype nature.

3. Build the tool:
Run mvn clean package to build your jar.

4. Configuration
Put together a configuration file, similar to one included in this project. Reader, writer and router sections are 
mandatory as well as parallelism and bean name for reader/writer section. Parallelism drives the number of instances
deployed for the tool, bean name should match the names you have provided for your reader (or writer respectively).
Save it to a file of your preference, make sure it ends with a *.conf* suffix

###Running the tool
Once you have completed all the steps from the previous section run
``java -jar mightytool-1.0-SNAPSHOT.jar /path/to/config/app.conf``




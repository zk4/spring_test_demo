# run:
# 	javac src/main/java/com/zk/App.java
# 	java -cp ./src/main/java com.zk.App


package:
	@echo "take the compiled code and package it in its distributable format, such as a JAR."
	mvn package

test:
	mvn test

run: run_one_jar

run_one_jar:
	mvn assembly:assembly -DdescriptorId=jar-with-dependencies
	java -jar target/*.jar

run_jar_with_lib:
	mvn clean package
	java -jar target/Crunchify/*.jar


wrun:
	watchexec -rce java "make run"

install:
	@echo "install the package into the local repository, for use as a dependency in other projects locally."
	mvn install


clean:
	mvn clean

effective_pom:
	mvn help:effective-pom

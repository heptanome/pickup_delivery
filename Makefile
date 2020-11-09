JAR=PickupDelivery.jar

all: clean build

jar: all
	cd bin && jar cfm $(JAR) ../Manifest */*.class \
	view/graphical/*.class controller/*/*.class
	mv bin/$(JAR) .

build:
	javac -d bin src/controller/*.java src/controller/state/*.java \
		src/controller/command/*.java src/model/*.java src/view/*.java \
		src/view/graphical/*.java src/tsp/*.java

build_test:
	javac -cp .deps/apiguardian-api-1.1.0.jar:.deps/junit-jupiter-api-5.7.0.jar:.deps/mockito-all-2.0.2-beta.jar:. \
		-d bin src/controller/*.java src/controller/state/*.java \
		src/controller/command/*.java	src/model/*.java src/view/*.java \
		src/view/graphical/*.java src/tsp/*.java src/tests/*.java

run_test: build_test
	java -jar .deps/junit-platform-console-standalone-1.7.0.jar --classpath \
		bin:.deps/mockito-all-2.0.2-beta.jar --scan-classpath

doc:
	javadoc src/*/*.java src/*/*/*.java -cp .deps/junit-platform-console-standalone-1.7.0.jar:.deps/mockito-all-2.0.2-beta.jar:. -d doc

clean:
	rm -rf bin doc

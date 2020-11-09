JAR=PickupDelivery.jar

all: build

jar: all
	cd bin && jar cfm $(JAR) ../Manifest */*.class \
	view/graphical/*.class
	mv bin/$(JAR) .

build:
	javac -d bin src/controller/*.java src/model/*.java src/view/*.java src/tsp/*.java src/view/graphical/*.java

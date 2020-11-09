# Pickup & delivery

## Authors

	-	BATEL Arthur
	- BODELOT Paul
	- BUONOMO Fanny
	- GUERRAOUI Camélia
	- KESSIBI Guillaume
	- PELTIER Camille
	- ZIEGER Luise

## The project

Our team has been assigned the "Pickup \& Delivery" project. We will design and
implement an application for optimising delivery tours in cities. The developed
application will load an XML file describing a city map. This file contains a
list of all intersections together with the list of all road segments. The
application needs to display the map once it is loaded.

Once a city map is loaded, the application takes in another XML file describing
the address, the time of departure and a list of requests such that each request
is composed of a pickup address, a delivery address and the duration of the
delivery.

## How to build

Simply run `make` or open the project using eclipse and press the run button.

It is also possible to `make jar` to create a java jar file and then launch it
with `jar -jar PickupDelivery.jar`.

## Technical notes

This project follows the MVC design pattern. Most of the computation (except for
computing a tour) is done in the model. The view solely displays the information
it is given and the controller is a bridge inbetween.

## Tests

The goal with these tests is to test each component (class) independantly from
the others. When necessary, we used mocks to mimick a component's behavior.
The tests are written using Junit.

## Limits of the project

- The UI's size is fixed and has not been thought to be extendable. Overall, it
has not been thought out to be user-friendly and would need some more time
- In the tour, we only display the intersections' number, not the street's name
that go with it

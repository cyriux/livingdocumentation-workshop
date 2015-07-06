Feature: Fuel Card Transactions anomalies
In order to detect potential fuel card abnormal behavior by drivers
As a fleet manager
I want to automatically detect anomalies in all fuel card transactions 

Description:
The monitoring detects the following anomalies:
* Fuel leakage: whenever capacity > 1 + tolerance, where capacity = transaction fuel quantity / vehicle tank size
* Transaction away from vehicle: whenever distance to vehicle > threshold, 
where distance to vehicle = geo-distance (vehicle coordinates, gas station coordinates),
and where the vehicle coordinates are provided by the GPS Tracking by (vehicle, timestamp),
and where the gas station coordinates are provided by geocoding its post address.

Unless specified otherwise, we assume the following background:
Background:
	Given a fuel card exclusive to Peter Scam
	And a vehicle 23 exclusively associated to Peter Scam
	And that the tank size of the vehicle 23 is 48L
	And that the location of the vehicle 23 is (48.8582, 2.2946) on 2015/04/21 15.10
	And assuming that the geocoding of the address "Shell Paris Eiffel, 12 rue de Trocadero 75008 Paris" is (48.858222, 2.2945)

Scenario: Fuel transaction with no anomaly
	When a transaction is reported on the fuel card
	Then no anomaly is reported
	

Scenario: Fuel transaction with more fuel than the vehicle tank can hold
	Given that the tank size of the vehicle 23 is 48L
	When a transaction is reported for 52L on the fuel card
	Then an anomaly "The fuel transaction of 52L exceeds the tank size of 48L" is reported
	

Scenario: Vehicle too far from fuel transaction
	Given that the location of the vehicle 23 is (48.8608333, 2.3516667) on 2015/04/21 15.10
	And assuming that the geocoding of the address "Shell Paris Eiffel, 12 rue de Trocadero 75008 Paris" is (48.858222, 2.2945)
	When a transaction is reported for this address on 2015/04/21 15.10 on the fuel card
	Then an anomaly "vehicle was 4192m away" is reported
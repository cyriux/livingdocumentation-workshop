/**
 * Dispatching is the procedure for assigning employees (drivers) or vehicles to customers requests, e.g. for parcel delivery or couriers.
 * 
 * With vehicle dispatching, requests are matched according to the order of the incoming requests and the proximity of vehicles to the target locations or routes. 
 * 
 * The main stake in dispatching is to increase revenue by fitting more service requests into each workday.
 */
@BoundedContext(name = "Dispatching")
package flottio.dispatching;

import flottio.annotations.BoundedContext;


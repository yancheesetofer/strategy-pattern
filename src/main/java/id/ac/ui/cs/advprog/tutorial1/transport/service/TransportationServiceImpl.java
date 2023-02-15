package id.ac.ui.cs.advprog.tutorial1.transport.service;

import id.ac.ui.cs.advprog.tutorial1.transport.core.*;
import id.ac.ui.cs.advprog.tutorial1.transport.exceptions.InvalidDistanceException;
import id.ac.ui.cs.advprog.tutorial1.transport.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportationServiceImpl implements TransportationService {

    private final LocationRepository locationRepository;
    private TransportCostCalculator transportStrategy;

    @Autowired
    public TransportationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Cost calculateCost(String locationName, String transportationTypeName) {
        Location location = locationRepository.findByName(locationName);

        if (location == null) {
            return null;
        }

        if (transportationTypeName.equalsIgnoreCase("motorcycle")) {
            transportStrategy = new MotorCostCalculator();
        } else if (transportationTypeName.equalsIgnoreCase("car")) {
            transportStrategy = new CarCostCalculator();
        } else if (transportationTypeName.equalsIgnoreCase("airplane")) {
            transportStrategy = new AirplaneCostCalculator();
        } else {
            return null;
        }

        Double distanceInKm = location.getDistance();
//        try {
//            transportStrategy.assertWithinDistanceLimit(distanceInKm);
//        } catch (InvalidDistanceException e) {
//            return null;
//        }

        Cost cost = transportStrategy.getCosts(distanceInKm);
        cost.setTimeEstimationInHour(transportStrategy.getTransportTimeInHour(distanceInKm));
        cost.setSatisfaction(transportStrategy.getTransportSatisfaction(distanceInKm));

        return cost;
    }
}

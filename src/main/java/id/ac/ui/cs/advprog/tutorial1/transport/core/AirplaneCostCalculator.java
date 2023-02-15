package id.ac.ui.cs.advprog.tutorial1.transport.core;
import id.ac.ui.cs.advprog.tutorial1.transport.exceptions.InvalidDistanceException;

public class AirplaneCostCalculator implements TransportCostCalculator {

    private static final double MINIMUM_DISTANCE = 300.0;
    private static final double MINIMUM_FARE = 500000.0;
    private static final double FARE_INCREASE = 150000.0;
    private static final double SATISFACTION_DECREASE = 1.0;

    @Override
    public Cost getCosts(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        Double fare = getTransportFare(distanceInKm);
        Double time = getTransportTimeInHour(distanceInKm);
        Integer satisfaction = getTransportSatisfaction(distanceInKm);
        return new Cost(fare, time, satisfaction);
    }

    @Override
    public Double getTransportFare(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        if (distanceInKm <= MINIMUM_DISTANCE) {
            return MINIMUM_FARE;
        } else {
            double additionalDistance = distanceInKm - MINIMUM_DISTANCE;
            double additionalFare = Math.floor(additionalDistance / 100.0) * FARE_INCREASE;
            return MINIMUM_FARE + additionalFare;
        }
    }

    @Override
    public Double getTransportTimeInHour(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        return distanceInKm / 800.0;
    }

    @Override
    public Integer getTransportSatisfaction(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        double satisfaction = 10.0;
        double additionalDistance = distanceInKm - MINIMUM_DISTANCE;
        if (additionalDistance > 0) {
            satisfaction -= Math.floor(additionalDistance / 100.0) * SATISFACTION_DECREASE;
        }
        return Math.max((int) Math.round(satisfaction), 1);
    }

    private void assertWithinDistanceLimit(Double distanceInKm) {
        if (distanceInKm < MINIMUM_DISTANCE) {
            throw new InvalidDistanceException();
        }
    }
}

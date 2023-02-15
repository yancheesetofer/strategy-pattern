package id.ac.ui.cs.advprog.tutorial1.transport.core;
import id.ac.ui.cs.advprog.tutorial1.transport.exceptions.InvalidDistanceException;

public class MotorCostCalculator implements TransportCostCalculator {
    private static final double COST_PER_KM = 1500;
    private static final double SPEED_IN_KM_PER_HOUR = 40.0;
    private static final int MAX_DISTANCE_IN_KM = 25;

    @Override
    public Cost getCosts(Double distanceInKm) {
        assertWithinDistanceLimit(distanceInKm);
        double totalCost = 5000;
        if (distanceInKm > 2){
            totalCost += (distanceInKm - 2) * COST_PER_KM;
        }
        double timeEstimationInHour = getTransportTimeInHour(distanceInKm);
        int satisfaction = getTransportSatisfaction(distanceInKm);
        return new Cost(totalCost, timeEstimationInHour, satisfaction);
    }

    @Override
    public Double getTransportFare(Double distanceInKm) {
        return getCosts(distanceInKm).getFare();
    }

    @Override
    public Double getTransportTimeInHour(Double distanceInKm) {
        return distanceInKm / SPEED_IN_KM_PER_HOUR;
    }

    @Override
    public Integer getTransportSatisfaction(Double distanceInKm) {
        return distanceInKm <= 10? 10 : 10 - (int) Math.floor((distanceInKm)/10);
    }

    private void assertWithinDistanceLimit(Double distanceInKm) {
        if (distanceInKm <= 0 || distanceInKm > MAX_DISTANCE_IN_KM) {
            throw new InvalidDistanceException();
        }
    }
}

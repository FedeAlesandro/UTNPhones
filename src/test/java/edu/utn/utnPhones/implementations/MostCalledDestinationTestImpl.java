package edu.utn.utnPhones.implementations;

import edu.utn.utnPhones.models.projections.MostCalledDestination;

public class MostCalledDestinationTestImpl implements MostCalledDestination {

    @Override
    public String getPhoneNumber() {
        return "223555444";
    }

    @Override
    public String getName() {
        return "Rodrigo";
    }

    @Override
    public Integer getCallsCount() {
        return 5;
    }
}

package edu.utn.utnPhones.models.projections;

public interface MostCalledDestination {

    String getPhoneNumber();

    String getName();

    Integer getCallsCount();

    void setPhoneNumber(String phoneNumber);

    void setName(String name);

    void setCallsCount(Integer callsCount);
}

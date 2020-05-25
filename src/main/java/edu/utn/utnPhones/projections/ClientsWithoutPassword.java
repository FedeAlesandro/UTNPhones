package edu.utn.utnPhones.projections;

public interface ClientsWithoutPassword {

    Integer getId();

    String getName();

    String getLastName();

    String getDni();

    String getUserName();

    boolean getRemoved();

    String getCityName();
}

package edu.utn.utnPhones.models.projections;

public interface UsersWithoutPassword {

    Integer getId();

    String getName();

    String getLastName();

    String getDni();

    String getUserName();

    String getUserType();

    boolean getRemoved();

    String getCityName();
}

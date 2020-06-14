package edu.utn.utnPhones.models.projections.implementations;

import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;

public class ClientsWithoutPasswordTestImpl implements ClientsWithoutPassword {

    @Override
    public Integer getId() {

        return 1;
    }

    @Override
    public String getName() {

        return "Federico";
    }

    @Override
    public String getLastName() {

        return "Laguna";
    }

    @Override
    public String getDni() {

        return "42457871";
    }

    @Override
    public String getUserName() {

        return "EuveniasPR";
    }

    @Override
    public String getCityName() {

        return "Mar del Plata";
    }
}

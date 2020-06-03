package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.projections.PhoneLineUser;

public class PhoneLineUserTestImpl implements PhoneLineUser {

    @Override
    public Integer getId() {
        return 6;
    }

    @Override
    public Integer getIdUser() {
        return 20;
    }

    @Override
    public String getNameUser() {
        return "Ramon";
    }

    @Override
    public String getPhoneNumber() {
        return "2235456879";
    }

    @Override
    public String getLineType() {
        return "home";
    }

    @Override
    public String getState() {
        return "register";
    }
}

package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.AlreadyExistsException;
import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.PhoneLineRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest implements FactoryService{

    private PhoneLineService phoneLineService;

    @Mock
    private PhoneLineRepository phoneLineRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CityRepository cityRepository;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineService = new PhoneLineService(phoneLineRepository, userRepository, cityRepository);
    }

    @Test
    public void getAllTest(){
        when(phoneLineRepository.getAll()).thenReturn(new ArrayList<PhoneLine>());
        Assert.assertNotNull(phoneLineService.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void getByUserNameNotFoundException(){
        when(userRepository.findByUserNameAndRemoved("Euvenias", false))
                .thenReturn(Optional.empty());
        phoneLineService.getByUserName("Euvenias");
    }

    @Test
    public void getByUserNameOk(){
        when(userRepository.findByUserNameAndRemoved("Euvenias", false)).thenReturn(Optional.of(new User()));
        when(phoneLineRepository.getAll()).thenReturn(new ArrayList<PhoneLine>());

        Assert.assertNotNull(phoneLineService.getByUserName("Euvenias"));
    }

    @Test(expected = NotFoundException.class)
    public void addUserNotFoundException(){
        PhoneLineDtoAdd phoneLine = createPhoneLineDtoAdd();
        when(userRepository.findByIdAndRemoved(phoneLine.getUser().getId(),false)).thenReturn(Optional.empty());
        phoneLineService.add(phoneLine);
    }

    @Test(expected = NotFoundException.class)
    public void addCityNotFoundException(){
        PhoneLineDtoAdd phoneLine = createPhoneLineDtoAdd();
        when(userRepository.findByIdAndRemoved(phoneLine.getUser().getId(),false)).thenReturn(Optional.of(new User()));
        when(cityRepository.findAreaCodeByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(Optional.empty());

        phoneLineService.add(phoneLine);
    }

    @Test(expected = AlreadyExistsException.class)
        public void addAlreadyExistsException(){
        PhoneLineDtoAdd phoneLine = createPhoneLineDtoAdd();

        when(userRepository.findByIdAndRemoved(phoneLine.getUser().getId(),false)).thenReturn(Optional.of(new User()));
        when(cityRepository.findAreaCodeByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(createPhoneLine());

        phoneLineService.add(phoneLine);
    }

    @Test
    public void addRemovedPhoneLine(){
        PhoneLineDtoAdd phoneLine = createPhoneLineDtoAdd();
        PhoneLine oldPhoneLine = createPhoneLineRemoved();
        PhoneLine newPhoneLine = createPhoneLine();

        when(userRepository.findByIdAndRemoved(phoneLine.getUser().getId(),false)).thenReturn(Optional.of(new User()));
        when(cityRepository.findAreaCodeByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(oldPhoneLine);
        when(phoneLineRepository.save(oldPhoneLine)).thenReturn(newPhoneLine);

        Assert.assertEquals(newPhoneLine, phoneLineService.add(phoneLine));
    }

    @Test
    public void addOk(){
        PhoneLineDtoAdd phoneLine = createPhoneLineDtoAdd();
        PhoneLine newPhoneLine = createPhoneLine();

        when(userRepository.findByIdAndRemoved(phoneLine.getUser().getId(),false)).thenReturn(Optional.of(new User()));
        when(cityRepository.findAreaCodeByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber(phoneLine.getPhoneNumber())).thenReturn(null);
        when(phoneLineRepository.save(newPhoneLine)).thenReturn(newPhoneLine);

        Assert.assertEquals(newPhoneLine, phoneLineService.add(phoneLine));
    }

    @Test(expected = NotFoundException.class)
    public void updatePhoneLineNotFoundException(){
        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.empty());

        phoneLineService.update(1, createPhoneLineDtoUpdate());
    }

    @Test(expected = NotFoundException.class)
    public void updateAreaCodeNotFoundException(){
        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.of(new PhoneLine()));
        when(cityRepository.findAreaCodeByPhoneNumber("2235860225")).thenReturn(Optional.empty());

        phoneLineService.update(1, createPhoneLineDtoUpdate());
    }

    @Test(expected = AlreadyExistsException.class)
    public void updateAlreadyExistsException(){
        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.of(new PhoneLine()));
        when(cityRepository.findAreaCodeByPhoneNumber("2235860225")).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber("2235860225")).thenReturn(new PhoneLine());

        phoneLineService.update(1, createPhoneLineDtoUpdate());
    }

    @Test(expected = NotFoundException.class)
    public void updateUserNotFoundException(){
        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.of(new PhoneLine()));
        when(cityRepository.findAreaCodeByPhoneNumber("2235860225")).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber("2235860225")).thenReturn(null);
        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.empty());

        phoneLineService.update(1, createPhoneLineDtoUpdate());
    }

    @Test
    public void updateOk(){
        PhoneLine phoneLine = createPhoneLine();
        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.of(new PhoneLine()));
        when(cityRepository.findAreaCodeByPhoneNumber("2235860225")).thenReturn(Optional.of(new City()));
        when(phoneLineRepository.findByPhoneNumber("2235860225")).thenReturn(null);
        when(userRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.of(new User()));
        when(phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        phoneLineService.update(1, createPhoneLineDtoUpdate());
    }

    @Test(expected = NotFoundException.class)
    public void removeNotFoundException(){

        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.empty());

        phoneLineService.remove(1);
    }

    @Test
    public void removeOk(){

        when(phoneLineRepository.findByIdAndState(1)).thenReturn(Optional.of(new PhoneLine()));
        doNothing().when(phoneLineRepository).remove(1);

        phoneLineService.remove(1);
    }
}


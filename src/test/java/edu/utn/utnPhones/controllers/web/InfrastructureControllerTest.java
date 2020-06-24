package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.FactoryController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.utils.RestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestUtils.class)
public class InfrastructureControllerTest implements FactoryController {

    InfrastructureController infrastructureController;

    @Mock
    PhoneCallController phoneCallController;

    @Before
    public void setUp(){

        initMocks(this);
        infrastructureController = new InfrastructureController(phoneCallController);
        mockStatic(RestUtils.class);
    }

    @Test
    public void testAddPhoneCall(){

        PhoneCallDtoAdd phoneCallDto = createPhoneCallAddDto();
        PhoneCall phoneCall = createPhoneCall();
        phoneCall.setId(1);

        when(phoneCallController.add(phoneCallDto)).thenReturn(phoneCall);
        when(RestUtils.getLocation(phoneCall)).thenReturn(URI.create("URI/test"));

        ResponseEntity<URI> responseEntityUri = infrastructureController.addPhoneCall(phoneCallDto);
        URI uri = responseEntityUri.getHeaders().getLocation();

        Assert.assertEquals(uri, URI.create("URI/test"));
    }

}

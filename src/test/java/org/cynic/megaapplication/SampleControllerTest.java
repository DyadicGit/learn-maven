package org.cynic.megaapplication;

import org.cynic.megaapplication.controller.SampleController;
import org.cynic.megaapplication.service.SampleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;


public class SampleControllerTest {
    @InjectMocks
    private SampleController sampleController;
    @Mock
    private SampleService sampleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldEcho() {
        int result =  sampleController.echo("other");
        assertEquals(6, result);
    }

    @Test
    public void shouldEchoUsingService() {
        Mockito.when(sampleService.process(Mockito.eq("banana"))).thenReturn("ResultIs9");
        int result = sampleController.echo("banana");
        assertEquals(9, result);
        Mockito.verify(sampleService).process(Mockito.eq("banana"));
    }
}

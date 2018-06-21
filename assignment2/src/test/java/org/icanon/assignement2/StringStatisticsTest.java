package org.icanon.assignement2;


import org.icanon.assignement2.service.StringStatistics;
import org.icanon.assignements.model.StatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringStatisticsTest {

    @InjectMocks
    private StringStatistics stringStatistics;

    @Mock
    private StatService statService;


    @Test
    public void assert_that_call_is_performed_as_expected() {
        final String s1 = "my&friend&Paul has heavy hats! &";
        final String s2 = "my friend John has many many friends &";
        when(statService.mix(any(Collection.class), anyString()))
                .thenReturn("2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/1,2:ee/1,2:ss");
        stringStatistics.mix(Arrays.asList(s1, s2), ",");
        Mockito.verify(statService)
                .mix(Arrays.asList(s1, s2), ",");

    }
}
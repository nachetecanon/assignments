package org.icanon.assignement1;


import org.icanon.assignement1.service.StringStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class StringStatisticsTest {

    @Test(expected = IllegalArgumentException.class)
    public void assert_exception_thrown_when_first_argument_is_missing() {
        new StringStatistics().mix(null, "some");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assert_exception_thrown_when_second_argument_is_missing() {
        new StringStatistics().mix("some", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assert_exception_thrown_when_both_argument_is_missing() {
        new StringStatistics().mix(null, null);
    }

    /**
     * <code>
     *     s1 = "my&friend&Paul has heavy hats! &";
     *     s2 = "my friend John has many many friends &";
     *     mix(s1, s2) --> "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
     * </code>
     */
    @Test
    public void assert_assumption1_works_fine() {
        final String s1 = "my&friend&Paul has heavy hats! &";
        final String s2 = "my friend John has many many friends &";
        final String expectedResult = "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
        assertThat(new StringStatistics().mix(s1, s2))
                .isEqualTo(expectedResult);
    }

    /**
     * <code>
     *     s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
     *     s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
     *     mix(s1, s2) --> "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
     * </code>
     */
    @Test
    public void assert_assumption2_works_fine() {
        final String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        final String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
        final String expectedResult = "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
        assertThat(new StringStatistics().mix(s1, s2))
                .isEqualTo(expectedResult);
    }

    /**
     * <code>
     *     s1 = "Are the kids at home? aaaaa fffff";
     *     s2 = "Yes they are here! aaaaa fffff";
     *     mix(s1, s2) --> "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh";
     * </code>
     */
    @Test
    public void assert_assumption3_works_fine() {
        final String s1 = "Are the kids at home? aaaaa fffff";
        final String s2 = "Yes they are here! aaaaa fffff";
        final String expectedResult = "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh";
        assertThat(new StringStatistics().mix(s1, s2))
                .isEqualTo(expectedResult);
    }
}

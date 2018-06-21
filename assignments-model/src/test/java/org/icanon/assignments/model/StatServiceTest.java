package org.icanon.assignments.model;

import org.icanon.assignements.model.Stat;
import org.icanon.assignements.model.StatService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StatServiceTest {

    private StatService statService;
    
    @Before
    public void setUp() {
        
        statService=new StatService();
    }
    
    @Test
    public void assert_that_filtering_rejects_non_lowercase_letters_only() {
        String test1 = "adsfñAASDfle   e$$$&&/{} ";
        String expected = "adsfñflee";
        assertThat(statService.filterLowercaseCharactersOnly(test1))
                .isEqualTo(expected);
    }

    /**
     * ramdon test to parse, for comparing with desired result
     * we've collected the statistics via <code>sed 's/\(.\)/\1\n/g' file.txt | sort | uniq -ic</code>
     * where file is the file containing <code>test1</code>
     * <p>
     * 21 a
     * 2 c
     * 3 d
     * 28 e
     * 4 f
     * 8 g
     * 9 h
     * 10 i
     * 16 l
     * 13 m
     * 13 n
     * 18 o
     * 2 p
     * 12 r
     * 9 s
     * 25 t
     * 5 u
     * 6 v
     * 2 w
     * 4 x
     */
    @Test
    public void assert_that_aggregate_by_char_returns_proper_statistics_map() {
        String test1 = "\n" +
                "Calling stream() method on the list to get a stream of values from the list\n" +
                "Calling mapToInt(value -> value) on the stream to get an Integer Stream\n" +
                "Calling max() method on the stream to get the max value\n" +
                "Calling orElseThrow() to throw an exception if no value is received from max()";
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('\n', 4);
        expected.put(' ', 47);
        expected.put('(', 5);
        expected.put(')', 5);
        expected.put('-', 1);
        expected.put('>', 1);
        expected.put('C', 4);
        expected.put('E', 1);
        expected.put('I', 2);
        expected.put('S', 1);
        expected.put('T', 2);
        expected.put('a', 21);
        expected.put('c', 2);
        expected.put('d', 3);
        expected.put('e', 28);
        expected.put('f', 4);
        expected.put('g', 8);
        expected.put('h', 9);
        expected.put('i', 10);
        expected.put('l', 16);
        expected.put('m', 13);
        expected.put('n', 13);
        expected.put('o', 18);
        expected.put('p', 2);
        expected.put('r', 12);
        expected.put('s', 9);
        expected.put('t', 25);
        expected.put('u', 5);
        expected.put('v', 6);
        expected.put('w', 2);
        expected.put('x', 4);
        Map<Character, Stat> obtainedMap = statService.aggregateByChar("test", test1, "");

        assertThat(obtainedMap)
                .isNotEmpty()
                .hasSize(expected.size());
        obtainedMap.entrySet().stream().map(e -> String.format("%c##%d", e.getValue().getCharToBeCounted(), e.getValue().getFrequency()))
                .forEach(formattedString -> {
                            String character = formattedString.split("##")[0];
                            int frequency = Integer.parseInt(formattedString.split("##")[1]);
                            System.out.println(formattedString);
                            assertThat(frequency).isEqualTo(expected.get(character.toCharArray()[0]));
                        }
                );

    }

    @Test(expected = IllegalArgumentException.class)
    public void assert_exception_thrown_when_first_argument_is_missing() {
        statService.mix(null, "");
    }

    /**
     * regression test for accomplishing both assignment 1 and 2
     * <code>
     * s1 = "my&friend&Paul has heavy hats! &";
     * s2 = "my friend John has many many friends &";
     * --before-- mix(s1, s2) --> "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
     * --now-- mix(s1, s2)    --> "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/1,2:ee/1,2:ss";
     * </code>
     */
    @Test
    public void assert_assumption1_regression_works_fine() {
        final String s1 = "my&friend&Paul has heavy hats! &";
        final String s2 = "my friend John has many many friends &";
        final String expectedResult = "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/1,2:ee/1,2:ss";
        assertThat(statService.mix(Arrays.asList(s1, s2), ","))
                .isEqualTo(expectedResult);
    }

    /**
     * regression test for accomplishing both assignment 1 and 2
     * <code>
     * s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
     * s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
     * --before-- mix(s1, s2) --> "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
     * --now-- mix(s1, s2)    --> "1:mmmmmm/1,2=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/1,2=ee/1,2=:ss";
     * </code>
     */
    @Test
    public void assert_assumption2_works_fine() {
        final String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        final String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
        final String expectedResult = "1:mmmmmm/1,2:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/1,2:ee/1,2:ss";
        assertThat(statService.mix(Arrays.asList(s1, s2), ","))
                .isEqualTo(expectedResult);
    }

    /**
     * regression test for accomplishing both assignment 1 and 2
     * <code>
     * s1 = "Are the kids at home? aaaaa fffff";
     * s2 = "Yes they are here! aaaaa fffff";
     * --before -- mix(s1, s2) --> "=:aaaaaa/2:eeeee/=:fffff/2:rr/1:tt/=:hh";
     * --now-- mix(s1, s2) --> "1,2:aaaaaa/2:eeeee/1,2:fffff/1:rr/2:tt/1,2:hh";
     * </code>
     */
    @Test
    public void assert_assumption3_works_fine() {
        final String s1 = "Are the kids at home? aaaaa fffff";
        final String s2 = "Yes they are here! aaaaa fffff";
        final String expectedResult = "1,2:aaaaaa/2:eeeee/1,2:fffff/2:rr/1:tt/1,2:hh";
        assertThat(statService.mix(Arrays.asList(s1, s2), ","))
                .isEqualTo(expectedResult);
    }

    /**
     * new assumption with 4 strings:
     * <code>
     * s1= "Where are you?"
     * s2= "You should be coding!!"
     * s3= "c'amon baby, you should see it;)"
     * s4= "several errors today"
     * mix(s1,s2,s3,s4) --> 4:rrrr/1,4:eee/2,3:ooo/3:bb/2:dd/3:yy/3,4:aa/3,4:ss/2,3:uu"
     * </code>
     */

    @Test
    public void assert_assumption4_works_fine() {
        final String expectedResult = "4:rrrr/1,4:eee/2,3:ooo/3:bb/2:dd/3:yy/3,4:aa/3,4:ss/2,3:uu";
        assertThat(statService.mix(Arrays.asList(
                "Where are you?", "You should be coding!!",
                "c'amon baby, you should see it;)",
                "several errors today"
        ), ",")).isEqualTo(expectedResult);
    }

}

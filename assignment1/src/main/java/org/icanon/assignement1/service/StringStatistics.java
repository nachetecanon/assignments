package org.icanon.assignement1.service;


import org.icanon.assignements.model.Stat;
import org.icanon.assignements.model.StatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description of the problem :
 * <p>
 * Given two strings s1 and s2, we want to visualize how different the two strings are. We will only
 * take into account the lowercase letters (a to z). First let us count the frequency of each lowercase
 * letters in s1 and s2.
 * s1 = "A aaaa bb c"
 * s2 = "& aaa bbb c d"
 * s1 has 4 'a', 2 'b', 1 'c'
 * s2 has 3 'a', 3 'b', 1 'c', 1 'd'
 * So the maximum for 'a' in s1 and s2 is 4 from s1; the maximum for 'b' is 3 from s2. In the
 * following we will not consider letters when the maximum of their occurrences is less than or equal
 * to 1.
 * We can resume the differences between s1 and s2 in the following string: "1:aaaa/2:bbb" where 1
 * in 1:aaaa stands for string s1 and aaaa because the maximum for a is 4. In the same manner 2:bbb
 * stands for string s2 and bbb because the maximum for b is 3.
 * The task is to produce a string in which each lowercase letters of s1 or s2 appears as many times
 * as its maximum if this maximum is strictly greater than 1; these letters will be prefixed by the
 * number of the string where they appear with their maximum value and :. If the maximum is in s1 as
 * well as in s2 the prefix is =:.
 * In the result, substrings will be in decreasing order of their length and when they have the same
 * length sorted alphabetically; the different groups will be separated by '/'.
 * Hopefully other examples can make this clearer.
 * s1 = "my&friend&Paul has heavy hats! &"
 * s2 = "my friend John has many many friends &"
 * mix(s1, s2) --> "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"
 * s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &"
 * s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&"
 * mix(s1, s2) --> "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"
 * s1="Are the kids at home? aaaaa fffff"
 * s2="Yes they are here! aaaaa fffff"
 * mix(s1, s2) --> "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh"
 */
public class StringStatistics {

    private final StatService statService;

    public StringStatistics(StatService statService) {
        this.statService = statService;
    }

    /**
     * Mix string.
     *
     * @param s1 the s 1
     * @param s2 the s 2
     * @return the string
     */
    public String mix(final String s1, final String s2) {
        final String string1 = Optional.ofNullable(s1)
                .orElseThrow(() -> new IllegalArgumentException("first argument must not be null!"));
        final String string2 = Optional.ofNullable(s2)
                .orElseThrow(() -> new IllegalArgumentException("second argument must not be null!"));
        final List<Stat> result = new ArrayList<>();
        // create maps of statistics for each string
        final Map<Character, Stat> aggregatedS1 = statService.aggregateByChar("1", statService.filterLowercaseCharactersOnly(string1), "=");
        final Map<Character, Stat> aggregatedS2 = statService.aggregateByChar("2", statService.filterLowercaseCharactersOnly(string2), "=");

        // iterate over first map, for populating the result list.
        // For each item, search if the character exists at the second map, and if true, choose the one with higher
        // frequency; if false, use the one from the first map to add to the result list.
        aggregatedS1.entrySet()
                .stream().forEach(cS1 -> result.add(
                Optional.ofNullable(aggregatedS2.get(cS1.getKey()))
                        .map(cS2 -> checkFrecuencies(cS1.getValue(), cS2))
                        .orElse(cS1.getValue())
                )
        );
        // at this point we have in result, the list of statistics from aggregatedS1 map which are of higher frequency
        // comparing with the same character at aggregatedS2 map, along with the "single" characters only
        // existing at aggregatorS1.
        // So for completing the population of result list, we only need to add the "single" characters not existing at
        // aggregatorS1.
        result.addAll(aggregatedS2.entrySet().stream().filter(e -> !aggregatedS1.containsKey(e.getKey()))
                .map(Map.Entry::getValue).collect(Collectors.toList()));

        // we return a string using a filter for removing values with frequency <= 1,
        // sort using the Stat implementation of Comparable interface
        // and finally collect to a single string, first with the overriding implementation of toString for each Stat class, and then
        // joining with '/' character
        return result.stream()
                .filter(s -> s.getFrequency() > 1)
                .sorted()
                .map(Stat::toString)
                .collect(Collectors.joining("/"));
    }


    private Stat checkFrecuencies(Stat value, Stat cS2) {
        if (value.getFrequency() > cS2.getFrequency()) {
            return value;
        } else if (value.getFrequency() < cS2.getFrequency()) {
            return cS2;
        } else {
            return new Stat("=", value.getCharToBeCounted(), "=").setFrequency(value.getFrequency());
        }
    }


}

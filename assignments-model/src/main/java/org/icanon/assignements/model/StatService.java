package org.icanon.assignements.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * class with utilities relative to Stat class
 */
public class StatService {

    /**
     * removes from passed string any character non between a-z (lowercase).
     * Excludes also admiration, question tags, and other simbols
     *
     * @param string original string
     * @return string lowercased with only the characters filtered.
     */
    public String filterLowercaseCharactersOnly(final String string) {
        return string.replaceAll("[\\\\\\\\!\\\"#$%&()*+,./:;<=>?@\\\\[\\\\]^_{|}~\\sA-Z]+", "");
    }

    /**
     * Construct a map of statistics of number of different characters in the passed string.
     * The map will have as key the character readed, and as value a new Stat class, with the id passed,
     * the char to be counted, and the frequency of its occurrences at string parameter.
     *
     * @param id           identifier of the Stat class
     * @param string       string to be processed
     * @param equalsSymbol string or char used to mark stats with same frequencies
     * @return map
     */
    public Map<Character, Stat> aggregateByChar(String id, final String string, final String equalsSymbol) {
        Map<Character, Stat> mapToReturn = new HashMap<>();
        for (int i = 0; i < string.length(); i++) {
            if (mapToReturn.containsKey(string.charAt(i))) {
                final Stat data = mapToReturn.get(string.charAt(i));
                data.setFrequency(data.getFrequency() + 1);
            } else {
                mapToReturn.put(string.charAt(i), new Stat(id, string.charAt(i), equalsSymbol).setFrequency(1));
            }
        }
        return mapToReturn;
    }

    /**
     * Mix string.
     *
     * @param s the s 1
     * @return the string
     */
    public String mix(final Collection<String> s, final String equalsSymbol) {
        Optional.ofNullable(s)
                .orElseThrow(() -> new IllegalArgumentException("argument list must not be null!"));
        if(s.isEmpty()) {
            throw new IllegalArgumentException("argument list must not be null!");
        }
        if(!s.stream().allMatch(Objects::nonNull)) {
            throw new IllegalArgumentException("argument list must not be null!");
        }

        final List<Stat> result = new ArrayList<>();
        // create maps of statistics for each string, constructing a hashmap with a list of stats for each character
        final Map<Character, List<Stat>> aggregated = new HashMap<>();
        int i = 0;
        for (String ss : s) {
            aggregateByChar(++i + "", filterLowercaseCharactersOnly(ss), equalsSymbol)
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().getFrequency() > 1)
                    .forEach((e) -> {
                        if (!aggregated.containsKey(e.getKey())) {
                            aggregated.put(e.getKey(), new ArrayList<>());
                        }
                        aggregated.get(e.getKey()).add(e.getValue());
                    });
        }
        aggregated.entrySet().forEach(entry -> {
            final Stat st = new Stat("", entry.getKey(), equalsSymbol).setFrequency(0);
            result.add(entry.getValue().stream()
                    .reduce(st, (stat, stat2) -> {
                        if (stat.getFrequency() == stat2.getFrequency()) {
                            st.setFrequency(stat.getFrequency());
                            st.setId(stat.getId() + "," + stat2.getId());
                        } else if (stat.getFrequency() > stat2.getFrequency()) {
                            st.setFrequency(stat.getFrequency());
                            st.setId(stat.getId());
                        } else {
                            st.setFrequency(stat2.getFrequency());
                            st.setId(stat2.getId());
                        }
                        return st;
                    }));
        });
        return result.stream()
                .sorted()
                .map(Stat::toString)
                .collect(Collectors.joining("/"));
    }

}

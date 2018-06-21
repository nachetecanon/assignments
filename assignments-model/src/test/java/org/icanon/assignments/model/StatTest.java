package org.icanon.assignments.model;

import org.icanon.assignements.model.Stat;
import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * different assertions testing Stat.class compareTo implementation for assuring assignment requirements.
 */
public class StatTest {

    @Test
    public void assert_both_multi_and_same_frequency_stat_orders_alphabetically() {
        Stat s1 = new Stat("1,2", 'a', ",").setFrequency(3);
        Stat s2 = new Stat("2,3", 'b', ",").setFrequency(3);

        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("1,2:aaa/2,3:bbb");
    }

    @Test
    public void assert_both_multi_and_different_frequency_stat_orders_by_descent_frequency() {
        Stat s1 = new Stat("1,2", 'a', ",").setFrequency(13);
        Stat s2 = new Stat("2,3", 'b', ",").setFrequency(3);

        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("1,2:aaaaaaaaaaaaa/2,3:bbb");
    }

    @Test
    public void assert_first_multi_and_same_frequency_stat_orders_multi_last() {
        Stat s1 = new Stat("1,2", 'a', ",").setFrequency(8);
        Stat s2 = new Stat("2", 'b', ",").setFrequency(8);

        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("2:bbbbbbbb/1,2:aaaaaaaa");
    }

    @Test
    public void assert_first_multi_and_different_frequency_stat_orders_by_descentdent_frecuency() {
        Stat s1 = new Stat("1,2", 'a', ",").setFrequency(3);
        Stat s2 = new Stat("2", 'b', ",").setFrequency(5);

        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("2:bbbbb/1,2:aaa");
    }

    @Test
    public void assert_first_simple_second_multiple_same_frequency_stat_orders_by_simple_first() {
        Stat s1 = new Stat("3", 'c', ",").setFrequency(4);
        Stat s2 = new Stat("1,3", 'd', ",").setFrequency(4);
        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("3:cccc/1,3:dddd");

    }

    @Test
    public void assert_first_simple_second_multiple_different_frequency_stat_orders_by_descending_frequency() {
        Stat s1 = new Stat("3", 'c', ",").setFrequency(4);
        Stat s2 = new Stat("1,3", 'd', ",").setFrequency(6);
        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("1,3:dddddd/3:cccc");
    }

    @Test
    public void assert_both_simple_same_frequency_orders_alphabetically() {
        Stat s1 = new Stat("3", 'j', ",").setFrequency(4);
        Stat s2 = new Stat("1", 'c', ",").setFrequency(4);
        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("1:cccc/3:jjjj");
    }

    @Test
    public void assert_both_simple_different_frequency_orders_by_descending_frequency() {
        Stat s1 = new Stat("2", 'a', ",").setFrequency(5);
        Stat s2 = new Stat("1", 'b', ",").setFrequency(2);
        TreeSet<Stat> set = new TreeSet<>();
        set.addAll(Arrays.asList(s1, s2));
        assertThat(set.stream().map(Stat::toString).collect(Collectors.joining("/")))
                .isEqualTo("2:aaaaa/1:bb");
    }
}

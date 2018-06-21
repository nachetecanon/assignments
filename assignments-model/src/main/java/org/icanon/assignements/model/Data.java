package org.icanon.assignements.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

public class Data {
    @Valid
    @NotEmpty(message = "None of the strings can be empty!")
    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public Data setStrings(List<String> strings) {
        this.strings = strings;
        return this;
    }
}

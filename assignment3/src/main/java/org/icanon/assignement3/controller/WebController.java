package org.icanon.assignement3.controller;

import org.icanon.assignements.model.Data;
import org.icanon.assignements.model.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    private static final String EQUALS_SYMBOL = ",";
    private final StatService statService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        return new ResponseEntity<>("argument list must not be null!", HttpStatus.BAD_REQUEST);
    }

    @Autowired
    public WebController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping(path = "/api/mix", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> mix(@RequestBody @Validated final Data data) {
        return ResponseEntity.ok(statService.mix(data.getStrings(), EQUALS_SYMBOL));
    }

}

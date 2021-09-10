package com.tis.mx.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EjemploController {

  @GetMapping(value = "/mensaje")
  public String getMessage() {
    return "Hola Mundo!!!";
  }
}

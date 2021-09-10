/* 
* This program is free software: you can redistribute it and/or modify  
* it under the terms of the GNU General Public License as published by  
* the Free Software Foundation, version 3.
*
* This program is distributed in the hope that it will be useful, but 
* WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
* General Public License for more details.
*
* Nombre de archivo: ApplicationController.java
* Autor: salvgonz
* Fecha de creación: 9 sep. 2021
*/

package com.tis.mx.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import java.util.List;

/**
 * The Class ApplicationController.
 */
@RestController
public class ApplicationController {

  /** The calculator. */
  private CompoundInterestCalculator calculator; 

  /**
   * Instantiates a new application controller.
   *
   * @param calculator the calculator
   */
  public ApplicationController(CompoundInterestCalculator calculator) {
    this.calculator = calculator;
  }

  /**
   * Creates the table yield.
   *
   * @param initialInvestment the initial investment
   * @return the list
   */
  @PostMapping(value = "/api/v1/investors/calculators/ci")
  public List<InvestmentYieldDto> createTableYield(
      @RequestHeader(value = "Content-Type", required = false) String contentType,
      @RequestBody InitialInvestmentDto initialInvestment) {

    if (calculator.validateInput(initialInvestment)) {
      return calculator.createRevenueGrid(initialInvestment);
    }

    throw new CalculatorInputException("El cálculo no puede ser ejecutado");
  }
}







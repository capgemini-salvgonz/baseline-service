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
* Nombre de archivo: ApplicationControllerTest.java
* Autor: salvgonz
* Fecha de creación: 9 sep. 2021
*/

package com.tis.mx.application.controller;

import static org.junit.Assert.assertEquals;

import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import com.tis.mx.application.service.impl.CompoundInterestCalculatorImpl;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/**
 * The Class ApplicationControllerTest.
 */
public class ApplicationControllerTest {

  /** The controller. */
  private ApplicationController controller;
  
  /** The initial investment. */
  private InitialInvestmentDto initialInvestment;
  
  /** The calculator. */
  private CompoundInterestCalculator calculator;

  /**
   * Creates the values before to test.
   */
  @Before
  public void createValuesBeforeToTest() {
    // Crear una calculadora
    this.calculator = new CompoundInterestCalculatorImpl();
    this.controller = new ApplicationController(this.calculator);

    // Crear los valores iniciales de la inversion
    this.initialInvestment = new InitialInvestmentDto();

    this.initialInvestment.setInitialInvestment(5000.00f);
    this.initialInvestment.setYearlyInput(3000.00f);
    this.initialInvestment.setYearlyInputIncrement(1);
    this.initialInvestment.setInvestmentYears(5);
    this.initialInvestment.setInvestmentYield(21f);
  }

  /**
   * Should generate table yield.
   */
  @Test
  public void shouldGenerateTableYield() {

    List<InvestmentYieldDto> tableYield = controller.createTableYield("application/json",
        initialInvestment);

    assertEquals(5, tableYield.size());
    
    InvestmentYieldDto firstYear =  tableYield.get(0);
    assertEquals(Float.valueOf(5000), firstYear.getInitialInvestment());
    assertEquals(Float.valueOf(3000), firstYear.getYearlyInput());
    assertEquals(Float.valueOf(1680), firstYear.getInvestmentYield());
    assertEquals(Float.valueOf(9680), firstYear.getFinalBalance());
  }
  
  /**
   * Should throw validation exception.
   */
  @Test
  public void shouldThrowValidationException() {
    InitialInvestmentDto badInputRequest = new InitialInvestmentDto();
    badInputRequest.setInitialInvestment(0.00f);
    badInputRequest.setYearlyInput(3000.00f);
    badInputRequest.setYearlyInputIncrement(1);
    badInputRequest.setInvestmentYears(5);
    badInputRequest.setInvestmentYield(21f);
    
    try {
      controller.createTableYield("application/json", badInputRequest);      
    }catch(Exception ex) {
      assertEquals("El cálculo no puede ser ejecutado", ex.getMessage());
    }
    
  }

}



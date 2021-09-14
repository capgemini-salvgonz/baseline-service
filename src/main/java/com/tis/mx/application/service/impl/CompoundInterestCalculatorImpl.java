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
* Nombre de archivo: CompoundInterestCalculatorImpl.java
* Autor: salvgonz
* Fecha de creación: 10 sep. 2021
*/

package com.tis.mx.application.service.impl;

import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The Class CompoundInterestCalculatorImpl.
 */
@Service
public class CompoundInterestCalculatorImpl implements CompoundInterestCalculator {

  /**
   * Creates the revenue grid.
   *
   * @param initialInvestmentDto the initial investment dto
   * @return the list
   */
  @Override
  public List<InvestmentYieldDto> createRevenueGrid(InitialInvestmentDto initialInvestmentDto) {
    
    List<InvestmentYieldDto> tableYield = new ArrayList<>();
    
    for (int year = 0; year < initialInvestmentDto.getInvestmentYears(); year++) { 
      tableYield.add(
          this.calculateYield(initialInvestmentDto,
              year == 0 ? Optional.empty() : Optional.of(tableYield.get(year - 1))));
    }
    
    return tableYield; 
  }
  
  
  /**
   * Calculate yield.
   *
   * @param initialInvestment the initial investment dto
   * @param lastInvestmentYield the last investment yield
   * @return the investment yield dto
   */
  private InvestmentYieldDto calculateYield(InitialInvestmentDto initialInvestment, 
      Optional<InvestmentYieldDto> lastInvestmentYield) {
    
    InvestmentYieldDto investmentYield = new InvestmentYieldDto();
    
    if (lastInvestmentYield.isPresent()) {      
      InvestmentYieldDto lastResult = lastInvestmentYield.get();
      investmentYield.setInvestmentYear(lastResult.getInvestmentYear() + 1);
      investmentYield.setInitialInvestment(lastResult.getFinalBalance());
      investmentYield.setYearlyInput(
          lastResult.getYearlyInput() 
          * (1 + ((float) initialInvestment.getYearlyInputIncrement() / 100f)));
    } else {
      investmentYield.setInvestmentYear(1);
      investmentYield.setInitialInvestment(initialInvestment.getInitialInvestment());
      investmentYield.setYearlyInput(initialInvestment.getYearlyInput());
    }
    
    Float revenue = (investmentYield.getInitialInvestment() + investmentYield.getYearlyInput())
        * (initialInvestment.getInvestmentYield() / 100f); 
    investmentYield.setInvestmentYield(revenue);
    
    Float finalBalance = investmentYield.getInitialInvestment() 
        + investmentYield.getYearlyInput()
        + investmentYield.getInvestmentYield();
    investmentYield.setFinalBalance(finalBalance);
    
    return investmentYield;
  }
  

  /**
   * Validate input.
   *
   * @param initialInvestment the initial investment
   * @return true, if successful
   */
  @Override
  public boolean validateInput(InitialInvestmentDto initialInvestment) {

    this.setDefaults(initialInvestment);
    boolean cumple = true;

    cumple = (initialInvestment.getInitialInvestment() >= 1000);
    cumple = cumple && (initialInvestment.getYearlyInput() >= 0.0);
    cumple = cumple && (initialInvestment.getYearlyInputIncrement() >= 0);
    cumple = cumple && (initialInvestment.getInvestmentYears() > 0.0);
    cumple = cumple && (initialInvestment.getInvestmentYield() > 0.0);

    return cumple;
  }

  /**
   * Sets the defaults.
   *
   * @param initialInvestment the new defaults
   */
  private void setDefaults(InitialInvestmentDto initialInvestment) {
    Float yearlyInput = initialInvestment.getYearlyInput();
    yearlyInput = yearlyInput == null ? 0 : yearlyInput;
    initialInvestment.setYearlyInput(yearlyInput);

    Integer yearlyInputIncrement = initialInvestment.getYearlyInputIncrement();
    yearlyInputIncrement = yearlyInputIncrement == null ? 0 : yearlyInputIncrement;
    initialInvestment.setYearlyInputIncrement(yearlyInputIncrement);
  }


}

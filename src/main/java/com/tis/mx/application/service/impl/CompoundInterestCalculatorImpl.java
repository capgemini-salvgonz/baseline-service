package com.tis.mx.application.service.impl;

import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CompoundInterestCalculatorImpl implements CompoundInterestCalculator {

//  @Override
//  public List<InvestmentYieldDto> createRevenueGrid(InitialInvestmentDto initialInvestmentDto) {
//    Integer investmentYear = 0;
//    Double initialInvestment = 0.00;
//    Double yearlyInput = 0.00;
//    Double investmentYield = 0.00;
//    Double finalBalance = 0.00;
//
//    ArrayList<InvestmentYieldDto> investmentYieldList = new ArrayList<>();
//    double aux = 0.00;
//    for (int i = 0; i < initialInvestmentDto.getInvestmentYears(); i++) {
//      investmentYear = i + 1;
//      yearlyInput = initialInvestmentDto.getYearlyInput() + aux;
//      aux += yearlyInput * initialInvestmentDto.getYearlyInputIncrement() / 100;
//      aux = Math.ceil(aux);
//
//      if (i == 0) {
//        initialInvestment = initialInvestmentDto.getInitialInvestment();
//      } else if (i > 0) {
//        initialInvestment = finalBalance;
//      }
//
//      investmentYield =
//          (initialInvestment + yearlyInput) * initialInvestmentDto.getInvestmentYield();
//      finalBalance = initialInvestment + yearlyInput + investmentYield;
//      investmentYieldList.add(new InvestmentYieldDto(investmentYear, initialInvestment, yearlyInput,
//          investmentYield, finalBalance));
//    }
//
//    return investmentYieldList;
//  }
  
  @Override
  public List<InvestmentYieldDto> createRevenueGrid(InitialInvestmentDto initialInvestmentDto) {
    List<InvestmentYieldDto> investmentYieldDtos = new ArrayList<>();
    List<InvestmentYieldDto> aux = Stream.generate(InvestmentYieldDto::new)
        .limit(initialInvestmentDto.getInvestmentYears()).collect(Collectors.toList());
    AtomicInteger year = new AtomicInteger(1);
    aux.stream().map(investment -> {
      if (year.get() == 1) {
        return new InvestmentYieldDto(year.getAndIncrement(),

            initialInvestmentDto.getInitialInvestment(),

            initialInvestmentDto.getYearlyInput(),

            (initialInvestmentDto.getInitialInvestment() + initialInvestmentDto.getYearlyInput())
                * (initialInvestmentDto.getInvestmentYield().doubleValue() / 100.00),

            initialInvestmentDto.getInitialInvestment() + initialInvestmentDto.getYearlyInput()
                + ((initialInvestmentDto.getInitialInvestment()
                    + initialInvestmentDto.getYearlyInput())
                    * (initialInvestmentDto.getInvestmentYield().doubleValue() / 100.00)));
      } else {
        return new InvestmentYieldDto(year.getAndIncrement(),

            investmentYieldDtos.get(investmentYieldDtos.size() - 1).getFinalBalance(),

            investmentYieldDtos.get(investmentYieldDtos.size() - 1).getYearlyInput()
                * (1 + (initialInvestmentDto.getYearlyInputIncrement().doubleValue() / 100.00)),

            (investmentYieldDtos.get(investmentYieldDtos.size() - 1).getFinalBalance()
                + (investmentYieldDtos.get(investmentYieldDtos.size() - 1).getYearlyInput()
                    * (1 + (initialInvestmentDto.getInvestmentYield().doubleValue() / 100d))))
                * (initialInvestmentDto.getInvestmentYield() / 100f),

            (investmentYieldDtos.get(investmentYieldDtos.size() - 1).getFinalBalance())
                + (investmentYieldDtos.get(investmentYieldDtos.size() - 1).getYearlyInput()
                    * (1 + (initialInvestmentDto.getYearlyInputIncrement().doubleValue() / 100.00)))
                + ((investmentYieldDtos.get(investmentYieldDtos.size() - 1).getFinalBalance()
                    + (investmentYieldDtos.get(investmentYieldDtos.size() - 1).getYearlyInput()
                        * (1 + (initialInvestmentDto.getInvestmentYield().doubleValue() / 100d))))
                    * (initialInvestmentDto.getInvestmentYield() / 100f)));
      }
    }).forEachOrdered(investmentYieldDtos::add);
    return investmentYieldDtos;
  }

  @Override
  public boolean validateInput(InitialInvestmentDto initialInvestment) {

    this.setDefaults(initialInvestment);
    boolean cumple = true;

    cumple = cumple && (initialInvestment.getInitialInvestment() >= 1000);
    cumple = cumple && (initialInvestment.getYearlyInput() >= 0.0);
    cumple = cumple && (initialInvestment.getYearlyInputIncrement() >= 0);
    cumple = cumple && (initialInvestment.getInvestmentYears() > 0.0);
    cumple = cumple && (initialInvestment.getInvestmentYield() > 0.0);

    return cumple;
  }

  private void setDefaults(InitialInvestmentDto initialInvestment) {
    Double yearlyInput = initialInvestment.getYearlyInput();
    yearlyInput = yearlyInput == null ? 0.0 : yearlyInput;
    initialInvestment.setYearlyInput(yearlyInput);

    Integer yearlyInputIncrement = initialInvestment.getYearlyInputIncrement();
    yearlyInputIncrement = yearlyInputIncrement == null ? 0 : yearlyInputIncrement;
    initialInvestment.setYearlyInputIncrement(yearlyInputIncrement);
  }


}

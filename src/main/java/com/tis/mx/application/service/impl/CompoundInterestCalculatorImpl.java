package com.tis.mx.application.service.impl;

import org.springframework.stereotype.Service;
import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service 
public class CompoundInterestCalculatorImpl implements CompoundInterestCalculator {
  
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

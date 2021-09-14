package com.tis.mx.application.service.impl;

import com.tis.mx.application.dto.InitialInvestmentDto;
import com.tis.mx.application.dto.InvestmentYieldDto;
import com.tis.mx.application.service.CompoundInterestCalculator;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDosImpl implements CompoundInterestCalculator {

  @Override
  public List<InvestmentYieldDto> createRevenueGrid(InitialInvestmentDto initialInvestment) {

    List<InvestmentYieldDto> tablaDeRendimiento = new ArrayList<>();

    int ciclosDeInversion = initialInvestment.getInvestmentYears();

    for (int ciclo = 0; ciclo < ciclosDeInversion; ciclo++) {

      InvestmentYieldDto rendimientoAnual = null;
      
      if (ciclo == 0) {
        rendimientoAnual =
            this.calcularRendimientoAnual(initialInvestment, null);
      } else { 
        
        rendimientoAnual =
            this.calcularRendimientoAnual(initialInvestment, tablaDeRendimiento.get(ciclo - 1));
      }
      
      tablaDeRendimiento.add(rendimientoAnual);      
    }


    return tablaDeRendimiento;
  }


  private InvestmentYieldDto calcularRendimientoAnual(InitialInvestmentDto inversionInicial,
      InvestmentYieldDto rendimientoAnterior) {
    
    InvestmentYieldDto rendimiento = new InvestmentYieldDto();
    
    if (rendimientoAnterior == null) {
      /**
       * Aqui no existe rendimiento anterior
       */
      rendimiento.setInvestmentYear(1); 
      rendimiento.setInitialInvestment(inversionInicial.getInitialInvestment());
      rendimiento.setYearlyInput(inversionInicial.getYearlyInput());
    } else {
      /**
       * Aqui si hay un rendimiento anterior
       */
       rendimiento.setInvestmentYear(rendimientoAnterior.getInvestmentYear() + 1);
       rendimiento.setInitialInvestment(rendimientoAnterior.getFinalBalance());
       
       Float aportacion = rendimientoAnterior.getYearlyInput() * 
           (1 + (inversionInicial.getYearlyInputIncrement() / 100));
       rendimiento.setYearlyInput(aportacion);
    }
    
    Float rendimientoAnual = rendimiento.getInitialInvestment() + rendimiento.getYearlyInput();
    rendimientoAnual = rendimientoAnual * (inversionInicial.getInvestmentYield() / 100);
    
    rendimiento.setInvestmentYield(rendimientoAnual);
    
    
    Float saldoFinal = rendimiento.getInitialInvestment() 
        + rendimiento.getYearlyInput() + rendimiento.getInvestmentYield();
    
    rendimiento.setFinalBalance(saldoFinal);

    return rendimiento;
  }


  @Override
  public boolean validateInput(InitialInvestmentDto initialInvestment) {
    return true;
  }

}














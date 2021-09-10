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
* Nombre de archivo: ErrorResponse.java
* Autor: salvgonz
* Fecha de creación: 10 sep. 2021
*/

package com.tis.mx.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
/**
 * Instantiates a new error response.
 *
 * @param code the code
 * @param message the message
 * @param location the location
 * @param moreInfo the more info
 */
@AllArgsConstructor
public class ErrorResponse {
  
  /** The code. */
  private Integer code;
  
  /** The message. */
  private String message;
  
  /** The location. */
  private String location;
  
  /** The more info. */
  private String moreInfo;
}

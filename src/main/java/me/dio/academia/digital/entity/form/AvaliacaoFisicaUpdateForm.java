package me.dio.academia.digital.entity.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisicaUpdateForm {
  @Positive(message = "${validatedValue}' precisa ser positivo.")
  private double peso;

  @DecimalMin(value = "120", message = "'${validatedValue}' precisa ser no mínimo {value}.")
  private double altura;
}

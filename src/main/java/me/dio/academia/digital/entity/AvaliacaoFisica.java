package me.dio.academia.digital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_avaliacoes")
public class AvaliacaoFisica {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "instrutor_id")
  private Instrutor instrutor;

  @ManyToOne
  @JoinColumn(name = "aluno_id")
  private Aluno aluno;

  @Column(name="peso_atual")
  private double peso;

  @Column(name="altura_atual")
  private double altura;

  private LocalDateTime dataDaAvaliacao = LocalDateTime.now();
}

package me.dio.academia.digital.controller;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.service.IAlunoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
  private IAlunoService service;

  public AlunoController(IAlunoService service) {
    this.service = service;
  }

  @PostMapping
  public Aluno create(@Valid @RequestBody AlunoForm form) {
    return service.create(form);
  }

  @GetMapping("/{id}")
  public Aluno get(@PathVariable Long id) {
    return service.get(id);
  }

  @PutMapping("/{id}")
  public Aluno create(@PathVariable Long id, @Valid @RequestBody AlunoUpdateForm form) {
    return service.update(id, form);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  @GetMapping
  public List<Aluno> getAll(@RequestParam(value = "dataDeNascimento", required = false)
                                  String dataDeNacimento){
    return service.getAll(dataDeNacimento);
  }

  @GetMapping("/ultima-avaliacao/{id}")
  public AvaliacaoFisica getUltimaAvaliacaoFisicaId(@PathVariable Long id) {
    return service.getLastAvaliacaoFisica(id);
  }

  @GetMapping("/imc/{id}")
  public String getImcId(@PathVariable Long id) {
    return service.getImc(id);
  }

  @GetMapping("/avaliacoes/{id}")
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
    return service.getAllAvaliacaoFisicaId(id);
  }
}

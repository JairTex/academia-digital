package me.dio.academia.digital.controller;

import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaForm;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaUpdateForm;
import me.dio.academia.digital.service.IAvaliacaoFisicaService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {
  private IAvaliacaoFisicaService service;

  public AvaliacaoFisicaController(IAvaliacaoFisicaService service) {
    this.service = service;
  }

  @PostMapping
  public AvaliacaoFisica create(@RequestBody AvaliacaoFisicaForm form) {
    return service.create(form);
  }

  @GetMapping("/{id}")
  public AvaliacaoFisica get(@PathVariable Long id) {
    return service.get(id);
  }

  @PutMapping("/{id}")
  public AvaliacaoFisica create(@PathVariable Long id,
                                @Valid @RequestBody AvaliacaoFisicaUpdateForm form)
  {
    return service.update(id, form);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  @GetMapping
  public List<AvaliacaoFisica> getAll(){
    return service.getAll();
  }

}

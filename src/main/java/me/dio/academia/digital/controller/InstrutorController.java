package me.dio.academia.digital.controller;

import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.Instrutor;
import me.dio.academia.digital.entity.form.InstrutorForm;
import me.dio.academia.digital.entity.form.InstrutorUpdateForm;
import me.dio.academia.digital.service.IInstrutorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/instrutor")
public class InstrutorController {
  private IInstrutorService service;

  public InstrutorController(IInstrutorService service) {
    this.service = service;
  }

  @PostMapping
  public Instrutor create(@Valid @RequestBody InstrutorForm form) {
    return service.create(form);
  }

  @GetMapping("/{id}")
  public Instrutor get(@PathVariable Long id) {
    return service.get(id);
  }

  @PutMapping("/{id}")
  public Instrutor create(@PathVariable Long id, @Valid @RequestBody InstrutorUpdateForm form) {
    return service.update(id, form);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  @GetMapping
  public List<Instrutor> getAll(){
    return service.getAll();
  }

  @GetMapping("/avaliacoes/{id}")
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
    return service.getAllAvaliacaoFisicaId(id);
  }
}

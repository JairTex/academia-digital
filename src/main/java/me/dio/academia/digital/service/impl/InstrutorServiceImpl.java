package me.dio.academia.digital.service.impl;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.Instrutor;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.entity.form.InstrutorForm;
import me.dio.academia.digital.entity.form.InstrutorUpdateForm;
import me.dio.academia.digital.exceptons.AvaliacaoFisicaNotFoundException;
import me.dio.academia.digital.infra.utils.JavaTimeUtils;
import me.dio.academia.digital.repository.InstrutorRepository;
import me.dio.academia.digital.service.IAlunoService;
import me.dio.academia.digital.service.IInstrutorService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
public class InstrutorServiceImpl implements IInstrutorService {
  private InstrutorRepository repository;

  public InstrutorServiceImpl(InstrutorRepository repository) {
    this.repository = repository;
  }

  @Override
  public Instrutor create(InstrutorForm form) {
    Instrutor instrutor = new Instrutor();
    instrutor.setNome(form.getNome());
    instrutor.setCpf(form.getCpf());
    return repository.save(instrutor);
  }

  @Override
  public Instrutor get(Long id) {
    return repository.findById(id).get();
  }

  @Override
  public List<Instrutor> getAll() {
      return repository.findAll();
  }

  @Override
  public Instrutor update(Long id, InstrutorUpdateForm formUpdate) {
    Instrutor instrutor = repository.getById(id);
    instrutor.setNome(formUpdate.getNome());
    return repository.save(instrutor);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {
    Instrutor instrutor = repository.findById(id).get();
    return instrutor.getAvaliacoes();
  }

}

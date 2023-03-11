package me.dio.academia.digital.service.impl;

import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.Instrutor;
import me.dio.academia.digital.entity.form.InstrutorForm;
import me.dio.academia.digital.entity.form.InstrutorUpdateForm;
import me.dio.academia.digital.exceptions.InstrutorNotFoundException;
import me.dio.academia.digital.repository.InstrutorRepository;
import me.dio.academia.digital.service.IInstrutorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrutorServiceImpl implements IInstrutorService {
  final private InstrutorRepository repository;

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
    if(repository.findById(id).isPresent()) return repository.findById(id).get();

    throw new InstrutorNotFoundException("Instrutor não encontrado!");
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
    if(repository.findById(id).isPresent()) {
      Instrutor instrutor = repository.findById(id).get();
      return instrutor.getAvaliacoes();
    }
    throw new InstrutorNotFoundException("Instrutor não encontrado!");
  }
}

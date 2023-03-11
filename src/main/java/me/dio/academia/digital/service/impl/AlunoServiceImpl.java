package me.dio.academia.digital.service.impl;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.exceptions.AvaliacaoFisicaNotFoundException;
import me.dio.academia.digital.infra.utils.JavaTimeUtils;
import me.dio.academia.digital.repository.AlunoRepository;
import me.dio.academia.digital.service.IAlunoService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoServiceImpl implements IAlunoService {
  private AlunoRepository repository;

  public AlunoServiceImpl(AlunoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Aluno create(AlunoForm form) {
    Aluno aluno = new Aluno();
    aluno.setNome(form.getNome());
    aluno.setCpf(form.getCpf());
    aluno.setBairro(form.getBairro());
    aluno.setDataDeNascimento(form.getDataDeNascimento());
    aluno.setGenero(form.getGenero());
    return repository.save(aluno);
  }

  @Override
  public Aluno get(Long id) {
    return repository.findById(id).get();
  }

  @Override
  public List<Aluno> getAll(String dataDeNascimento) {
    if(dataDeNascimento == null) {
      return repository.findAll();
    } else {
      LocalDate localDate = LocalDate.parse(dataDeNascimento, JavaTimeUtils.LOCAL_DATE_FORMATTER);
      return repository.findByDataDeNascimento(localDate);
    }

  }

  @Override
  public Aluno update(Long id, AlunoUpdateForm formUpdate) {
    Aluno aluno = repository.getById(id);
    aluno.setNome(formUpdate.getNome());
    aluno.setBairro(formUpdate.getBairro());
    aluno.setDataDeNascimento(formUpdate.getDataDeNascimento());
    return repository.save(aluno);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public String getImc(Long id) {
    AvaliacaoFisica ultimaAvaliacao = getLastAvaliacaoFisica(id);
    double imc = ultimaAvaliacao.getPeso() / Math.pow(ultimaAvaliacao.getAltura(), 2);
    String classificacao;

    if (imc < 17){
      classificacao = "Muito abaixo do peso";
    } else if (imc >= 17 && imc < 18.5){
      classificacao = "Abaixo do peso";
    } else if (imc >= 18.5 && imc < 25){
      classificacao = "Peso normal";
    } else if (imc >= 25 && imc < 30){
      classificacao = "Acima do peso";
    } else if (imc >= 30 && imc < 35){
      classificacao = "Obesidade I";
    } else if (imc >= 35 && imc < 40){
      classificacao = "Obesidade II (severa)";
    } else {
      classificacao = "Obesidade III (mórbida)";
    }
    DecimalFormat formato = new DecimalFormat("#.##");
    return "IMC: " + formato.format(imc) + " --- Classificação" + classificacao;
  }

  @Override
  public AvaliacaoFisica getLastAvaliacaoFisica(Long id) {
    try {
      int lastIndex = repository.getById(id).getAvaliacoes().size() - 1;
      AvaliacaoFisica ultimaAvaliacao = repository.getById(id).getAvaliacoes().get(lastIndex);
      return ultimaAvaliacao;
    }catch (RuntimeException e){
      throw new AvaliacaoFisicaNotFoundException("Produto não encontrado!");
    }
  }

  @Override
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {
    Aluno aluno = repository.findById(id).get();
    return aluno.getAvaliacoes();
  }

}

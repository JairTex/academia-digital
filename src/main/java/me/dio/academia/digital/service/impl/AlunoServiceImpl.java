package me.dio.academia.digital.service.impl;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.exceptions.AlunoNotFoundException;
import me.dio.academia.digital.exceptions.AvaliacaoFisicaNotFoundException;
import me.dio.academia.digital.infra.utils.JavaTimeUtils;
import me.dio.academia.digital.repository.AlunoRepository;
import me.dio.academia.digital.service.IAlunoService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AlunoServiceImpl implements IAlunoService {
  final private AlunoRepository repository;

  public AlunoServiceImpl(AlunoRepository repository) {
    this.repository = repository;
  }

  @Transactional
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
    if(repository.findById(id).isPresent()){
      return repository.findById(id).get();
    }
    throw new AlunoNotFoundException("Aluno não encontrado!");
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
    if(repository.findById(id).isPresent()){
      Aluno aluno = repository.findById(id).get();

      aluno.setNome(formUpdate.getNome());
      aluno.setBairro(formUpdate.getBairro());
      aluno.setDataDeNascimento(formUpdate.getDataDeNascimento());

      return repository.save(aluno);
    }
    throw new AlunoNotFoundException("Aluno não encontrado!");
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public String getImc(Long id) {
    AvaliacaoFisica ultimaAvaliacao = getLastAvaliacaoFisica(id);
    double imc = ultimaAvaliacao.getPeso() / (Math.pow((ultimaAvaliacao.getAltura()/100), 2));
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
    DecimalFormat formato = new DecimalFormat("#,##");
    return "IMC: " + formato.format(imc) + " - Classificação: " + classificacao;
  }

  @Override
  public AvaliacaoFisica getLastAvaliacaoFisica(Long id) {
    if(repository.findById(id).isPresent()){
      if(repository.findById(id).get().getAvaliacoes()!=null) {
        ArrayList<AvaliacaoFisica> avaliacoes =
                new ArrayList<AvaliacaoFisica> (repository.findById(id).get().getAvaliacoes());
        AvaliacaoFisica ultimaAvaliacao = avaliacoes.get(0);

        for (AvaliacaoFisica avaliacao : avaliacoes){
          if(ultimaAvaliacao.getDataDaAvaliacao()
                  .isBefore(avaliacao.getDataDaAvaliacao())){
            ultimaAvaliacao = avaliacao;
          }
        }

        return ultimaAvaliacao;
      }
      throw new AvaliacaoFisicaNotFoundException("Avaliação Fisica não encontrada!");
    }
    throw new AlunoNotFoundException("Aluno não encontrado!");
  }

  @Override
  public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {
    if(repository.findById(id).isPresent()){
      Aluno aluno = repository.findById(id).get();
      if(aluno.getAvaliacoes()!=null) {
        return aluno.getAvaliacoes();
      }else{
        throw new AvaliacaoFisicaNotFoundException("Avaliação Fisica não encontrada!");
      }
    }
    throw new AlunoNotFoundException("Aluno não encontrado!");
  }
}

package me.dio.academia.digital.service.impl;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.Instrutor;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaForm;
import me.dio.academia.digital.entity.form.AvaliacaoFisicaUpdateForm;
import me.dio.academia.digital.exceptions.AlunoNotFoundException;
import me.dio.academia.digital.exceptions.AvaliacaoFisicaNotFoundException;
import me.dio.academia.digital.exceptions.InstrutorNotFoundException;
import me.dio.academia.digital.repository.AlunoRepository;
import me.dio.academia.digital.repository.AvaliacaoFisicaRepository;
import me.dio.academia.digital.repository.InstrutorRepository;
import me.dio.academia.digital.service.IAvaliacaoFisicaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvaliacaoFisicaServiceImpl implements IAvaliacaoFisicaService {
  final private AvaliacaoFisicaRepository avaliacaoFisicaRepository;
  final private AlunoRepository alunoRepository;
  final private InstrutorRepository instrutorRepository;

  public AvaliacaoFisicaServiceImpl(AvaliacaoFisicaRepository avaliacaoFisicaRepository,
                                    AlunoRepository alunoRepository,
                                    InstrutorRepository instrutorRepository)
  {
    this.avaliacaoFisicaRepository = avaliacaoFisicaRepository;
    this.alunoRepository = alunoRepository;
    this.instrutorRepository = instrutorRepository;
  }

  @Override
  @Transactional
  public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
    AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
    if(alunoRepository.findById(form.getAlunoId()).isPresent()){
      Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();
      avaliacaoFisica.setAluno(aluno);
    }else{
      throw new AlunoNotFoundException("Aluno não encontrado!");
    }
    if(instrutorRepository.findById(form.getAvaliadorId()).isPresent()){
      Instrutor instrutor = instrutorRepository.findById(form.getAvaliadorId()).get();
      avaliacaoFisica.setInstrutor(instrutor);
    }else{
      throw new InstrutorNotFoundException("Instrutor não encontrado!");
    }
    avaliacaoFisica.setPeso(form.getPeso());
    avaliacaoFisica.setAltura(form.getAltura());

    return avaliacaoFisicaRepository.save(avaliacaoFisica);
  }

  @Override
  public AvaliacaoFisica get(Long id) {
    if (avaliacaoFisicaRepository.findById(id).isPresent()){
      return avaliacaoFisicaRepository.findById(id).get();
    }
    throw new AvaliacaoFisicaNotFoundException("Avaliação Física não encontrada!");
  }

  @Override
  public List<AvaliacaoFisica> getAll() {
    return avaliacaoFisicaRepository.findAll();
  }

  @Override
  public AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm formUpdate) {
    if (avaliacaoFisicaRepository.findById(id).isPresent()){
      AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaRepository.findById(id).get();
      avaliacaoFisica.setPeso(formUpdate.getPeso());
      avaliacaoFisica.setAltura(avaliacaoFisica.getAltura());
      return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }
    throw new AvaliacaoFisicaNotFoundException("Avaliação Física não encontrada!");
  }

  @Override
  public void delete(Long id) {
    avaliacaoFisicaRepository.deleteById(id);
  }
}

package me.dio.academia.digital.service.impl;

import  me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.Matricula;
import me.dio.academia.digital.entity.form.MatriculaForm;
import me.dio.academia.digital.exceptions.AlunoNotFoundException;
import me.dio.academia.digital.exceptions.MatriculaNotFoundException;
import me.dio.academia.digital.repository.AlunoRepository;
import me.dio.academia.digital.repository.MatriculaRepository;
import me.dio.academia.digital.service.IMatriculaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaServiceImpl implements IMatriculaService {
  final private MatriculaRepository matriculaRepository;

  final private AlunoRepository alunoRepository;

  public MatriculaServiceImpl(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository) {
    this.matriculaRepository = matriculaRepository;
    this.alunoRepository = alunoRepository;
  }

  @Override
  @Transactional
  public Matricula create(MatriculaForm form) {
    if(alunoRepository.findById(form.getAlunoId()).isPresent()){
      Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();
      Matricula matricula = new Matricula();
      matricula.setAluno(aluno);

      return matriculaRepository.save(matricula);
    }
    throw new AlunoNotFoundException("Aluno não encontrado!");
  }

  @Override
  public Matricula get(Long id) {
    if(matriculaRepository.findById(id).isPresent()){
      return matriculaRepository.findById(id).get();
    }
    throw new MatriculaNotFoundException("Matricula não encontrada!");
  }

  @Override
  public List<Matricula> getAll(String bairro) {
    if(bairro==null){
      return matriculaRepository.findAll();
    }else{
      return matriculaRepository.findAlunosMatriculadosBairro(bairro);
    }

  }

  @Override
  public void delete(Long id) {
    matriculaRepository.deleteById(id);
  }
}

package me.dio.academia.digital.service;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.Instrutor;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.entity.form.InstrutorForm;
import me.dio.academia.digital.entity.form.InstrutorUpdateForm;

import java.util.List;

public interface IInstrutorService {
  /**
   * Cria um Instrutor e salva no banco de dados.
   * @param form formulário referente aos dados para criação de um Instrutor no banco de dados.
   * @return Instrutor recém-criado.
   */
  Instrutor create(InstrutorForm form);

  /**
   * Retorna um Instrutor que está no banco de dados de acordo com seu Id.
   * @param id id do Instrutor que será exibido.
   * @return Instrutor de acordo com o Id fornecido.
   */
  Instrutor get(Long id);

  /**
   * Retorna os Instrutores que estão no banco de dados.
   * @return Uma lista os Instrutores que estão salvas no DB.
   */
  List<Instrutor> getAll();

  /**
   * Atualiza o Instrutor.
   * @param id id do Instrutor que será atualizado.
   * @param formUpdate formulário referente aos dados necessários para atualização do Instrutor
   * no banco de dados.
   * @return Instrutor recém-atualizado.
   */
  Instrutor update(Long id, InstrutorUpdateForm formUpdate);

  /**
   * Deleta um Instrutor específico.
   * @param id id do Instrutor que será removido.
   */
  void delete(Long id);

  /**
   *
   * @param id id do Instrutor que será recuperada a lista de avaliações
   * @return uma lista com todas as avaliações feitas pelo Instrutor de acordo com o Id
   */
  List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id);

}

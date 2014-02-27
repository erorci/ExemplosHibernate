package com.livro.capitulo3.crudxml;

import java.sql.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.livro.capitulo3.conexao.HibernateUtil;

public class ContatoCrudXML {

	public void salvar(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possível inserir o contato.\nErro: "
					+ e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out
						.println("Falha ao fechar operação de inserção.\nErro: "
								+ e.getMessage());
			}
		}
	}

	public void atualizar(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			sessao.update(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possível alterar o contato.\nErro: "
					+ e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out
						.println("Falha ao fechar operação de alteração.\nErro: "
								+ e.getMessage());
			}
		}
	}

	public void excluir(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.delete(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possível excluir o contato.\nErro: "
					+ e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out
						.println("Falha ao fechar operação de alteração.\nErro: "
								+ e.getMessage());
			}
		}
	}

	public List<Contato> listar() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Contato> resultado = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato");
			resultado = consulta.list();
			transacao.commit();
			return resultado;
		} catch (HibernateException e) {
			System.out.println("Não foi possível excluir o contato.\nErro: "
					+ e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out
						.println("Falha ao fechar operação de alteração.\nErro: "
								+ e.getMessage());
			}
		}
	}

	public Contato buscaContato(long valor) {
		Session sessao = null;
		Contato contato = null;
		Transaction transacao = null;
		Query consulta = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao
					.createQuery("from Contato where codigo = :parametro");
			consulta.setLong("parametro", valor);
			contato = (Contato) consulta.uniqueResult();
			transacao.commit();
			return contato;
		} catch (HibernateException e) {
			System.out.println("Não foi possível excluir o contato.\nErro: "
					+ e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out
						.println("Falha ao fechar operação de alteração.\nErro: "
								+ e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		ContatoCrudXML contatoCrudXML = new ContatoCrudXML();
		String[] nomes = { "Emerson", "Rodrigo", "Orci" };
		String[] fones = { "(47)2222-2222", "(47)23232-5454", "(19)4542-0909" };
		String[] emails = { "emerson@teste.com", "rodrigo@teste.com",
				"orci@teste.com" };
		String[] obs = { "Novo cliente", "Cliente bom pagador", "Ligar urgente" };
		Contato contato = null;
		for (int i = 0; i < nomes.length; i++) {
			contato = new Contato();
			contato.setNome(nomes[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setObservacao(obs[i]);
			contatoCrudXML.salvar(contato);
		}
		System.out.println("Total de registros na tabela de Contatos "
				+ contatoCrudXML.listar().size());
	}

}

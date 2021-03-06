package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import conexao.Conexao;
import model.Cidade;
import model.Instituicao;

public class InstituicaoDAO {
	private Connection conexao;
	private PreparedStatement stmt;

	public InstituicaoDAO() {
        this.conexao = new Conexao().getConexao();
    }

    public boolean inserirInstituicao(Instituicao instituicao){
        try {
            String sql = "INSERT INTO instituicao (nome, telefone, endereco) VALUES (?, ?, ?)";
            this.stmt = this.conexao.prepareStatement(sql);
            this.stmt.setString(1, instituicao.getNome());
            this.stmt.setString(2, instituicao.getTelefone());
            this.stmt.setString(3, instituicao.getEndereco());
            this.stmt.execute();
            this. stmt.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	private boolean editarInstituicao(String campo, String valor, int id){
        try {
            String sql = "UPDATE instituicao SET " + campo + " = ? WHERE id = ?";
        	this.stmt = this.conexao.prepareStatement(sql);
        	this.stmt.setString(1, valor);
        	this.stmt.setInt(2, id);
        	this.stmt.execute();
        	this.stmt.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editarNome(Instituicao instituicao){
		return this.editarInstituicao("nome", instituicao.getNome(), instituicao.getId());
	}

	public boolean editarTelefone(Instituicao instituicao){
		return this.editarInstituicao("telefone", instituicao.getTelefone(), instituicao.getId());
	}

	public boolean editarEndereco(Instituicao instituicao){
		return this.editarInstituicao("endereco", instituicao.getEndereco(), instituicao.getId());
	}

	public boolean editarIdCidade(Instituicao instituicao, Cidade cidade){
        try {
            String sql = "UPDATE instituicao SET id_cidade = ? WHERE id = ?";
        	this.stmt = this.conexao.prepareStatement(sql);
            this.stmt.setInt(1, cidade.getId());
            this.stmt.setInt(2, instituicao.getId());
            this.stmt.execute();
            this.stmt.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	private ArrayList<Instituicao> consutarInstituicoes(String campo, String valor){
		try {
			String sql = "SELECT * FROM instituicao WHERE " + campo + " = ?";
			this.stmt = this.conexao.prepareStatement(sql);
			this.stmt.setString(1, valor);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Instituicao> instituicoes = new ArrayList<Instituicao>();
            Instituicao instituicao = new Instituicao();
            boolean aux1 = true;
            while(rs.next()) {
            	aux1 = false;
                instituicao = new Instituicao(rs.getInt("id"), rs.getString("nome"), rs.getString("cep"), rs.getString("uf"));
                instituicoes.add(instituicao);
            }
            if(aux1) {
            	instituicoes.add(instituicao);
            }
            this.stmt.close();
            return instituicoes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	public ArrayList<Instituicao> consultarNome(Instituicao instituicao){
		return this.consutarInstituicoes("nome", instituicao.getNome());
	}

	public Instituicao consultarTelefone(Instituicao instituicao){
		return this.consutarInstituicoes("telefone", instituicao.getTelefone()).get(0);
	}

	public Instituicao consultarEndereco(Instituicao instituicao){
		return this.consutarInstituicoes("endereco", instituicao.getEndereco()).get(0);
	}
	public Instituicao consultarTudo(Instituicao instituicaoSel){
		try {
			String sql = "SELECT * FROM instituicao WHERE nome = ? AND telefone = ? AND endereco = ?";
			this.stmt = this.conexao.prepareStatement(sql);
			this.stmt.setString(1, instituicaoSel.getNome());
			this.stmt.setString(2, instituicaoSel.getTelefone());
			this.stmt.setString(3, instituicaoSel.getEndereco());
            ResultSet rs = stmt.executeQuery();
            Instituicao instituicao = new Instituicao();
            if(rs.next()) {
            	instituicao = new Instituicao(rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("endereco"));
            }
            this.stmt.close();
            return instituicao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public ArrayList<Instituicao> consutarIdCidade(Cidade cidade){
		try {
			String sql = "SELECT * FROM instituicao WHERE id_cidade = ?";
            this.stmt = this.conexao.prepareStatement(sql);
            this.stmt.setInt(1, cidade.getId());
            ResultSet rs = this.stmt.executeQuery();
            ArrayList<Instituicao> instituicoes = new ArrayList<Instituicao>();
            Instituicao instituicao = new Instituicao();
            boolean aux1 = true;
            while(rs.next()) {
            	aux1 = false;
                instituicao = new Instituicao(rs.getInt("id"), rs.getString("nome"), rs.getString("cep"), rs.getString("uf"));
                instituicoes.add(instituicao);
            }
            if(aux1) {
            	instituicoes.add(instituicao);
            }
            this.stmt.close();
            return instituicoes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	public ArrayList<Instituicao> listarInstituicoes(){
		try {
			String sql = "SELECT * FROM instituicao";
			this.stmt = this.conexao.prepareStatement(sql);
            ResultSet rs = this.stmt.executeQuery();
            boolean aux = true; 
            ArrayList<Instituicao> instituicoes = new ArrayList<Instituicao>();
            Instituicao instituicao = new Instituicao();
            while(rs.next()) {
                aux = false;
            	instituicao = new Instituicao(rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("endereco"));
                instituicoes.add(instituicao);
            }
            if(aux) {
            	instituicoes.add(instituicao);
            }
            this.stmt.close();
            return instituicoes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public Instituicao consultarId(int id){
		try {
			String sql = "SELECT * FROM ponto WHERE id = ?";
			this.stmt = this.conexao.prepareStatement(sql);
			this.stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Instituicao instituicao = new Instituicao();
            if(rs.next()) {
            	instituicao = new Instituicao(rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("endereco"));
            }
            this.stmt.close();
            return instituicao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public boolean excluirInstituicao(Instituicao instituicao){
        try {
            String sql = "DELETE instituicao WHERE id = ?";
        	this.stmt = this.conexao.prepareStatement(sql);
        	this.stmt.setInt(1, instituicao.getId());
        	this.stmt.execute();
        	this.stmt.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}

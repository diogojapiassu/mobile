package br.ufg.inf.fs.android.persist;

import java.util.Date;

public class Notificacao {
 
    private int id;
    private String descricao;
    private Date data;
    private Boolean is_lida;
    private Boolean is_publica;
    private int grupo_notificacao;
    
    public Notificacao(){
    	
    }
    
	public Notificacao(int id, String descricao, Date data, Boolean is_lida,
			Boolean is_publica, int grupo_notificacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.data = data;
		this.is_lida = is_lida;
		this.is_publica = is_publica;
		this.grupo_notificacao = grupo_notificacao;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Boolean getIs_lida() {
		return is_lida;
	}
	public void setIs_lida(Boolean is_lida) {
		this.is_lida = is_lida;
	}
	public Boolean getIs_publica() {
		return is_publica;
	}
	public void setIs_publica(Boolean is_publica) {
		this.is_publica = is_publica;
	}
	public int getGrupo_notificacao() {
		return grupo_notificacao;
	}
	public void setGrupo_notificacao(int grupo_notificacao) {
		this.grupo_notificacao = grupo_notificacao;
	}
}
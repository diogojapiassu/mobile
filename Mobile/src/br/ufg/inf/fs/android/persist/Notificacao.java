package br.ufg.inf.fs.android.persist;

import java.util.Date;

public class Notificacao {
 
    private int id;
    private String descricao;
    //private Date data;
    private int is_lida;
    private int is_publica;
    private int grupo_notificacao;
    
    public Notificacao(){
    	
    }
    
	public Notificacao(
			int id, 
			String descricao, 
			//Date data, 
			int is_lida,
			int is_publica, 
			int grupo_notificacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		//this.data = data;
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
	/*public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}*/
	public int getIs_lida() {
		return is_lida;
	}
	public void setIs_lida(int is_lida) {
		this.is_lida = is_lida;
	}
	public int getIs_publica() {
		return is_publica;
	}
	public void setIs_publica(int is_publica) {
		this.is_publica = is_publica;
	}
	public int getGrupo_notificacao() {
		return grupo_notificacao;
	}
	public void setGrupo_notificacao(int grupo_notificacao) {
		this.grupo_notificacao = grupo_notificacao;
	}
}
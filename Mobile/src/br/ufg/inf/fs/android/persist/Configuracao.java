package br.ufg.inf.fs.android.persist;

public class Configuracao {
 
    private int id;
    private int id_usuario;
    private int exibir_persistencia;
    private int exibir_mobile;
    private int somente_nao_lidas;
    
    public Configuracao(
    		int id, 
    		int id_usuario, 
    		int exibir_persistencia,
			int exibir_mobile,
			int somente_nao_lidas) {
		this.id = id;
		this.id_usuario = id_usuario;
		this.exibir_persistencia = exibir_persistencia;
		this.exibir_mobile = exibir_mobile;
		this.somente_nao_lidas = somente_nao_lidas;
	}

	public Configuracao() {
    	
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public int getExibir_persistencia() {
		return exibir_persistencia;
	}
	public void setExibir_persistencia(int exibir_persistencia) {
		this.exibir_persistencia = exibir_persistencia;
	}
	public int getExibir_mobile() {
		return exibir_mobile;
	}
	public void setExibir_mobile(int exibir_mobile) {
		this.exibir_mobile = exibir_mobile;
	}

	public int getSomente_nao_lidas() {
		return somente_nao_lidas;
	}

	public void setSomente_nao_lidas(int somente_nao_lidas) {
		this.somente_nao_lidas = somente_nao_lidas;
	}
}
package br.ufg.inf.fs.android.persist;


public class Notificacao {
 
    private int id;
    private String descricao;
    private String detalhes;
    //private Date data;
    private String remetente;
    private String data_string;
    private Long data_long;
    private int is_lida;
    private int is_publica;
    private int grupo_notificacao;
    
    public Notificacao(){
    	
    }
    
	public Notificacao(
			int id, 
			String descricao, 
			String detalhes, 
			String remetente, 
			String data_string,
			Long data_long,
			//Date data, 
			int is_lida,
			int is_publica, 
			int grupo_notificacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.detalhes = detalhes;
		this.remetente = remetente;
		this.data_string = data_string;
		this.data_long = data_long;
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
	
	/**
	 * 1 - Lida
	 * 0 - Não lida
	 * @return
	 */
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

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getData_string() {
		return data_string;
	}

	public void setData_string(String data_string) {
		this.data_string = data_string;
	}

	public Long getData_long() {
		return data_long;
	}

	public void setData_long(Long data_long) {
		this.data_long = data_long;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
}
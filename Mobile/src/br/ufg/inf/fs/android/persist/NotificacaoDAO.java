package br.ufg.inf.fs.android.persist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotificacaoDAO {
	 
	 
    public static final String NOME_TABELA = "Notificacao";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_DETALHES = "detalhes";
    public static final String COLUNA_REMETENTE = "remetente";
    public static final String COLUNA_DATA_STRING = "data_string";
    public static final String COLUNA_DATA_LONG = "data_long";
    //public static final String COLUNA_DATA = "data";
    public static final String COLUNA_IS_LIDA = "is_lida";
    public static final String COLUNA_IS_PUBLICA = "is_publica";
    public static final String COLUNA_GRUPO_NOTIFICACAO = "grupo_notificacao";
 
 
    public static final String SCRIPT_CRIACAO_TABELA_NOTIFICACAO = 
    		"CREATE TABLE " + NOME_TABELA + "(" + 
	    		COLUNA_ID + " INTEGER PRIMARY KEY," + 
	    		COLUNA_DESCRICAO + " TEXT," + 
	    		COLUNA_DETALHES + " TEXT," + 
	    		COLUNA_REMETENTE + " TEXT," + 
	    		COLUNA_DATA_STRING + " TEXT," + 
	    		COLUNA_DATA_LONG + " INTEGER," + 
	            //COLUNA_DATA + " DATE," + 
	    		COLUNA_IS_LIDA + " INTEGER, " +
	    		COLUNA_IS_PUBLICA + " INTEGER, " +
	    		COLUNA_GRUPO_NOTIFICACAO + " INTEGER" + ")";
 
    public static final String SCRIPT_DELECAO_TABELA_NOTIFICACAO =  "DROP TABLE IF EXISTS " + NOME_TABELA;
 
 
    private SQLiteDatabase dataBase = null;
 
 
    private static NotificacaoDAO instance;
     
    public static NotificacaoDAO getInstance(Context context) {
        if(instance == null)
            instance = new NotificacaoDAO(context);
        return instance;
    }
 
    private NotificacaoDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
        //persistenceHelper.onCreate(dataBase);
    }
 
    public void salvar(Notificacao notificacao) {
        ContentValues values = gerarContentValeuesNotificacao(notificacao);
        dataBase.insert(NOME_TABELA, null, values);
    }
 
    public Notificacao getNotificacaoPorId(String id){
    	abrirConexao();
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA + " WHERE " +
        						COLUNA_ID + " = " + id;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Notificacao> notificacoes = construirNotificacaoPorCursor(cursor);
 
        if(notificacoes != null && !notificacoes.isEmpty()){
        	return notificacoes.get(0);
        }
        
        return null;
    }
    
    public List<Notificacao> recuperarTodos() {
    	abrirConexao();
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Notificacao> notificacoes = construirNotificacaoPorCursor(cursor);
 
        return notificacoes;
    }
    
    public List<Notificacao> recuperarNotificacoesPublicas(int ordemDataLista) {
    	abrirConexao();
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA + " WHERE " +
        		COLUNA_GRUPO_NOTIFICACAO + " = 0";
        
        if(ordemDataLista == 1){
        	queryReturnAll += " ORDER BY " + COLUNA_DATA_LONG + " ASC ";
    	}else if(ordemDataLista == 2){
    		queryReturnAll += " ORDER BY " + COLUNA_DATA_LONG + " DESC ";
    	}
        
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Notificacao> notificacoes = construirNotificacaoPorCursor(cursor);
 
        return notificacoes;
    }
    
    public List<Notificacao> recuperarNotificacoesDoUsuario(Configuracao configuracao, int ordemDataLista) {
    	abrirConexao();
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE ";
        
        //Caso tenha alguma configuração:
        if((configuracao.getExibir_persistencia() + configuracao.getExibir_mobile()) > 0){
        	
        	if(configuracao.getExibir_persistencia() > 0){
        		query += COLUNA_GRUPO_NOTIFICACAO + " =  1";
        		
        		if(configuracao.getExibir_mobile() > 0){
        			query += " OR " + COLUNA_GRUPO_NOTIFICACAO + " =  2";
        		}
        		
        	}else if(configuracao.getExibir_mobile() > 0){
        		query += COLUNA_GRUPO_NOTIFICACAO + " =  2";
        	}
        	
        	if(ordemDataLista == 1){
        		query += " ORDER BY " + COLUNA_DATA_LONG + " ASC ";
        	}else if(ordemDataLista == 2){
        		query += " ORDER BY " + COLUNA_DATA_LONG + " DESC ";
        	}
        			
        }else{
        	//Não possui nenhuma disciplina vinculada:
        	return new ArrayList<Notificacao>();
        }
        
        Cursor cursor = dataBase.rawQuery(query, null);
        List<Notificacao> notificacoes = construirNotificacaoPorCursor(cursor);
 
        return notificacoes;
    }
    
	private void abrirConexao() {
		if(!dataBase.isOpen()){
    		dataBase = dataBase.openDatabase(dataBase.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    	}
	}
 
    public void deletar(Usuario usuario) {
 
        String[] valoresParaSubstituir = {
                String.valueOf(usuario.getId())
        };
 
        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }
 
    public void editar(Notificacao notificacao) {
        abrirConexao();
        
    	ContentValues valores = gerarContentValeuesNotificacao(notificacao);
 
        String[] valoresParaSubstituir = {
                String.valueOf(notificacao.getId())
        };
 
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
 
    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close(); 
    }
 
 
    private List<Notificacao> construirNotificacaoPorCursor(Cursor cursor) {
        List<Notificacao> notificacoes = new ArrayList<Notificacao>();
        if(cursor == null)
            return notificacoes;
         
        try {
 
            if (cursor.moveToFirst()) {
                do {
 
                    int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexDescricao = cursor.getColumnIndex(COLUNA_DESCRICAO);
                    int indexDetalhes = cursor.getColumnIndex(COLUNA_DETALHES);
                    int indexRemetente = cursor.getColumnIndex(COLUNA_REMETENTE);
                    int indexDataString = cursor.getColumnIndex(COLUNA_DATA_STRING);
                    int indexDataLong = cursor.getColumnIndex(COLUNA_DATA_LONG);
                    int indexIsLida = cursor.getColumnIndex(COLUNA_IS_LIDA);
                    int indexIsPublica = cursor.getColumnIndex(COLUNA_IS_PUBLICA);
                    int indexGrupoNotificacao = cursor.getColumnIndex(COLUNA_GRUPO_NOTIFICACAO);
 
                    int id = cursor.getInt(indexID);
                    String descricao = cursor.getString(indexDescricao);
                    String detalhes = cursor.getString(indexDetalhes);
                    String remetente = cursor.getString(indexRemetente);
                    String dataString = cursor.getString(indexDataString);
                    Long dataLong = cursor.getLong(indexDataLong);
                    int isLida = cursor.getInt(indexIsLida);
                    int isPublica = cursor.getInt(indexIsPublica);
                    int grupoNotificacao = cursor.getInt(indexGrupoNotificacao);
 
                    Notificacao notificacao = new Notificacao(id, descricao, detalhes, remetente, dataString, dataLong, isLida, isPublica, grupoNotificacao);
                    notificacoes.add(notificacao);
 
                } while (cursor.moveToNext());
            }
             
        } finally {
            cursor.close();
        }
        return notificacoes;
    }
 
    private ContentValues gerarContentValeuesNotificacao(Notificacao notificacao) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_DESCRICAO, notificacao.getDescricao());
        values.put(COLUNA_DETALHES, notificacao.getDetalhes());
        values.put(COLUNA_REMETENTE, notificacao.getRemetente());
        values.put(COLUNA_DATA_STRING, notificacao.getData_string());
        values.put(COLUNA_DATA_LONG, notificacao.getData_long());
        //values.put(COLUNA_DATA, notificacao.getData().toString());
        values.put(COLUNA_IS_LIDA, notificacao.getIs_lida());
        values.put(COLUNA_IS_PUBLICA, notificacao.getIs_publica());
        values.put(COLUNA_GRUPO_NOTIFICACAO, notificacao.getGrupo_notificacao());
 
        return values;
    }
}

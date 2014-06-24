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
    //public static final String COLUNA_DATA = "data";
    public static final String COLUNA_IS_LIDA = "is_lida";
    public static final String COLUNA_IS_PUBLICA = "is_publica";
    public static final String COLUNA_GRUPO_NOTIFICACAO = "grupo_notificacao";
 
 
    public static final String SCRIPT_CRIACAO_TABELA_NOTIFICACAO = 
    		"CREATE TABLE " + NOME_TABELA + "(" + 
	    		COLUNA_ID + " INTEGER PRIMARY KEY," + 
	    		COLUNA_DESCRICAO + " TEXT," + 
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
                    int indexIsLida = cursor.getColumnIndex(COLUNA_IS_LIDA);
                    int indexIsPublica = cursor.getColumnIndex(COLUNA_IS_PUBLICA);
                    int indexGrupoNotificacao = cursor.getColumnIndex(COLUNA_GRUPO_NOTIFICACAO);
 
                    int id = cursor.getInt(indexID);
                    String descricao = cursor.getString(indexDescricao);
                    int isLida = cursor.getInt(indexIsLida);
                    int isPublica = cursor.getInt(indexIsPublica);
                    int grupoNotificacao = cursor.getInt(indexGrupoNotificacao);
 
                    Notificacao notificacao = new Notificacao(id, descricao, isLida, isPublica, grupoNotificacao);
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
        //values.put(COLUNA_DATA, notificacao.getData().toString());
        values.put(COLUNA_IS_LIDA, notificacao.getIs_lida());
        values.put(COLUNA_IS_PUBLICA, notificacao.getIs_publica());
        values.put(COLUNA_GRUPO_NOTIFICACAO, notificacao.getGrupo_notificacao());
 
        return values;
    }
}

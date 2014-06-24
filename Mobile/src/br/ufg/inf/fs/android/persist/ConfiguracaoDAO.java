package br.ufg.inf.fs.android.persist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConfiguracaoDAO {
	 
    public static final String NOME_TABELA = "Configuracao";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ID_USUARIO = "id_usuario";
    public static final String COLUNA_EXIBIR_PERSISTENCIA = "exibir_persistencia";
    public static final String COLUNA_EXIBIR_MOBILE = "exibir_mobile";
 
    public static final String SCRIPT_CRIACAO_TABELA_CONFIGURACAO = 
    		"CREATE TABLE " + NOME_TABELA + "(" +
	            COLUNA_ID + " INTEGER PRIMARY KEY," + 
	    		COLUNA_ID_USUARIO + " INTEGER," + 
	            COLUNA_EXIBIR_MOBILE + " INTEGER," +
	            COLUNA_EXIBIR_PERSISTENCIA + " INTEGER" + ")";
 
    public static final String SCRIPT_DELECAO_TABELA_CONFIGURACAO =  
    		"DROP TABLE IF EXISTS " + NOME_TABELA;
 
    private SQLiteDatabase dataBase = null;
 
    private static ConfiguracaoDAO instance;
     
    public static ConfiguracaoDAO getInstance(Context context) {
        if(instance == null)
            instance = new ConfiguracaoDAO(context);
        return instance;
    }
 
    private ConfiguracaoDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
        //persistenceHelper.onCreate(dataBase);
    }
 
    public void salvar(Configuracao configuracao) {
        ContentValues values = gerarContentValeuesConfiguracao(configuracao);
        dataBase.insert(NOME_TABELA, null, values);
    }
 
    public List<Configuracao> recuperarTodos() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Configuracao> configuracoes = construirConfiguracaoPorCursor(cursor);
 
        return configuracoes;
    }
    
    public Configuracao getConfiguracaoUsuario(int idUsuario){
    	String queryReturnAll = 
    			"SELECT * FROM " + NOME_TABELA + " WHERE "
    			+ COLUNA_ID_USUARIO + " = " + idUsuario;
    	//String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
    	abrirConexao();
    	
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Configuracao> configuracoes = construirConfiguracaoPorCursor(cursor);
 
        if(configuracoes != null && !configuracoes.isEmpty()){
        	return configuracoes.get(0);
        }
        
        return null;
    }

	private void abrirConexao() {
		if(!dataBase.isOpen()){
    		dataBase = dataBase.openDatabase(dataBase.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    	}
	}
 
    public void deletar(Configuracao configuracao) {
 
        String[] valoresParaSubstituir = {
                String.valueOf(configuracao.getId())
        };
 
        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }
 
    public void editar(Configuracao configuracao) {
        abrirConexao();
    	ContentValues valores = gerarContentValeuesConfiguracao(configuracao);
 
        String[] valoresParaSubstituir = {
                String.valueOf(configuracao.getId())
        };
 
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
 
    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close(); 
    }
 
 
    private List<Configuracao> construirConfiguracaoPorCursor(Cursor cursor) {
        List<Configuracao> configuracoes = new ArrayList<Configuracao>();
        if(cursor == null)
            return configuracoes;
         
        try {
 
            if (cursor.moveToFirst()) {
                do {
 
                    int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexIdUsuario = cursor.getColumnIndex(COLUNA_ID_USUARIO);
                    int indexExibirPersistencia = cursor.getColumnIndex(COLUNA_EXIBIR_PERSISTENCIA);
                    int indexExibirMobile = cursor.getColumnIndex(COLUNA_EXIBIR_MOBILE);
 
                    int id = cursor.getInt(indexID);
                    int idUsuario = cursor.getInt(indexIdUsuario);
                    int exibirPersistencia = cursor.getInt(indexExibirPersistencia);
                    int exibirMobile = cursor.getInt(indexExibirMobile);
 
                    Configuracao configuracao = new Configuracao(id, idUsuario, exibirPersistencia, exibirMobile);
 
                    configuracoes.add(configuracao);
 
                } while (cursor.moveToNext());
            }
             
        } finally {
            cursor.close();
        }
        return configuracoes;
    }
 
    private ContentValues gerarContentValeuesConfiguracao(Configuracao configuracao) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_ID_USUARIO, configuracao.getId_usuario());
        values.put(COLUNA_EXIBIR_PERSISTENCIA, configuracao.getExibir_persistencia());
        values.put(COLUNA_EXIBIR_MOBILE, configuracao.getExibir_mobile());
 
        return values;
    }
}

package br.ufg.inf.fs.android.persist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
	 
	 
    public static final String NOME_TABELA = "Usuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LOGIN = "login";
    public static final String COLUNA_SENHA = "senha";
 
 
    public static final String SCRIPT_CRIACAO_TABELA_USUARIOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY," + COLUNA_NOME + " TEXT," + COLUNA_SENHA + " TEXT,"
            + COLUNA_LOGIN + " TEXT" + ")";
 
    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
 
 
    private SQLiteDatabase dataBase = null;
 
 
    private static UsuarioDAO instance;
     
    public static UsuarioDAO getInstance(Context context) {
        if(instance == null)
            instance = new UsuarioDAO(context);
        return instance;
    }
 
    private UsuarioDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
        persistenceHelper.onCreate(dataBase);
    }
    
    /*public void executarCargaInicial(Context context) {
        getInstance(context);
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        persistenceHelper.executarCargaInicial(dataBase, context);
    }*/
 
    public void salvar(Usuario usuario) {
        ContentValues values = gerarContentValeuesUsuario(usuario);
        dataBase.insert(NOME_TABELA, null, values);
    }
 
    public List<Usuario> recuperarTodos() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Usuario> usuarios = construirUsuarioPorCursor(cursor);
 
        return usuarios;
    }
    
    public Usuario getUsuario(String login, String senha){
    	String queryReturnAll = "SELECT * FROM " + NOME_TABELA + " WHERE "
    			+ COLUNA_LOGIN + " = '" + login + "' AND " + COLUNA_SENHA
    			+ " = '" + senha + "'";
    	//String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
    	
    	abrirConexao();
    	
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Usuario> usuarios = construirUsuarioPorCursor(cursor);
 
        if(usuarios != null && !usuarios.isEmpty()){
        	return usuarios.get(0);
        }
        
        return null;
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
 
    public void editar(Usuario usuario) {
        ContentValues valores = gerarContentValeuesUsuario(usuario);
 
        String[] valoresParaSubstituir = {
                String.valueOf(usuario.getId())
        };
 
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
 
    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close(); 
    }
 
 
    private List<Usuario> construirUsuarioPorCursor(Cursor cursor) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        if(cursor == null)
            return usuarios;
         
        try {
 
            if (cursor.moveToFirst()) {
                do {
 
                    int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
                    int indexLogin = cursor.getColumnIndex(COLUNA_LOGIN);
                    int indexSenha = cursor.getColumnIndex(COLUNA_SENHA);
 
                    int id = cursor.getInt(indexID);
                    String nome = cursor.getString(indexNome);
                    String login = cursor.getString(indexLogin);
                    String senha = cursor.getString(indexSenha);
 
                    Usuario usuario = new Usuario(id, nome, login, senha);
 
                    usuarios.add(usuario);
 
                } while (cursor.moveToNext());
            }
             
        } finally {
            cursor.close();
        }
        return usuarios;
    }
 
    private ContentValues gerarContentValeuesUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
 
        return values;
    }
}

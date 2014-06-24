package br.ufg.inf.fs.android.persist;

import java.util.Date;

import br.ufg.inf.fs.android.util.UtilidadesData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View.OnCreateContextMenuListener;

public class PersistenceHelper extends SQLiteOpenHelper {
 
    public static final String NOME_BANCO = "NotificaUniversidade";
    public static final int VERSAO =  1;
    //private boolean isCargaInicialExecutada = false;
     
    private static PersistenceHelper instance;
     
    private PersistenceHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }
     
    public static PersistenceHelper getInstance(Context context) {
        if(instance == null){
        	instance = new PersistenceHelper(context);
        }
         
        return instance;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(UsuarioDAO.SCRIPT_DELECAO_TABELA);
        db.execSQL(UsuarioDAO.SCRIPT_CRIACAO_TABELA_USUARIOS);
        
        db.execSQL(NotificacaoDAO.SCRIPT_DELECAO_TABELA_NOTIFICACAO);
        db.execSQL(NotificacaoDAO.SCRIPT_CRIACAO_TABELA_NOTIFICACAO);
        
        db.execSQL(ConfiguracaoDAO.SCRIPT_DELECAO_TABELA_CONFIGURACAO);
        db.execSQL(ConfiguracaoDAO.SCRIPT_CRIACAO_TABELA_CONFIGURACAO);
    }
    
    /*public void executarCargaInicial(SQLiteDatabase db, Context context) {
    	//if(!isCargaInicialExecutada){
    		db.execSQL(UsuarioDAO.SCRIPT_DELECAO_TABELA);
            db.execSQL(UsuarioDAO.SCRIPT_CRIACAO_TABELA_USUARIOS);
            
            db.execSQL(NotificacaoDAO.SCRIPT_DELECAO_TABELA_NOTIFICACAO);
            db.execSQL(NotificacaoDAO.SCRIPT_CRIACAO_TABELA_NOTIFICACAO);
            
            realizarCargaTabelaUsuarios(context);
            realizarCargaTabelaNotificacoes(context);
            
           // isCargaInicialExecutada = true;
    	//}
    }*/
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(UsuarioDAO.SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }
 
}
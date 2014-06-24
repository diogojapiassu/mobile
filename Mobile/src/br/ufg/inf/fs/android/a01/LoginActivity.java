package br.ufg.inf.fs.android.a01;

import java.util.List;

import br.ufg.inf.fs.android.persist.Notificacao;
import br.ufg.inf.fs.android.persist.NotificacaoDAO;
import br.ufg.inf.fs.android.persist.Usuario;
import br.ufg.inf.fs.android.persist.UsuarioDAO;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		realizarCargaTabelaUsuarios();
		realizarCargaTabelaNotificacoes();
	}
	
	private void realizarCargaTabelaNotificacoes() {
		Notificacao notificacao1 = new Notificacao(1, "Recesso no dia 23/06/2014", 0, 1, 0);
		Notificacao notificacao2 = new Notificacao(2, "Prova de Persistência", 0, 0, 0);
		Notificacao notificacao3 = new Notificacao(3, "Trabalho de Mobile", 0, 0, 0);
		
		NotificacaoDAO notificacaoDAO =  NotificacaoDAO.getInstance(getBaseContext());
		
		notificacaoDAO.salvar(notificacao1);
		notificacaoDAO.salvar(notificacao2);
		notificacaoDAO.salvar(notificacao3);
		
		notificacaoDAO.fecharConexao();
	}

	public void realizarCargaTabelaUsuarios() {
        
        //Salva usuário padrão para acesso ao sistema:
    	Usuario usuario = new Usuario(1, "Diogo", "diogo", "12345");
    	Usuario usuario2 = new Usuario(2, "Fábio", "fabio", "54321");
        UsuarioDAO usuarioDAO =  UsuarioDAO.getInstance(getBaseContext());
         
        usuarioDAO.salvar(usuario);
        usuarioDAO.salvar(usuario2);
         
        //List<Usuario> usuariosNaBase = usuarioDAO.recuperarTodos();
        //assertFalse(usuariosNaBase.isEmpty());
         
        //Usuario usuarioRecuperado = usuariosNaBase.get(0);
        //usuarioRecuperado.setNome("Diogo Japiassu");
         
        //usuarioDAO.editar(usuarioRecuperado);
         
        //Usuario usuarioEditado = usuarioDAO.recuperarTodos().get(0);
         
        //assertSame(usuarioRecuperado.getId(), usuarioEditado.getId());
        //assertNotSame(usuario.getNome(), usuarioEditado.getNome());
         
        //usuarioDAO.deletar(usuarioEditado);
         
        //assertTrue(usuarioDAO.recuperarTodos().isEmpty());
         
        //usuarioDAO.fecharConexao();
         
    }
	
	public void onClickLogin(View view) {
		Intent intent = new Intent(this, AutenticaActivity.class);
		startActivity(intent);
	}
	
	public void onClickAcessoPublico(View view) {
		Intent intent = new Intent(this, ListaDeMensagensActivity.class);
		//Passar o id do usuário, para recuperar suas mensagens
		intent.putExtra("idUsuario", -1);
		intent.putExtra("isUsuarioPublico", true);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			return rootView;
		}
	}

}

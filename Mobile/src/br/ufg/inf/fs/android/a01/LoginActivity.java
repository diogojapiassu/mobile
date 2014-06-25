package br.ufg.inf.fs.android.a01;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import br.ufg.inf.fs.android.persist.Configuracao;
import br.ufg.inf.fs.android.persist.ConfiguracaoDAO;
import br.ufg.inf.fs.android.persist.Notificacao;
import br.ufg.inf.fs.android.persist.NotificacaoDAO;
import br.ufg.inf.fs.android.persist.Usuario;
import br.ufg.inf.fs.android.persist.UsuarioDAO;
import br.ufg.inf.fs.android.util.UtilidadesData;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		executarCargaInicial();
		//realizarCargaTabelaUsuarios();
		//realizarCargaTabelaNotificacoes();
	}
	
	private void executarCargaInicial() {
		if(!isCargaJaExecutada()){
			realizarCargaTabelaUsuarios();
			realizarCargaTabelaNotificacoes();
			realizarCargaTabelaConfiguracao();
		}
	}

	private void realizarCargaTabelaConfiguracao() {
		//Configurações Diogo:
		Configuracao configuracao1 = new Configuracao(1, 1, 1, 0);
		
		//Configurações Fábio:
		Configuracao configuracao2 = new Configuracao(2, 2, 1, 1);
		
		ConfiguracaoDAO configuracaoDAO =  ConfiguracaoDAO.getInstance(getBaseContext());
         
		configuracaoDAO.salvar(configuracao1);
		configuracaoDAO.salvar(configuracao2);
		
		List<Configuracao> t = configuracaoDAO.recuperarTodos();
	}

	private boolean isCargaJaExecutada() {
		UsuarioDAO usuarioDAO =  UsuarioDAO.getInstance(getBaseContext());
		List<Usuario> listaDeUsuarios = usuarioDAO.recuperarTodos();
		return listaDeUsuarios != null && !listaDeUsuarios.isEmpty();
	}

	/**
	 * Insere notificações, todas como não lidas.
	 */
	private void realizarCargaTabelaNotificacoes() {
		Date dataCarga1 = new Date();
		dataCarga1.setDate(24);
		Date dataCarga2 = new Date(); 
		dataCarga2.setDate(25);
		Date dataCarga3 = new Date(); 
		dataCarga3.setDate(26);
		
		Notificacao notificacao1 = new Notificacao(
				1, 
				"Recesso no dia 23/06/2014", 
				"A Reitoria informa que no dia 23 e junho de 2014 a Universidade Federal de Goiás "
				+ "estará de recesso nas cidades de Goiânia e Catalão. Maiores informações: "
				+ "http://www.ufg.br ",
				"Reitoria UFG",
				UtilidadesData.getDataString(dataCarga1),
				dataCarga1.getTime(),
				0, //Não lida
				1, //Pública
				0); //Sem grupo de notificação
		Notificacao notificacao2 = new Notificacao(
				2, 
				"Prova de Persistência", 
				"Informo que a prova de Persistência, marcada para o dia 25/06/2014 foi adiada "
				+ "para a próxima semana, ou seja, para o dia 01/07/2014. Maiores informações: "
				+ "http://www.ufg.br",
				"Marcelo Quinta",
				UtilidadesData.getDataString(dataCarga2),
				dataCarga2.getTime(),
				0, //Não lida
				0, //Privada
				1); //Grupo de persistencia
		Notificacao notificacao3 = new Notificacao(
				3, 
				"Trabalho de Mobile", 
				"Informo que a entrega do trabalho de Mobile está marcada para o dia 24/06/2014, "
				+ "sem possibilidades de qualquer adiamento desta data, uma vez que a data "
				+ "já foi postergada uma vez. Maiores informações: "
				+ "http://www.ufg.br",
				"Fábio Nogueira",
				UtilidadesData.getDataString(dataCarga3),
				dataCarga3.getTime(),
				0, //Não lida
				0, //Privada
				2); //Grupo de mobile
		
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

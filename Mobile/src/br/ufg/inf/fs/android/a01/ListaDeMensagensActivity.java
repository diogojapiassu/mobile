package br.ufg.inf.fs.android.a01;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.fs.android.persist.Configuracao;
import br.ufg.inf.fs.android.persist.ConfiguracaoDAO;
import br.ufg.inf.fs.android.persist.Notificacao;
import br.ufg.inf.fs.android.persist.NotificacaoDAO;
import br.ufg.inf.fs.android.persist.UsuarioDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListaDeMensagensActivity extends Activity {

	ListView listView;
	
	private int idUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_de_mensagens);
		// Show the Up button in the action bar.
		setupActionBar();
		idUsuario = getIntent().getIntExtra("idUsuario", -1);
		
		Button botaoConfigurar = (Button) findViewById(R.id.buttonConfigurar);
		
		//Botão configurara somente para usuários privados:
		if(idUsuario > 0){
			botaoConfigurar.setVisibility(View.VISIBLE);
		}else{
			botaoConfigurar.setVisibility(View.INVISIBLE);
		}
		
		carregaListaDeAcordoComUsuario();
	}
	
	public void onClickConfigurar(View view) {
		Intent intent = new Intent(this, ConfiguracoesActivity.class);
		intent.putExtra("idUsuario", idUsuario);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		recuperarListaDeMensagens();
		super.onResume();
	}

	private void carregaListaDeAcordoComUsuario() {
		Boolean isUsuarioPublico = getIntent().getBooleanExtra("isUsuarioPublico", false);
		
		if(!isUsuarioPublico && idUsuario > 0){
			//Carrega lista de acordo com as configurações do usuario
			carregaLista(true);
		}else{
			//Carrega lista publica
			carregaLista(false);
		}
	}
	
	private void carregaLista(Boolean mensagemPrivada) {
		// Get ListView object from xml
		recuperarListaDeMensagens();
	}

	private void recuperarListaDeMensagens() {
		listView = (ListView) findViewById(R.id.listaDeMensagens);
		
		NotificacaoDAO notificacaoDAO =  NotificacaoDAO.getInstance(getBaseContext());
		ConfiguracaoDAO configuracaoDAO =  ConfiguracaoDAO.getInstance(getBaseContext());
		
		List<Notificacao> listaDeNotificacoes;
		
		//Se for mensagem privada:
		if(idUsuario > 0){
			Configuracao configuracao = configuracaoDAO.getConfiguracaoUsuario(idUsuario);
			listaDeNotificacoes = notificacaoDAO.recuperarNotificacoesDoUsuario(configuracao);
		}else{
			listaDeNotificacoes = notificacaoDAO.recuperarNotificacoesPublicas();
		}
		
		// Defined Array values to show in ListView
		String values[] = new String[listaDeNotificacoes.size()];
		
		
		for (int i = 0; i < listaDeNotificacoes.size(); i++) {
			
			String msgLida = "";
			if(listaDeNotificacoes.get(i).getIs_lida() == 1){
				msgLida = " - Lida";
			}else{
				msgLida = " - Não Lida";
			}
			
			values[i] = listaDeNotificacoes.get(i).getId() + "-" + 
						listaDeNotificacoes.get(i).getDescricao() + msgLida;
		}

		/*if(mensagemPrivada){
			values = new String[] { 
					"Prova Persistência",
					"Aula de Concorrência",
					"Trabalho em Grupo de Web"};
		}else{
			values = new String[] { 
					"Recesso no dia 23/06/2014",
					"Novo Vestibular UFG",
					"Aberta as inscrições para o inglês"};

		}*/

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);
		
		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;

				// ListView Clicked item value
				String itemValue = (String) listView
						.getItemAtPosition(position);
				
				testando(itemValue);

				// Show Alert
				/*Toast.makeText(
						getApplicationContext(),
						"Position :" + itemPosition + "  ListItem : "
								+ itemValue, Toast.LENGTH_LONG).show();*/
				
				

			}
		});
	}
	
	public void testando(String itemValue){
		Intent intent = new Intent(this, DetalheMensagemActivity.class);
        
		String id = itemValue.split("-")[0];
		String mensagem = itemValue.split("-")[1];
		
		intent.putExtra("id", id);
        intent.putExtra("mensagem", mensagem);
        startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_de_mensagens, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

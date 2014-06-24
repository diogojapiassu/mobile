package br.ufg.inf.fs.android.a01;

import br.ufg.inf.fs.android.persist.Configuracao;
import br.ufg.inf.fs.android.persist.ConfiguracaoDAO;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.os.Build;

public class ConfiguracoesActivity extends Activity {

	private Configuracao configuracao;
	//private Switch switchPersistencia;
	//private Switch switchMobile;
	private CheckBox ckPersistencia;
	private CheckBox ckMobile;
	private int idUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		setupActionBar();
		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		idUsuario = getIntent().getIntExtra("idUsuario", -1);
		
		ConfiguracaoDAO configuracaoDAO = ConfiguracaoDAO.getInstance(getBaseContext());
		configuracao = configuracaoDAO.getConfiguracaoUsuario(idUsuario);
		
		boolean isExibirPersistencia = configuracao.getExibir_persistencia() > 0;
		boolean isExibirMobile = configuracao.getExibir_mobile() > 0;
		
		ckPersistencia = (CheckBox)findViewById(R.id.checkBoxPersistencia);
		ckPersistencia.setChecked(isExibirPersistencia);
		
		ckMobile = (CheckBox)findViewById(R.id.checkBoxMobile);
		ckMobile.setChecked(isExibirMobile);
		
		/*switchPersistencia = (Switch)findViewById(R.id.switchPersistencia);
		switchPersistencia.setChecked(isExibirPersistencia);
		
		switchMobile = (Switch)findViewById(R.id.switchMobile);
		switchMobile.setChecked(isExibirMobile);*/
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void onClickSalvarConfiguracao(View view) {
		ConfiguracaoDAO configuracaoDAO = ConfiguracaoDAO.getInstance(getBaseContext());
		
		configuracao.setExibir_persistencia(ckPersistencia.isChecked() ? 1 : 0);
		configuracao.setExibir_mobile(ckMobile.isChecked() ? 1 : 0);
		configuracaoDAO.editar(configuracao);
		
		Intent intent = new Intent(this, ListaDeMensagensActivity.class);
		//Passar o id do usuário, para recuperar suas mensagens
		intent.putExtra("idUsuario", idUsuario);
		intent.putExtra("isUsuarioPublico", false);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracoes, menu);
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
}
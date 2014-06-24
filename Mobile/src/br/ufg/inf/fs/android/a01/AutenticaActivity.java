package br.ufg.inf.fs.android.a01;

import br.ufg.inf.fs.android.persist.Usuario;
import br.ufg.inf.fs.android.persist.UsuarioDAO;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AutenticaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autentica);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	public void onClickEntrar(View view) {
		EditText nomeUsuario = (EditText)findViewById(R.id.editText1);
		EditText senhaUsuario = (EditText)findViewById(R.id.editText2);
		
		
		
		if(nomeUsuario.getText() != null && senhaUsuario.getText() != null
				//&& nomeUsuario.getText().toString().equalsIgnoreCase("diogo")
				//&& senhaUsuario.getText().toString().equalsIgnoreCase("12345")
				){
			
			UsuarioDAO usuarioDAO =  UsuarioDAO.getInstance(getBaseContext());
		
			Usuario usuarioLogado = usuarioDAO.getUsuario(nomeUsuario.getText().toString(), senhaUsuario.getText().toString());
			
			if(usuarioLogado != null){
				Intent intent = new Intent(this, ListaDeMensagensActivity.class);
				//Passar o id do usuário, para recuperar suas mensagens
				intent.putExtra("idUsuario", usuarioLogado.getId());
				intent.putExtra("isUsuarioPublico", false);
				startActivity(intent);
			}else{
				//usuario e senha errados
				Toast.makeText(
						getApplicationContext(),
						"Credenciais Inválidas", Toast.LENGTH_LONG).show();
			}
			
		}else{
			//Exibir um alerta
			//preencer usuario e senha
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.autentica, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_autentica,
					container, false);
			return rootView;
		}
	}

}

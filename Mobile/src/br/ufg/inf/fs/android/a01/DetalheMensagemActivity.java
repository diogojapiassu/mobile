package br.ufg.inf.fs.android.a01;

import java.util.List;

import br.ufg.inf.fs.android.persist.Notificacao;
import br.ufg.inf.fs.android.persist.NotificacaoDAO;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DetalheMensagemActivity extends Activity {

	private Notificacao notificacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_mensagem);
		// Show the Up button in the action bar.
		String mensagemAberta = getIntent().getStringExtra("mensagem");
		String id = getIntent().getStringExtra("id");
		
		NotificacaoDAO notificacaoDAO =  NotificacaoDAO.getInstance(getBaseContext());
		notificacao = notificacaoDAO.getNotificacaoPorId(id);
		
		CheckBox ckMensagemLida = (CheckBox)findViewById(R.id.checkBox1);
		ckMensagemLida.setChecked(true);
		
		notificacao.setIs_lida(1);
		notificacaoDAO.editar(notificacao);
		
		TextView text = (TextView)findViewById(R.id.mensagemNotificacao);
		text.setText(notificacao.getDescricao());
		
		TextView tvData = (TextView)findViewById(R.id.TextViewData);
		tvData.setText(notificacao.getData_string());
		
		TextView tvDetalhe = (TextView)findViewById(R.id.TextViewMensagem);
		tvDetalhe.setText(notificacao.getDetalhes());
		
		TextView tvRemetente = (TextView)findViewById(R.id.TextViewRemetente);
		tvRemetente.setText(notificacao.getRemetente());
		
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.detalhe_mensagem, menu);
		return true;
	}

	public void onClickMenuSair(MenuItem item) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                onClickMenuSair(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	public void onClickCheckBoxLida(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		
		NotificacaoDAO notificacaoDAO =  NotificacaoDAO.getInstance(getBaseContext());
		if(checked){
			notificacao.setIs_lida(1);
		}else{
			notificacao.setIs_lida(0);
		}
		
		notificacaoDAO.editar(notificacao);
	}

}

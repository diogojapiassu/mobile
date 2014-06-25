package br.ufg.inf.fs.android.a01;

import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufg.inf.fs.android.persist.Usuario;
import br.ufg.inf.fs.android.persist.UsuarioDAO;

/**
 * Uma atividade herda de Activity.
 */
@SuppressLint("NewApi")
public class MyActivity extends Activity {

	ListView listView ;
	
    /**
     * Chamado quando a atividade é criada. Uma atividade
     * contém uma ContentView, que é inflada conforme o
     * resource, neste caso, o arquivo main.xml
     * contido no diretório res\layout.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Observe o R (resource) abaixo. Ele corresponde ao
        // diretório "res". Neste diretório interessa o diretório
        // layout e, neste diretório, o arquivo main.xml. Tudo isto
        // é "deduzível" de R.lyout.main.
        setContentView(R.layout.main);
        
        carregaLista();
        
        //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //notificationManager.cancel(R.string.app_name);
        
        //testCRUD();
    }
    
	private void carregaLista() {
		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.listView1);

		// Defined Array values to show in ListView
		String[] values = new String[] { 
				"Prova Persistência",
				"Aula de Concorrência", "Recesso no dia 20/06/2014",
				"Trabalho em Grupo de Web"};

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

				// Show Alert
				Toast.makeText(
						getApplicationContext(),
						"Position :" + itemPosition + "  ListItem : "
								+ itemValue, Toast.LENGTH_LONG).show();

			}
		});
	}

	public void testCRUD() {
        
        //Salva usuário padrão para acesso ao sistema:
    	Usuario usuario = new Usuario(0, "Diogo", "djapiassu", "1010");
        UsuarioDAO usuarioDAO =  UsuarioDAO.getInstance(getBaseContext());
         
        usuarioDAO.salvar(usuario);
         
        List<Usuario> usuariosNaBase = usuarioDAO.recuperarTodos();
        //assertFalse(usuariosNaBase.isEmpty());
         
        Usuario usuarioRecuperado = usuariosNaBase.get(0);
        usuarioRecuperado.setNome("Diogo Japiassu");
         
        usuarioDAO.editar(usuarioRecuperado);
         
        Usuario usuarioEditado = usuarioDAO.recuperarTodos().get(0);
         
        //assertSame(usuarioRecuperado.getId(), usuarioEditado.getId());
        //assertNotSame(usuario.getNome(), usuarioEditado.getNome());
         
        usuarioDAO.deletar(usuarioEditado);
         
        //assertTrue(usuarioDAO.recuperarTodos().isEmpty());
         
        //usuarioDAO.fecharConexao();
         
    }
    
	public void salvarConfiguracoes() {
		// Salva usuário padrão para acesso ao sistema:
		
		boolean isExibirMensagensPersistencia = false;
		boolean isExibirMensagensIntegracao = false;
		boolean isExibirMensagensMobile = false;
		boolean isExibirMensagensweb = false;
		
		Usuario usuario = new Usuario(0, "", "", "");
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(getBaseContext());

		usuarioDAO.salvar(usuario);

		List<Usuario> usuariosNaBase = usuarioDAO.recuperarTodos();
		// assertFalse(usuariosNaBase.isEmpty());

		Usuario usuarioRecuperado = usuariosNaBase.get(0);
		usuarioRecuperado.setNome("Diogo Japiassu");

		usuarioDAO.editar(usuarioRecuperado);

		Usuario usuarioEditado = usuarioDAO.recuperarTodos().get(0);
		usuarioDAO.deletar(usuarioEditado);
		usuarioDAO.fecharConexao();
	}

    /**
     * Chamado para "inflar" o recurso "menu" contendo
     * os itens do menu de opções.
     * @param menu Objeto a ser "inflado" com o resource fornecido.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    /**
     * Chamado quando se seleciona a opção Configurar.
     * Neste caso, apenas exibe uma mensagem por um
     * período de tempo.
     * @param item Opção de menu selecionada.
     */
    public void onClickMenuConfigurar(MenuItem item) {
        Toast toast = Toast.makeText(this, "Configurar...", Toast.LENGTH_LONG);
        toast.show();
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
    
    //Teste notificação
    /** Called when the user touches the button */
    @SuppressLint("NewApi")
	public void notificaTeste2(View view) {
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        //.setSmallIcon(R.drawable.notification_icon)
    	        .setContentTitle("My notification")
    	        .setContentText("Hello World!");
    	// Creates an explicit intent for an Activity in your app
    	Intent resultIntent = new Intent(this, MyActivity.class);

    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	// Adds the back stack for the Intent (but not the Intent itself)
    	stackBuilder.addParentStack(MyActivity.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	stackBuilder.addNextIntent(resultIntent);
    	PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );
    	mBuilder.setContentIntent(resultPendingIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(0, mBuilder.build());
    }
    
    public void notificaTeste(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "Nova notificação UFG!", System.currentTimeMillis());
        
        String mensagem = "Mobile dia 24/05/2014";
        
        Intent intent = new Intent(this, DetalheMensagemActivity.class);
        intent.putExtra("mensagem", mensagem);
		
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification.setLatestEventInfo(this, "Prova", mensagem, pendingIntent);
        //int id = ;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(new Random().nextInt(), notification);
    }
}

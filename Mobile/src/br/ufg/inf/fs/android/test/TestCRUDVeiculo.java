package br.ufg.inf.fs.android.test;

import java.util.List;

import br.ufg.inf.fs.android.persist.Usuario;
import br.ufg.inf.fs.android.persist.UsuarioDAO;

import android.test.AndroidTestCase;

public class TestCRUDVeiculo extends AndroidTestCase {
 
    public void testCRUD() {
         
        Usuario usuario = new Usuario(0, "Diogo", "djapiassu", "1010");
        UsuarioDAO usuarioDAO =  UsuarioDAO.getInstance(getContext());
         
        usuarioDAO.salvar(usuario);
         
        List<Usuario> usuariosNaBase = usuarioDAO.recuperarTodos();
        assertFalse(usuariosNaBase.isEmpty());
         
        Usuario usuarioRecuperado = usuariosNaBase.get(0);
        usuarioRecuperado.setNome("Diogo Japiassu");
         
        usuarioDAO.editar(usuarioRecuperado);
         
        Usuario usuarioEditado = usuarioDAO.recuperarTodos().get(0);
         
        assertSame(usuarioRecuperado.getId(), usuarioEditado.getId());
        assertNotSame(usuario.getNome(), usuarioEditado.getNome());
         
        usuarioDAO.deletar(usuarioEditado);
         
        assertTrue(usuarioDAO.recuperarTodos().isEmpty());
         
        usuarioDAO.fecharConexao();
         
    }
 
}
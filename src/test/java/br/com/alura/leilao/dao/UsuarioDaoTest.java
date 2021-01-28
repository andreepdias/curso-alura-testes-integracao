package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(this.em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach(){
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado(){
        Usuario usuario = criarUsuario();

        em.persist(usuario);

        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        assertNotNull(encontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado(){
        Usuario usuario = criarUsuario();

        em.persist(usuario);

        assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    void deveriaRemoverUmUsuario(){
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);

        assertThrows(NoResultException.class,
                () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    private Usuario criarUsuario(){
        return new Usuario("fulano", "fulano@email.com", "123456789");
    }

}
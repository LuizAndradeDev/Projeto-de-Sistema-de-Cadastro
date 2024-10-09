package com.cadastro.principal.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cadastro.pessoas.DAO.UsuarioDAO;
import com.cadastro.pessoas.modelo.UsuarioModelo;
import com.cadastro.pessoas.modelo.enums.Nivel;

@Named
@ViewScoped
public class LoginUsuarioBean implements Serializable {

	private static final long serialVersionUID = -6783214793282108514L;

	@Inject
	UsuarioDAO usuarioDao;

	UsuarioModelo usuario = new UsuarioModelo();
	private UsuarioModelo usuarioLogado;

	private Nivel[] niveis;
	private List<UsuarioModelo> usuarios;

	public LoginUsuarioBean() {
		niveis = Nivel.values();
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		usuarioLogado = (UsuarioModelo) sessionMap.get("usuarioLogado");

		if (usuarioLogado != null) {
			usuarios = usuarioDao.listarTodos().stream().filter(u -> !u.getId().equals(usuarioLogado.getId()))
					.collect(Collectors.toList());
		}
	}

	public List<UsuarioModelo> getUsuarios() {
		return usuarios;
	}

	public UsuarioModelo getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModelo usuario) {
		this.usuario = usuario;
	}

	public Nivel[] getNiveis() {
		return niveis;
	}

	public void setNiveis(Nivel[] niveis) {
		this.niveis = niveis;
	}

	public void limparObjetos() {
		usuario = new UsuarioModelo();
	}
	

	public UsuarioModelo getUsuarioLogado() {
		return usuarioLogado;
	}

	public String cadastrarUsuario() {
		if (usuarioDao.verificarExistenciaEmail(usuario.getEmail())) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Email inserido é inválido ou repetido, tente novamente!!"));
			return "CadastroUsuario?faces-redirect=true";
		} else {
			usuarioDao.cadastrarUsuario(usuario);
			limparObjetos();
			return "Login?faces-redirect=true";
		}
	}

	public String efetuarLogin() {
		boolean existe = usuarioDao.existe(this.usuario);
		if (existe) {
			usuario = usuarioDao.retornarUsuarioPorEmail(usuario.getEmail());
			usuarioLogado = usuario;
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "CadastroPrincipal.xhtml?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido", "Usuário ou senha inválidos"));
			return null;
		}
	}

	public String deslogar() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login?faces-redirect=true";
	}
	
	public boolean verificarPermissao() {
	    return usuarioLogado != null && usuarioLogado.getNivel() == Nivel.ADMINISTRADOR;
	}

	public void salvar(UsuarioModelo usuario) {
		try {
			usuarioDao.atualizarUsuario(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário atualizado com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar usuário", e.getMessage()));
		}
	}
	
	public String atualizarUsuario() {
	    try {
	        usuarioDao.atualizarUsuario(usuarioLogado);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil atualizado com sucesso"));
	        return null;
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar perfil", e.getMessage()));
	        return null;
	    }
	}
}

package com.cadastro.validator;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cadastro.pessoas.modelo.UsuarioModelo;
import com.cadastro.pessoas.modelo.enums.Nivel;

@WebFilter("/*")
public class FiltroLogin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURL = httpRequest.getContextPath() + "/Login.xhtml";
        String cadastroPrincipalURL = httpRequest.getContextPath() + "/CadastroPrincipal.xhtml";
        String cadastroUsuarioURL = httpRequest.getContextPath() + "/CadastroUsuario.xhtml";
        String gerenciadorURL = httpRequest.getContextPath() + "/Gerenciador.xhtml";
        String perfilURL = httpRequest.getContextPath() + "/Perfil.xhtml";
        String resource = httpRequest.getRequestURI();

        boolean usuarioLogado = (session != null)  && (session.getAttribute("usuarioLogado")!= null);
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURL);
        boolean cadastroUsuarioRequest = httpRequest.getRequestURI().equals(cadastroUsuarioURL);
        boolean perfilRequest = httpRequest.getRequestURI().equals(perfilURL);
        boolean resourceRequest = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/javax.faces.resource");

        if (usuarioLogado) {
            UsuarioModelo usuario = (UsuarioModelo) session.getAttribute("usuarioLogado");
            boolean isAdmin = usuario.getNivel() == Nivel.ADMINISTRADOR;

            if (loginRequest || cadastroUsuarioRequest) {
                httpResponse.sendRedirect(cadastroPrincipalURL);
                return;
            } else if (httpRequest.getRequestURI().equals(gerenciadorURL) && !isAdmin) {
                httpResponse.sendRedirect(cadastroPrincipalURL);
                return;
            }
        } else if (perfilRequest) {
            httpResponse.sendRedirect(loginURL);
            return;
        }

        if (usuarioLogado || loginRequest || resourceRequest) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURL);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}

package com.cadastro.principal.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.FilterMeta;

import com.cadastro.endereco.DAO.CadastroEnderecoBanco;
import com.cadastro.endereco.buscador.BuscarEnderecoCEP;
import com.cadastro.endereco.modelo.EnderecoPessoa;
import com.cadastro.endereco.modelo.enums.EstadoEndereco;
import com.cadastro.pessoa.CPF.validador.validacaoCPF;
import com.cadastro.pessoas.DAO.CadastroPessoaBanco;
import com.cadastro.pessoas.modelo.PessoaModelo;
import com.cadastro.pessoas.modelo.enums.Raca;
import com.cadastro.pessoas.modelo.enums.Sexo;
import com.cadastro.relatorio.GeradorRelatorio;
import com.cadastro.socioDemografico.DAO.CadastroSocioDemograficoBanco;
import com.cadastro.socioDemografico.modelo.BeneficioSocioDemografico;
import com.cadastro.socioDemografico.modelo.CBO;
import com.cadastro.socioDemografico.modelo.DadosSocioDemograficos;
import com.cadastro.socioDemografico.modelo.enums.GrauDeIntrucao;
import com.cadastro.teste.HibernateUtil;

@Named
@ViewScoped
public class CadastroPrincipalBean implements Serializable {

	private static final long serialVersionUID = -7983759284586757214L;
	// -----------------------Declarações de variaveis e criação de
	// Objetos------------------

	@Inject
	CadastroEnderecoBanco cadastroEndereco = new CadastroEnderecoBanco();
	@Inject
	CadastroSocioDemograficoBanco cadastroDado = new CadastroSocioDemograficoBanco();
	@Inject
	CadastroPessoaBanco cadastroPessoa = new CadastroPessoaBanco();

	PessoaModelo pessoa = new PessoaModelo();
	PessoaModelo outraPessoa = new PessoaModelo();
	DadosSocioDemograficos dados = new DadosSocioDemograficos();
	EnderecoPessoa endereco = new EnderecoPessoa();
	BeneficioSocioDemografico beneficio = new BeneficioSocioDemografico();
	CBO cbo = new CBO();

	private Sexo[] sexos;
	private Raca[] racas;
	private EstadoEndereco[] estados;
	private GrauDeIntrucao[] graus;
	private boolean cpfValido;
	private List<PessoaModelo> pessoasModelos;
	private List<FilterMeta> filterBy;
	private List<PessoaModelo> pessoa1;
	private List<PessoaModelo> filteredPessoa1;
	private boolean globalFilterOnly;
	private boolean modoEdicao;

	// --------------------"Metodos Genericos"------------------
	// metodos "getters" e "setters" e algumas outras coisas genericas

	public List<PessoaModelo> getPessoa1() {
		return pessoa1;
	}

	public List<PessoaModelo> getFilteredPessoa1() {
		return filteredPessoa1;
	}

	public void setFilteredPessoa1(List<PessoaModelo> filteredPessoa1) {
		this.filteredPessoa1 = filteredPessoa1;
	}

	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}

	public List<PessoaModelo> getPessoasModelos() {
		return pessoasModelos;
	}

	public boolean isCpfValido() {
		return cpfValido;
	}

	public void setCpfValido(boolean cpfValido) {
		this.cpfValido = cpfValido;
	}

	public CadastroPrincipalBean() {

		sexos = Sexo.values();
		racas = Raca.values();
		estados = EstadoEndereco.values();
		graus = GrauDeIntrucao.values();
	}

	public EstadoEndereco[] getEstados() {
		return estados;
	}

	public GrauDeIntrucao[] getGraus() {
		return graus;
	}

	public Raca[] getRacas() {
		return racas;
	}

	public Sexo[] getSexos() {
		return sexos;
	}

	public PessoaModelo getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaModelo pessoa) {
		this.pessoa = pessoa;
	}

	public DadosSocioDemograficos getDados() {
		return dados;
	}

	public void setDados(DadosSocioDemograficos dados) {
		this.dados = dados;
	}

	public EnderecoPessoa getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoPessoa endereco) {
		this.endereco = endereco;
	}

	public BeneficioSocioDemografico getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(BeneficioSocioDemografico beneficio) {
		this.beneficio = beneficio;
	}

	public CBO getCbo() {
		return cbo;
	}

	public void setCbo(CBO cbo) {
		this.cbo = cbo;
	}

	// --------------------"Metodos Especificos"------------------

	// -------------"Validação do CPF"---------------
	// retira os pontos e os traços para "limpar" o CPF e depois
	// puxa o metodo do validador e valida, alterando o estado
	// da variavel "cpfValido" para true ou false

	public void validarCPFnoBean() {
		String CPFlimpo = pessoa.getCPF();
		CPFlimpo = CPFlimpo.replaceAll("-", "");
		CPFlimpo = CPFlimpo.replaceAll("\\.", "");
		validacaoCPF validacao = new validacaoCPF();
		if (!validacao.metodoValidarCPF(CPFlimpo)) {
			cpfValido = false;
		} else {
			cpfValido = true;
		}
	}

	// -------------"Validação do CPF na Hora"---------------
	// um metodo que utiliza dos metodos "validarCPFnoBean" e
	// "verificarExistenciaCPF" para que assim que o usuario
	// coloque o CPF já faça uma validação na hora e informe para ele

	public void validarCPFnaHora() {
		outraPessoa = cadastroPessoa.retornarPessoaPorID(pessoa.getId());
		boolean cpfAlterado = !modoEdicao || !pessoa.getCPF().equals(outraPessoa.getCPF());

		if (cpfAlterado) {
			validarCPFnoBean();
			if (!cpfValido) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "CPF inserido é inválido, tente novamente!!"));
				System.out.println("CPF inválido");
				return;
			}

			if (cadastroPessoa.verificarExistenciaCPF(pessoa.getCPF())) {
				if (!modoEdicao || !pessoa.getCPF().equals(outraPessoa.getCPF())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error", "CPF já existe no banco de dados, tente novamente!!"));
					System.out.println("CPF já existente");
					return;
				}
			}
		}
	}

	// -------------"Busca de Informações do CEP"---------------
	// metodo que basicamente puxa a função de outra classe aqui para o bean
	// cuja a função dela é basicamente pegar o endereço a partir
	// de um CEP informado pelo usuario, junto disso tem uma
	// pequena verificação caso o CEP seja invalido

	public void buscarInformacoesCEP() {
		BuscarEnderecoCEP buscarEndereco = new BuscarEnderecoCEP();
		String cepSemHifen = endereco.getCEP().replaceAll("-", "");
		if (buscarEndereco.consultarCEP(cepSemHifen) == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "CEP inserido é invalido!!"));
			System.out.println("CEP invalido");
		} else {
			endereco = buscarEndereco.consultarCEP(cepSemHifen);
		}
	}

	// -------------"Metodo para finalizar o Cadastro------------
	// serve para finalizar o cadastro enviando todas as informações
	// para o banco de dados a partir dos metodos criados no DAO,
	// aqui tem uma segunda camada de validação para o CPF
	// pra caso a primeira falhar

	public void finalizarCadastro() {
		if (modoEdicao) {
			validarCPFnoBean();
			if (cpfValido) {
				outraPessoa = cadastroPessoa.retornarPessoaPorID(pessoa.getId());
				boolean mesmoCPF = outraPessoa.getCPF().equals(pessoa.getCPF());

				if (cadastroPessoa.verificarExistenciaCPF(pessoa.getCPF()) && !mesmoCPF) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error", "CPF inserido é inválido ou repetido, tente novamente!!"));
					System.out.println("CPF já existente");
				} else {
					cadastroPessoa.atualizarPessoa(pessoa);
					cadastroPessoa.atualizarDadosSocio(dados);
					cadastroPessoa.atualizarEndereco(endereco);
					cadastroDado.atualizarBeneficio(beneficio);
					cadastroDado.atualizarCBO(cbo);

					modoEdicao = false;
					limparTodosObjetos();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "CPF inserido é inválido, tente novamente!!"));
				System.out.println("CPF inválido");
			}
		} else {
			validarCPFnoBean();
			if (cpfValido && !cadastroPessoa.verificarExistenciaCPF(pessoa.getCPF())) {
				dados.setCbo(cbo);
				dados.setBeneficio(beneficio);
				pessoa.setDadosSocioDemograficos(dados);
				pessoa.setEnderecoPessoa(endereco);
				cadastroDado.cadastrarBeneficio(beneficio);
				cadastroDado.cadastrarCBO(cbo);
				cadastroDado.cadastrarDadosSocio(dados);
				cadastroEndereco.cadastrarEndereco(endereco);
				cadastroPessoa.cadastrarPessoa(pessoa);
				limparTodosObjetos();
				init();
			} else {
				if (!cpfValido) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error", "CPF inserido é inválido, tente novamente!!"));
					System.out.println("CPF inválido");
				}
			}
		}
	}

	// ------------------- Filtro para a lista-----------------
	// serve pra poder fazer a função de filtro do primefaces
	// funcionar
	@PostConstruct
	public void init() {
		globalFilterOnly = false;
		pessoasModelos = cadastroPessoa.listarTodasPessoas();
		pessoa1 = pessoasModelos;
		filterBy = new ArrayList<>();

	}

	// -------------"Exclusao de pessoas"---------------
	// metodo implementado no bean para deletar pessoa

	public void deletarPessoaEDados(BigDecimal id) {
		cadastroPessoa.excluirPessoaPorID(id);
		System.out.println(id);
		init();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa excluída com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// -------------"Puxar dados para a edição"-------------
	// metodo para receber os dados para a edição
	public void puxarDadosEdicao(BigDecimal id) {
		modoEdicao = true;
		pessoa = cadastroPessoa.retornarPessoaPorID(id);
		endereco = pessoa.getEnderecoPessoa();
		dados = pessoa.getDadosSocioDemograficos();
		cbo = dados.getCbo();
		beneficio = dados.getBeneficio();

	}

	// -------------"Limpar todos os objetos-------------
	// metodo para limpar tudo
	public void limparTodosObjetos() {
		pessoa = new PessoaModelo();
		dados = new DadosSocioDemograficos();
		endereco = new EnderecoPessoa();
		beneficio = new BeneficioSocioDemografico();
		cbo = new CBO();
	}

	// -------------"Gerar o relatorio-------------
	// metodo para gerar o relatorio

	public void gerarRelatorio(String tipoRelatorio) throws SQLException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String nome;
		
		if ("completo".equals(tipoRelatorio)) {
			nome = request.getServletContext().getRealPath("/WEB-INF/jasper/report3.jasper");
		} else {
			nome = request.getServletContext().getRealPath("/WEB-INF/jasper/relatorioSimples.jasper");
		}
		
		Map<String, Object> params = new HashMap<>();
		Connection connection = HibernateUtil.getConnection();
        
		if(pessoa.getDataDeNascimento() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data = sdf.format(pessoa.getDataDeNascimento());
			params.put("datadenascimentopessoa", data);
		}

		params.put("sexopessoa", pessoa.getSexo());
		params.put("nomepessoa", pessoa.getNome());
		params.put("cidadeendereco", endereco.getCidade());
		params.put("bairroendereco", endereco.getBairro());
		params.put("logradouroendereco", endereco.getLogradouro());
		params.put("nome_beneficio_socio", beneficio.getNome());

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");

		try (ServletOutputStream outputStream = response.getOutputStream()) {
			GeradorRelatorio gerador = new GeradorRelatorio(nome, params, connection);
			gerador.geraPDFParaOutputStream(outputStream);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar o relatório PDF", e);
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão com o banco de dados", e);
			}
		}
	}

}

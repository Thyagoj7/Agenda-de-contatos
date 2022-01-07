package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Dao;
import models.JavaBeans;

@WebServlet(urlPatterns = { "/agendacontrolleruno", "/main", "/insert", "/select"})
public class AgendaControllerUno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dao dao = new Dao();
	JavaBeans contato = new JavaBeans();
	public AgendaControllerUno() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		}else if (action.equals("/insert")) {
			novoContato(request, response);
		}else if (action.equals("/select")) {
			listarContato(request, response);
		}else {
			response.sendRedirect("index.jsp");
		}
				
			

	}
	//Listar Contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Criando um objeto que ira receber od dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		}
	
	// Novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar as variavies Javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o metodo inserirContato passando o on=bjeto contato
		dao.inserirContato(contato);
		//redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}
	
	// Editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//Recebimento do id do contato que sera editado
		String idcon = request.getParameter("idcon");
		//System.out.println(idcon);
		//Setar a varivael Javabeans
		contato.setIdcon(idcon);
		// Executar o metodo selecionar contato
		dao.selecionarContato(contato);
		// setar os atributos do formulario com o conteudo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request,response);
		
		
	}
	
}
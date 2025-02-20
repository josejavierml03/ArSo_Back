package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTest extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public ServletTest() {
        super();
    }   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String pathFichero = request.getServletContext().getRealPath("index.html");
        BufferedReader br = new BufferedReader(new FileReader(pathFichero));
        String linea;
        while ( (linea = br.readLine()) != null){
            out.println(linea);
        }
        br.close();
    }   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
package Controller;

import Model.Fruit;
import Model.FruitDB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Fruit> fruits = new FruitDB().listAll();
        request.setAttribute("fruits", fruits);
        for (Fruit item : fruits) {
            System.out.println(item.toString());
        }
        RequestDispatcher rd = request.getRequestDispatcher("product.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getParameter("method");
        String id = request.getParameter("productId");
        String name = request.getParameter("productName");
        String descript = request.getParameter("description");
        String price = request.getParameter("price");

        switch (method) {
            case "add":
                new FruitDB().addFruit(name, descript, Double.parseDouble(price));
                break;
            case "update":
                new FruitDB().update(new Fruit(Integer.parseInt(id), name, descript, Double.parseDouble(price)));
                break;
            case "delete":
                new FruitDB().delete(Integer.parseInt(request.getParameter("productId")));
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method not supported");
                break;
        }

        List<Fruit> fruits = new FruitDB().listAll();
        request.setAttribute("fruits", fruits);
        RequestDispatcher rd = request.getRequestDispatcher("product.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

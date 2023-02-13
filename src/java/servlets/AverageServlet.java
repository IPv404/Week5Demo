package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zeina Mint
 */
public class AverageServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the current session
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        if (action != null && action.equals("reset")) {
            session.invalidate();
            session = request.getSession();
        }
        // get the list of numbers from the session
        ArrayList<Integer> numbers = (ArrayList<Integer>)session.getAttribute("numbers");
        // singleton pattern
        // if there is no list of numbers in the session, create a list
        if (numbers == null)
            numbers = new ArrayList<>();
        
        // get the number the user entered
        // if there is a number, add it to the list
        if (request.getParameter("number") != null) {
            int number = Integer.parseInt(request.getParameter("number"));
            numbers.add(number);
            
            // store the list back into the session
            session.setAttribute("numbers", numbers);
        }
        
        // calculate average
        // fancy way using streams:
        // double average = numbers.stream().mapToDouble(x -> x).average().orElse(0.0);
        double average = 0.0;
        for(int number : numbers) {
            average += number;
        }
        
        if (numbers.size() > 0) {
            average /= numbers.size();
        }
        
        // store the average in the request
        // average will need to be recalculated for each request (page view)
        request.setAttribute("average", average);
        
        getServletContext().getRequestDispatcher("/WEB-INF/average.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // nothing here
    }
}

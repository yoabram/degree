import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            JSONObject jsonObject = new JSONObject(jb.toString());

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            int command = jsonObject.getInt("command");

            switch (command) {

                case 0:

                    int arg = jsonObject.getInt("arg");
                    Map<Integer, Long> degrees = calc(arg);
                    JSONObject jsonToReturn = new JSONObject();
                    jsonToReturn.put("answer", "degrees");
                    jsonToReturn.put("map", degrees.toString());
                    out.println(jsonToReturn.toString());

                    break;

                default:
                    System.out.println("default switch");
                    break;

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private HashMap<Integer,Long> calc(int p) {
        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        for (int i = 0; i <= 16; i++) {
            map.put(i, (long) Math.pow(p, i));
        }
        return map;
    }
}

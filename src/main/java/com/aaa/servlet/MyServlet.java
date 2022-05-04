package com.aaa.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;





@WebServlet(value = "/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("servlet");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>servlet</h1>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String id = request.getParameter("id");
        String k = request.getParameter("k");

        System.out.println("Producing...");
        Prod p = new Prod();
        p.run(Integer.parseInt(id));

        System.out.println("Consuming...");
        Consu c = new Consu();
        c.run(Integer.parseInt(k));


        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        System.out.println("ss1");
        Process pr = Runtime.getRuntime().exec("python D:\\folium_code.py D:\\res.txt");
        System.out.println("ss2");
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            out.println(line);
            System.out.println("ss");
        }
        in.close();
        try {
            pr.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.close();

    }
}

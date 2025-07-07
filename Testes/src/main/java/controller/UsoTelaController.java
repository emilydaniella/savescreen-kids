package controller;

import service.UsoTelaService;
import model.UsoTela;
import com.google.gson.Gson;

import static spark.Spark.*;

public class UsoTelaController {
    private static UsoTelaService service = new UsoTelaService();
    private static Gson gson = new Gson();

    public static void rotas() {
        post("/uso", (req, res) -> {
            UsoTela uso = gson.fromJson(req.body(), UsoTela.class);
            service.registrarUso(uso.getIdUso(), uso.getDataHoraInicio(), uso.getDataHoraFim());
            res.status(201);
            return "Uso registrado com sucesso!";
        });

        get("/usos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(service.listarUsos());
        });
    }
}

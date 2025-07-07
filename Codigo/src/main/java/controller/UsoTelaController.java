package controller;

import service.UsoTelaService;
import model.UsoTela;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.GsonConfig;

import static spark.Spark.*;

import java.time.LocalDateTime;

public class UsoTelaController {
    private static UsoTelaService service = new UsoTelaService();
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, new GsonConfig())
    .create();

    public static void rotas() {
        post("/uso", (req, res) -> {
            UsoTela uso = gson.fromJson(req.body(), UsoTela.class);
            service.registrarUso(uso.getIdUso(), uso.getDataHoraInicio(), uso.getDataHoraFim());
            res.status(201);
            return "Uso registrado com sucesso!";
        });

        get("/uso", (req, res) -> {
            res.type("application/json");
            var result = service.listarUsos();
            return gson.toJson(result);
        });
    }
}

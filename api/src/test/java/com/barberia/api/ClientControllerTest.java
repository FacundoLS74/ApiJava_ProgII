package com.barberia.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // inyectar al test la clase Mock MVC
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc; //crear y enviar peticiones al servidor

    @Test
    public void Cuando_Se_Llama_Los_Usuarios_Entonces_Estado_200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/obtenerTodos") // Enviamos datos de forma simulada y a que ruta
                .contentType(MediaType.APPLICATION_JSON)) // Tipo de contenido que enviamos a la petición
                .andExpect(status().isOk()) //Recibe tipo de resultado que nosotros esperamos, en este caso OK
                .andExpect(MockMvcResultMatchers // mi contenido que se devuelva despues de realizar la peticion sea de tipo JSON
                        .content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    public void Cuando_Se_Crea_Un_Nuevo_Cliente_Entonces_Estado_201() throws Exception {
        // Crear un objeto JSON con los atributos del cliente
        String nuevoClienteJson = "{\"nombre\": \"Juan\", \"apellido\": \"Pérez\", \"correo\": \"juan@xxxxxxxx.com\", \"dni\": \"12345678\"}";

        // Realizar una solicitud POST para crear el cliente
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nuevoClienteJson))
                .andExpect(status().isCreated());
}}

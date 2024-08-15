package org.opensearch.tasks.config.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig  {

    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Complemento OpenSearch")
                        .version("1.0")
                        .description("Creación de complemento para OpenSearch (persistir los datos en un índice de OpenSearch)")
                        .contact(new Contact()
                                .name("Martin Colavita")
                                .email("martincolavita@gmail.com")));
    }

}
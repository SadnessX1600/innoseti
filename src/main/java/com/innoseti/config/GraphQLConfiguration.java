package com.innoseti.config;

import com.innoseti.model.datafetchers.LibraryDataFetchers;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Configuration
public class GraphQLConfiguration {
    private LibraryDataFetchers libraryDataFetchers;
    @Value("classpath:library.graphqls")
    private Resource resource;
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Autowired
    public GraphQLConfiguration(LibraryDataFetchers graphQLDataFetchers) {
        this.libraryDataFetchers = graphQLDataFetchers;
    }

    @PostConstruct
    public void init() throws IOException {
        String sdl = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }


    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("getBooksByAuthor", libraryDataFetchers.getBooksByAuthor()))
                .type(newTypeWiring("Query").dataFetcher("getAuthor", libraryDataFetchers.getAuthorByName()))
                .type(newTypeWiring("Query").dataFetcher("getAllBooks", libraryDataFetchers.getAllBooks()))
                .type(newTypeWiring("Mutation").dataFetcher("saveAuthor", libraryDataFetchers.saveAuthor()))
                .type(newTypeWiring("Mutation").dataFetcher("saveBook", libraryDataFetchers.saveBook()))
                .build();
    }

}

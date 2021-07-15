package com.eni.ioc.assetintegrity.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {
    
    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() { 
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {

        GraphQLSchema graphQLSchema = buildSchema("graphql/query.graphqls","graphql/corrosion.graphqls","graphql/operational_kpi.graphqls","graphql/integrity.graphqls");
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String... sdlS) throws IOException {
        URL url = Resources.getResource(sdlS[0]);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        for(int i = 1; i < sdlS.length; i++) {
            url = Resources.getResource(sdlS[i]);
            sdl = Resources.toString(url, Charsets.UTF_8);            
            typeRegistry.merge(new SchemaParser().parse(sdl));
        }
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();       
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("criticalSignalsOperationalKpi", graphQLDataFetchers.getCriticalSignalsOperationalKpi())
                        .dataFetcher("backlogOperationalKpi", graphQLDataFetchers.getBacklogOperationalKpi())
                        .dataFetcher("corrosionBacteriaTable", graphQLDataFetchers.getCorrosionBacteriaTable())
                        .dataFetcher("corrosionKpi", graphQLDataFetchers.getCorrosionKpi())                        
                        .dataFetcher("operatingWindowKpi", graphQLDataFetchers.getOperatingWindowKpi())
                        .dataFetcher("integrityOperatingWindowKpi", graphQLDataFetchers.getIntegrityOperatingWindowKpi())
                        .dataFetcher("corrosionCNDCount", graphQLDataFetchers.getCorrosionCNDCount())
                        .dataFetcher("corrosionCouponTable", graphQLDataFetchers.getCorrosionCouponTable())
                        .dataFetcher("corrosionPiggingTable", graphQLDataFetchers.getCorrosionPiggingTable())
                        .dataFetcher("corrosionProtectionTable", graphQLDataFetchers.getCorrosionProtectionTable())
                        .dataFetcher("EVPMSTable", graphQLDataFetchers.getEVPMS())
                        .dataFetcher("JacketedPipesTable", graphQLDataFetchers.getJacketedPipes())
                ).build();
    }
}
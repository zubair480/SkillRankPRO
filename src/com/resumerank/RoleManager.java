package com.resumerank;

import java.util.*;

public class RoleManager {

    public List<JobRole> loadRoles() {

        JobRole javaDev = new JobRole(
                "Java Developer",
                Arrays.asList("java", "spring", "spring boot", "hibernate", "microservices", "rest api", "git"),
                Arrays.asList("oop", "backend", "unit testing", "junit", "api", "docker")
        );

        JobRole dataAnalyst = new JobRole(
                "Data Analyst",
                Arrays.asList("sql", "excel", "python", "tableau", "power bi", "data analysis"),
                Arrays.asList("dashboard", "reporting", "etl", "insights")
        );

        JobRole aiEngineer = new JobRole(
                "AI Engineer",
                Arrays.asList("python", "tensorflow", "pytorch", "ml", "machine learning", "deep learning", "nlp"),
                Arrays.asList("neural network", "dataset", "model training", "inference")
        );

        return Arrays.asList(javaDev, dataAnalyst, aiEngineer);
    }
}

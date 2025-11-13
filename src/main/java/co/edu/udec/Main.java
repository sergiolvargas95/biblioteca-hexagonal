package co.edu.udec;

import co.edu.udec.infrastructure.config.DatabaseConfig;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("🔍 Probando conexión con SQL Server...");
        DatabaseConfig.testConnection();
    }
}
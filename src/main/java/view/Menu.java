package view;

import controller.JugadoresController;
import controller.EquiposController;

import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

public class Menu {
    private int option;
    private String opciones;

    public Menu() {
        super();
    }
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Actualizar Tablas");
            System.out.println("2. Mostrar los que sean ?");
            System.out.println("3. Mostrar todos los jugadores que empiezan por ?");
            System.out.println("4. Modificar el nombre de un jugador");
            System.out.println("5. Modificar el equipo de los jugadores que empiezan por ?");
            System.out.println("6. Eliminar un jugador");
            System.out.println("7. Eliminar jugadores que esten en el equipo ?");
            System.out.println("8. Añadir un equipo");
            System.out.println("9. Añadir un jugador");
            System.out.println("10. Mostrar jugadores");
            System.out.println("11. Mostrar equipos");
            System.out.println("12. Exit");
            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();

            }

        } while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7
                && option != 8 && option != 9 && option != 10 && option != 11 && option != 12);


        return option;
    }

    public String equipoMenu(Connection c, EntityManagerFactory entityManagerFactory){
        EquiposController equiposController = new EquiposController(c, entityManagerFactory);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(;;){
            equiposController.showEquipo();
            System.out.println("Elige el equipo: ");
            try {
                opciones = br.readLine();
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
            return opciones;
        }
    }
    public int NomMenu(Connection c, EntityManagerFactory entityManagerFactory){
        JugadoresController jugadoresController = new JugadoresController(c, entityManagerFactory);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n" + "Jugadores: ");
        for(;;){
            jugadoresController.showJugadoresNom();
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
            return option;
        }
    }
}
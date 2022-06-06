import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import controller.*;
import database.ConnectionFactory;
import model.*;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class Main {

    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPAMagazines");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection c = connectionFactory.connect();

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

        EquiposController equiposController = new EquiposController(c, entityManagerFactory);
        JugadoresController jugadoresController = new JugadoresController(c, entityManagerFactory);


        Menu menu = new Menu();
        int opcio = menu.mainMenu();

        while (opcio > 0 && opcio < 14) {
            switch (opcio) {
                case 1:
                    List<Equipos> equipos = equiposController.readEquipoFile("src/main/resources/equipos.txt");
                    for (Equipos e : equipos) {
                        try {
                            equiposController.addEquipo(e);
                        } catch (Exception ex) {
                        }
                    }

                    List<Jugadores> jugadores = jugadoresController.readJugadoresFile("src/main/resources/jugadores.txt");
                    for (Jugadores j : jugadores) {
                        jugadoresController.addJugadores(j);
                    }
                    break;
                case 2:
                    jugadoresController.showJugadoresPorEquipo();
                    break;
                case 3:
                    jugadoresController.showJugadoresPor();
                    break;
                case 4:
                    jugadoresController.modificarJugadores();
                    break;
                case 5:
                    equiposController.modificarEquipo();
                    break;
                case 6:
                    jugadoresController.borrarJugadores();
                    break;
                case 7:
                    jugadoresController.borrarJugadoresPorEquipos();
                    break;
                case 8:
                    System.out.println("----------------------");
                    System.out.println("Crear Equipo");
                    System.out.println("----------------------");

                    System.out.println("Equipo:");
                    String equipo = sc.nextLine().toUpperCase(Locale.ROOT);

                    equiposController.addEquipo(new Equipos(equipo));

                    break;
                case 9:
                    System.out.println("----------------------");
                    System.out.println("Crear Jugadores");
                    System.out.println("----------------------");

                    System.out.println("Nombre:");
                    String nom = sc.nextLine().toUpperCase(Locale.ROOT);

                    System.out.println("Elige un equipo:");
                    String equipoo = menu.equipoMenu(c, entityManagerFactory).toUpperCase(Locale.ROOT);

                    System.out.println("nacionalitat:");
                    String nacionalitat = sc.nextLine();

                    System.out.println("any_naixement:");
                    String any_naixement = sc.nextLine();


                    jugadoresController.addJugadores(new Jugadores(nom, Integer.parseInt(any_naixement), nacionalitat, new Equipos(equipoo)));

                    break;
                case 10:
                    jugadoresController.showJugadores();
                    break;
                case 11:
                    equiposController.showEquipo();
                    break;
                case 12:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Introdueixi una de les opcions anteriors");
                    break;
            }
            opcio = menu.mainMenu();
        }
    }
}
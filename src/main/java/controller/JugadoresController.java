package controller;

import model.Jugadores;
import model.Equipos;
import view.Menu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class JugadoresController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    Scanner sc;
    List<Jugadores> jugadores;
    Menu menu = new Menu();

    public JugadoresController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.sc = new Scanner(System.in);
        jugadores = new ArrayList<>();
    }

    public List<Jugadores> readJugadoresFile(String file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea = "";

        while((linea = br.readLine()) != null){
            List<String> listToken = getTokenList(linea, ",");
            jugadores.add(new Jugadores(listToken.get(0),Integer.parseInt(listToken.get(1)), listToken.get(2), new Equipos(listToken.get(3))));
        }

        return jugadores;
    }

    public void addJugadores(Jugadores jugadores) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(jugadores);
        em.getTransaction().commit();
        em.close();
    }

    public void showJugadores(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery("from Jugadores", Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void showJugadoresPorEquipo(){
        String equipo = menu.equipoMenu(connection,entityManagerFactory).toUpperCase(Locale.ROOT);
        String sql = "from Jugadores where equiposjuegan='" + equipo + "'";

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery(sql, Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void showJugadoresPor(){
        System.out.println("Escribe la letra de inicio: ");
        String letra = sc.nextLine().toUpperCase(Locale.ROOT);

        String sql = "from Jugadores where nom like '" + letra + "%'";

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery(sql, Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void showJugadoresNom(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery("from Jugadores order by id_jugadores", Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.getId() + " " + jugadores.getNom());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void modificarJugadores() {
        int id = menu.NomMenu(connection,entityManagerFactory);
        System.out.println("Escribe el nuevo nombre: ");
        String newNom = sc.nextLine().toUpperCase(Locale.ROOT);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Jugadores jugadores = (Jugadores) em.find(Jugadores.class, id);
        jugadores.setNom(newNom);
        em.merge(jugadores);
        em.getTransaction().commit();
        em.close();
    }

    public void borrarJugadores() {
        int id = menu.NomMenu(connection,entityManagerFactory);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Jugadores jugadores = (Jugadores) em.find(Jugadores.class, id);
        em.remove(jugadores);
        em.getTransaction().commit();
        em.close();
    }

    public void borrarJugadoresPorEquipos() {
        String equipos = menu.equipoMenu(connection,entityManagerFactory).toUpperCase(Locale.ROOT);
        String sql = "from Jugadores where equiposjuegan='" + equipos + "'";

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery(sql, Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            em.remove(jugadores);
        }
        em.getTransaction().commit();
        em.close();
    }

    private static List<String> getTokenList(String string, String delimiters) {

        List<String> listTokens = new ArrayList<String>();

        try {

            StringTokenizer st = new StringTokenizer(string, delimiters);

            while( st.hasMoreTokens() ) {
                //add token to the list
                listTokens.add( st.nextToken() );
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return listTokens;
    }
}

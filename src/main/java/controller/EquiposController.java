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

public class EquiposController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    Scanner sc;
    List<Equipos> equipos ;
    Menu menu = new Menu();

    public EquiposController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.sc = new Scanner(System.in);
        equipos = new ArrayList<>();
    }

    public List<Equipos> readEquipoFile(String file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea = "";

        while((linea = br.readLine()) != null){
            List<String> listToken = getTokenList(linea, ",");
             equipos.add(new Equipos(listToken.get(0)));
        }

        return equipos;
    }

    public void addEquipo(Equipos equipos) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(equipos);
        em.getTransaction().commit();
        em.close();
    }

    public void showEquipo(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Equipos> result = em.createQuery("from Equipos", Equipos.class).getResultList();
        for (Equipos equipos : result) {
            System.out.println(equipos.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void modificarEquipo(){
        String equipo = menu.equipoMenu(connection,entityManagerFactory).toUpperCase(Locale.ROOT);
        System.out.println("Escribe la primera letra del jugador que quieras modificar ?");
        String letra = sc.nextLine().toUpperCase(Locale.ROOT);
        String sql = "from Jugadores where nom like '" + letra + "%'";

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<Jugadores> result = em.createQuery(sql, Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            jugadores.setEquipo(new Equipos(equipo));
            em.merge(jugadores);
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

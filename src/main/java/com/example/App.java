package com.example;

import com.example.model.joined.Developpeur;
import com.example.model.joined.Employe;
import com.example.model.joined.Manager;
import com.example.model.singletable.Moto;
import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.tableperclass.Electronique;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // 1. Stratégie SINGLE_TABLE
        Voiture v = new Voiture("Peugeot", 5);
        Moto m = new Moto("Dacia", false);
        em.persist(v);
        em.persist(m);

        // 2. Stratégie JOINED
        Developpeur dev = new Developpeur("Zakaria", "Java");
        Manager mgr = new Manager("Ziko", 3);
        em.persist(dev);
        em.persist(mgr);

        // 3. Stratégie TABLE_PER_CLASS
        Livre livre = new Livre("Les Misérables", "Victor Hugo");
        Electronique tel = new Electronique("Smartphone", 24);
        em.persist(livre);
        em.persist(tel);

        em.getTransaction().commit();

        System.out.println("=== 1. TEST STRATÉGIE SINGLE_TABLE ===");
        List<Vehicule> vehicules = em.createQuery("SELECT v FROM Vehicule v", Vehicule.class).getResultList();
        vehicules.forEach(System.out::println);

        System.out.println("\n=== 2. TEST STRATÉGIE JOINED ===");
        List<Employe> employes = em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        employes.forEach(System.out::println);

        System.out.println("\n=== 3. TEST STRATÉGIE TABLE_PER_CLASS ===");
        List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        produits.forEach(System.out::println);

        em.close();
        emf.close();
    }
}

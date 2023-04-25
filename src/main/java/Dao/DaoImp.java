package Dao;

import model.Books;
import model.Library;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DaoImp implements Dao{
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sujan");
    private static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public void insertLibrary() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        System.out.println("Enter Library Name");
        String libName=bf.readLine();

        Library library=new Library();
        library.setLibName(libName);

        transaction.begin();
        entityManager.persist(library);
        transaction.commit();
        entityManager.close();


    }

    @Override
    public void insert() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        System.out.println("Enter Book Name");
        String bookName=bf.readLine();
        System.out.println("Enter Book Price");
        int bookPrice= Integer.parseInt(bf.readLine());


        System.out.println("Enter published date (yyyy-mm-dd )");
        Date publishedDate= Date.valueOf(bf.readLine());

        System.out.println("Enter publisher Name");
        String publisherName=bf.readLine();

        System.out.print("Enter Library ID : ");
        int inputLibId = Integer.parseInt(bf.readLine());
        Library library;
        try {
            int libId = inputLibId;
            library = entityManager.find(Library.class, libId);
        } catch (NumberFormatException e) {
            library = new Library();
            library.setLid(inputLibId);
        }

        Books books=new Books();
        books.setBookName(bookName);
        books.setBookPrice(bookPrice);
        books.setPublisherName(publisherName);
        books.setPublishedDate(publishedDate);
        books.setLibrary(library);


        transaction.begin();
        entityManager.persist(books);
        transaction.commit();
        System.out.println("Data added Successfully ");
        entityManager.close();

    }

    public void update() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Enter Book ID");
        int bookId = Integer.parseInt(bf.readLine());

        Books books = entityManager.find(Books.class, bookId);
        //update price
        if(books== null){
            System.out.println("book not found");
        }
        else {
            System.out.println("Enter Book Price");
            int bookPrice = Integer.parseInt(bf.readLine());
            books.setBookPrice(bookPrice);
            //merge data
            entityManager.merge(books);

            transaction.commit();
            entityManager.close();
        }
    }

    public void delete() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        System.out.println("Enter Book ID");
        int bookId = Integer.parseInt(bf.readLine());
        transaction.begin();
//retrieve data
        Books books= entityManager.find(Books.class,bookId);
       if(books== null){
           System.out.println("Book not found");
       }else{
           //Delete Book
           entityManager.remove(books);
           System.out.println("Entry Deleted");
       }

        transaction.commit();
        entityManager.close();

    }


    public void greaterPrice() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<Books>  cq= cb.createQuery(Books.class);
        Root<Books> root= cq.from(Books.class);
        cq.select(root).where(cb.greaterThan(root.<Comparable>get("bookPrice"),400));

        Query query=entityManager.createQuery(cq);
        List<Books> books= query.getResultList();

         for (Books book : books){
             System.out.println(" Book Name :"+ book.getBookName() +" Price : "+ book.getBookPrice() );
         }
        entityManager.close();
    }
    public void priceRange() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<Books>  cq= cb.createQuery(Books.class);
        Root<Books> root= cq.from(Books.class);
        cq.select(root).where(cb.between(root.<Comparable>get("bookPrice"),400,700));

        Query query=entityManager.createQuery(cq);
        List<Books> books= query.getResultList();

        for (Books book : books){
            System.out.println(" Book Name :"+ book.getBookName() +" Price : "+ book.getBookPrice() );
        }
        entityManager.close();
    }
    public void groupByPublisher() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.print("Enter  publisher : ");
        String publisher = bf.readLine();
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<Books>  cq= cb.createQuery(Books.class);
        Root<Books> root= cq.from(Books.class);
        cq.select(root).where(cb.equal(root.<Comparable>get("publisherName"),publisher));

        Query query=entityManager.createQuery(cq);
        List<Books> books= query.getResultList();
        if (books.isEmpty()) {
            System.out.println("No books found .");
        } else {
            for (Books book : books) {
                System.out.println(" Book Name :" + book.getBookName() + " Price : " + book.getBookPrice() + " Publisher Name : "+book.getPublisherName()+ " Publisher Date : "+book.getPublishedDate());
            }
        }
        entityManager.close();
    }
    public void dateRange() throws ParseException {
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date lowerBound = new java.sql.Date(formatter.parse("2000-08-09").getTime());
            java.sql.Date upperBound = new java.sql.Date(formatter.parse("2023-09-15").getTime());

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Books> cq = cb.createQuery(Books.class);
            Root<Books> root = cq.from(Books.class);

            cq.select(root).where(cb.between(root.<java.sql.Date>get("publishedDate"), lowerBound, upperBound));

            TypedQuery<Books> query = entityManager.createQuery(cq);
            List<Books> books = query.getResultList();

            for (Books book : books) {
                System.out.println(" Book Name: " + book.getBookName() + " Published Date: " + book.getPublishedDate());
            }

            entityManager.close();
        }


    }

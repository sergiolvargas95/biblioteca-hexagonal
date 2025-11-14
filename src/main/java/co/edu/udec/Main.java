package co.edu.udec;

import co.edu.udec.domain.model.aggregates.Author;
import co.edu.udec.domain.model.aggregates.Book;
import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Email;
import co.edu.udec.domain.model.valueObjects.FullName;
import co.edu.udec.domain.model.valueObjects.Title;
import co.edu.udec.domain.repositories.AuthorRepository;
import co.edu.udec.domain.repositories.BookRepository;
import co.edu.udec.domain.repositories.UserRepository;

import co.edu.udec.infrastructure.config.DatabaseConfig;
import co.edu.udec.infrastructure.persistence.AuthorRepositoryImpl;
import co.edu.udec.infrastructure.persistence.BookRepositoryImpl;
import co.edu.udec.infrastructure.persistence.UserRepositoryImpl;

import java.time.LocalDateTime;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("===== 🧱 PRUEBA DE REPOSITORIOS DDD + SQL SERVER =====");

        // 1️⃣ Verificar conexión
        try {
            if (DatabaseConfig.getConnection() != null) {
                System.out.println("✅ Conexión a SQL Server exitosa");
            }
        } catch (Exception e) {
            System.err.println("❌ Error en conexión: " + e.getMessage());
            return;
        }

        // 2️⃣ Inicializar repositorios
        AuthorRepositoryImpl autorRepository = new AuthorRepositoryImpl();
        BookRepositoryImpl bookRepository = new BookRepositoryImpl();

        // 3️⃣ Crear un nuevo Autor
        Author autor = new Author(
                null,
                new FullName("Gabriel", "José", "García", "Márquez"),
                "Colombiano",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        autorRepository.save(autor);
        System.out.println("✅ Autor guardado correctamente en la base de datos.");

        // 4️⃣ Buscar el autor (puedes ajustar el ID según tu DB)
        Optional<Author> autorOpt = autorRepository.findById(1L);
        if (autorOpt.isPresent()) {
            Author autorEncontrado = autorOpt.get();
            System.out.println("🔹 Autor encontrado: " +
                    autorEncontrado.getFullName().getFirstName() + " " +
                    autorEncontrado.getFullName().getLastName());

            // 5️⃣ Crear un libro asociado a este autor
            Book libro = new Book(
                    null,
                    new Title("Cien años de soledad"),
                    autorEncontrado, // ✅ Autor asociado
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            bookRepository.save(libro);
            System.out.println("📚 Libro guardado correctamente en la base de datos.");

            // 6️⃣ Buscar el libro recién creado
            Optional<Book> libroOpt = bookRepository.findById(1L);
            libroOpt.ifPresent(b ->
                    System.out.println("🔹 Libro encontrado: " + b.getTitle().getValue())
            );

        } else {
            System.out.println("⚠️ No se encontró el autor con ID 1.");
        }

        System.out.println("===== ✅ PRUEBA FINALIZADA =====");
    }
}
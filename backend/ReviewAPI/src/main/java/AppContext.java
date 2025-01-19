import javax.sql.DataSource;

import java.util.Map;
import java.util.Optional;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dao.CategoryDAO;
import dao.ProductDAO;
import dao.ProductImageDAO;
import dao.ReviewDAO;
import dao.TagDAO;
import dao.UserDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.CategoryService;
import service.ProductImageService;
import service.ProductService;
import service.ReviewService;
import service.TagService;
import service.UserService;

class DependencyInjector {
	public static CategoryService newCategoryService(DataSource source) {
		return new CategoryService(new CategoryDAO(source));
	}

	public static ReviewService newReviewService(DataSource source) {
		return new ReviewService(new ReviewDAO(source));
	}

	public static ProductService newProductService(DataSource source) {
		return new ProductService(new ProductDAO(source));
	}

	public static TagService newTagService(DataSource source) {
		return new TagService(new TagDAO(source));
	}

	public static UserService newUserService(DataSource source) {
		return new UserService(new UserDAO(source));
	}

	public static ProductImageService newProductImageService(DataSource source) {
		return new ProductImageService(new ProductImageDAO(source));
	}
}

@WebListener
public class AppContext implements ServletContextListener {
	private static final String URL				= "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true";
	private static final String USER			= "root";
	private static final String DATABASE	= "ReviewAPI";

	private HikariConfig dataConfig;
	private HikariDataSource dataSource;

	// Create database connection and initialize services
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Retrieve env keys
		Map<String, String> envs = System.getenv();

		String DBPassword = Optional.ofNullable(envs.get("DB_PASS"))
			.filter(val -> val != null && !val.isBlank())
			.orElseThrow(() -> new RuntimeException("No database password was provided!"));

		String DBHost			= Optional.ofNullable(envs.get("DB_HOST"))
			.filter(val -> val != null && !val.isBlank())
			.orElseThrow(() -> new RuntimeException("No database host was provided!"));

		String DBPort		= Optional.ofNullable(envs.get("PORT_MYSQL"))
			.filter(val -> val != null && !val.isBlank())
			.orElseThrow(() -> new RuntimeException("No database port was provided!"));

		Integer DBPoolSize = Optional.ofNullable(envs.get("DB_POOL"))
			.map((id) -> {
				try {
					return Integer.parseInt(id);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Given DB_POOL is not a valid number!", e);
				}
			})
			.orElseThrow(() -> new RuntimeException("No database password were provided!"));

		dataConfig = new HikariConfig();
			dataConfig.setJdbcUrl(String.format(URL, DBHost, DBPort, DATABASE));
			dataConfig.setUsername(USER);
			dataConfig.setPassword(DBPassword);
			dataConfig.setMaximumPoolSize(DBPoolSize);

		dataSource = new HikariDataSource(dataConfig);

		// Store the service in the ServletContext
		ServletContext context = sce.getServletContext();
		context.setAttribute("dataSource", dataSource);
		context.setAttribute("categoryService", DependencyInjector.newCategoryService(dataSource));
		context.setAttribute("productService", DependencyInjector.newProductService(dataSource));
		context.setAttribute("tagService", DependencyInjector.newTagService(dataSource));
		context.setAttribute("userService", DependencyInjector.newUserService(dataSource));
		context.setAttribute("reviewService", DependencyInjector.newReviewService(dataSource));
		context.setAttribute("productImageService", DependencyInjector.newProductImageService(dataSource));

		// Set the session cookie properties
		// N.B.: The release application WOULD require SetSecure=true
		// as browsers DISALLOWS non-secure connections with cross-site (samesite: none) connections 
		// 
		// context.getSessionCookieConfig().setAttribute("SameSite", "None");
		// context.getSessionCookieConfig().setSecure(false);
		// context.getSessionCookieConfig().setHttpOnly(true);
		System.out.println("Connection initialized");
	}
	
	// Drop database connection
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if (dataSource != null) { dataSource.close(); }
	}
}

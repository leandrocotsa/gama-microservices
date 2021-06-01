package com.thesis.gamamicroservices.productservice;



/**
@Configuration
public class InitDBtest {

    private static final Logger log = LoggerFactory.getLogger(InitDBtest.class);

    @Bean
    CommandLineRunner initDatabase(ProductService productService, BrandService brandService, CategoryService categoryService, SpecificationService specificationService) {

        return args -> {
            log.info("Preloading ");


            specificationService.createSpecification("Tamanho");
            specificationService.createSpecification("Peso");

            brandService.createBrand("Sony");

            List<Specification> specifications1 = new ArrayList<>();
            specifications1.add(specificationService.findById(1));
            CategorySetDTO category1 = new CategorySetDTO("Consolas", specifications1);
            categoryService.createCategory(category1);

            List<Specification> specifications2 = new ArrayList<>();
            specifications2.add(specificationService.findById(1));
            CategorySetDTO category2 = new CategorySetDTO("Jogos PS4", specifications2);
            categoryService.createCategory(category2);



            productService.createProduct(new ProductSetDTO("PS4", "description", 250.0, 2f, 1, 1, new ArrayList<>()));
            productService.createProduct(new ProductSetDTO("PS5", "description", 499.0, 3f, 1, 1, new ArrayList<>()));


            productService.createProduct(new ProductSetDTO("Uncharted", "description", 19.00, 0.5f, 1, 2, new ArrayList<>()));
            productService.createProduct(new ProductSetDTO("The Last of Us", "description", 20.0, 0.4f, 1, 2, new ArrayList<>()));


        };
    }
}

**/
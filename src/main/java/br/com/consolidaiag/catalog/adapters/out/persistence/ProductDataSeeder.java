package br.com.consolidaiag.catalog.adapters.out.persistence;

import br.com.consolidaiag.catalog.application.ports.out.ProductRepositoryPort;
import br.com.consolidaiag.catalog.domain.Product;
import br.com.consolidaiag.catalog.domain.ProductCategory;
import br.com.consolidaiag.catalog.domain.UnitMeasure;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductDataSeeder implements ApplicationRunner {

    private final ProductRepositoryPort productRepository;

    public ProductDataSeeder(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    private record ProductSeed(
            String name,
            UnitMeasure unitMeasure,
            ProductCategory category
    ) {}

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        getInitialProducts().forEach(this::seedProductIfAbsent);
    }

    private List<ProductSeed> getInitialProducts() {
        return List.of(
                // Grãos e Cereais
                new ProductSeed("Arroz Agulhinha Tipo 1", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Feijão Carioca", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Feijão Preto", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Farinha de Trigo", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Farinha de Mandioca", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Fubá", UnitMeasure.KG, ProductCategory.GRAOS_E_CEREAIS),
                new ProductSeed("Aveia em Flocos", UnitMeasure.PCT, ProductCategory.GRAOS_E_CEREAIS),

                // Proteínas e Carnes
                new ProductSeed("Peito de Frango sem Osso", UnitMeasure.KG, ProductCategory.PROTEINAS_CARNES),
                new ProductSeed("Coxa e Sobrecoxa de Frango", UnitMeasure.KG, ProductCategory.PROTEINAS_CARNES),
                new ProductSeed("Carne Moída (Patinho)", UnitMeasure.KG, ProductCategory.PROTEINAS_CARNES),
                new ProductSeed("Acém Bovino", UnitMeasure.KG, ProductCategory.PROTEINAS_CARNES),
                new ProductSeed("Filé de Tilápia", UnitMeasure.KG, ProductCategory.PROTEINAS_CARNES),
                new ProductSeed("Ovos Brancos (Pente 30 un)", UnitMeasure.PCT, ProductCategory.PROTEINAS_CARNES),

                // Hortifrúti e Frescos
                new ProductSeed("Batata Inglesa", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Cebola", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Tomate", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Alho", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Cenoura", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Alface", UnitMeasure.UN, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Couve", UnitMeasure.UN, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Banana Nanica", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),
                new ProductSeed("Maçã", UnitMeasure.KG, ProductCategory.HORTIFRUTI_FRESCOS),

                // Laticínios e Derivados
                new ProductSeed("Leite Integral UHT", UnitMeasure.L, ProductCategory.LATICINIOS_DERIVADOS),
                new ProductSeed("Manteiga sem Sal", UnitMeasure.UN, ProductCategory.LATICINIOS_DERIVADOS),
                new ProductSeed("Queijo Muçarela", UnitMeasure.KG, ProductCategory.LATICINIOS_DERIVADOS),
                new ProductSeed("Iogurte Natural", UnitMeasure.L, ProductCategory.LATICINIOS_DERIVADOS),

                // Mercearia e Panificação
                new ProductSeed("Macarrão Espaguete", UnitMeasure.PCT, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Pão Francês", UnitMeasure.KG, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Óleo de Soja", UnitMeasure.L, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Açúcar Cristal", UnitMeasure.KG, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Sal Refinado", UnitMeasure.KG, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Molho de Tomate", UnitMeasure.UN, ProductCategory.MERCEARIA_PANIFICACAO),
                new ProductSeed("Café em Pó", UnitMeasure.PCT, ProductCategory.MERCEARIA_PANIFICACAO)
        );
    }

    private void seedProductIfAbsent(ProductSeed seed) {
        if (!productRepository.existsByName(seed.name())) {
            Product product = Product.create(
                    seed.name(),
                    seed.unitMeasure(),
                    seed.category()
            );
            productRepository.save(product);
        }
    }
}

package com.example.bookportal.services;

import com.example.bookportal.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<Product>();
    private long ID = 0;

    {
        products.add(new Product(++ID, "Anna Karenina", "Leo Tolstoy", "Drama",
                "Set in 19th-century Russia, this novel revolves around the life " +
                        "of Anna Karenina, a high-society woman who, dissatisfied with her " +
                        "loveless marriage, embarks on a passionate affair with a charming " +
                        "officer named Count Vronsky. This scandalous affair leads to her " +
                        "social downfall, while parallel to this, the novel also explores " +
                        "the rural life and struggles of Levin, a landowner who seeks the " +
                        "meaning of life and true happiness. The book explores themes such " +
                        "as love, marriage, fidelity, societal norms, and the human quest " +
                        "for happiness."));
        products.add(new Product(++ID, "Pride and Prejudice", "Jane Austen", "Drama",
                "Set in early 19th-century England, this classic novel revolves around " +
                        "the lives of the Bennet family, particularly the five unmarried daughters. " +
                        "The narrative explores themes of manners, upbringing, morality, education, " +
                        "and marriage within the society of the landed gentry. It follows the romantic " +
                        "entanglements of Elizabeth Bennet, the second eldest daughter, who is intelligent, " +
                        "lively, and quick-witted, and her tumultuous relationship with the proud, wealthy, " +
                        "and seemingly aloof Mr. Darcy. Their story unfolds as they navigate societal " +
                        "expectations, personal misunderstandings, and their own pride and prejudice."));
    }

    public List<Product> listProducts() {
        return products;
    }

    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    }

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId() == id);
    }

    public Product getProductById(Long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}

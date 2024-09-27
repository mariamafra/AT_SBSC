package com.example.Q6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Flux<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Mono<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Mono<Produto> save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Mono<Produto> update(Long id, Produto produto) {
        return produtoRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setNome(produto.getNome());
                    existingProduct.setPreco(produto.getPreco());
                    return produtoRepository.save(existingProduct);
                });
    }

    public Mono<Void> deleteById(Long id) {
        return produtoRepository.deleteById(id);
    }
}

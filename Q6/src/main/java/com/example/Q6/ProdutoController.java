package com.example.Q6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Flux<Produto> findAllProdutos() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Produto> findProdutoById(@PathVariable Long id) {
        return produtoService.findById(id);
    }

    @PostMapping
    public Mono<Produto> saveProduto(@RequestBody Produto produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{id}")
    public Mono<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return produtoService.update(id, produto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduto(@PathVariable Long id) {
        return produtoService.deleteById(id);
    }
}

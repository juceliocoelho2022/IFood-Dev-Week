package br.com.jucelio.Ifood_Dev_Week.SacolaAPI.service.Impl;

import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.enumeration.FormaPagamento;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Item;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Restaurante;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Sacola;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.repository.ItemRepository;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.repository.ProdutoRepository;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.repository.SacolaRepository;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.resource.dto.ItemDto;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.service.SacolaService;
import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getSacolaId());

        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola esta fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esta produto não existe.");
                        }
                ))
                .build();
        ;
        List<Item> itensDaSacola = sacola.getItem();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possivel adicionar produtos de restaurante diferntes. Feche a sacola ou esvazie! .");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();

        for (Item itemDaSacola : itensDaSacola) {
            double valorTotalItem = itemDaSacola.getProduto().getValorUnitario()
                    * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe!");
                }
        );

    }

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItem().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola!");
        }

        FormaPagamento formaPagamento
                = numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);


    }
}

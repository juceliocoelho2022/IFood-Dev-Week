package br.com.jucelio.Ifood_Dev_Week.SacolaAPI.service;

import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Item;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Sacola;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.resource.dto.ItemDto;

public interface SacolaService {
    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);
}

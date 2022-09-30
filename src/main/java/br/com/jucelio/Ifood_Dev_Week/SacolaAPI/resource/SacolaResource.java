package br.com.jucelio.Ifood_Dev_Week.SacolaAPI.resource;

import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Item;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Sacola;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.resource.dto.ItemDto;
import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.service.SacolaService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Api(value="/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    private final SacolaService sacolaService;


    @PostMapping
    public Item incluirItenNaSacola(@RequestBody ItemDto itemDto) {
        return sacolaService.incluirItemNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable() Long id) {
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamento") int formaPagamento) {
        return sacolaService.fecharSacola(sacolaId, formaPagamento);
    }

}

package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CarroServiceTest {

    @Test
    public void saveCarro(){
        Carro carro = new Carro();
        carro.setModelo("modelo");
        carro.setAno(1990);
        carro.setId(1L);
        carro.setPreco(10000);

        assertEquals(carro.getAno() > 1980, true);
        assertEquals(carro.getAno() > 2026, false);
        assertEquals(carro.getPreco() > 0, true);
        assertEquals(carro.getId() > 0, true);
    }

}

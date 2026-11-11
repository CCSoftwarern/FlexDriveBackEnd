package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.CarroException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.model.Log;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    public CarroRepository carroRepository;

    @Autowired
    public LogService logService;

    public Carro save(Carro c) {
        Carro savedCarro = carroRepository.save(c);

        if(savedCarro.getPreco() >= 0){
            Log log = new Log();
            log.setHorario(LocalDate.now());
            log.setIdCarro(savedCarro.getId());
            log.setIdUsuario(1L);
            log.setOperacao("salvar carro");
            log.setParametros("Preco = " + c.getPreco());
            logService.save(log);
        }else{
            System.out.println("Erro ao salvar o carro: " + c);
        }

        return savedCarro;
    }

    // Novo método para deletar por ID
    public void deleteById(Long id) {
        if(id <= 0){
            throw new CarroException("O ID do carro deve ser maior que zero. ID fornecido: " + id);
        }
        carroRepository.deleteById(id);
    }

    // Novo método para pesquisar por ID
    public Optional<Carro> findById(Long id) {
        if(id <= 0){
            throw new CarroException("O ID do carro deve ser maior que zero. ID fornecido: " + id);
        }
        return carroRepository.findById(id);
    }

    // Novo método para listar todos os carros
    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    public Optional<Carro> findByModelo(String modelo) {
        validarModelo(modelo);
        return carroRepository.findFirstByModelo(modelo);
    }

    public Carro saveFromLegacy(String modelo, double preco) {
        Carro carro = new Carro();
        carro.setModelo(modelo);
        carro.setAno(LocalDate.now().getYear());
        carro.setPreco(preco);
        return save(carro);
    }

    public Carro updateByModelo(String modelo, double preco) {
        Carro carro = localizarCarroPorModelo(modelo);
        validarPreco(preco);
        carro.setPreco(preco);
        return carroRepository.save(carro);
    }

    public Carro deleteByModelo(String modelo) {
        Carro carro = localizarCarroPorModelo(modelo);
        carroRepository.delete(carro);
        return carro;
    }

    // Método para atualizar (usa o save existente, mas pode ser renomeado se preferir)
    public Carro update(Carro c) {
        if (c.getId() == null) {
            throw new CarroException("O ID do carro para atualização não pode ser nulo.");
        }
        if (!carroRepository.existsById(c.getId())) {
            throw new CarroException("Carro com ID " + c.getId() + " não encontrado para atualização.");
        }
        validarModelo(c.getModelo());              // Valida o modelo antes de atualizar
        validarPreco(c.getPreco());
        validarCor(c.getCor());
        validarQuilometragem(c.getQuilometragem());
        validarTipoCombustivel(c.getTipoCombustivel());
        validarTipoTransmissao(c.getTipoTransmissao());
        validarSituacao(c.getSituacao());
        validarDescricao(c.getDescricao());
        validarFotografiaPrincipal(c.getFotografiaPrincipal());
        return carroRepository.save(c);            // Retorna o carro salvo para feedback
    }

    // Validação do modelo
    private void validarModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new CarroException("O modelo do carro não pode estar vazio.");
        }
        if (modelo.length() >= 5) {
            throw new CarroException("O modelo do carro deve ter menos de 5 caracteres. Tamanho atual: " + modelo.length());
        }
    }

    private void validarPreco(double preco) {
        if (preco < 0) {
            throw new CarroException("O preço do carro não pode ser negativo. Valor fornecido: " + preco);
        }
    }

    private void validarCor(String cor) {
        if (cor == null || cor.trim().isEmpty()) {
            throw new CarroException("A cor do carro não pode estar vazia.");
        }
    }

    private void validarQuilometragem(int quilometragem) {
        if (quilometragem < 0) {
            throw new CarroException("A quilometragem não pode ser negativa. Valor fornecido: " + quilometragem);
        }
    }

    private void validarTipoCombustivel(String tipoCombustivel) {
        if (tipoCombustivel == null || tipoCombustivel.trim().isEmpty()) {
            throw new CarroException("O tipo de combustível não pode estar vazio.");
        }
    }

    private void validarTipoTransmissao(String tipoTransmissao) {
        if (tipoTransmissao == null || tipoTransmissao.trim().isEmpty()) {
            throw new CarroException("O tipo de transmissão não pode estar vazio.");
        }
    }

    private void validarSituacao(String situacao) {
        if (situacao == null || situacao.trim().isEmpty()) {
            throw new CarroException("A situação do veículo não pode estar vazia.");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao != null && descricao.length() > 1000) {
            throw new CarroException("A descrição deve ter no máximo 1000 caracteres. Tamanho atual: " + descricao.length());
        }
    }

    private void validarFotografiaPrincipal(String fotografiaPrincipal) {
        if (fotografiaPrincipal != null && fotografiaPrincipal.length() > 255) {
            throw new CarroException("O caminho da fotografia deve ter no máximo 255 caracteres. Tamanho atual: " + fotografiaPrincipal.length());
        }
    }

    private Carro localizarCarroPorModelo(String modelo) {
        validarModelo(modelo);
        return carroRepository.findFirstByModelo(modelo)
                .orElseThrow(() -> new CarroException("Carro com modelo " + modelo + " não encontrado."));
    }

    public List<Carro> findByMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new CarroException("A marca não pode estar vazia.");
        }
        return carroRepository.findByMarcaIgnoreCase(marca.trim());
    }

    public List<Carro> findBySituacao(String situacao) {
        if (situacao == null || situacao.trim().isEmpty()) {
            throw new CarroException("A situação não pode estar vazia.");
        }
        return carroRepository.findBySituacaoIgnoreCase(situacao.trim());
    }
}
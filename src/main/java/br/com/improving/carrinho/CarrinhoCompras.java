package br.com.improving.carrinho;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {


	private ArrayList<Item> itens;
	private BigDecimal valorTotal;

	public CarrinhoCompras() {
		this.itens = new ArrayList<>();
		this.valorTotal = BigDecimal.ZERO;
	}

	public CarrinhoCompras(Produto produto, BigDecimal valorUnitario, int quantidade) {
		this();
		this.adicionarItem(produto, valorUnitario, quantidade);
	}



    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param produto
     * @param valorUnitario
     * @param quantidade
     */
    public void adicionarItem(Produto produto, BigDecimal valorUnitario, int quantidade) {
		if (quantidade <= 0 || valorUnitario.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Quantidade e valor unitário devem ser maiores que zero.");
		}

		for (Item item : this.itens) {
			if (item.getProduto().equals(produto)) {
				if (item.getValorUnitario().compareTo(valorUnitario) != 0) {
					item.setValorUnitario(valorUnitario);
				}
				item.setQuantidade(item.getQuantidade() + quantidade);
				this.setValorTotal(this.getValorTotal().add(valorUnitario.multiply(BigDecimal.valueOf(quantidade))));
				return;
			}
		}
		this.itens.add(new Item(produto, valorUnitario, quantidade));

    }

	public void recalcularValorTotal() {
		BigDecimal novoValorTotal = BigDecimal.ZERO;
		for(Item item : this.itens) {
			novoValorTotal = novoValorTotal.add(item.getValorTotal());
		}
		this.setValorTotal(novoValorTotal);
	}

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param produto
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(Produto produto) {
		if (this.itens.removeIf(item -> item.getProduto().equals(produto))){
			this.recalcularValorTotal();
			return true;

		};
		return false;
    }

    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na 
     * coleção, em que zero representa o primeiro item.
     *
     * @param posicaoItem
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(int posicaoItem) {
		if(posicaoItem < 0 || posicaoItem >= this.itens.size()) {
			return false;
		}
		this.itens.remove(posicaoItem);
		this.recalcularValorTotal();
		return true;
    }

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTotal() {
		return valorTotal;
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return itens
     */
    public Collection<Item> getItens() {
		return this.itens;

    }
}
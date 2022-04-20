package org.azgraal.data;

import org.azgraal.excecoes.AnoInvalidoExcecao;
import org.azgraal.excecoes.DiaInvalidoExcecao;
import org.azgraal.excecoes.MesInvalidoExcecao;

import java.util.Calendar;

/**
 * Representa uma data através do dia, mês e ano.
 *
 * @author ISEP-DEI-PPROG, fork por Jorge "Azgraal" Simões
 */
public class Data implements Comparable<Data> {

    /**
     * O ano da data.
     */
    private int ano;

    /**
     * O mês da data.
     */
    private int mes;

    /**
     * O dia da data.
     */
    private int dia;

    /**
     * O ano por omissão.
     */
    private static final int ANO_POR_OMISSAO = 1;

    /**
     * O mês por omissão.
     */
    private static final int MES_POR_OMISSAO = 1;

    /**
     * O dia por omissão.
     */
    private static final int DIA_POR_OMISSAO = 1;

    /**
     * Nomes dos dias da semana.
     */
    private static String[] nomeDiaDaSemana = {"Domingo", "Segunda-feira",
            "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"};

    /**
     * Número de dias de cada mês do ano.
     */
    private static int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};

    /**
     * Nomes dos meses do ano.
     */
    private static String[] nomeMes = {"Inválido", "Janeiro", "Fevereiro",
            "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
            "Outubro", "Novembro", "Dezembro"};

    /**
     * Constrói uma instância de Data recebendo o ano, o mês e o dia.
     *
     * @param ano o ano da data.
     * @param mes o mês da data.
     * @param dia o dia da data.
     */
    public Data(int ano, int mes, int dia, boolean verificacao) throws MesInvalidoExcecao, AnoInvalidoExcecao, DiaInvalidoExcecao {
        if (verificacao){
            setData(ano, mes, dia);
        } else {
            this.ano = ano;
            this.mes = mes;
            this.dia = dia;
        }
    }

    /**
     * Constrói uma instância de Data com a data por omissão.
     */
    public Data() throws MesInvalidoExcecao, AnoInvalidoExcecao, DiaInvalidoExcecao {
        setData(ANO_POR_OMISSAO, MES_POR_OMISSAO, DIA_POR_OMISSAO);
    }

    /**
     * Constrói uma instância de Data com as mesmas caraterísticas da data
     * recebida por parâmetro.
     *
     * @param outraData a data com as características a copiar.
     */
    public Data(Data outraData) {
        ano = outraData.ano;
        mes = outraData.mes;
        dia = outraData.dia;
    }

    /**
     * Devolve o ano da data.
     *
     * @return ano da data
     */
    public int getAno() {
        return ano;
    }

    /**
     * Devolve o mês da data.
     *
     * @return mês da data.
     */
    public int getMes() {
        return mes;
    }

    /**
     * Devolve o dia da data.
     *
     * @return dia da data.
     */
    public int getDia() {
        return dia;
    }

    /**
     * Modifica o ano, o mês e o dia da data.
     *
     * @param ano o novo ano da data.
     * @param mes o novo mês da data.
     * @param dia o novo dia da data.
     */
    public void setData(int ano, int mes, int dia) throws AnoInvalidoExcecao, MesInvalidoExcecao, DiaInvalidoExcecao {
        setAno(ano);
        setMes(mes);
        setDia(dia);
    }

    public void setAno(int ano) throws AnoInvalidoExcecao, MesInvalidoExcecao, DiaInvalidoExcecao {
        if (ano <= 0 || ano > dataAtual().getAno()){
            throw new AnoInvalidoExcecao("Data com ano inválido");
        } else {
            this.ano = ano;
        }
    }

    public void setMes(int mes) throws MesInvalidoExcecao {
        if (mes <= 0 || mes > 12) {
            throw new MesInvalidoExcecao("Data com mês inválido");
        } else {
            this.mes = mes;
        }
    }

    public void setDia(int dia) throws DiaInvalidoExcecao {
        int diasMax = diasPorMes[mes];
        if (isAnoBissexto(ano)) {
            diasMax++;
        }
        if (dia <= 0 || dia > diasMax){
            throw new DiaInvalidoExcecao("Data com dia inválido");
        } else {
            this.dia = dia;
        }
    }

    /**
     * Devolve a descrição textual da data no formato: diaDaSemana, dia de mês
     * de ano.
     *
     * @return caraterísticas da data.
     */
    @Override
    public String toString() {
        return String.format("%s, %d de %s de %d", diaDaSemana(), dia,
                nomeMes[mes], ano);
    }

    /**
     * Devolve a data no formato:%04d/%02d/%02d.
     *
     * @return caraterísticas da data.
     */
    public String toAnoMesDiaString() {
        return String.format("%04d/%02d/%02d", ano, mes, dia);
    }

    /**
     * Compara a data com o objeto recebido.
     *
     * @param outroObjeto o objeto a comparar com a data.
     * @return true se o objeto recebido representar uma data equivalente à
     *         data. Caso contrário, retorna false.
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Data outraData = (Data) outroObjeto;
        return ano == outraData.ano && mes == outraData.mes
                && dia == outraData.dia;
    }

    /**
     * Compara a data com a outra data recebida por parâmetro.
     *
     * @param outraData a data a ser comparada.
     * @return o valor 0 se a outraData recebida é igual à data; o valor -1 se
     *         a outraData for posterior à data; o valor 1 se a outraData for
     *         anterior à data.
     */
    @Override
    public int compareTo(Data outraData) {
        return (outraData.isMaior(this)) ? -1 : (isMaior(outraData)) ? 1 : 0;
    }

    /**
     * Devolve o dia da semana da data.
     *
     * @return dia da semana da data.
     */
    public String diaDaSemana() {
        int totalDias = contaDias();
        totalDias = totalDias % 7;

        return nomeDiaDaSemana[totalDias];
    }


    /**
     * Devolve true se a data for maior do que a data recebida por parâmetro. Se
     * a data for menor ou igual à data recebida por parâmetro, devolve false.
     *
     * @param outraData a outra data com a qual se compara a data.
     * @return true se a data for maior do que a data recebida por parâmetro,
     *         caso contrário devolve false.
     */
    public boolean isMaior(Data outraData) {
        int totalDias = contaDias();
        int totalDias1 = outraData.contaDias();

        return totalDias > totalDias1;
    }

    /**
     * Devolve a diferença em número de dias entre a data e a data recebida por
     * parâmetro.
     *
     * @param outraData a outra data com a qual se compara a data para calcular
     *                  a diferença do número de dias.
     * @return diferença em número de dias entre a data e a data recebida por
     *         parâmetro.
     */
    public int diferenca(Data outraData) {
        int totalDias = contaDias();
        int totalDias1 = outraData.contaDias();

        return Math.abs(totalDias - totalDias1);
    }

    /**
     * Devolve a diferença em número de dias entre a data e a data recebida por
     * parâmetro com ano, mês e dia.
     *
     * @param ano o ano da data com a qual se compara a data para calcular a
     *            diferença do número de dias.
     * @param mes o mês da data com a qual se compara a data para calcular a
     *            diferença do número de dias.
     * @param dia o dia da data com a qual se compara a data para calcular a
     *            diferença do número de dias.
     * @return diferença em número de dias entre a data e a data recebida por
     *         parâmetro com ano, mês e dia.
     */
    public int diferenca(int ano, int mes, int dia) throws MesInvalidoExcecao, AnoInvalidoExcecao, DiaInvalidoExcecao {
        int totalDias = contaDias();
        Data outraData = new Data(ano, mes, dia, false);
        int totalDias1 = outraData.contaDias();

        return Math.abs(totalDias - totalDias1);
    }

    /**
     * Devolve true se o ano passado por parâmetro for bissexto. Se o ano
     * passado por parâmetro não for bissexto, devolve false.
     *
     * @param ano o ano a validar.
     * @return true se o ano passado por parâmetro for bissexto, caso contrário
     *         devolve false.
     */
    public static boolean isAnoBissexto(int ano) {
        return ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0;
    }

    /**
     * Devolve a data atual do sistema.
     *
     * @return a data atual do sistema.
     */
    public static Data dataAtual() throws MesInvalidoExcecao, AnoInvalidoExcecao, DiaInvalidoExcecao {
        Calendar hoje = Calendar.getInstance();
        int ano = hoje.get(Calendar.YEAR);
        int mes = hoje.get(Calendar.MONTH) + 1;    // janeiro é representado por 0
        int dia = hoje.get(Calendar.DAY_OF_MONTH);
        return new Data(ano, mes, dia, false);
    }

    /**
     * Devolve o número de dias desde o dia 1/1/1 até à data.
     *
     * @return número de dias desde o dia 1/1/1 até à data.
     */
    private int contaDias() {
        int totalDias = 0;

        for (int i = 1; i < ano; i++) {
            totalDias += isAnoBissexto(i) ? 366 : 365;
        }
        for (int i = 1; i < mes; i++) {
            totalDias += diasPorMes[i];
        }
        totalDias += (isAnoBissexto(ano) && mes > 2) ? 1 : 0;
        totalDias += dia;

        return totalDias;
    }
}

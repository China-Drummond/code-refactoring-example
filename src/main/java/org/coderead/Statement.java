package org.coderead;

import org.coderead.calculator.AbstractPerfCalculator;
import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * 客户服务类
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Statement {
    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder stringBuilder = new StringBuilder(formatShowResult());
        stringBuilder.append(getPerfStat());
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(getTotalAmount())));
        stringBuilder.append(String.format("You earned %s credits\n", getVolumeCredits()));
        return stringBuilder.toString();
    }

    private String formatShowResult() {
        return String.format("Statement for %s", invoice.getCustomer());
    }

    private StringBuilder getPerfStat() {
        StringBuilder perfStat = new StringBuilder();
        for (Performance performance : invoice.getPerformances()) {
            perfStat.append(formatPerfStat(performance, plays.get(performance.getPlayId()),
                getPerfAmount(performance, plays.get(performance.getPlayId()).getType())));
        }
        return perfStat;
    }

    private int getTotalAmount() {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            totalAmount += getPerfAmount(performance, plays.get(performance.getPlayId()).getType());
        }
        return totalAmount;
    }

    private int getVolumeCredits() {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            volumeCredits += getPerfCredits(performance, plays.get(performance.getPlayId()).getType());
        }
        return volumeCredits;
    }

    private String formatPerfStat(Performance performance, Play play, int thisAmount) {
        return String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(thisAmount), performance.getAudience());
    }

    private double getPerfCredits(Performance performance, String playType) {
        return AbstractPerfCalculator.of(playType).getCredits(performance);
    }

    private int getPerfAmount(Performance performance, String playType) {
        return AbstractPerfCalculator.of(playType).getAmount(performance);
    }

    private String formatUSD(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }
}

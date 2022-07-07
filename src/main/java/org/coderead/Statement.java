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

    private final Invoice invoice;
    private final Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder stringBuilder = getResult();
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(getTotalAmount())));
        stringBuilder.append(String.format("You earned %s credits\n", getVolumeCredits()));
        return stringBuilder.toString();
    }

    private StringBuilder getResult() {
        String result = String.format("Statement for %s", invoice.getCustomer());
        StringBuilder stringBuilder = new StringBuilder(result);
        for (Performance performance : invoice.getPerformances()) {
            stringBuilder.append(formatPerf(performance, plays.get(performance.getPlayId())));
        }
        return stringBuilder;
    }

    private int getTotalAmount() {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            totalAmount += getPerfAmount(performance, plays.get(performance.getPlayId()));
        }
        return totalAmount;
    }

    private int getVolumeCredits() {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            volumeCredits += getPerfCredits(performance, plays.get(performance.getPlayId()));
        }
        return volumeCredits;
    }

    private String formatPerf(Performance performance, Play play) {
        return String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(getPerfAmount(performance, play)),
            performance.getAudience());
    }

    private double getPerfCredits(Performance performance, Play play) {
        return AbstractPerfCalculator.of(play.getType()).getCredits(performance);
    }

    private int getPerfAmount(Performance performance, Play play) {
        return AbstractPerfCalculator.of(play.getType()).getAmount(performance);
    }

    private String formatUSD(int thisAmount) {
        Locale locale = new Locale("en", "US");
        return NumberFormat.getCurrencyInstance(locale).format(thisAmount / 100);
    }
}

package org.coderead.calculator;

import org.coderead.model.Performance;

public class ComedyCalculator extends AbstractPerfCalculator {

    @Override
    public double getCredits(Performance performance) {
        return Math.floor(performance.getAudience() / 5) + Math.max(performance.getAudience() - 30, 0);
    }

    @Override
    public int getAmount(Performance performance) {
        int thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 * (performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }
}
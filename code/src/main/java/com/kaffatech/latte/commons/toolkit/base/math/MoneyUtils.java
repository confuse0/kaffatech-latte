
package com.kaffatech.latte.commons.toolkit.base.math;

import com.kaffatech.latte.commons.bean.model.num.Money;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.math.BigDecimal;

/**
 * @author zhen.ling
 */
public class MoneyUtils {

    public static Money createMoney(String yuan) {
        if (StringUtils.isEmpty(yuan)) {
            return null;
        }
        return new Money(yuan);
    }

    public static Money createMoney(BigDecimal money) {
        if (money == null) {
            return null;
        }
        return new Money(money);
    }

    public static Money createMoney(long fen) {
        return new Money(fen);
    }

    public static boolean isNatural(Money money) {
        return (money.getValue().compareTo(BigDecimal.ZERO) >= 1) ? true : false;
    }

    public static BigDecimal toBigDecimal(Money money) {
        if (money == null) {
            return null;
        }
        return money.getValue();
    }

    /**
     * 取绝对值
     *
     * @param money
     * @return
     */
    public static Money abs(Money money) {
        if (isNatural(money)) {
            return money;
        }
        return money.multiply(-1);
    }

    public static Money add(Money a, Money b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("parameter is invalid!");
        }
        return a.add(b);
    }

    public static Money subract(Money a, Money b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("parameter is invalid!");
        }
        return a.subtract(b);
    }

    public static Money multiply(Money money, int times) {
        return money.multiply(times);
    }

    public static Money multiply(Money money, BigDecimal num) {
        return money.multiply(num);
    }

    public static Money reverse(Money money) {
        return money.reverse();
    }

    public static int compare(Money a, Money b) {
        if (a == null || b == null) {
            throw new NullPointerException("money is not null!");
        }
        return a.compareTo(b);
    }

    public static boolean equal(Money a, Money b) {
        if (a == null || b == null) {
            throw new NullPointerException("money is not null!");
        }
        return a.compareTo(b) == 0;
    }

}

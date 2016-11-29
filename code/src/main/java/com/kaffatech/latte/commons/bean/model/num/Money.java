package com.kaffatech.latte.commons.bean.model.num;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.bean.model.type.Currency;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import java.math.BigDecimal;

/**
 * @author zhen.ling
 */
public class Money extends BaseBean implements Comparable<Money> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 金额
     */
    public static final Money ZERO = new Money();

    /**
     * 币种
     */
    private Currency cur = Currency.CNY;

    /**
     * 数值（分）
     */
    private final BigDecimal value;

    public Money() {
        this(BigDecimal.ZERO);
    }

    public Money(long fen) {
        long yuan = fen / 100L;
        long f = fen % 100L;
        BigDecimal number = null;
        if (f == 0L) {
            // 没有小数
            number = new BigDecimal(yuan);
        } else {
            number = new BigDecimal(yuan + "." + StringUtils.leftPad(((Long) f).toString(), 2, '0'));
        }

        if (number.scale() > 2) {
            throw new IllegalArgumentException("money scale is invalid:" + number.scale());
        }
        this.value = number;
    }

    public Money(BigDecimal yuan) {
        if (yuan.scale() > 2) {
            throw new IllegalArgumentException("money scale is invalid:" + yuan.scale());
        }
        this.value = yuan;
    }

    public Money(String yuan) {
        if (StringUtils.isEmpty(yuan)) {
            throw new NullPointerException("money string is not null!");
        }

        BigDecimal number = null;
        try {
            number = new BigDecimal(yuan);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        if (number.scale() > 2) {
            throw new IllegalArgumentException("money scale is invalid:" + number.scale());
        }
        this.value = number;
    }

    public long longValue() {
        return this.value.longValue();
    }

    public Currency getCur() {
        return cur;
    }

    public void setCur(Currency cur) {
        this.cur = cur;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money add(Money money) {
        return new Money(value.add(money.getValue()));
    }

    public Money subtract(Money money) {
        return new Money(value.subtract(money.getValue()));
    }

    public Money multiply(int times) {
        return new Money(value.multiply(new BigDecimal(times)));
    }

    public Money multiply(BigDecimal num) {
        return new Money(value.multiply(num));
    }


    public Money reverse() {
        return multiply(-1);
    }

    public String toString() {
        return value.toPlainString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Money o) {
        return (value).compareTo(o.getValue());
    }

    public boolean equals(Money o) {
        return (value).equals(o.getValue());
    }

    public int hashCode() {
        return (value).hashCode();
    }

    public static void main(String[] args) {
        Money m = new Money("12.39");
        System.out.println(m);
        System.out.println(m.multiply(2));
        System.out.println(m.add(m));
        System.out.println(m.subtract(m));
        System.out.println(m.subtract(m));
        System.out.println(m);

        m = new Money("-0.36");
        System.out.println(m);
        System.out.println(m.multiply(2));
        System.out.println(m.add(m));
        System.out.println(m.subtract(m));
        System.out.println(m.subtract(m));
        System.out.println(m);
    }

}


package com.kaffatech.latte.commons.toolkit.base.math;

import java.math.BigDecimal;

import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhen.ling
 *
 */
public abstract class NumberUtils extends
		org.apache.commons.lang3.math.NumberUtils {

	private static final Character[] RADIX_62 = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};

	public static BigDecimal createBigDecimal(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new BigDecimal(value);
	}

	public static String decimalToString(BigDecimal decimal) {
		if (decimal == null) {
			return null;
		}
		return decimal.toPlainString();
	}

	public static int halfUp(double d) {
		BigDecimal b = new BigDecimal(d).setScale(0, BigDecimal.ROUND_HALF_UP);
		return b.intValue();
	}

	public static int down(double d) {
		BigDecimal b = new BigDecimal(d).setScale(0, BigDecimal.ROUND_DOWN);
		return b.intValue();
	}

	public static int up(double d) {
		BigDecimal b = new BigDecimal(d).setScale(0, BigDecimal.ROUND_UP);
		return b.intValue();
	}

	public static String format(Integer money) {
		if (money == null) {
			return null;
		}

		String moneyStr = String.valueOf(money / 100);
		int rem = money % 100;
		if (rem > 0) {
			moneyStr = moneyStr + "." + String.valueOf(rem);
		}
		return moneyStr;
	}

	public static Integer parseInt(String moneyStr) {
		if (StringUtils.isEmpty(moneyStr)) {
			return null;
		}

		int intMoney = 0;
		BigDecimal b = new BigDecimal(moneyStr);
		b = b.setScale(2, BigDecimal.ROUND_DOWN); // 小数位 直接舍去
		b = b.multiply(new BigDecimal(100));

		intMoney = (int) b.toBigInteger().longValue();

		return intMoney;
	}

	public static boolean isPositiveNum(Long num) {
		if (null != num && num > 0L) {
			return true;
		}
		return false;
	}

	public static boolean isPositiveNum(Integer num) {
		if (null != num && num > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNatural(Long num) {
		if (null != num && num >= 0L) {
			return true;
		}
		return false;
	}

	public static boolean isNatural(Integer num) {
		if (null != num && num >= 0) {
			return true;
		}
		return false;
	}

	public static String to62String(long value) {
		if (value < 0L) {
			//负数处理
			return "-" + to62String(0L - value);
		}
		long n = value % 62L;
		if (value < 62) {
			return String.valueOf(RADIX_62[(int)n]);
		}
		return to62String(value / 62L) + RADIX_62[(int)n];
	}

	public static long from62String(String value) {
		if (value.startsWith("-")) {
			// 负数处理
			return -1L * from62String(value.substring(1));
		}

		char[] valueArray = value.toCharArray();

		int x = valueArray.length - 1;
		long n = 0;
		for (char c : valueArray) {
			int idx = CollectionUtils.search(RADIX_62, c);
			long pow = (long) Math.pow(62, x);
			n = n + (idx * pow);
			--x;
		}
		return n;
	}

	public static void main(String[] args) {
		System.out.println(CollectionUtils.search(RADIX_62, 'Z'));
		long[] array = new long[] {0L, 1L, 61L, 62L, 63L, 12089430192L};
		for (long i : array) {
			System.out.println(to62String(i));
			System.out.println(from62String(to62String(i)));
		}
	}
}
